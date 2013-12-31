package scala.es.weso.wfLodPortal.utils

import org.scalatest.Matchers
import javax.naming.directory.NoSuchAttributeException
import org.scalatest.selenium.Firefox
import org.scalatest.selenium.WebBrowser.Element
import org.scalatest.selenium.HtmlUnit
import org.scalatest.selenium.InternetExplorer
import org.scalatest.selenium.Chrome

object SeleniumUtils extends Matchers with Firefox {

  def applyElement(elem: Option[Element], f: Element => Unit) {
    elem match {
      case Some(e) => f(e)
      case _ => throw new NoSuchElementException
    }
  }

  def applyAttribute(elem: Option[Element], attr: String, f: String => Unit) {
    def checkAttribute(e: Element) {
      e.attribute(attr) match {
        case Some(h) => f(h)
        case _ => throw new NoSuchAttributeException
      }
    }
    applyElement(elem, checkAttribute)
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

  def checkHref(elem: Option[Element], expected: String) {
    applyAttribute(elem, "href", x => x should be(expected))
  }
  
  def checkPageTitle(expected: String) {
    pageTitle should be(expected)
  }
  
  def checkCurrentUrl(expected: String) {
    currentUrl should be(expected)
  }
  
  def checkStyle(elem: Option[Element], expected: String) {
    applyAttribute(elem, "style", x => x should be(expected))
  }
  
  def findById(reqId: String) = find(id(reqId))

  def findByXpath(reqXpath: String) = find(xpath(reqXpath))

  def findByClassName(reqClass: String) = find(className(reqClass))

  def findByTagName(reqTag: String) = find(tagName(reqTag))
  
  def loadPage(url: String) = go to (url)

}