Feature: Product Purchase

  Background:
    Given User logs into application

  @Smoke
  Scenario: Verify user can purchase product
    When User adds product "ZARA COAT 3" to cart
    And User navigates to cart page
    Then Product "ZARA COAT 3" should be displayed in cart
    When User clicks checkout button
    And User enters country "India"
    And User places the order
    Then Order success message should be displayed