import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
import de.heikoseeberger.sbtheader.HeaderKey._
import de.heikoseeberger.sbtheader.license.Apache2_0

sbtPlugin    := true
organization := "com.nrinaudo"
name         := "sbt-glpages"
moduleName   := "sbt-glpages"

licenses := Seq("Apache-2.0" → url("https://www.apache.org/licenses/LICENSE-2.0.html"))
pomExtra := <developers>
  <developer>
    <id>nrinaudo</id>
    <name>Nicolas Rinaudo</name>
    <url>http://nrinaudo.github.io</url>
  </developer>
</developers>
homepage := Some(url(s"https://nrinaudo.github.io/sbt-glpages"))

scmInfo := Some(ScmInfo(
  url(s"https://github.com/nrinaudo/sbt-glpages"),
  s"scm:git:git@github.com:nrinaudo/sbt-glpages.git"
))

val license = Apache2_0("2017", "Nicolas Rinaudo")

headers := Map(
  "scala" → license,
  "java"  → license
)

enablePlugins(AutomateHeaderPlugin)

addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.6.0")

addCommandAlias("validate", ";clean;scalastyle;test:scalastyle;compile")
