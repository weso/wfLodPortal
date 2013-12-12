Feature: Navigation
	In order to check the webpage navigation
	As a machine
	I want to be able to validate the view transition
	
	Scenario: Validate the webindex transitions
		Given I want to load the "home" page
		When I click the button with class "webindex"
		Then the text in the element "h1" should be "WEB INDEX"
		And the url should be "/webindex/v2013"
		And the "first" breadcrumb text should be "DATA"
		
		Given I want to load the "/webindex/v2013" page
		When I click the "first" breadcrumb
		Then the url should be "/"
		
		Given I want to load the "/webindex/v2013" page
		When I click the button with class "compare"
		Then the url should be "/webindex/compare"
		And the "first" breadcrumb text should be "DATA"
		And the "second" breadcrumb text should be "WEB INDEX"
		
		Given I want to load the "/webindex/compare" page
		When I click the button with class "compare"
		Then the url should be "/webindex/compare"
		And the "first" breadcrumb text should be "DATA"
		And the "second" breadcrumb text should be "WEB INDEX"
		
		Given I want to load the "/webindex/compare" page
		When I click the "first" breadcrumb
		Then the url should be "/"
		
		Given I want to load the "/webindex/compare" page
		When I click the "second" breadcrumb
		Then the url should be "/webindex/v2013"
		
		Given I want to load the "/webindex/compare" page
		When I click the button with id "compareButton"
		Then the url should be "/webindex/compare/COUNTRY(ALL)/YEAR(ALL)/INDICATOR(ALL)?"
		
		Given I want to load the "/webindex/compare/COUNTRY(ALL)/YEAR(ALL)/INDICATOR(ALL)?" page
		When I click the button with class "compare"
		Then the url should be "/webindex/compare"
		
		Given I want to load the "/webindex/compare/COUNTRY(ALL)/YEAR(ALL)/INDICATOR(ALL)?" page
		When I click the "first" breadcrumb
		Then the url should be "/"
		
		Given I want to load the "/webindex/compare/COUNTRY(ALL)/YEAR(ALL)/INDICATOR(ALL)?" page
		When I click the "second" breadcrumb
		Then the url should be "/webindex/v2013"
		