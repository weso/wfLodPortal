package scala.es.weso.wfLodPortal

import org.scalatest.Matchers
import org.scalatest.selenium.Firefox
import cucumber.api.scala.EN
import cucumber.api.scala.ScalaDsl
import javax.naming.directory.NoSuchAttributeException

class CucumberSteps extends ScalaDsl with EN with Matchers with Firefox {

//  val host = "http://data.webfoundation.org"
  val host = "http://localhost:9000"

  Given("""^I want to load the "([^"]*)" page$""") { (page: String) =>
    page match {
      case "home" => go to (host + "/")
      case other => go to (host + other)
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

      go to (host + url)
  }

  Given("""^I want to load the "([^"]*)" contry page from "([^"]*)"$""") { (repo: String, country: String) =>
    val baseUrl = repo match {
      case "webindex" => "/webindex/v2013/country/"
      case "odb" => "/odb/v2013/country/"
    }
    val url = host + baseUrl + country
    go to (url)
  }

  When("""^I click the button with class "([^"]*)"$""") {
    (buttonClassName: String) => click on className(buttonClassName)
  }

  When("""^I click the link with xpath "(.*?)"$""") { (path: String) =>
    val element: Option[Element] = find(xpath(path))
    clickOn(element)
  }

  When("""^I click the link with id "(.*?)"$""") { (id: String) =>
    val element: Option[Element] = find(id)
    clickOn(element)
  }

  When("""^I click the "([^"]*)" breadcrumb$""") { (order: String) =>
    clickOn(getBreadrcumb(order))
  }
  When("""^I click the button with id "([^"]*)"$""") { (buttonId: String) =>
    clickOn(find(id(buttonId)))
  }

  Then("""^the text in the element with xpath "([^"]*)" should be "([^"]*)"$""") { (path: String, expected: String) =>
    val element: Option[Element] = find(xpath(path))
    checkText(element, expected)
  }
  Then("""^there should be an element with id "([^"]*)"$""") { (elem: String) =>
    val element = find(id(elem))
    checkExists(element)
  }

  Then("""^the "([^"]*)" breadcrumb text should be "([^"]*)"$""") { (order: String, expected: String) =>
    val element = getBreadrcumb(order);
    checkText(element, expected)
  }

  Then("""^the text in the element "([^"]*)" should be "([^"]*)"$""") {
    (elem: String, expected: String) =>
      val element: Option[Element] = find(tagName(elem))
      checkText(element, expected.replace("_", " "))
  }

  Then("""^the page title should be "([^"]*)"$""") { (expected: String) =>
    pageTitle should be(expected)
  }

  Then("""^the "([^"]*)" breadcrumb href should be "([^"]*)"$""") { (order: String, expected: String) =>
    val element = getBreadrcumb(order);
    element match {
      case Some(e) => e.attribute("href") match {
        case Some(h) => h should be(host + expected)
        case _ => throw new NoSuchAttributeException
      }
      case _ => throw new NoSuchElementException
    }
  }
  Then("""^the "([^"]*)" button href should be "([^"]*)"$""") { (btnClass: String, expected: String) =>
    val element = find(className(btnClass))
    element match {
      case Some(e) => e.attribute("href") match {
        case Some(h) => h should be(host + expected)
        case _ => throw new NoSuchAttributeException
      }
      case _ => throw new NoSuchElementException
    }
  }

  Then("""^the url should be "([^"]*)"$""") { (subUrl: String) =>
    currentUrl should be (host + subUrl)
  }

  def getBreadrcumb(order: String) = order match {
    case "first" => find(xpath("/html/body/div[1]/div/nav/a[" + 1 + "]"))
    case "second" => find(xpath("/html/body/div[1]/div/nav/a[" + 2 + "]"))
    case "third" => find(xpath("/html/body/div[1]/div/nav/a[" + 3 + "]"))
  }

  def applyElement(elem: Option[Element], f: Element => Unit) {
    elem match {
      case Some(e) => f(e)
      case _ => throw new NoSuchElementException
    }
  }

  def clickOn(elem: Option[Element]) {
    applyElement(elem, x => click on (x))
  }

  def checkText(elem: Option[Element], expected: String) {
    applyElement(elem, x => x.text should be(expected))
  }

  def checkExists(elem: Option[Element]) {
    applyElement(elem, x => {})
  }

}