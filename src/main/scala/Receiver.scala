import java.net.URI
import java.net.http.{HttpClient, HttpRequest}
import java.net.http.HttpClient.Version
import io.javalin.{Context, Handler, Javalin}
object Receiver {
  val readMessage: Handler = new Handler {override def handle(context: Context) ={
    println("GETING REQUEST")
    context.html("received");
  }}
  def main(args: Array[String]): Unit = {
    var app: Javalin = Javalin.create.start(8888)
    app.get("/", readMessage)
  }
}




