package es.weso.wfLodPortal.sparql.custom

import scala.Array.canBuildFrom
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.{Map => MutableMap}
import com.hp.hpl.jena.query.QuerySolution
import es.weso.wesby.Configurable
import es.weso.wesby.models.Uri
import es.weso.wesby.sparql.QueryEngine
import es.weso.wesby.utils.UriFormatter
import play.api.libs.functional.syntax.functionalCanBuildApplicative
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.Json

object IndicatorCustomQuery extends CustomQuery with Configurable {
  case class Indicator(val uri: Uri, val code: String, var description: String, observations: ListBuffer[Observation])
  case class Observation(val uri: Uri, val value: Float, val year: Float, val country: Country)
  case class Country(uri: Uri, val label: String, iso3: String)

  val queryCountries = conf.getString("query.compare")

  implicit val countryReads = Json.reads[Country]
  implicit val countryWrites = Json.writes[Country]

  implicit val observationReads = Json.reads[Observation]
  implicit val observationWrites = Json.writes[Observation]

  implicit val indicatorReads = Json.reads[Indicator]
  implicit val indicatorWrites = Json.writes[Indicator]

  def loadObservations(mode: String, regions: Array[String],
    years: Array[String], indicators: Array[String]) = {

    val rs = QueryEngine.performQuery(queryCountries, Seq(mode,
      indicatorFilters(indicators), yearFilters(years), regionFilters(regions)))

    val map: MutableMap[String, Indicator] = HashMap.empty

    while (rs.hasNext) {
      val qs = rs.next
      val uri = UriFormatter.format(qs.getResource("?indicator").getURI)
      val code = qs.getLiteral("?indicatorCode").getString
      val description = qs.getLiteral("?definition").getString

      val indicator = map.getOrElse(code, Indicator(uri, code, description,
        ListBuffer.empty))

      if (description.length > indicator.description.length) {
        indicator.description = description
      }

      indicator.observations += loadObservation(qs, indicator)
      map += code -> indicator
    }
    Map() ++ map
  }

  protected def loadObservation(qs: QuerySolution, indicator: Indicator) = {
    val uri = UriFormatter.format(qs.getResource("?obs").getURI())
    val value = qs.getLiteral("?value").getFloat
    val year = qs.getLiteral("?year").getFloat
    Observation(uri, value, year, loadCountry(qs))
  }

  protected def loadCountry(qs: QuerySolution) = {
    val uri = UriFormatter.format(qs.getResource("?country").getURI)
    val label = qs.getLiteral("?countryLabel").getString
    val iso3 = qs.getLiteral("?iso3").getString
    Country(uri, label, iso3)
  }

  protected def indicatorFilters(indicators: Array[String]): String = {
    if (indicators(0) == "ALL") ""
    else {
      "Filter(" + indicators.map {
        indicator =>
          new StringBuilder("(str(?indicatorCode)  = \"")
            .append(indicator.replace("_", " "))
            .append("\")")
      }.mkString(" || ") + ") . "
    }
  }

  protected def yearFilters(years: Array[String]): String = {
    if (years(0) == "ALL") ""
    else {
      "Filter(" + years.map {
        new StringBuilder("(?year = ")
          .append(_)
          .append(")")
      }.mkString(" || ") + ") . "
    }
  }

  protected def regionFilters(regions: Array[String]): String = {
    if (regions(0) == "ALL") ""
    else {
      "Filter(" + regions.map {
        new StringBuilder("(str(?iso3) = \"")
          .append(_)
          .append("\")")
      }.mkString(" || ") + ") . "
    }
  }

}