package controllers

import play.api.mvc._

/**
  * Created by shubham on 10/9/17.
  */
class MyController extends Controller{

  def loginHandle = Action(parse.multipartFormData){ implicit request =>
//    println("hello shubham")
    println(">>>>>>>>>>>>>> name >>"+request.body.dataParts("name"))
    println(">>>>>>>>>>>>>> pass >>"+request.body.dataParts("pass"))
    Ok("your response has been saved")

  }

}
