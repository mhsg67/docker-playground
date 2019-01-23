package ca.mhsg

import java.util
import java.util.Properties

import io.confluent.kafka.serializers.{AbstractKafkaAvroSerDeConfig, KafkaAvroDeserializer}
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConverters._

/**
  * kafka-avro-console-producer --broker-list kafka:9092 --topic test-topic --property parse.key=true --property key.separator=: --property key.schema='{"type":"string"}' --property value.schema='{"type":"record","name":"testRecord","fields":[{"name":"id","type":"long"}]}'
  * java -jar /Users/mohammad/Projects/docker-playground/target/scala-2.11/docker-playground-assembly-0.1.jar  tester kafka:9092 http://schema-registry:8082 test-topic
  *
  */
object Driver {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(ConsumerConfig.GROUP_ID_CONFIG, args(0))
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, args(1))
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[KafkaAvroDeserializer])
    props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, args(2))
    println(props)

    val consumer = new KafkaConsumer[String, GenericRecord](props)
    val topics = new util.ArrayList[String]()
    topics.add(args(3))
    consumer.subscribe(topics)


    while (true) {
      val results = consumer.poll(2000).asScala
      for (x <- results) {
        println(x.key() + ":" + x.value())
      }
    }
  }
}
