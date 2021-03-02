import monocle.{Optional, Traversal}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class PokemonOptionalSpec extends AnyFlatSpec {
  val pokemons = Pokedex.loadJson().toOption.get
  "Optional" should "get an object when present" in {
    val optional: Optional[String, Char] = ???
    optional.getOption("Coucou") should be (Some('C'))
    optional.getOption("") should be(None)
  }

  it should "compose" in {
    val optional: Optional[String, Char] = ???
    val traversal: Traversal[Vector[Pokemon], Char] = Traversal.fromTraverse[Vector, Pokemon] andThen Pokemon.names andThen Names.french andThen optional
    val result = traversal.modify(c => c.toLower)(pokemons)
    info(result.toString())
    result.head.names.french should be("bulbizarre")
  }
}
