package controllers

import play.api.libs.json.{JsString, JsObject}
import play.api.mvc._
import model.NinjaNameGenerator

class Application extends Controller {

  def healthCheck = Action {
    Ok("ok")
  }

  def ninja(name:String) = Action {
    val ninjaName = NinjaNameGenerator.generate(name)
    Ok(JsObject(Seq("result" -> JsString(ninjaName))))
  }

}
