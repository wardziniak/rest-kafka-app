package com.wardziniak.kafka.rest.app.controller

import javax.inject.{Inject, Singleton}

import play.api.mvc._

/**
  * Created by wardziniak on 24.06.18.
  */
@Singleton
class PeopleController @Inject ()(cc: ControllerComponents) extends AbstractController(cc) {

  def test() = Action { implicit request: Request[AnyContent] =>
    Ok("ddaaa")
  }
}
