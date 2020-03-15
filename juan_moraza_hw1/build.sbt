name := "juan_moraza_hw1"

version := "0.1"

scalaVersion := "2.13.1"
// https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
libraryDependencies ++= {
  Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe" % "config" % "1.4.0",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
    "ch.qos.logback" % "logback-classic" % "1.2.3",

    "org.eclipse.core" % "org.eclipse.core.resources" % "3.6.0.v20100526-0737",
    "org.eclipse.core" % "runtime" % "3.10.0-v20140318-2214",
    "org.eclipse.core" % "commands" % "3.3.0-I20070605-0010",
    "org.eclipse.jdt" % "org.eclipse.jdt.core" % "3.19.0",
    "org.ow2.asm" % "asm" % "5.0.3",

  )
}
