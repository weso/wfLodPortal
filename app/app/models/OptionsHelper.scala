package app.models

import es.weso.wesby.models.Options
import es.weso.wesby.Configurable

/**
 * Extends an Options object
 * @param options the options to be extended
 */
class OptionsHelper(options: Options) {
  //Add here your custom attributes and/or methods
  val mode = if (options.uri contains "odb/") "odb" else "webindex"
  def version = defaultVersion
  def defaultVersion = OptionsHelper.defaultVersion
}

/**
 * OptionsHeelper Companion Object
 */
object OptionsHelper extends Configurable{
  implicit def optionsWrapper(options: Options) = new OptionsHelper(options)
  
  /**
   * Receive and options object and returns its OptionsHelper associated.
   * @param options the options to be extended
   */
  val defaultVersion = conf.getString("application.version")
}