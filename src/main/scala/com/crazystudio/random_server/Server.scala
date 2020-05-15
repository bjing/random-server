package com.crazystudio.random_server

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.global

object Server extends IOApp {
  val service: HttpApp[IO] =
    HttpRoutes
      .of[IO] {
        case GET -> Root / "health" =>
          Ok("OK")
      }
      .orNotFound

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(service)
      .withoutBanner
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
