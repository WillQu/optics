import monocle.Iso
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class PokemonIsoSpec extends AnyFlatSpec {
  val pokemons = Pokedex.loadJson().toOption.get
  "name" should "be iso with a tuple" in {
    Names.isoNames.get(pokemons.head.names) should be (("Bulbasaur", "フシギダネ", "妙蛙种子", "Bulbizarre"))
    Names.isoNames.modify(tuple => tuple.copy(_4 = "bulbi"))(pokemons.head.names) should be(Names("Bulbasaur", "フシギダネ", "妙蛙种子", "bulbi"))
  }

  "a vector" should "be inverted by an iso" in {
    val iso: Iso[Vector[Pokemon], Vector[Pokemon]] = ???
    info(iso.get(pokemons).toString)
    iso.get(pokemons).last.names.french should be("Bulbizarre")
    iso.modify(_.init)(pokemons).head.names.french should be("Herbizarre")
  }
}
