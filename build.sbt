import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

// General Settings
lazy val commonSettings = Seq(
  scalaVersion := "2.12.8"
)

lazy val micrositeSettings = {
  import microsites._
  Seq(
    micrositeName := "sbt-no-publish",
    micrositeDescription := "Setting No Publication Settings",
    micrositeAuthor := "Christopher Davenport",
    micrositeGithubOwner := "ChristopherDavenport",
    micrositeGithubRepo := "sbt-no-publish",
    micrositeBaseUrl := "/sbt-no-publish",
    micrositeDocumentationUrl := "https://www.javadoc.io/doc/io.chrisdavenport/sbt-no-publish_2.12",
    micrositeGitterChannelUrl := "ChristopherDavenport/libraries", // Feel Free to Set To Something Else
    micrositeFooterText := None,
    micrositeHighlightTheme := "atom-one-light",
    micrositePalette := Map(
      "brand-primary" -> "#3e5b95",
      "brand-secondary" -> "#294066",
      "brand-tertiary" -> "#2d5799",
      "gray-dark" -> "#49494B",
      "gray" -> "#7B7B7E",
      "gray-light" -> "#E5E5E6",
      "gray-lighter" -> "#F4F3F4",
      "white-color" -> "#FFFFFF"
    ),
    micrositeCompilingDocsTool := WithMdoc,
    scalacOptions in Tut --= Seq(
      "-Xfatal-warnings",
      "-Ywarn-unused-import",
      "-Ywarn-numeric-widen",
      "-Ywarn-dead-code",
      "-Ywarn-unused:imports",
      "-Xlint:-missing-interpolator,_"
    ),
    libraryDependencies += "com.47deg" %% "github4s" % "0.20.1",
    micrositePushSiteWith := GitHub4s,
    micrositeGithubToken := sys.env.get("GITHUB_TOKEN"),
    micrositeExtraMdFiles := Map(
        file("CODE_OF_CONDUCT.md")  -> ExtraMdFileConfig("code-of-conduct.md",   "page", Map("title" -> "code of conduct",   "section" -> "code of conduct",   "position" -> "100")),
        file("LICENSE")             -> ExtraMdFileConfig("license.md",   "page", Map("title" -> "license",   "section" -> "license",   "position" -> "101"))
    )
  )
}

lazy val skipOnPublishSettings = Seq(
  skip in publish := true,
  publish := (()),
  publishLocal := (()),
  publishArtifact := false,
  publishTo := None
)

// Projects
lazy val `sbt-no-publish` = project.in(file("."))
  .disablePlugins(MimaPlugin)
  .settings(commonSettings, skipOnPublishSettings)
  .aggregate(core)

lazy val core = project.in(file("core"))
  .settings(commonSettings)
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-no-publish",

    scriptedLaunchOpts := { scriptedLaunchOpts.value ++
      Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    test := {
      (Test / test).value
      scripted.toTask("").value
    }
  )

lazy val site = project.in(file("site"))
  .disablePlugins(MimaPlugin)
  .settings(commonSettings, skipOnPublishSettings, micrositeSettings)
  .dependsOn(core)
  .enablePlugins(MicrositesPlugin)
  .enablePlugins(MdocPlugin)

// General Settings
inThisBuild(List(
  organization := "io.chrisdavenport",
  developers := List(
    Developer("ChristopherDavenport", "Christopher Davenport", "chris@christopherdavenport.tech", url("https://github.com/ChristopherDavenport"))
  ),

  homepage := Some(url("https://github.com/ChristopherDavenport/sbt-no-publish")),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),

  pomIncludeRepository := { _ => false},
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/ChristopherDavenport/sbt-no-publish"),
      "git@github.com:ChristopherDavenport/sbt-no-publish.git"
    )
  ),
  scalacOptions in (Compile, doc) ++= Seq(
      "-groups",
      "-sourcepath", (baseDirectory in LocalRootProject).value.getAbsolutePath,
      "-doc-source-url", "https://github.com/ChristopherDavenport/sbt-no-publish/blob/v" + version.value + "€{FILE_PATH}.scala"
  )
))