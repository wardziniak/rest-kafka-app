name := "rest-kafka-app"
organization := "com.wardziniak"

version := "1.0"

scalaVersion := "2.12.6"
lazy val root = (project in file(".")).enablePlugins(PlayScala)

import Dependencies._

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.0-M1" % Test
libraryDependencies ++= Seq(
  kafkaStreams,
  scalaLogging,
  pureConfig,
  avro4sCore
)

    