name := "optics"

version := "0.1"

scalaVersion := "2.13.4"

val monocleVersion = "3.0.0-M1"

libraryDependencies ++= Seq(
  "com.github.julien-truffaut" %% "monocle-core",
  "com.github.julien-truffaut" %% "monocle-macro"
).map(_ % monocleVersion)

val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
).map(_ % circeVersion)
