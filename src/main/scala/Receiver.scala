import java.io.{BufferedOutputStream, FileOutputStream}
import java.net.URI
import java.net.http.{HttpClient, HttpRequest}
import java.net.http.HttpClient.Version

import io.javalin.{Context, Handler, Javalin}
object Receiver {
  def valueOf(buf: Array[Byte]): String = buf.map("%02X" format _).mkString

  val readMessage: Handler = new Handler {override def handle(context: Context) ={
    println("GETING REQUEST")
    context.html("received");
  }}

  val writetoFile: Handler = new Handler {override def handle(context: Context) ={
    val received = context.bodyAsBytes()
    println(valueOf(received))
    val bos = new BufferedOutputStream(new FileOutputStream("name.wav"))
    bos.write(received)
    bos.close()
    println("Length="+ received.length)
  }}
  def main(args: Array[String]): Unit = {
    var app: Javalin = Javalin.create.start(8888)
    app.get("/", readMessage)
    app.post("/", writetoFile)
  }
}




