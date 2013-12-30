Feature: Country view
  In order to check the country view
  As a machine
  I want to be able to validate the country view and the breadcrumbs

  Scenario Outline: Validate the country view
    Given I want to load the <repository> contry page from <country_short_name>
    Then the text in the element "h1" should be <country_name>
    And the "second" breadcrumb text should be <repo_name>
    And the "third" breadcrumb text should be "COUNTRY"

    Examples: 
      | repository | country_short_name | country_name | repo_name   |
      | "webindex" | "KEN"              | "Kenya"      | "WEB INDEX" |
      | "odb"      | "CHN"              | "China"      | "ODB"       |

  Scenario Outline: Validate the breadcrumbs
    Given I want to load the "/webindex/v2013" page
    When I click the link with xpath <region>
    And I click the link with xpath <country>
    Then the text in the element "h1" should be <country_name>
    And the text in the element with xpath "/html/body/div[1]/div/nav[1]/a[3]" should be "COUNTRY"
    And the "second" breadcrumb text should be "WEB INDEX"

    Examples: 
      | region                                        | country                                                 | country_name               |
      | "/html/body/div/div/div[1]/div[1]/ul/li[1]/a" | "/html/body/div/div/table/tbody/tr[1]/td[2]/ul/li[4]/a" | "Senegal"                  |
      | "/html/body/div/div/div[1]/div[1]/ul/li[2]/a" | "/html/body/div/div/table/tbody/tr[1]/td[2]/ul/li[5]/a" | "United States of America" |
