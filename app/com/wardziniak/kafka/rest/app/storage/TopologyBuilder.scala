package com.wardziniak.kafka.rest.app.storage

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import com.wardziniak.kafka.rest.app.config.KafkaConfig
import com.wardziniak.kafka.rest.app.model.Person
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder}

@Singleton
class TopologyBuilder @Inject()(kafkaConfig: KafkaConfig) extends AbstractTopologyBuilder {

  val builder = new StreamsBuilder()

  val streams:KafkaStreams = new KafkaStreams(builder.build(), KafkaStreamConfigurationBuilder(kafkaConfig).build())
  streams.start()

  def buildPeopleStreamTopology(builder: StreamsBuilder, topicName: String, storeName: String): StreamsBuilder = {
    buildTableStreamTopology[Int, Person](builder, topicName, storeName)
  }

  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    streams.close(10, TimeUnit.SECONDS)
  }))
}
