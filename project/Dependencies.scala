import sbt._

object Dependencies {
  val kafkaStreams: ModuleID = "org.apache.kafka" % "kafka-streams" % "1.1.0"
  val scalaLogging: ModuleID = "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
  // Configuration parsing
  val pureConfig: ModuleID = "com.github.pureconfig" %% "pureconfig" % "0.7.2"

  val avro4sCore: ModuleID = "com.sksamuel.avro4s" %% "avro4s-core" % "1.7.0"
}

