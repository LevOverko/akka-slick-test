name := "scala-slick"

version := "3.3"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick"           % "3.3.0",
  "com.h2database"      % "h2"              % "2.1.210",
  "ch.qos.logback"      % "logback-classic" % "1.2.10"
)

