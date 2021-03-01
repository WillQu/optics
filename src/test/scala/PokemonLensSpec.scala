import cats.instances.list._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class PokemonLensSpec extends AnyFlatSpec {
  val pokemon = Pokedex.loadJson().toOption.flatMap(_.headOption).get
  "id" should "focus on the id" in {
    Pokemon.id.get(pokemon) should be(1)
    Pokemon.id.modify(_ + 1)(pokemon) should be(pokemon.copy(id = pokemon.id + 1))
    Pokemon.id.modifyF(id => List(id + 1, id + 2, id + 3))(pokemon) should be(List(1, 2, 3).map(x => pokemon.copy(id = pokemon.id + x)))
  }

  "names" should "compose with french" in {
    val lens = Pokemon.names andThen Names.french
    lens.get(pokemon) should be("Bulbizarre")
    lens.replace("Le Bulbizarre")(pokemon).names.french should be("Le Bulbizarre")
  }
}
