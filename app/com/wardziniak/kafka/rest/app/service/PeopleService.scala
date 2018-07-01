package com.wardziniak.kafka.rest.app.service

import javax.inject.{Inject, Singleton}

import com.wardziniak.kafka.rest.app.model.Person
import com.wardziniak.kafka.rest.app.storage.KafkaStorage

@Singleton
class PeopleService @Inject()(storage: KafkaStorage) {

  def findPerson(id: Int): Option[Person] = {
    Option.apply(storage.findPerson(id))
  }

  def findAll: List[Person] = {
    storage.findAll
  }

  def upsertPerson(person: Person): Option[Person] = {
    Option.apply(storage.upsertPerson(person))
  }
}
