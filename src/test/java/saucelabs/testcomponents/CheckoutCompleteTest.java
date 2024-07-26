package saucelabs.testcomponents;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucelabs.common.SiteHeader;
import saucelabs.pageobjects.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CheckoutCompleteTest extends BaseTest {
    //declare any global variables
    String dataFilePath = System.getProperty("user.dir")+"/src/test/java/saucelabs/data/happypath/checkoutComplete.json";
    //test cases
    @Test(dataProvider = "verifyThankYouMessageData")
    void verifyThankYouMessage(HashMap<String,Object> input) throws Exception {
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

        //8.Click Finish button
        CheckoutCompletePage checkoutCompletePageObj = checkoutOverviewPageObj.proceedToFinish();
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("checkout-complete.html"), "Checkout: Complete page is displayed successfully");

        //9.Verify the thank you message and Orderdispatch text
        Assert.assertTrue(checkoutCompletePageObj.getThanksText().equals(input.get("thanksText")),"Thanks text is displayed correctly");
        Assert.assertTrue(checkoutCompletePageObj.getOrderDispatchText().equals(input.get("orderDispatchMessage")),"Order dispatch message is displayed correctly");

        checkoutCompletePageObj.proceedBackToProducts();
        currentURL=driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/inventory.html"), "Inventory page is displayed successfully");

    }
    //data for test cases
    @DataProvider
    Object[][] verifyThankYouMessageData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(0)}};
    }

}
