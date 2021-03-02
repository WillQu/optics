import io.circe.{Decoder, Encoder, HCursor, Json}
import monocle.macros.GenLens
import monocle.{Iso, Lens, Optional, Traversal}

final case class Pokemon(id: Int, names: Names, _types: Vector[String], base: Base)

object Pokemon {
  lazy val id: Lens[Pokemon, Int] = ???
  lazy val names: Lens[Pokemon, Names] = GenLens[Pokemon](_.names)
  lazy val _types: Lens[Pokemon, Vector[String]] = GenLens[Pokemon](_._types)
  lazy val base: Lens[Pokemon, Base] = GenLens[Pokemon](_.base)

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

object Names {
  lazy val french: Lens[Names, String] = GenLens[Names](_.french)
  lazy val isoNames: Iso[Names, (String, String, String, String)] =
    Iso[Names, (String, String, String, String)](
      names => (names.english, names.japanese, names.chinese, names.french)
    )(tuple => Names(tuple._1, tuple._2, tuple._3, tuple._4))
}

final case class Base(hp: Int, attack: Int, defense: Int, spAttack: Int, spDefense: Int, speed: Int)

object Base {
  lazy val values: Traversal[Base, Int] = Traversal.applyN(
    GenLens[Base](_.hp),
    GenLens[Base](_.attack),
    GenLens[Base](_.defense),
    GenLens[Base](_.spAttack),
    GenLens[Base](_.spDefense),
    GenLens[Base](_.speed),
  )
}
