lazy val root = (project in file("."))
  .enablePlugins(NoPublishPlugin)
  .settings(
    scalaVersion := "2.13.0",
    organization := "askdfjakadjf",
    name := "fasdajhfdjah",

    
    
    TaskKey[Unit]("skipPublishTest") := {
      test("Skip in Publish is true", (skip in publish).value == true)
    },

    TaskKey[Unit]("publishArtifactTest") := {
      test("Publish Artifact is false", (publishArtifact.value == false))
    },

    TaskKey[Unit]("publishToTest") := {
      test("PublishTo is None", publishTo.value.isEmpty)
    }

  )

def test(name: String, check: Boolean): Unit = {
  if (check) ()
  else throw new Throwable(s"Test Failed: $name")
}