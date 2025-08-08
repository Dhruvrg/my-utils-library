name := "my-utils-library"
version := "0.1.0"
scalaVersion := "2.13.12"
organization := "com.github.Dhruvrg"

libraryDependencies ++= Seq(
  "com.aerospike" % "aerospike-client" % "8.0.0",

  "org.slf4j" % "slf4j-simple" % "2.0.17",
  "com.typesafe" % "config" % "1.4.4",

  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-stream" % "2.8.8",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.8",

  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",

)

// Mark this project as a library (not an app)
publishMavenStyle := true