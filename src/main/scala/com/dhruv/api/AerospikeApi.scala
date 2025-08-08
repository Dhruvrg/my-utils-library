package com.dhruv.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.stream.Materializer
import spray.json._
import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import scala.jdk.CollectionConverters._

import com.aerospike.client._
import com.aerospike.client.query.Statement
import com.dhruv.AerospikeClientManager

// === Data Models ===
final case class SetInfo(name: String)
final case class NamespaceInfo(name: String, sets: Seq[SetInfo])
final case class RecordInfo(key: String, bins: Map[String, String])

// === JSON Formats ===
trait JsonSupport extends DefaultJsonProtocol {
  implicit val setInfoFormat = jsonFormat1(SetInfo)
  implicit val nsInfoFormat = jsonFormat2(NamespaceInfo)
  implicit val recordInfoFormat = jsonFormat2(RecordInfo)
}

// === Aerospike API Server ===
object AerospikeApi extends JsonSupport {

  def startServer(host: String = "0.0.0.0", port: Int = 8080): Unit = {
    implicit val system: ActorSystem = ActorSystem("aerospike-system")
    implicit val materializer: Materializer = Materializer(system)
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val client = AerospikeClientManager.client

    val route = concat(
      pathPrefix("api") {
        path("namespaces") {
          get {
            val node = client.getNodes.head
            val infoStr = Info.request(node, "namespaces")
            val namespaces = infoStr.split(";").toList.map(_.trim).filter(_.nonEmpty)
            complete(namespaces.map(ns => NamespaceInfo(ns, List.empty)))
          }
        } ~
          path("sets" / Segment) { namespace =>
            get {
              val infoStr = Info.request(client.getNodes.head, s"sets/$namespace")
              val sets = infoStr.split(";").toList
                .filter(_.contains("set="))
                .map(_.split(":").find(_.startsWith("set=")).getOrElse("set="))
                .map(_.replace("set=", ""))
                .filter(_.nonEmpty)
                .distinct
              complete(sets.map(SetInfo))
            }
          } ~
          path("records" / Segment / Segment) { (namespace, set) =>
            get {
              val stmt = new Statement()
              stmt.setNamespace(namespace)
              stmt.setSetName(set)

              val rs = client.query(null, stmt)
              val records = Iterator
                .continually(rs)
                .takeWhile(_.next())
                .map { rs =>
                  val rec = rs.getRecord
                  val keyObj = rs.getKey
                  val key = Option(keyObj.userKey).map(_.toString).getOrElse(s"[digest:${keyObj.digest.toString}]")
                  val bins = rec.bins.asScala.map { case (k, v) => k -> v.toString }.toMap
                  RecordInfo(key, bins)
                }
                .toVector

              rs.close()
              complete(records)
            }
          }
      },
      pathSingleSlash {
        getFromResource("public/index.html")
      },
      getFromResourceDirectory("public")
    )

    Http().newServerAt(host, port).bind(route)
    println(s"🚀 Server running at http://$host:$port/\nPress RETURN to stop...")
    StdIn.readLine()
    system.terminate()
  }

  def main(args: Array[String]): Unit = {
    startServer()
  }
}
