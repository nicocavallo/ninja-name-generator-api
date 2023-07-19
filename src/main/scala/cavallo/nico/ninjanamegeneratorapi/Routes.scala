package cavallo.nico.ninjanamegeneratorapi

import cats.effect.{Concurrent, Sync}
import cats.syntax.all.*
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import NinjaNameGenerator._

object Routes:
  
  def ninjaNameRoutes[F[_]: Concurrent](N: NinjaNameGenerator[F]): HttpRoutes[F] =
    val dsl = new Http4sDsl[F]{}
    import dsl.*
    HttpRoutes.of[F] {
      case req @ POST -> Root / "ninja" =>
        for {
          originalName <- req.as[OriginalName]
          ninjaName <- N.generate(originalName)
          resp <- Ok(ninjaName)
        } yield resp
    }