name := "wfLodPortal"

version := "0.2.0-SNAPSHOT"

play.Project.playScalaSettings

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

templatesImport ++= Seq(
  "app.models.OptionsHelper",
  "app.models.OptionsHelper._",
  "es.weso.wesby.models._",
  "es.weso.wesby.sparql.Handlers._",
  "es.weso.wesby.utils.CommonURIS._",
  "views.helpers.Utils._",
  "views.html.helpers._",
  "views.html.helpers.utils._"
)

templatesImport ++= Seq(
  "views.helpers.wf.Utils._"
)

seq(cucumberSettings : _*)

cucumberJunitReport := true

cucumberStepsBasePackage := "scala.es.weso.wfLodPortal"

cucumberFeaturesLocation := "./test/resources/es/weso/wfLodPortal"

sourceGenerators in Compile <+= Def.task {
  val finder: PathFinder = (new File("app")/"views"/"lod") ** "*.scala.html"
  var sourceCode = """package es.weso.wesby
import es.weso.wesby.models.ResultQuery
import es.weso.wesby.models.Options
import play.api.mvc.RequestHeader
import play.templates.BaseScalaTemplate
import play.templates.Format
import play.api.templates.HtmlFormat.Appendable
import play.api.templates.Template3
/* This Source file is auto-generated at compile time*/
object TemplateMapping {
  type LodTemplate = BaseScalaTemplate[Appendable, Format[Appendable]] with Template3[ResultQuery,RequestHeader,Options,Appendable]
  var templates : Map[String, LodTemplate] = Map.empty 
"""
  /*I could not employ regular expressions due to an SBT-13.0.0's internal bug*/
  for( file <- finder.get){
    val chunks = file.name.split('.')
    if(!chunks.isEmpty){
       sourceCode += "  templates+=\""+chunks(0)+"\"->views.html.lod."+chunks(0)+"\n"
    }
  }
  sourceCode += "}"
  val file = (sourceManaged in Compile).value / "es" / "weso" / "wesby" / "TemplateMapping.scala"
  IO.write(file, sourceCode)
  Seq(file)
}