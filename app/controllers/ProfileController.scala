package controllers
import javax.inject._
import play.api.cache.CacheApi
import play.api.mvc._
import models.UserData


@Singleton
class ProfileController @Inject()(cache: CacheApi) extends Controller {

  def index = Action { implicit request=>
    val name = request.session.get("mySession").fold("unkown#@123")(identity) // use different name when there is no session
    val user = cache.get[UserData](name)

    user match {
      case Some(UserData(uname,fname,mname,lname,age,pass,mobile,gender,hobbies)) =>  Ok(views.html.profile(uname,fname,mname,lname,age,mobile,gender,hobbies))
      case None =>  Redirect(routes.LoginController.index()).flashing("success" -> "login first")
    }
  }

  def outSession = Action{ implicit request=>

    Ok(views.html.index("with new session")).withNewSession
  }

}
