import sbt._

object Dependencies {

  val deps = Seq(
    "com.typesafe" % "config" % "1.3.1",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.apache.kafka" % "kafka-streams" % "0.10.2.0",
    "org.apache.kafka" % "kafka-clients" % "0.10.2.0",
    "org.scalaz" %% "scalaz-core" % "7.2.10",
    "$organization$" %% "avro4s" % "0.8",
    "org.scalatest" %% "scalatest" % "3.0.1" % Test,
    "com.madewithtea" %% "mockedstreams" % "1.2.0" % Test
  )
}