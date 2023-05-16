//> using scala 3.2
//> using dep "org.typelevel::cats-effect::3.5.0"
//> using dep "org.http4s::http4s-ember-client::0.23.19"
//> using dep "org.http4s::http4s-dsl::0.23.19"

import cats.effect._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.client.Client
import org.http4s.Request
import org.http4s.Headers
import org.http4s.Method
import org.http4s.headers._
import org.http4s.MediaType
import org.http4s.implicits._

object Main extends IOApp.Simple {
  val run: IO[Unit] = EmberClientBuilder
    .default[IO]
    .build
    .use(shot)

  def shot(cli: Client[IO]): IO[Unit] = for {
    ver <- cli
      .expect[String]("http://localhost:9222/json/version")
    _ <- IO.println(ver)
    req = Request[IO](
      method = Method.PUT,
      uri = uri"http://localhost:9222/json/new?url=https://www.3qe.us"
    )
    ws <- cli
      .expect[String](req)
    _ <- IO.println(ws)
    // TODO: https://jdk-http-client.http4s.org/0.9/#websocket-client を使ってwebsocketにつなぎ、CDPを喋る
  } yield ()
}
