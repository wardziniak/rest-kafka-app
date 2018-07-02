package com.wardziniak.kafka.rest.app.storage

import java.util.concurrent.TimeUnit
import java.util.{HashMap => JHashMap, Map => JMap}
import javax.inject.{Inject, Singleton}

import com.wardziniak.kafka.rest.app.config.KafkaConfig
import com.wardziniak.kafka.rest.app.model.Person
import com.wardziniak.kafka.rest.app.storage.serialization.GenericSerializer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.streams.state.{QueryableStoreTypes, ReadOnlyKeyValueStore}
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder}

import scala.collection.JavaConverters._

/**
  * Created by wardziniak on 24.06.18.
  */

@Singleton
class KafkaStorage @Inject()(kafkaConfig: KafkaConfig) extends AbstractTopologyBuilder {

  val builder = new StreamsBuilder()

  buildTableStreamTopology[Person](builder, kafkaConfig.streams.topic, kafkaConfig.streams.store)
  val streams:KafkaStreams = new KafkaStreams(builder.build(), KafkaStreamConfigurationBuilder(kafkaConfig).build())
  streams.start()

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

  def findAll: List[Person] = {
    peopleStore.all().asScala.map(_.value).toList
  }

  def upsertPerson(person: Person): Person = {
    val producerRecord = new ProducerRecord[Integer, Person](
      kafkaConfig.producer.topic,
      person.id,
      person
    )
    producer.send(producerRecord).get()
    person
  }

  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    streams.close(10, TimeUnit.SECONDS)
  }))
}
