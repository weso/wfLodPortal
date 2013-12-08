name := "wfLodPortal"

version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  filters,
  "org.apache.jena" % "jena-arq" % "2.10.1",
  "commons-configuration" % "commons-configuration" % "1.9",
  "com.github.mumoshu" %% "play2-memcached" % "0.3.0.2",
  "junit" % "junit" % "4.11" % "test",
  "info.cukes" % "cucumber-jvm" % "1.1.4" % "test",
  "info.cukes" % "cucumber-core" % "1.1.4" % "test",
  "info.cukes" % "cucumber-junit" % "1.1.4" % "test",
  "org.seleniumhq.selenium" % "selenium-java" % "2.35.0" % "test",
  "org.scalatest" %% "scalatest" % "2.0" % "test",
  "info.cukes" %% "cucumber-scala" % "1.1.4" % "test"
)     

resolvers += "Spy Repository" at "http://files.couchbase.com/maven2"

templatesImport += "es.weso.wfLodPortal.models._"

templatesImport += "es.weso.wfLodPortal.utils.CommonURIS._"

templatesImport += "views.html.helpers._"

templatesImport += "views.html.helpers.utils._"

play.Project.playScalaSettings

seq(cucumberSettings : _*)

cucumberJunitReport := true

cucumberStepsBasePackage := "scala.es.weso.wfLodPortal"

cucumberFeaturesLocation := "./test/resources/es/weso/wfLodPortal"
