Feature: Product Purchase


  @Smoke @UI_API @ExcelData
  Scenario Outline: Verify user can create order using UI and verify using API

    Given User is authenticated using API
    And User logs into application
    When User adds product "<ProductName>" to cart
    And User navigates to cart page
    Then Product "<ProductName>" should be displayed in cart
    When User clicks checkout button
    And User enters country "<Country>"
    And User places the order
    Then Order success message should be displayed
    And Order should be validated using API



