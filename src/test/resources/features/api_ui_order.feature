Feature: API UI Order Validation

  @Smoke @API_UI
  Scenario: Create order using API and verify order in UI
    Given User is authenticated using API
    And User creates order using API for product "ZARA COAT 3" and country "India"
    When User logs into application
    And User navigates to Orders page
    Then User click on view order button
    And Product "ZARA COAT 3" should be displayed with order Id