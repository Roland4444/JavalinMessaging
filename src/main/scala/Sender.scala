import java.io.File
import java.net.URI
import java.net.http.{HttpClient, HttpRequest}
import java.net.http.HttpClient.Version
import java.net.http.HttpRequest.{BodyPublisher, BodyPublishers}
import java.nio.ByteBuffer
import java.nio.file.Files
import java.util.concurrent.Flow

import io.javalin.{Context, Handler, Javalin}
object Sender {
  val arr = Files.readAllBytes(new File("./a2002011001_e02_16kHz.wav").toPath);
  val httpClient = HttpClient.newBuilder()
    .version(Version.HTTP_2)
    .build();
  val server = "http://127.0.0.1:8888/";
  val objServer = "http://127.0.0.1:8888/obj"
  val request = HttpRequest.newBuilder()
    .uri(URI.create(server))
    .GET()
    .build();

  val objReq = HttpRequest.newBuilder()
    .uri(URI.create(objServer))
  //  .POST()
    .build();


  val binaryReq = HttpRequest.newBuilder()
    .uri(URI.create(server))
    .POST(BodyPublishers.ofByteArray(arr))
    .build();
  val sendMessage: Handler = new Handler {override def handle(context: Context) ={
    import java.net.http.HttpResponse.BodyHandlers
    httpClient.sendAsync(request, BodyHandlers.ofString).thenAccept((response) => {
      println("Response status code: " + response.statusCode)
      println("Response headers: " + response.headers)
      println("Response body: " + response.body)
    })
  }}

  val sendBinaryViaPost: Handler = new Handler {override def handle(context: Context) ={
    println("SEND BINARY!")
    import java.net.http.HttpResponse.BodyHandlers
    httpClient.sendAsync(binaryReq, BodyHandlers.ofString).thenAccept((response) => {
      println("Response status code: " + response.statusCode)
      println("Response headers: " + response.headers)
      println("Response body: " + response.body)
    })
  }}
  def main(args: Array[String]): Unit = {
    var app: Javalin = Javalin.create.start(5555)
    app.get("/", sendMessage)
    app.get("/sb", sendBinaryViaPost)
  }
}


case class sendFile(name: String, body: Array[Byte]) extends Serializable