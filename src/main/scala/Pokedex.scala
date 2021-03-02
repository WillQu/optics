import Pokemon._
import io.circe.Error
import io.circe.parser._

import scala.io.Source

object Pokedex extends App {
  def loadJson(): Either[Error, Vector[Pokemon]] = {
    val inputJson = loadRawFile()
    decode[Vector[Pokemon]](inputJson)
  }

  def loadRawFile(): String = Source.fromFile("src/main/resources/pokedex.json", "UTF-8").mkString
}
