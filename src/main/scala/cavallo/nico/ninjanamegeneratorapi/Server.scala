package cavallo.nico.ninjanamegeneratorapi

import cats.effect.Async
import cats.syntax.all.*
import com.comcast.ip4s.*
import fs2.io.net.Network
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger

object Server:

  def run[F[_]: Async: Network]: F[Nothing] = {
    val httpApp = Routes.ninjaNameRoutes[F](NinjaNameGenerator.impl[F]).orNotFound
    // With Middlewares in place
    val finalHttpApp = Logger.httpApp(true, true)(httpApp) 
    for {
      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract segments not checked
      // in the underlying routes.
      _ <- 
        EmberServerBuilder.default[F]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever