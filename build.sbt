name := "swordnet"

organization := "com.github.mrmechko"

version := "2.0-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, add dependency on scala-xml module
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq("org.scala-lang.modules" %% "scala-xml" % "1.0.3", "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test")
    case Some((2, scalaMajor)) if scalaMajor == 10 =>
      libraryDependencies.value ++ Seq("org.scalatest" % "scalatest_2.10" % "2.2.4" % "test")
    case _ => libraryDependencies.value ++ Seq()// or nothing since I only care about xml
  }
}

publishMavenStyle := true

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>http://mrmechko.github.io/SWordNet</url>
    <licenses>
      <license>
        <name>MIT License</name>
        <url>http://www.opensource.org/licenses/mit-license.php</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:mrmechko/swordnet.git</url>
      <connection>scm:git:git@github.com:mrmechko/swordnet.git</connection>
    </scm>
    <developers>
      <developer>
        <id>mrmechko</id>
        <name>Ritwik Bose</name>
        <url>http://cs.rochester.edu/~rbose</url>
      </developer>
    </developers>)
