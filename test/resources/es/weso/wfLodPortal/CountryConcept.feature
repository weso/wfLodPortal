Feature: Country Concept view
	In order to check the Country Concept view
	As a machine
	I want to be able to validate the Country Concept view and its contents
	
	Scenario: Validate the page title
	Given I want to load the "/ontology/Country" page
	Then the text in the element "h1" should be "Country concept"
	
	Scenario: Validate the tab loading
	Given I want to load the "/ontology/Country" page
	Then there should be an element with class "tabs"
	
	Scenario: Validate the map loading
	Given I want to load the "/ontology/Country" page
	Then there should be an element with class "jvectormap-container"
	
	Scenario: Valide the country list loading
	Given I want to load the "/ontology/Country" page
	Then there should be an element with class "country-list"
	
	Scenario: Check the WebIndex tab 
	Given I want to load the "/ontology/Country" page
	When I click the button with id "wi-button"
	#Only the WebIndex tab content must be showing (default option)
	Then the "div-webindex" element "style" attribute should be ""
	And the "div-odb" element "style" attribute should be "display: none;"
	
	Scenario: Check the ODB tab 
	Given I want to load the "/ontology/Country" page
	When I click the button with id "odb-button"
	#Only the ODB tab content must be showing
	Then the "div-webindex" element "style" attribute should be "display: none;"
	And the "div-odb" element "style" attribute should be "display: block;"
	
	Scenario: Check the WebIndex country links
	Given I want to load the "/ontology/Country" page
	When I click the button with id "wi-button"
	Then the href in the element with xpath "//*[@id="div-webindex"]/nav/a[1]" should be "http://data.webfoundation.org/webindex/v2013/country/AFG"
	
	Scenario: Check the ODB country links
	Given I want to load the "/ontology/Country" page
	When I click the button with id "odb-button"
	Then the href in the element with xpath "//*[@id="div-odb"]/nav/a[1]" should be "http://data.webfoundation.org/odb/v2013/country/AFG"
	