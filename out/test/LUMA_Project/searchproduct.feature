Feature: Searching Product

  Scenario: Searching Product list

   Given that the customer is on the Home page
  And enters a product name in the search field "digital watch"
  When the customers clicks the search icon to search
  Then the system should return a list of search result