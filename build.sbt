name := """ninja-name-generator-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

coverageExcludedPackages := "<empty>;router\\..*;Reverse.*;.*AuthService.*;models\\.data\\..*"
// fork in run := true

lazy val artifactoryHost = "artifactorylb-130644380.eu-west-1.elb.amazonaws.com"

lazy val artifactory = s"http://$artifactoryHost:80/artifactory/"

publishTo := {
  if (isSnapshot.value)
    Some("snapshots" at artifactory + "libs-snapshot-local")
  else
    Some("releases"  at artifactory + "libs-release-local")
}
val artifactoryUser = sys.env.get("ARTIFACTORY_USER")
val artifactoryPassword = sys.env.get("ARTIFACTORY_PASSWORD")

lazy val artifactoryCredentials = if (artifactoryUser.isDefined && artifactoryPassword.isDefined)
  Credentials("Artifactory Realm",artifactoryHost,artifactoryUser.get,artifactoryPassword.get)
else
  Credentials(Path.userHome / ".sbt" / ".credentials")

credentials += artifactoryCredentials
