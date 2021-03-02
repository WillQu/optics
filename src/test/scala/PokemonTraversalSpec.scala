import monocle.Traversal
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{contain, have, startWith}
import org.scalatest.matchers.should.Matchers._

class PokemonTraversalSpec extends AnyFlatSpec {
  val pokemons = Pokedex.loadJson().getOrElse(Vector())
  "traversal" should "list all french names" in {
    val traversal: Traversal[Vector[Pokemon], String] = ???
    val allPokemons = traversal.getAll(pokemons)
    info(allPokemons.toString)
    allPokemons should have length 809
    allPokemons should contain("Pikachu")
    val frenchPokemons = traversal.modify(name => "Le " + name)(pokemons)
    info(frenchPokemons.toString)
    all(frenchPokemons.map(_.names.french)) should startWith("Le")
  }

  it should "compose" in {
    val traversal: Traversal[Vector[Pokemon], String] = ???
    val _types = traversal.getAll(pokemons).toSet
    info(_types.toString)
    _types should contain("Fire")
  }

  "Base.values" should "be a traversal" in {
    val firePokemonTraversal: Traversal[Vector[Pokemon], Pokemon] = ???
    val cheat = (firePokemonTraversal andThen Pokemon.base andThen Base.values).modify(_ + 1000)(pokemons)
    info(cheat.toString)
    cheat(3).base.attack should be > 1000
  }

}
