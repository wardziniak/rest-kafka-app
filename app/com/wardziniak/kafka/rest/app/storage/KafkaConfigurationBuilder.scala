package com.wardziniak.kafka.rest.app.storage

import com.wardziniak.kafka.rest.app.config.KafkaConfig
import org.apache.kafka.common.config.AbstractConfig
import org.apache.kafka.streams.StreamsConfig
import java.util.{Properties, HashMap => JHashMap, Map => JMap}

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer

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
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, new StringSerializer().getClass.getCanonicalName)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, new StringSerializer().getClass.getCanonicalName)
    val config = new StreamsConfig(props)
    props
  }
}

case class KafkaProducerConfigurationBuilder(kafkaConfig: KafkaConfig) extends KafkaConfigurationBuilder {
  override def build(): Properties = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.common.bootstrapServer)
    props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, java.lang.Boolean.TRUE)
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, new StringSerializer().getClass.getCanonicalName)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, new StringSerializer().getClass.getCanonicalName)
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, "1")
    val config = new ProducerConfig(props)
    config.values()
    props
  }
}

