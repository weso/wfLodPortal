package scala.es.weso.wfLodPortal

import org.scalatest.Matchers
import org.scalatest.selenium.Firefox

import cucumber.api.scala.EN
import cucumber.api.scala.ScalaDsl

class WebpageSteps extends ScalaDsl with EN with Matchers with Firefox {

  private val host = "http://data.webfoundation.org"

  Given("""^I want to load the "([^"]*)" page$""") { (page: String) =>
    page match {
      case "home" => go to (host + "/")
      case other => go to (host + other)
    }
  }

  When("""^I click the button with class "([^"]*)"$""") {
    (buttonClassName: String) => click on className(buttonClassName)
  }

  Then("""^the text in the element "([^"]*)" should be "([^"]*)"$""") {
    (elem: String, expected: String) =>
      val element: Option[Element] = find(tagName(elem))
      element match {
        case Some(e) => e.text should be(expected)
        case _ => throw new NoSuchElementException
      }
  }

  Then("""^the page title should be "([^"]*)"$""") { (expected: String) =>
    pageTitle should be(expected)
  }

  When("""^I click the link with xpath "([^"]*)"$""") { (path: String) =>
    val element: Option[Element] = find(xpath(path))
    element match {
      case Some(e) => click on (e)
      case _ => throw new NoSuchElementException
    }
  }
  Then("""^the text in the element with xpath "([^"]*)" should be "([^"]*)"$"""){ (path:String, expected:String) =>
  //// Express the Regexp above with the code you wish you had
    val element: Option[Element] = find(xpath(path))
    element match {
      case Some(e) => e.text should be (expected)
      case _ => throw new NoSuchElementException
    }
}

}