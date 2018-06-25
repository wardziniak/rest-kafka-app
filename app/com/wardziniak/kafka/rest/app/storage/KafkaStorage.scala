package com.wardziniak.kafka.rest.app.storage

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import com.wardziniak.kafka.rest.app.config.KafkaConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.{IntegerSerializer, Serdes, StringSerializer}
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.state.{KeyValueStore, QueryableStoreTypes, ReadOnlyKeyValueStore}
import org.apache.kafka.streams.{Consumed, KafkaStreams, StreamsBuilder}
import java.util.{Properties, HashMap => JHashMap, Map => JMap}

import com.wardziniak.kafka.rest.app.model.Person
import com.wardziniak.kafka.rest.app.storage.serialization.GenericSerializer

/**
  * Created by wardziniak on 24.06.18.
  */

@Singleton
class KafkaStorage @Inject()(kafkaConfig: KafkaConfig) extends AbstractTopologyBuilder{

  val builder = new StreamsBuilder()

  val streams:KafkaStreams = new KafkaStreams(builder.build(), KafkaStreamConfigurationBuilder(kafkaConfig).build())
  streams.start()

  def buildPeopleStreamTopology(builder: StreamsBuilder, topicName: String, storeName: String): StreamsBuilder = {
    buildTableStreamTopology[Int, Person](builder, topicName, storeName)
  }

  val producer: KafkaProducer[Integer, Person] = new KafkaProducer[Integer, Person](
    KafkaProducerConfigurationBuilder(kafkaConfig).build(),
    new IntegerSerializer(),
    GenericSerializer[Person]()
  )

  lazy val peopleStore: ReadOnlyKeyValueStore[Integer, Person] =
    streams.store(kafkaConfig.streams.store, QueryableStoreTypes.keyValueStore[Integer, Person]())

  def findPerson(id: Int): Person = {
    peopleStore.get(id)
  }

  def upsertPerson(person: Person) = {
    val producerRecord = new ProducerRecord[Integer, Person](
      kafkaConfig.producer.topic,
      person.id,
      person
    )
    producer.send(producerRecord)
  }

  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    streams.close(10, TimeUnit.SECONDS)
  }))
}
