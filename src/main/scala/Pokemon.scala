import io.circe.{Decoder, Encoder, HCursor, Json}

final case class Pokemon(id: Int, names: Names, _types: Vector[String], base: Base)

object Pokemon {

  implicit val decodePokemon: Decoder[Pokemon] = (c: HCursor) => for {
    id <- c.downField("id").as[Int]
    names <- {
      val nameCursor = c.downField("name")
      for {
        english <- nameCursor.downField("english").as[String]
        japanese <- nameCursor.downField("japanese").as[String]
        chinese <- nameCursor.downField("chinese").as[String]
        french <- nameCursor.downField("french").as[String]
      } yield Names(english, japanese, chinese, french)
    }
    _types <- c.downField("type").as[Vector[String]]
    base <- {
      val baseCursor = c.downField("base")
      for {
        hp <- baseCursor.downField("HP").as[Int]
        attack <- baseCursor.downField("Attack").as[Int]
        defense <- baseCursor.downField("Defense").as[Int]
        spAttack <- baseCursor.downField("Sp. Attack").as[Int]
        spDefense <- baseCursor.downField("Sp. Defense").as[Int]
        speed <- baseCursor.downField("Speed").as[Int]
      } yield Base(hp, attack, defense, spAttack, spDefense, speed)
    }
  } yield Pokemon(id, names, _types, base)

  implicit val encodePokemon: Encoder[Pokemon] = (pokemon: Pokemon) => Json.obj(
    "id" -> Json.fromInt(pokemon.id),
    "name" -> Json.obj(
      "english" -> Json.fromString(pokemon.names.english),
      "japanese" -> Json.fromString(pokemon.names.japanese),
      "chinese" -> Json.fromString(pokemon.names.chinese),
      "french" -> Json.fromString(pokemon.names.french),
    ),
    "type" -> Json.fromValues(pokemon._types.map(Json.fromString)),
    "base" -> Json.obj(
      "HP" -> Json.fromInt(pokemon.base.hp),
      "Attack" -> Json.fromInt(pokemon.base.attack),
      "Defense" -> Json.fromInt(pokemon.base.defense),
      "Sp. Attack" -> Json.fromInt(pokemon.base.spAttack),
      "Sp. Defense" -> Json.fromInt(pokemon.base.spDefense),
      "Speed" -> Json.fromInt(pokemon.base.speed),
    )
  )
}

final case class Names(english: String, japanese: String, chinese: String, french: String)

final case class Base(hp: Int, attack: Int, defense: Int, spAttack: Int, spDefense: Int, speed: Int)
