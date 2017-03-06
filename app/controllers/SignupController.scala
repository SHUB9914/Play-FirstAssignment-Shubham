package controllers
import javax.inject._
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, _}
import play.api.cache._
import models.UserData

@Singleton
class SignupController @Inject()(cache: CacheApi)(implicit val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val loginForm: Form[UserData] = Form(
    mapping(
      "username" -> nonEmptyText,
      "firstname" -> nonEmptyText,
      "middlename" -> text,
      "lastname" -> nonEmptyText,
      "age" -> number(min = 18, max = 75),
      "pass"-> nonEmptyText,
      "mobile" -> nonEmptyText,
      "gender" -> nonEmptyText,
      "hobbies"-> list(text)



    )(UserData.apply)(UserData.unapply)
  )

  def index = Action { implicit request =>
    Ok(views.html.singup("",loginForm))
  }

  def createUser = Action{ implicit request=>
    loginForm.bindFromRequest.fold(
      errorForm => {
        BadRequest(views.html.singup("", errorForm))
      }, validForm => {
        val user = cache.get[models.UserData](validForm.name)

        user match {
          case Some(userdata)=>Redirect(routes.LoginController.index()).flashing("success" -> "User already exist please Login")
          case None =>cache.set(validForm.name,validForm)
            Redirect(routes.ProfileController.index())withSession (request.session + ("mySession" -> s"${validForm.name}"))
        }
      }
    )
  }
}
