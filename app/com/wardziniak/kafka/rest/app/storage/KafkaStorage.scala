package com.wardziniak.kafka.rest.app.storage

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import com.wardziniak.kafka.rest.app.config.KafkaConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.state.KeyValueStore
import org.apache.kafka.streams.{Consumed, KafkaStreams, StreamsBuilder}

/**
  * Created by wardziniak on 24.06.18.
  */

@Singleton
class KafkaStorage @Inject()(kafkaConfig: KafkaConfig) {

  val builder = new StreamsBuilder()

  buildPeopleStreamTopology(builder, kafkaConfig.streams.topic, kafkaConfig.streams.topic)

  val streams:KafkaStreams = new KafkaStreams(builder.build(), KafkaStreamConfigurationBuilder(kafkaConfig).build())
  streams.start()

  private def buildPeopleStreamTopology(builder: StreamsBuilder, topicName: String, storeName: String): StreamsBuilder = {
    val mal: Materialized[String, String, KeyValueStore[Bytes, Array[Byte]]] = Materialized.as[String, String, KeyValueStore[Bytes, Array[Byte]]](storeName)
    builder.table(topicName, Consumed.`with`(Serdes.String(), Serdes.String()), mal)
    builder
  }

  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    streams.close(10, TimeUnit.SECONDS)
  }))
}
