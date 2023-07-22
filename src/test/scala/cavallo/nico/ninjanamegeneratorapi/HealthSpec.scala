package cavallo.nico.ninjanamegeneratorapi

import cats.effect.IO
import munit.CatsEffectSuite
import org.http4s.*
import org.http4s.implicits.*

class HealthSpec extends CatsEffectSuite:
  
  test("health endpoint returns status code 200") {
    assertIO(retHealth.map(_.status), Status.NotFound)
  }

  private[this] def retHealth: IO[Response[IO]] = 
    val healthReq = 
        Request[IO](Method.GET)
        .withUri(uri"/health")
    Routes.health[IO].orNotFound(healthReq)
