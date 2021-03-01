import cats.instances.list._
import monocle.{Optional, Traversal}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

import scala.+:

class PokemonOptionalSpec extends AnyFlatSpec {
  val pokemons = Pokedex.loadJson().toOption.get
  "Optional" should "get an object when present" in {
    val optional: Optional[String, Char] = Optional.apply[String, Char](_.headOption)(c => string => c +: string.tail)
    optional.getOption("Coucou") should be (Some('C'))
    optional.getOption("") should be(None)
  }

  it should "compose" in {
    val optional: Optional[String, Char] = Optional.apply[String, Char](_.headOption)(c => string => c +: string.tail)
    val traversal: Traversal[Vector[Pokemon], Char] = Traversal.fromTraverse[Vector, Pokemon] andThen Pokemon.names andThen Names.french andThen optional
    val result = traversal.modify(c => c.toLower)(pokemons)
    info(result.toString())
    result.head.names.french should be("bulbizarre")
  }
}