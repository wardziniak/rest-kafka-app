package com.wardziniak.kafka.rest.app.storage

import com.sksamuel.avro4s.{FromRecord, SchemaFor, ToRecord}
import com.wardziniak.kafka.rest.app.storage.serialization.GenericSerde
import org.apache.kafka.common.serialization.Serdes.IntegerSerde
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.{Consumed, StreamsBuilder}
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.state.KeyValueStore

trait AbstractTopologyBuilder {

  def buildTableStreamTopology[V: SchemaFor: ToRecord: FromRecord](builder: StreamsBuilder, topicName: String, storeName: String): StreamsBuilder = {
    val mal: Materialized[Integer, V, KeyValueStore[Bytes, Array[Byte]]] = Materialized.as[Integer, V, KeyValueStore[Bytes, Array[Byte]]](storeName)
    builder.table(topicName, Consumed.`with`(new IntegerSerde(), GenericSerde[V]()), mal)
    builder
  }
}
