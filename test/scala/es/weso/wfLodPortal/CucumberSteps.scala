package scala.es.weso.wfLodPortal

import scala.es.weso.wfLodPortal.utils.SeleniumUtils

import org.scalatest.Matchers

import cucumber.api.scala.EN
import cucumber.api.scala.ScalaDsl

class CucumberSteps extends ScalaDsl with EN with Matchers {

  val host = "http://data.webfoundation.org"
  //  val host = "http://localhost:9000"

  Given("""^I want to load the "([^"]*)" page$""") { (page: String) =>
    page match {
      case "home" => SeleniumUtils.loadPage(host + "/")
      case other => SeleniumUtils.loadPage(host + other)
    }
  }

  Given("""^I want to load the "([^"]*)" comparer page with "([^"]*)" and "([^"]*)", the year "([^"]*)" and the indicator "([^"]*)"$""") {
    (repo: String, country1: String, country2: String, year: String, indicator: String) =>
      val baseUrl = repo match {
        case "webindex" => "/webindex/compare"
        case "odb" => "/odb/compare"
      }
      val cSubUrl = "/COUNTRY(" + country1 + "," + country2 + ")"
      val ySubUrl = "/YEAR(" + year + ")"
      val iSubUrl = "/INDICATOR(" + indicator + ")"
      val url = baseUrl + cSubUrl + ySubUrl + iSubUrl

      SeleniumUtils.loadPage(host + url)
  }

  Given("""^I want to load the "([^"]*)" contry page from "([^"]*)"$""") { (repo: String, country: String) =>
    val baseUrl = repo match {
      case "webindex" => "/webindex/v2013/country/"
      case "odb" => "/odb/v2013/country/"
    }
    val url = host + baseUrl + country
    SeleniumUtils.loadPage(url)
  }

  When("""^I click the button with class "([^"]*)"$""") { (buttonClassName: String) =>
      SeleniumUtils.clickOn(SeleniumUtils.findByClassName(buttonClassName))
    
  }

  When("""^I click the link with xpath "(.*?)"$""") { (path: String) =>
    SeleniumUtils.clickOn(SeleniumUtils.findByXpath(path))
  }

  When("""^I click the link with id "(.*?)"$""") { (id: String) =>
    SeleniumUtils.clickOn(SeleniumUtils.findById(id))
  }

  When("""^I click the "([^"]*)" breadcrumb$""") { (order: String) =>
    SeleniumUtils.clickOn(getBreadrcumb(order))
  }
  When("""^I click the button with id "([^"]*)"$""") { (buttonId: String) =>
    SeleniumUtils.clickOn(SeleniumUtils.findById(buttonId))
  }

  Then("""^the text in the element with xpath "([^"]*)" should be "([^"]*)"$""") { (path: String, expected: String) =>
    val element = SeleniumUtils.findByXpath(path)
    SeleniumUtils.checkText(element, expected)
  }
  
  Then("""^the href in the element with xpath "(.*?)" should be "([^"]*)"$""") { (path: String, expected: String) =>
    val element = SeleniumUtils.findByXpath(path)
    SeleniumUtils.checkHref(element, expected)
  }
  
  Then("""^there should be an element with id "([^"]*)"$""") { (elem: String) =>
    val element = SeleniumUtils.findById(elem)
    SeleniumUtils.checkExists(element)
  }
  
  Then("""^there should be an element with class "([^"]*)"$""") { (elem: String) =>
    val element = SeleniumUtils.findByClassName(elem)
    SeleniumUtils.checkExists(element)
  }

  Then("""^the "([^"]*)" breadcrumb text should be "([^"]*)"$""") { (order: String, expected: String) =>
    val element = getBreadrcumb(order);
    SeleniumUtils.checkText(element, expected)
  }

  Then("""^the text in the element "([^"]*)" should be "([^"]*)"$""") {
    (elem: String, expected: String) =>
      val element = SeleniumUtils.findByTagName(elem)
      SeleniumUtils.checkText(element, expected.replace("_", " "))
  }

  Then("""^the page title should be "([^"]*)"$""") { (expected: String) =>
    SeleniumUtils.checkPageTitle(expected)
  }

  Then("""^the "([^"]*)" breadcrumb href should be "([^"]*)"$""") { (order: String, expected: String) =>
    val element = getBreadrcumb(order);
    SeleniumUtils.checkHref(element, host + expected)
  }
  Then("""^the "([^"]*)" button href should be "([^"]*)"$""") { (btnClass: String, expected: String) =>
    val element = SeleniumUtils.findByClassName(btnClass)
    SeleniumUtils.checkHref(element, host + expected)
  }
  
  Then("""^the "([^"]*)" element "([^"]*)" attribute should be "([^"]*)"$""") { (elemId: String, attr: String, expected: String) =>
    val element = SeleniumUtils.findById(elemId)
    SeleniumUtils.checkStyle(element, expected)
  }

  Then("""^the url should be "([^"]*)"$""") { (subUrl: String) =>
    SeleniumUtils.checkCurrentUrl(host + subUrl)
  }

  def getBreadrcumb(order: String) = order match {
    case "first" => SeleniumUtils.findByXpath("/html/body/div[1]/div/nav/a[" + 1 + "]")
    case "second" => SeleniumUtils.findByXpath("/html/body/div[1]/div/nav/a[" + 2 + "]")
    case "third" => SeleniumUtils.findByXpath("/html/body/div[1]/div/nav/a[" + 3 + "]")
  }

}