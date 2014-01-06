package es.weso.wfLodPortal.sparql.custom

import es.weso.wesby.Configurable

trait CustomQuery extends Configurable {

  val baseUri = conf.getString("sparql.baseuri")
  val endpoint = conf.getString("sparql.endpoint")

  def checkMode(param: String, version: String) = {
    new StringBuilder(baseUri)
      .append(param).append("/")
      .append(version).append("/").toString
  }
}