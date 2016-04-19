package model

/**
 * Created by ncavallo on 19/04/16.
 */
object NinjaNameGenerator {
  private val toNinja = Map('a' -> "ka", 'b' -> "zu", 'c' -> "mi", 'd' -> "te",'e' -> "ku", 'f' -> "lu",
    'g' -> "ji", 'h' -> "ri", 'i' -> "ki", 'j' -> "zu", 'k' -> "me", 'l' -> "ta", 'n' -> "to", 'o' -> "mo",
    'p' -> "no", 'q' -> "ke", 'r' -> "shi", 's' -> "ari", 't' -> "chi", 'u' -> "do", 'v' -> "ru",
    'w' -> "mei", 'x' -> "na", 'y'-> "fu", 'z' -> "zi", ' ' -> " ").withDefaultValue("")

  def generate(fullName: String):String = {
    fullName.split(" ").map { name =>
      name.toLowerCase.foldLeft("") { (acc,ch) =>
        acc + toNinja(ch)
      } capitalize
    } mkString " "
  }

}
