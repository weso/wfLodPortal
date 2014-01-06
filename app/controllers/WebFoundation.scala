package controllers

import org.apache.commons.configuration.PropertiesConfiguration

import es.weso.wesby.Configurable
import es.weso.wfLodPortal.sparql.custom.IndicatorCustomQuery
import es.weso.wfLodPortal.sparql.custom.IndicatorCustomQuery.Indicator
import es.weso.wfLodPortal.sparql.custom.IndicatorCustomQuery.indicatorWrites
import es.weso.wfLodPortal.sparql.custom.RegionCustomQueries.Region
import es.weso.wfLodPortal.sparql.custom.RegionCustomQueries.loadRegions
import es.weso.wfLodPortal.sparql.custom.RegionCustomQueries.regionWrites
import es.weso.wfLodPortal.sparql.custom.RootQueries
import es.weso.wfLodPortal.sparql.custom.SubindexCustomQuery.Subindex
import es.weso.wfLodPortal.sparql.custom.SubindexCustomQuery.loadSubindexes
import es.weso.wfLodPortal.sparql.custom.SubindexCustomQuery.subindexWrites
import es.weso.wfLodPortal.sparql.custom.YearsCustomQuery.loadYears
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.Controller

object WebFoundation extends Controller with Configurable {

  conf.append(new PropertiesConfiguration("conf/wesby/comparer.properties"))
  
  val currentVersion = conf.getString("application.version")
  
  def preCompare(mode: String, selectedCountries: Option[String] = None,
    selectedIndicators: Option[String] = None) = Action { implicit request =>

    val maxCountries = conf.getString("comparer.max.countries")
    val maxIndicators = conf.getString("comparer.max.indicators")
    val maxYears = conf.getString("comparer.max.years")

    val regions = Json.toJson[List[Region]](loadRegions(mode, currentVersion))
    val years = Json.toJson[List[Int]](loadYears(mode, currentVersion))
    val subindexes = Json.toJson[List[Subindex]](loadSubindexes(mode, currentVersion))

    Ok(views.html.custom.compare(currentVersion, mode)(request, regions,
      years, subindexes, selectedCountries, selectedIndicators,
      maxCountries, maxIndicators, maxYears))

  }

  def compare(mode: String, countries: String, years: String,
    indicators: String) = Action { implicit request =>

    val c = countries.split(",")
    val y = years.split(",")
    val i = indicators.split(",")

    val observations = IndicatorCustomQuery.loadObservations(mode, c, y, i)
    implicit val json = Json.toJson[Map[String, Indicator]](observations)

    Ok(views.html.custom.comparison(currentVersion, mode)(request, json))
  }

  def root(mode: String, version: String) = Action { implicit request =>
    implicit val regions = loadRegions(mode, version)
    implicit val subindexes = loadSubindexes(mode, version)
    implicit val queries = RootQueries.loadQueries
    Ok(
      if (mode == "webindex") {
        views.html.custom.WIroot(version, mode)
      } else {
        views.html.custom.ODBroot(version, mode)
      })
  }

}