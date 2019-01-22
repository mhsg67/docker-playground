name                            := "docker-playground"
version                         := "0.1"
scalaVersion                    := "2.11.12"
assembly / mainClass            := Some("ca.mhsg.Driver")
resolvers                       += "Confluent Repository" at "http://packages.confluent.io/maven/"

val confluentAvroSer  = "io.confluent"        %  "kafka-avro-serializer"        % "5.1.0"
val kafka             =  "org.apache.kafka"   %% "kafka"                        % "1.0.0"



lazy val root = (project in file(".")).settings(
  libraryDependencies ++= Seq(confluentAvroSer, kafka)
)