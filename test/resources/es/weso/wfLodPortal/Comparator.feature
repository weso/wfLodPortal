Feature: Comparer
  In order to check the comparer
  As a machine
  I want to be able to validate the comparer and precomparer view

  Scenario: Validate the precomparer
    Given I want to load the "home" page
    When I click the button with class "webindex"
    And I click the button with class "compare"
    And I click the link with id "compareButton"
    Then there should be an element with id "graphs"

  Scenario: Validate the odb comparator view
    Given I want to load the "odb" comparer page with "BEN" and "BWA", the year "2012" and the indicator "ODB.2013.I.GOV"
    Then there should be an element with id "graphs"
    And the "second" breadcrumb text should be "ODB"
    And the "second" breadcrumb href should be "/odb/v2013"
    And the "compare" button href should be "/odb/compare"

  Scenario: Validate the webindex comparator view
    Given I want to load the "webindex" comparer page with "USA" and "CHN", the year "2012" and the indicator "FH_B"
    Then there should be an element with id "graphs"
    And the "second" breadcrumb text should be "WEB INDEX"
    And the "second" breadcrumb href should be "/webindex/v2013"
    And the "compare" button href should be "/webindex/compare"
