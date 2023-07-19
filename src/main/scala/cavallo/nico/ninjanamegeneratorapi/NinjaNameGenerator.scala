package cavallo.nico.ninjanamegeneratorapi

import cats.effect.Concurrent
import cats.syntax.all.*
import io.circe.{Decoder, Encoder, Json}
import org.http4s.EntityDecoder
import org.http4s.EntityEncoder
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.*

trait NinjaNameGenerator[F[_]]:
  def generate(originalName: NinjaNameGenerator.OriginalName): F[NinjaNameGenerator.NinjaName]

object NinjaNameGenerator:
  final case class OriginalName(fullName: String)
  object OriginalName:
    given Decoder[OriginalName] = Decoder.derived[OriginalName]
    given [F[_]: Concurrent]: EntityDecoder[F, OriginalName] = jsonOf

  final case class NinjaName(ninjaName: String)
  object NinjaName:
    given Encoder[NinjaName] = new Encoder[NinjaName]:
      final def apply(a: NinjaName): Json = Json.obj(
        ("ninjaName", Json.fromString(a.ninjaName)),
      )

    given [F[_]]: EntityEncoder[F, NinjaName] =
      jsonEncoderOf[F, NinjaName]

  private val toNinja = Map('a' -> "ka", 'b' -> "zu", 'c' -> "mi", 'd' -> "te",'e' -> "ku", 'f' -> "lu",
    'g' -> "ji", 'h' -> "ri", 'i' -> "ki", 'j' -> "zu", 'k' -> "me", 'l' -> "ta", 'n' -> "to", 'o' -> "mo",
    'p' -> "no", 'q' -> "ke", 'r' -> "shi", 's' -> "ari", 't' -> "chi", 'u' -> "do", 'v' -> "ru",
    'w' -> "mei", 'x' -> "na", 'y'-> "fu", 'z' -> "zi", ' ' -> " ").withDefaultValue("")
  
  def impl[F[_]: Concurrent] = new NinjaNameGenerator[F]:
    def generate(originalName: OriginalName): F[NinjaName] =
      val ninjaName = originalName.fullName.split(" ").map { name =>
        name.toLowerCase.foldLeft("") { (acc,ch) =>
          acc + toNinja(ch)
        }.capitalize
      }.mkString(" ")
      NinjaName(ninjaName).pure[F]