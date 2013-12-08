Feature: Cucumber
  In order to check the webpage
  As a machine
  I want to be able to validate the webpage navigation

  Scenario: Validate the home webpage
    Given I want to load the "home" page
    When I click the button with class "webindex"
    Then the page title should be "DATA PORTAL"
    Given I want to load the "home" page
    When I click the button with class "webindex"
    Then the text in the element "h1" should be "WEB INDEX"
    Given I want to load the "home" page
    When I click the button with class "odb"
    Then the text in the element "h1" should be "OPEN DATA BAROMETER"

  Scenario Outline: Validate the breadcrumbs
    Given I want to load the "/webindex/v2013" page
    When I click the link with xpath <region>
    And I click the link with xpath <country>
    Then the text in the element "h1" should be <country_name>
    And the text in the element with xpath "/html/body/div[1]/div/nav[1]/a[3]" should be "COUNTRY"

    Examples: 
      | region                                        | country                                                  | country_name               |
      | "/html/body/div/div/div[1]/div[1]/ul/li[1]/a" | "/html/body/div/div/table/tbody/tr[1]/td[2]/ul/li[4]/a"  | "Senegal"                  |
      | "/html/body/div/div/div[1]/div[1]/ul/li[2]/a" | "/html/body/div/div/table/tbody/tr[1]/td[2]/ul/li[5]/a"  | "United States of America" |
      | "/html/body/div/div/div[1]/div[1]/ul/li[3]/a" | "/html/body/div/div/table/tbody/tr[1]/td[2]/ul/li[9]/a"  | "China"                    |
      | "/html/body/div/div/div[1]/div[1]/ul/li[4]/a" | "/html/body/div/div/table/tbody/tr[1]/td[2]/ul/li[21]/a" | "Spain"                    |
      | "/html/body/div/div/div[1]/div[1]/ul/li[5]/a" | "/html/body/div/div/table/tbody/tr[1]/td[2]/ul/li[5]/a"  | "Qatar"                    |

  Scenario: Validate the comparer
    Given I want to load the "home" page
    When I click the button with class "webindex"
    And I click the button with class "compare"
    And I click the link with xpath "//*[@id="countrySelector"]/ul[2]/li[1]/a/div[1]"
    And I click the link with xpath "//*[@id="countrySelector"]/ul[2]/li[1]/ul/li[1]/a"
    And I click the link with xpath "//*[@id="countrySelector"]/ul[2]/li[1]/ul/li[2]/a"
    And I click the link with xpath "//*[@id="indicatorSelector"]/ul[2]/li[2]/a"
    And I click the link with xpath "//*[@id="yearSelector"]/ul[2]/li[7]/a"
    And I click the link with id "compareButton"
    Then there should be an element with id "graphs"
