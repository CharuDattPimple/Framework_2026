Feature: Product Purchase

  Background:

    Given User is authenticated using API

   @UI_API
  Scenario: Verify user can create order using UI and verify using in API
    Given User logs into application
    When User adds product "ZARA COAT 3" to cart
    And User navigates to cart page
    Then Product "ZARA COAT 3" should be displayed in cart
    When User clicks checkout button
    And User enters country "India"
    And User places the order
    Then Order success message should be displayed
    And Order should be validated using API

  @API_UI
  Scenario: Create order using API and verify order in UI
    And User creates order using API for product "ZARA COAT 3" and country "India"
    When User logs into application
    And User navigates to Orders page
    Then User click on view order button
    And Product "ZARA COAT 3" should be displayed with order Id

