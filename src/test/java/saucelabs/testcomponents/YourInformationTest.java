package saucelabs.testcomponents;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucelabs.common.SiteHeader;
import saucelabs.pageobjects.ProductsPage;
import saucelabs.pageobjects.YourCartPage;
import saucelabs.pageobjects.YourInformationPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YourInformationTest extends BaseTest{

    //declare any global variables
    String dataFilePath = System.getProperty("user.dir")+ "/src/test/java/saucelabs/data/yourInformation.json";
    //Testcases
    @Test(dataProvider = "enterInfoInYourInfoPageData")
    void enterInfoInYourInfoPage(HashMap<String,Object> input) {
        //1.login as standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2. Add desired product to cart
        ArrayList<String> desiredProducts = (ArrayList<String>) input.get("products");
        for (String desiredProduct : desiredProducts) {
            productsPageObj.addProductToCart(desiredProduct);
        }

        //3. Go to YourCart Page
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //4.Verify the product details in yourCartPage
        for (String desiredProduct : desiredProducts) {
            Assert.assertTrue(yourCartPageObj.verifyItemsInCart(desiredProduct), "The added product '" + desiredProduct + "' is present in yourCart page");

        }

        //5.Navigate to yourinfo page
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //6.Enter personal information
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        yourInformationPageObj.proceedFurther();
        String currentURL = driver.getCurrentUrl();

        //7.Navigate to Checkout Overview Page
        Assert.assertTrue(currentURL.contains("/checkout-step-two.html"));

    }

    @Test(dataProvider = "cancelProcessFromYourInformationPageData")
    void cancelProcessFromYourInformationPage(HashMap<String,Object> input) {
        //1.login as standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2. Add desired product to cart
        ArrayList<String> desiredProducts = (ArrayList<String>) input.get("products");
        for (String desiredProduct : desiredProducts) {
            productsPageObj.addProductToCart(desiredProduct);
        }

        //3. Go to YourCart Page
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //4.Verify the product details in yourCartPage
        for (String desiredProduct : desiredProducts) {
            Assert.assertTrue(yourCartPageObj.verifyItemsInCart(desiredProduct), "The added product '" + desiredProduct + "' is present in yourCart page");

        }

        //5.Navigate to yourinfo page
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //6.Enter personal information
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        yourInformationPageObj.cancelProcess();
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("cart.html"));
    }

    @Test(dataProvider = "navigateToCartPageFromInfoPageData")
    void navigateToCartPageFromInfoPage(HashMap<String,Object> input) {
        //1.login as standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2. Add desired product to cart
        ArrayList<String> desiredProducts = (ArrayList<String>) input.get("products");
        for (String desiredProduct : desiredProducts) {
            productsPageObj.addProductToCart(desiredProduct);
        }

        //3. Go to YourCart Page
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //4.Verify the product details in yourCartPage
        for (String desiredProduct : desiredProducts) {
            Assert.assertTrue(yourCartPageObj.verifyItemsInCart(desiredProduct), "The added product '" + desiredProduct + "' is present in yourCart page");

        }

        //5.Navigate to yourinfo page
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //6.Click Cart link
        siteHeaderObj.clickShoppingCartLink();

        //7.Verify if your cart page is displayed successfully
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("cart.html"));
    }

    @Test(dataProvider = "enterLastNameErrorUserData")
    void enterLastNameErrorUser(HashMap<String,Object> input) {
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

        String[] valueOfField = yourInformationPageObj.valueOfPersonalDetails();

        Assert.assertEquals(valueOfField[1],"","The value of LastName is empty");
    }

    //Data for Testcases
    @DataProvider
    Object[][] enterInfoInYourInfoPageData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(0)}};
    }

    @DataProvider
    Object[][] cancelProcessFromYourInformationPageData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(1)}};
    }

    @DataProvider
    Object[][] navigateToCartPageFromInfoPageData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(2)}};
    }

    @DataProvider
    Object[][] enterLastNameErrorUserData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(3)}};
    }
}
