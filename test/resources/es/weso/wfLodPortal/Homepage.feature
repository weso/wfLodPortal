Feature: Homepage
  In order to check the homepage
  As a machine
  I want to be able to validate its buttons

Scenario: Validate the home webpage
    Given I want to load the "home" page
    When I click the button with class "webindex"
    Then the page title should be "DATA PORTAL"
    And the text in the element "h1" should be "WEB INDEX"
    Given I want to load the "home" page
    When I click the button with class "odb"
    Then the page title should be "DATA PORTAL"
    And the text in the element "h1" should be "OPEN DATA BAROMETER"
