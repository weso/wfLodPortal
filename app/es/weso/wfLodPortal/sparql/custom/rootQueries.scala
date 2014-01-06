package es.weso.wfLodPortal.sparql.custom

import es.weso.wesby.Configurable

object RootQueries extends CustomQuery with Configurable {

  val queries = List(
    ("root.webindex.ranking", "root.webindex.ranking.description"),
    ("root.webindex.allIndicators", "root.webindex.allIndicators.description"),
    ("root.webindex.allIndicatorsByComponentAndIndex", "root.webindex.allIndicatorsByComponentAndIndex.description"),
    ("root.webindex.allCountries", "root.webindex.allCountries.description"))

  def loadQueries(): List[(String, String, String)] = {
    for (query <- queries) yield {
      (conf.getString(query._2), conf.getString(query._1), endpoint)
    }
  }

}