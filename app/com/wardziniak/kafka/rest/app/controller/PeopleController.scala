package com.wardziniak.kafka.rest.app.controller

import javax.inject.{Inject, Singleton}

import com.wardziniak.kafka.rest.app.model.Person
import com.wardziniak.kafka.rest.app.service.PeopleService
import play.api.libs.json.Json
import play.api.mvc._

/**
  * Created by wardziniak on 24.06.18.
  */
@Singleton
class PeopleController @Inject ()(cc: ControllerComponents, peopleService: PeopleService) extends AbstractController(cc) {

  implicit val personFormat = Json.format[Person]

  def test() = Action { implicit request: Request[AnyContent] =>
    Ok("ddaaa")
  }

  def find(id: Int) = Action {implicit request: Request[AnyContent] =>
    peopleService.findPerson(id).
      map(person => Ok(Json.toJson (person))).
      getOrElse(NotFound (s"Person with id: $id cannot be found"))
  }

  def upsert(id: Int) = Action(parse.json[Person]){implicit request =>
    peopleService.upsertPerson(request.body).
      map(person => Ok(Json.toJson (person))).
      getOrElse(NotFound (s"Cannot upsert"))
  }
}
