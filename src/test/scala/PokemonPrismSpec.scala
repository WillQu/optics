import io.circe.jawn.decode
import io.circe.syntax._
import monocle.{Prism, Traversal}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class PokemonPrismSpec extends AnyFlatSpec {
  "prism" should "modify when it matches" in {
    val prism: Prism[Either[String, Int], Int] = ???
    prism.getOption(Right(15)) should be(Some(15))
    prism.getOption(Left("Coucou")) should be(None)
    prism.modify(_ + 1)(Right(15)) should be(Right(16))
  }

  "prism" should "compose with other optics" in {
    val prism: Prism[String, Vector[Pokemon]] = ???
    val inputJson = Pokedex.loadRawFile()
    val result = (prism andThen Traversal.fromTraverse[Vector, Pokemon] andThen Pokemon.names andThen Names.french modify(name => "Le " + name))(inputJson)
    info(result)
    result should include ("Le Pikachu")
  }
}
