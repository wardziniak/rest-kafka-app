package com.wardziniak.kafka.rest.app.config

case class KafkaConfig(common: KafkaCommonConfig, streams: KafkaStreamsConfig, producer: ProducerConfig)


case class KafkaCommonConfig(bootstrapServer: String)

case class KafkaStreamsConfig(applicationId: String, topic: String, store: String)

case class ProducerConfig(topic: String)

object KafkaConfig {
  val KeyName = "kafka"
}



