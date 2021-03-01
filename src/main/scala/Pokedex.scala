import Pokemon._
import io.circe.Error
import io.circe.parser._

import scala.io.Source

object Pokedex extends App {
  def loadJson(): Either[Error, Vector[Pokemon]] = {
    val inputJson = Source.fromFile("src/main/resources/pokedex.json", "UTF-8").mkString
    val json = parse(inputJson)
    decode[Vector[Pokemon]](inputJson)
  }
}
