import AssemblyKeys._

Nice.javaProject

javaVersion := "1.8"

fatArtifactSettings

organization := "bio4j"

name := "bio4j-graphical-tools"

description := "bio4j-graphical-tools project"

bucketSuffix := "era7.com"

libraryDependencies ++= Seq(
  "bio4j" % "bio4j-titan" % "0.4.0-SNAPSHOT",
  "com.google.code.gson" % "gson" % "2.2.4",
  // test deps
  "junit" % "junit" % "3.8.1" % "test",
  "org.scalatest" %% "scalatest" % "2.2.2" % "test"
)

dependencyOverrides ++= Set(
  "commons-codec" % "commons-codec" % "1.7",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.1.2",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.2",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.1",
  "commons-beanutils" % "commons-beanutils" % "1.8.3",
  "commons-beanutils" % "commons-beanutils-core" % "1.8.3",
  "com.tinkerpop.blueprints" % "blueprints-core" % "2.5.0"
)

// fat jar assembly settings
mainClass in assembly := Some("com.bio4j.dataviz.GenerateDataViz")

assemblyOption in assembly ~= { _.copy(includeScala = false) }

mergeStrategy in assembly ~= { old => {
  case PathList("META-INF", "CHANGES.txt")                      => MergeStrategy.rename
  case PathList("META-INF", "LICENSES.txt")                     => MergeStrategy.rename
  case "log4j.properties"                                       => MergeStrategy.filterDistinctLines
  case PathList("org", "apache", "commons", "collections", _*)  => MergeStrategy.first
  case x                                                        => old(x)
}
}
