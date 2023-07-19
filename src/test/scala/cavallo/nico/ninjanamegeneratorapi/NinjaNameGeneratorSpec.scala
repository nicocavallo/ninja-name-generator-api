package cavallo.nico.ninjanamegeneratorapi

import cats.effect.IO
import org.http4s.*
import org.http4s.circe.*
import org.http4s.implicits.*
import munit.CatsEffectSuite
import NinjaNameGenerator._
import io.circe.Encoder
import io.circe.Json

class NinjaNameGeneratorSpec extends CatsEffectSuite:
  
  given Encoder[OriginalName] = new Encoder[OriginalName]:
    final def apply(a: OriginalName): Json = Json.obj(
      ("fullName", Json.fromString(a.fullName)),
    )

  given [F[_]]: EntityEncoder[F, OriginalName] =
    jsonEncoderOf[F, OriginalName]  

  test("NinjaNameGenerator returns status code 200") {
    assertIO(retNinjaName("Nico").map(_.status), Status.Ok)
  }

  test("NinjaNameGenerator returns a Ninja Name") {
    assertIO(retNinjaName("Al Pacino").flatMap(_.as[String]), "{\"ninjaName\":\"Kata Nokamikitomo\"}")
  }

  private[this] def retNinjaName(normalName: String): IO[Response[IO]] = 
    val postNinjaName = 
        Request[IO](Method.POST)
        .withUri(uri"/ninja")
        .withEntity(OriginalName(normalName))
    val ninjaNameGenerator = NinjaNameGenerator.impl[IO]
    Routes.ninjaNameRoutes(ninjaNameGenerator).orNotFound(postNinjaName)
