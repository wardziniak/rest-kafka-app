package com.wardziniak.kafka.rest.app.storage

import javax.inject.Inject

import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder}

/**
  * Created by wardziniak on 24.06.18.
  */

class KafkaStorage @Inject()() {

  val kafkaStreamBuilder = new StreamsBuilder()
  val ssssas = kafkaStreamBuilder.table("")
  val sss = kafkaStreamBuilder.stream("")

  kafkaStreamBuilder

  val streams:KafkaStreams = new KafkaStreams(kafkaStreamBuilder, ???)

}
