package stepDefinitions.ui;

import context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.OrdersPage;

import static org.junit.Assert.assertEquals;

public class OrdersStepDefinitions {
    OrdersPage ordersPage=new OrdersPage();
    private final TestContext testContext;

    public OrdersStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("User navigates to Orders page")
    public void user_navigates_to_orders_page() {
        ordersPage.clickOrdersTab();
    }
    @Then("User click on view order button")
    public void user_click_on_view_order_button() {
        String orderIdAPI = testContext.getOrderId();
        System.out.println("fetch from API : "+orderIdAPI);
        ordersPage.clickViewOrder(orderIdAPI);
    }
    @Then("Product {string} should be displayed with order Id")
    public void product_should_be_displayed_with_order_id(String actualProduct) {
        assertEquals("Product Mismatched",ordersPage.getProductName(),actualProduct);
        assertEquals("Order Id mismatch",ordersPage.getOrderId(),testContext.getOrderId());

    }

}
