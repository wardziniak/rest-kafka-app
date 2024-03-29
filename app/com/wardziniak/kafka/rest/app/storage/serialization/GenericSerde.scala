package com.wardziniak.kafka.rest.app.storage.serialization

import java.util

import com.sksamuel.avro4s.{ FromRecord, SchemaFor, ToRecord }
import org.apache.kafka.common.serialization.{ Serde, Serializer }

case class GenericSerde[T: SchemaFor: ToRecord: FromRecord]() extends Serde[T] {

  override def deserializer(): GenericDeserializer[T] = {
    GenericDeserializer[T]()
  }

  override def serializer(): Serializer[T] = {
    GenericSerializer[T]()
  }

  override def configure(configs: util.Map[String, _], isKey: Boolean) = {}

  override def close() = {}
}
