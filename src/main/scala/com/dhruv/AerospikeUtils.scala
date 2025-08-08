package com.dhruv

import com.aerospike.client.policy.WritePolicy
import com.aerospike.client.{Bin, Key, Record, Value}

object AerospikeUtils {
  private val client = AerospikeClientManager.client

  val put: (String, String, String, Map[String, Any]) => Unit = {
    (namespace, set, key, bins) =>
      val binArray = bins.map { case (name, value) => new Bin(name, Value.get(value))}.toArray
      client.put(new WritePolicy(), new Key(namespace, set, key), binArray: _*)
  }

  val get: ((String, String, String) => Option[Record]) =
    (namespace, set, key) => Option(client.get(null, new Key(namespace, set, key)))

}
