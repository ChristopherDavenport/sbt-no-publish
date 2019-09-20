// Plugin Not Enabled Should Result In Fine Publication Settings
lazy val root = (project in file("."))
  .settings(
    scalaVersion := "2.13.0",
    organization := "askdfjakadjf",
    name := "fasdajhfdjah",

    // Common Setting
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },

    TaskKey[Unit]("skipPublishTest") := {
      test("Skip in Publish is flase", (skip in publish).value == false)
    },

    TaskKey[Unit]("publishArtifactTest") := {
      test("Publish Artifact is false", (publishArtifact.value == true))
    },

    TaskKey[Unit]("publishToTest") := {
      test("PublishTo is None", publishTo.value.isDefined)
    }

  )

def test(name: String, check: Boolean): Unit = {
  if (check) ()
  else throw new Throwable(s"Test Failed: $name")
}