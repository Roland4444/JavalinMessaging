import java.net.URI
import java.net.http.{HttpClient, HttpRequest}
import java.net.http.HttpClient.Version
import io.javalin.{Context, Handler, Javalin}
object Sender {
  val httpClient = HttpClient.newBuilder()
    .version(Version.HTTP_2)  
    .build();
  val server = "http://127.0.0.1:8888/";
  val request = HttpRequest.newBuilder()
    .uri(URI.create(server))
    .GET()
    .build();
  val sendMessage: Handler = new Handler {override def handle(context: Context) ={
    import java.net.http.HttpResponse.BodyHandlers
    httpClient.sendAsync(request, BodyHandlers.ofString).thenAccept((response) => {
      println("Response status code: " + response.statusCode)
      println("Response headers: " + response.headers)
      println("Response body: " + response.body)
    })
  }}
  def main(args: Array[String]): Unit = {
    var app: Javalin = Javalin.create.start(5555)
    app.get("/", sendMessage)
  }
}
