package es.weso.wfLodPortal.sparql.custom

import scala.collection.mutable.HashMap
import scala.collection.mutable.{ Map => MutableMap }

import es.weso.wesby.Configurable
import es.weso.wesby.models.Uri
import es.weso.wesby.sparql.QueryEngine
import es.weso.wesby.utils.UriFormatter

object IndexValueCustomQuery extends CustomQuery with Configurable {
  case class Index(uri: Uri, name: String, rank: String, score: String,
    children: MutableMap[String, Index], colour1: String, colour2: String)

  val queryHierarchy = conf.getString("query.hierarchy")
  val queryValues = conf.getString("query.hierarchy.values")
  val queryRanking = conf.getString("query.hierarchy.ranking")

  def loadHierarchy(uri: String, mode: String) = {
    val rs = QueryEngine.performQuery(queryHierarchy, Seq(mode))

    val values = loadValues(uri, queryValues, mode)
    val rankings = loadValues(uri, queryRanking, mode)

    var rootUri = ""
    var rootName = ""

    var subindexes: MutableMap[String, Index] = HashMap.empty

    val colourPatterns = Array(
      ("#333366", "#116699"),
      ("#006644", "#88CC22"),
      ("#882222", "#CC2222"),
      ("#EE6611", "#EEAA11"));

    var count = 0;

    while (rs.hasNext) {
      val qs = rs.next

      val index = qs.getResource("?index").getURI.toString
      val indexLabel = qs.getLiteral("?indexLabel").getString
      rootUri = index
      rootName = indexLabel

      val subindex = qs.getResource("?subindex").getURI.toString
      val subindexLabel = qs.getLiteral("?subindexLabel").getString

      val component = qs.getResource("?component").getURI.toString
      val componentLabel = qs.getLiteral("?componentLabel").getString

      val ranking = if (rankings.contains(component)) rankings(component) else "0"
      val value = if (values.contains(component)) values(component) else "0"

      val componentObject = Index(UriFormatter.format(component), componentLabel,
        ranking, value, HashMap.empty, "", "")

      val element = if (subindexes.contains(subindex)) {
        subindexes(subindex)
      } else {
        val ranking = if (rankings.contains(subindex)) rankings(subindex) else "0"
        val value = if (values.contains(subindex)) values(subindex) else "0"

        val colour = colourPatterns(count)
        count += 1

        val subindexObject = Index(UriFormatter.format(subindex), subindexLabel,
          ranking, value, HashMap.empty, colour._1, colour._2)
        subindexes += subindex -> subindexObject
        subindexObject
      }

      element.children += component -> componentObject
    }

    val ranking = if (rankings.contains(rootUri)) rankings(rootUri) else "0"
    val value = if (values.contains(rootUri)) values(rootUri) else "0"

    Index(UriFormatter.format(rootUri), rootName, ranking, value, subindexes, "#000", "")
  }

  def loadValues(uri: String, query: String, mode: String) = {
    val rs = QueryEngine.performQuery(query, Seq(mode, uri))

    val map: MutableMap[String, String] = HashMap.empty

    while (rs.hasNext) {
      val qs = rs.next

      val uri = qs.getResource("?element").getURI.toString
      val value = qs.getLiteral("?value").getString

      map += uri -> value
    }
    map
  }
}