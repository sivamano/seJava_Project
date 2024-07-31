package saucelabs.testcomponents;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucelabs.common.SiteHeader;
import saucelabs.pageobjects.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CheckoutOverviewTest extends BaseTest {

    //declare any global variables
    String dataFilePath = System.getProperty("user.dir")+"/src/test/java/saucelabs/data/checkoutOverview.json";
    //test
    @Test(dataProvider = "verifyInfoInCheckoutOverviewPageData")
    void verifyInfoInCheckoutOverviewPage(HashMap<String,Object> input) throws Exception {
        //1.Login as Standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2.Add a product to cart
        String prodName = (String) input.get("product");
        productsPageObj.addProductToCart(prodName);

        //3.Store the details of the product in a string array
        String[] prodDetails = productsPageObj.getProductDetails(prodName);

        //4.Go to "Your Cart"
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //5.Click "Checkout" and navigate to "Your Information" page
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //6.Provide personal information
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));

        //7.Navigate to checkoutOverviewPage
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //8.Verify the following information in "Checkout:Overview" page
                    /* Description of products
                        Value of products
                        Payment Information
                        Shipping Information
                         */

        //name of products
        Assert.assertTrue(checkoutOverviewPageObj.verifyNameOfItemsInCart(prodDetails[0]));
        //desc of products
        Assert.assertTrue(checkoutOverviewPageObj.verifyDescOfItemsInCart(prodDetails[1]));
        //price of products
        Assert.assertTrue(checkoutOverviewPageObj.verifyPriceOfItemsInCart(prodDetails[2]));
        //payment info
        Assert.assertTrue(checkoutOverviewPageObj.verifyPaymentInformation((String) input.get("paymentInfo")));
        //shipping info
        Assert.assertTrue(checkoutOverviewPageObj.verifyShippingInformation((String) input.get("shippingInfo")));


    }

    @Test(dataProvider = "cancelOrderProcessData")
    void cancelOrderProcess(HashMap<String,Object> input) {
        //1.Login as Standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2.Add a product to cart
        String prodName = (String) input.get("product");
        productsPageObj.addProductToCart(prodName);

        //3.Store the details of the product in a string array
        String[] prodDetails = productsPageObj.getProductDetails(prodName);

        //4.Go to "Your Cart"
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //5.Click "Checkout" and navigate to "Your Information" page
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //6.Provide personal information
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));

        //7.Navigate to checkoutOverviewPage
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //8.Verify the Checkoutoverview page is reached and cancel the order
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/checkout-step-two.html"),"Checkout : Overview page is displayed");
        checkoutOverviewPageObj.cancelOrderProcess();
        currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/inventory.html"), "inventory page is displayed");

    }

    @Test(dataProvider = "valueComputeAndVerifyData")
    void valueComputeAndVerify(HashMap<String,Object> input) {
        //1.Login as Standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2.Add a product to cart
        String prodName = (String) input.get("product");
        productsPageObj.addProductToCart(prodName);

        //3.Store the details of the product in a string array
        String[] prodDetails = productsPageObj.getProductDetails(prodName);

        //4.Go to "Your Cart"
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //5.Click "Checkout" and navigate to "Your Information" page
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //6.Provide personal information
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));

        //7.Navigate to checkoutOverviewPage
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //8.Compute and verify the cart value

        float totalB4Tax[] = checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        Assert.assertEquals(totalB4Tax[0], totalB4Tax[1],"Total value of items(before tax) is computed correctly");
        float totalValue[] = checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        Assert.assertEquals(totalValue[0], totalValue[1], "Total value of items is computed and displayed correctly");
    }

    @Test (dataProvider = "navigateToYourCartPageData")
    void navigateToYourCartPage(HashMap<String,Object> input) {
        //1.Login as Standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2.Add a product to cart
        String prodName = (String) input.get("product");
        productsPageObj.addProductToCart(prodName);

        //3.Store the details of the product in a string array
        String[] prodDetails = productsPageObj.getProductDetails(prodName);

        //4.Go to "Your Cart"
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //5.Click "Checkout" and navigate to "Your Information" page
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //6.Provide personal information
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));

        //7.Navigate to checkoutOverviewPage
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //8.Click shopping cart link
        siteHeaderObj.clickShoppingCartLink();

        //9.Verify if Your Cart page is displayed properly
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/cart.html"));

    }

    @Test(dataProvider = "finishBtnErrorUserData")
    void finishBtnErrorUser(HashMap<String,Object> input) throws Exception {
        //1.Login as error user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        //2.Add a product to cart
        String prodName = (String) input.get("product");
        productsPageObj.addProductToCart(prodName);
        //3.Go to "Your Cart"
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //4.Click "Checkout" and navigate to "Your Information" page
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //5.Verify if the user is able to provide information in "Last Name" field
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //6.Click Finish Button
        checkoutOverviewPageObj.proceedToFinish();

        //7. Verify that Checkout Complete page is NOT displayed
        String currentURL = driver.getCurrentUrl();
        Assert.assertFalse(currentURL.contains("checkout-complete.html"), "Checkout: Complete page is not displayed, because Finish button is not working for error user");
    }

    //data for test
    @DataProvider
    Object[][] verifyInfoInCheckoutOverviewPageData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(0)}};
    }

    @DataProvider
    Object[][] cancelOrderProcessData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(1)}};
    }

    @DataProvider
    Object[][] valueComputeAndVerifyData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(2)}};
    }

    @DataProvider
    Object[][] navigateToYourCartPageData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(3)}};
    }

    @DataProvider
    Object[][] finishBtnErrorUserData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(4)}};
    }
}
