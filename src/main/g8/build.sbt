import Dependencies._
import sbt.Keys._
import sbtdocker.BuildOptions.Remove.Always
import sbtrelease.ReleaseStateTransformations._

name := "$name$"
scalaVersion := "$scala_version$"
organization := "$organization$"

scalacOptions += "-Xexperimental"

libraryDependencies ++= deps

resolvers ++= Seq(
  "Confluent" at "http://packages.confluent.io/maven/",
  "Artima Maven Repository" at "http://repo.artima.com/releases"
)

assemblyMergeStrategy in assembly := {
  case PathList("org", "slf4j", xs@_*) => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
mainClass in assembly := Some("$package$.Application")
test in assembly := {}

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,                                             // : ReleaseStep
  inquireVersions,                                                       // : ReleaseStep
  runTest,                                                               // : ReleaseStep
  setReleaseVersion,                                                     // : ReleaseStep
  commitReleaseVersion,                                                  // : ReleaseStep, performs the initial git checks
  tagRelease,                                                            // : ReleaseStep
  ReleaseStep(releaseStepTask(DockerKeys.dockerBuildAndPush in docker)), // : ReleaseStep, pushes docker image
  setNextVersion,                                                        // : ReleaseStep
  commitNextVersion,                                                     // : ReleaseStep
  pushChanges                                                            // : ReleaseStep, also checks that an upstream branch is properly configured
)

enablePlugins(DockerPlugin)

docker <<= docker.dependsOn(assembly)

dockerfile in docker := {
  val artifact = (assemblyOutputPath in assembly).value
  val artifactTargetPath = s"/app/\${artifact.name}"
  val startCommand = "java \$JAVA_OPTS -jar " + artifactTargetPath
  new Dockerfile {
    from("$docker_base$")
    add(assembly.value, artifactTargetPath)
    cmdRaw(startCommand)
  }
}

buildOptions in docker := BuildOptions(
  cache = false,
  removeIntermediateContainers = BuildOptions.Remove.Always,
  pullBaseImage = BuildOptions.Pull.Always
)

imageNames in docker := Seq(
  ImageName(
    registry = Some("$docker_registry$"),
    namespace = Some("$docker_namespace$"),
    repository = name.value,
    tag = Some(version.value)
  )
)
