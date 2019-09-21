# sbt-no-publish - Setting No Publication Settings [![Build Status](https://travis-ci.com/ChristopherDavenport/sbt-no-publish.svg?branch=master)](https://travis-ci.com/ChristopherDavenport/sbt-no-publish) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/sbt-no-publish_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/sbt-no-publish_2.12) ![Code of Consuct](https://img.shields.io/badge/Code%20of%20Conduct-Scala-blue.svg)

## [Head on over to the microsite](https://ChristopherDavenport.github.io/sbt-no-publish)

## Quick Start

To use sbt-no-publish in an existing SBT project with version 1.0 or greater, add the following dependencies to your
`plugins.sbt` depending on your needs:

```sbt
addSbtPlugin("io.chrisdavenport" % "sbt-no-publish" % "<version>")
```

## Example of Use

```sbt
lazy val root = project.in(file("."))
  .enablePlugins(NoPublishPlugin) // We disable publishing of the root project
  .aggregate(core, site)

lazy val core = project.in(file("core"))
  .settings(commonSettings, releaseSettings)
  .settings(
    name := "project-name"
  )


lazy val site = project.in(file("site"))
  .disablePlugins(MimaPlugin)
  .enablePlugins(NoPublishPlugin) // We disable publishing of the site
  .enablePlugins(MicrositesPlugin)
  .enablePlugins(MdocPlugin)
  .dependsOn(core)
```
