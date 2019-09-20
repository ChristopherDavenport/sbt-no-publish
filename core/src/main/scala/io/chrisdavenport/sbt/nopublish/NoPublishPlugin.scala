package io.chrisdavenport.sbt.nopublish

import sbt._
import Keys._

object NoPublishPlugin extends AutoPlugin {

  override lazy val projectSettings = Seq(
    skip in publish := true,
    publish := (()),
    publishLocal := (()),
    publishArtifact := false,
    publishTo := None
  )
}