name := "lab1"

version := "0.1"

scalaVersion := "2.11.12"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.3",
  "org.apache.spark" %% "spark-sql" % "2.4.3",
  "org.apache.spark" %% "spark-mllib" % "2.4.3",



  "org.apache.spark" %% "spark-streaming" % "2.4.3" % "provided",
  "org.scala-sbt" %% "util-logging" % "1.3.0-M2"

)