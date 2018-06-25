package com.wardziniak.kafka.rest.app.storage

import com.wardziniak.kafka.rest.app.config.KafkaConfig
import org.apache.kafka.common.config.AbstractConfig
import org.apache.kafka.streams.StreamsConfig
import java.util.{Properties, HashMap => JHashMap, Map => JMap}

import org.apache.kafka.clients.producer.ProducerConfig

/**
  * Created by wardziniak on 24.06.18.
  */
trait KafkaConfigurationBuilder {
  def build(): Properties
}

case class KafkaStreamConfigurationBuilder(kafkaConfig: KafkaConfig) extends KafkaConfigurationBuilder {
  override def build(): Properties = {
    val props = new Properties()
    val streamsConfig = kafkaConfig.streams
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, streamsConfig.applicationId)
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.common.bootstrapServer)
    val config = new StreamsConfig(props)
    props
  }
}

case class KafkaProducerConfigurationBuilder(kafkaConfig: KafkaConfig) extends KafkaConfigurationBuilder {
  override def build(): Properties = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.common.bootstrapServer)
    props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true)
    val config = new ProducerConfig(props)
    config.values()
    props
  }
}

