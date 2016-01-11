
name := "scala-hive"

version := "1.0"

scalaVersion := "2.11.7"

unmanagedSourceDirectories in Compile += baseDirectory.value / "src" / "main" / "gen" / "1.0.1" / "gen-javabean"
libraryDependencies += "org.apache.thrift" % "libthrift" % "0.9.3" % "compile"