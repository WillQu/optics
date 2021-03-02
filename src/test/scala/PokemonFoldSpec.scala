import cats.instances.list._
import monocle.{Fold, Getter, Traversal}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class PokemonFoldSpec extends AnyFlatSpec {
  val pokemons = Pokedex.loadJson().toOption.get
  "Fold" should "select on all the list" in {
    val bestStatFold: Fold[Pokemon, (String, Int)] = Getter((pokemon:Pokemon) => (pokemon.names.french, Base.values.getAll(pokemon.base).max)).asFold
    val result = (Traversal.fromTraverse[Vector, Pokemon] andThen bestStatFold).getAll(pokemons)
    info(result.toString)
    result should contain(("Pikachu", 90))
  }
}
