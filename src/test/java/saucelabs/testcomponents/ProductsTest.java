package saucelabs.testcomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucelabs.common.SiteHeader;
import saucelabs.pageobjects.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductsTest extends BaseTest {

    String dataFilePath = System.getProperty("user.dir") + "/src/test/java/saucelabs/data/products.json";

    @Test(dataProvider = "getSingleProdData", groups={"E2E","Products Test"})
    public void orderASingleProduct(HashMap<String, Object> input) throws Exception {


        //1.Login
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));


        //2.Verify the following product details:
        //product name
        ArrayList<String> desiredItems = (ArrayList<String>) input.get("products");
        for (String desiredItem : desiredItems) {
            WebElement productName = productsPageObj.selectProductByName(desiredItem);
            Assert.assertEquals(desiredItem, productName.findElement(By.xpath(".//div[@class=\"inventory_item_name \"]")).getText());
            productsPageObj.addProductToCart(desiredItem);
        }

        //3.Go to yourCartPage
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //4.Verify the information in your cart page
        for (String desiredItem : desiredItems) {
            boolean x = yourCartPageObj.verifyItemsInCart(desiredItem);
            Assert.assertTrue(x, desiredItem + " is present in cart");
        }
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //5.Enter information in yourInformationPage
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //6.Verify the price value computation in CheckoutOverviewPage
        float[] totalB4Tax = checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        Assert.assertEquals(totalB4Tax[0], totalB4Tax[1]);
        float[] totalValue = checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        Assert.assertEquals(totalValue[0], totalValue[1]);
        CheckoutCompletePage checkoutCompletePageObj = checkoutOverviewPageObj.proceedToFinish();

        //7.Verify the thanksText and go back to products page
        String thanksTextDisplayed = checkoutCompletePageObj.getThanksText();
        Assert.assertEquals(thanksTextDisplayed, (String) input.get("thanksText"));
        checkoutCompletePageObj.proceedBackToProducts();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
    }

    @Test(dataProvider = "getMultipleProdData", groups={"E2E","Products Test"})
    public void orderMultipleProduct(HashMap<String, Object> input) throws Exception {


        //1.Login
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));


        //2.Verify the following product details:
        //product name
        ArrayList<String> desiredItems = (ArrayList<String>) input.get("products");
        for (String desiredItem : desiredItems) {
            WebElement productName = productsPageObj.selectProductByName(desiredItem);
            Assert.assertEquals(desiredItem, productName.findElement(By.xpath(".//div[@class=\"inventory_item_name \"]")).getText());
            productsPageObj.addProductToCart(desiredItem);
        }

        //3.Go to yourCartPage
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //4.Verify the information in your cart page
        for (String desiredItem : desiredItems) {
            boolean x = yourCartPageObj.verifyItemsInCart(desiredItem);
            Assert.assertTrue(x, desiredItem + " is present in cart");
        }
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //5.Enter information in yourInformationPage
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //6.Verify the price value computation in CheckoutOverviewPage
        float[] totalB4Tax = checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        Assert.assertEquals(totalB4Tax[0], totalB4Tax[1]);
        float[] totalValue = checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        Assert.assertEquals(totalValue[0], totalValue[1]);
        CheckoutCompletePage checkoutCompletePageObj = checkoutOverviewPageObj.proceedToFinish();

        //7.Verify the thanksText and go back to products page
        String thanksTextDisplayed = checkoutCompletePageObj.getThanksText();
        Assert.assertEquals(thanksTextDisplayed, (String) input.get("thanksText"));
        checkoutCompletePageObj.proceedBackToProducts();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
    }

    @Test(dataProvider = "sortHiLoData",groups={"E2E","Products Test"})
    public void orderCostliestProduct(HashMap<String, Object> input) throws Exception {
        //1.Login
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2.verify if the products are Sorted from highest to lowest price
        productsPageObj.selectFromDropdown((String) input.get("sortBy"));
        Assert.assertTrue(productsPageObj.isSortedHiToLo(), "The  products are sorted from high to low price");

        //3.select the costliest product
        productsPageObj.selectProductByIndex(0);
        productsPageObj.addProductToCart(0);

        //3.Go to yourCartPage
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("/cart.html"));

        //4.Proceed to yourInformationPage
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //5.Enter information in yourInformationPage and proceed to CheckoutOverviewPage
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //6.Verify the price value computation in CheckoutOverviewPage
        float[] totalB4Tax = checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        Assert.assertEquals(totalB4Tax[0], totalB4Tax[1]);
        float[] totalValue = checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        Assert.assertEquals(totalValue[0], totalValue[1]);
        CheckoutCompletePage checkoutCompletePageObj = checkoutOverviewPageObj.proceedToFinish();

        //7.Verify the thanksText and go back to products page
        String thanksTextDisplayed = checkoutCompletePageObj.getThanksText();
        Assert.assertEquals(thanksTextDisplayed, (String) input.get("thanksText"));
        checkoutCompletePageObj.proceedBackToProducts();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
    }

    @Test(dataProvider = "sortLoHiData",groups={"E2E","Products Test"})
    public void orderCheapestProduct(HashMap<String, Object> input) throws Exception {
        // 1.Login
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2.verify if the products are Sorted from lowest to highest price
        productsPageObj.selectFromDropdown((String) input.get("sortBy"));
        Assert.assertTrue(productsPageObj.isSortedLoToHi(), "The products are sorted from low to high price");

        //3.select the costliest product
        productsPageObj.selectProductByIndex(0);
        productsPageObj.addProductToCart(0);

        //3.Go to yourCartPage
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("/cart.html"));

        //4.Proceed to yourInformationPage
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //5.Enter information in yourInformationPage and proceed to CheckoutOverviewPage
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //6.Verify the price value computation in CheckoutOverviewPage
        float[] totalB4Tax = checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        Assert.assertEquals(totalB4Tax[0], totalB4Tax[1]);
        float[] totalValue = checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        Assert.assertEquals(totalValue[0], totalValue[1]);
        CheckoutCompletePage checkoutCompletePageObj = checkoutOverviewPageObj.proceedToFinish();

        //7.Verify the thanksText and go back to products page
        String thanksTextDisplayed = checkoutCompletePageObj.getThanksText();
        Assert.assertEquals(thanksTextDisplayed, (String) input.get("thanksText"));
        checkoutCompletePageObj.proceedBackToProducts();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
    }

    @Test(dataProvider = "alphaSortZtoAData",groups={"E2E","Products Test"})
    public void alphaSortZtoA(HashMap<String, Object> input) throws Exception {
        // 1.Login
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2.verify if the products are Sorted from lowest to highest price
        productsPageObj.selectFromDropdown((String) input.get("sortBy"));
        Assert.assertTrue(productsPageObj.isAlphaSortedZtoA(), "The products are sorted from Z to A ");

        //3.select the costliest product
        productsPageObj.selectProductByIndex(0);
        productsPageObj.addProductToCart(0);

        //3.Go to yourCartPage
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("/cart.html"));

        //4.Proceed to yourInformationPage
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //5.Enter information in yourInformationPage and proceed to CheckoutOverviewPage
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //6.Verify the price value computation in CheckoutOverviewPage
        float[] totalB4Tax = checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        Assert.assertEquals(totalB4Tax[0], totalB4Tax[1]);
        float[] totalValue = checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        Assert.assertEquals(totalValue[0], totalValue[1]);
        CheckoutCompletePage checkoutCompletePageObj = checkoutOverviewPageObj.proceedToFinish();

        //7.Verify the thanksText and go back to products page
        String thanksTextDisplayed = checkoutCompletePageObj.getThanksText();
        Assert.assertEquals(thanksTextDisplayed, (String) input.get("thanksText"));
        checkoutCompletePageObj.proceedBackToProducts();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
    }

    @Test(dataProvider = "alphaSortAtoZData",groups={"E2E","Products Test"})
    public void alphaSortedAtoZ(HashMap<String,Object> input) throws Exception {
        // 1.Login
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2.verify if the products are Sorted from lowest to highest price
        productsPageObj.selectFromDropdown((String) input.get("sortBy"));
        Assert.assertTrue(productsPageObj.isAlphaSortedAtoZ(), "The products are sorted from A to Z ");

        //3.select the costliest product
        productsPageObj.selectProductByIndex(0);
        productsPageObj.addProductToCart(0);

        //3.Go to yourCartPage
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("/cart.html"));

        //4.Proceed to yourInformationPage
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //5.Enter information in yourInformationPage and proceed to CheckoutOverviewPage
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //6.Verify the price value computation in CheckoutOverviewPage
        float[] totalB4Tax = checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        Assert.assertEquals(totalB4Tax[0], totalB4Tax[1]);
        float[] totalValue = checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        Assert.assertEquals(totalValue[0], totalValue[1]);
        CheckoutCompletePage checkoutCompletePageObj = checkoutOverviewPageObj.proceedToFinish();

        //7.Verify the thanksText and go back to products page
        String thanksTextDisplayed = checkoutCompletePageObj.getThanksText();
        Assert.assertEquals(thanksTextDisplayed, (String) input.get("thanksText"));
        checkoutCompletePageObj.proceedBackToProducts();
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
    }

    @Test(dataProvider = "addProdFromDetailsPageData", groups="Products Test")
    public void addProdFromDetailsPage(HashMap<String,Object> input) {
        //1.Login and go to products page
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2. Get prodname, proddesc, prodprice and click the name of your desired product
        String[] prodValuesInMainPage = productsPageObj.getProductDetails((String) input.get("product"));
        ProductDetailsPage productDetailsPageObj = productsPageObj.clickOnProdName((String) input.get("product"));

        //3.Verify the prodname, proddesc, prodprice details and click add to cart button
        String[] prodValuesInDetailsPage = productDetailsPageObj.getProductValuesFromDetailsPage();
        Assert.assertTrue(prodValuesInMainPage[0].equals(prodValuesInDetailsPage[0]), "Product name is displayed correctly in details page");
        Assert.assertTrue(prodValuesInMainPage[1].equals(prodValuesInDetailsPage[1]), "Product description is displayed correctly in details page");
        Assert.assertTrue(prodValuesInMainPage[2].equals(prodValuesInDetailsPage[2]), "Product price is displayed correctly in details page");
        productDetailsPageObj.addProductToCart();
        productDetailsPageObj.removeBtn.isDisplayed();

        //4.Navigate back to products page and verify the remove button of the desired product
        productDetailsPageObj.backToProducts();
        Assert.assertTrue(productsPageObj.removeBtnOfDesiredProduct((String) input.get("product")).isDisplayed(),"Remove button is displayed for the specified product");

        //5. To remove the product from cart, for next test case readiness
        productsPageObj.removeBtnOfDesiredProduct((String) input.get("product")).click();
    }

    @Test(dataProvider = "productDetailsVerificationData")
    public void productDetailsVerification(HashMap<String,Object> input) {

        //1. login as standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String)input.get("username"),(String) input.get("password"));

        //2. verify the details of the desired product
        String actualProductDetails[] = productsPageObj.getProductDetails((String) input.get("product"));
        Assert.assertEquals(actualProductDetails[0], (String) input.get("product"),"Product name is verified" );
        Assert.assertEquals(actualProductDetails[1], (String)input.get("productDesc"), "Product description is verified");
        Assert.assertEquals(actualProductDetails[2],input.get("productPrice"),"Product price is verified");
        WebElement addToCartBtn = productsPageObj.addTOCartBtnOfDesiredProduct((String) input.get("product"));
        Assert.assertTrue(addToCartBtn.isDisplayed(),"Add to Cart button is displayed for the specified product");
        addToCartBtn.click();
        WebElement removeBtn = productsPageObj.removeBtnOfDesiredProduct((String) input.get("product"));
        Assert.assertTrue(removeBtn.isDisplayed(),"Remove button is displayed for the desired product");
        removeBtn.click();

    }

    @Test(dataProvider = "removeProductErrorUserData")
    void removeProductErrorUser(HashMap<String,Object> input) {
        //1.Login as error user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        //2.Add a product to cart
        String prodName = (String) input.get("product");
        productsPageObj.addProductToCart(prodName);
        //3.Remove items from cart in "Products" page
        WebElement removeBtn = productsPageObj.removeBtnOfDesiredProduct(prodName);
        removeBtn.click();
        //4.Verify if "Remove" button is still displayed (Negative Validation)
        Assert.assertTrue(removeBtn.isDisplayed(),"unable to remove product "+prodName+" from cart");

    }

    @Test(dataProvider = "addToCartProductErrorUserData")
    void addToCartProductErrorUser(HashMap<String,Object> input) {
        //1.Login as error user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        //2.Add a product to cart
        ArrayList<String> desiredItems = (ArrayList<String>) input.get("products");
        for(String desiredItem : desiredItems) {
            productsPageObj.addProductToCart(desiredItem);
            //3.Verify if "Add to Cart" Button is still displayed for each product, which means product is not added to cart
            Assert.assertTrue(productsPageObj.addTOCartBtnOfDesiredProduct(desiredItem).isDisplayed(),"unable to add product "+desiredItem+" to cart");
        }


    }





    @DataProvider
    Object[][] getSingleProdData() throws IOException {
        String dataFiePath = System.getProperty("user.dir") + "/src/test/java/saucelabs/data/happypath/products.json";
        List<HashMap<String, Object>> data = getDataToMap(dataFiePath);
        return new Object[][]{{data.get(1)}};

    }

    @DataProvider
    Object[][] getMultipleProdData() throws IOException {

        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(0)}};

    }

    @DataProvider
    Object[][] sortHiLoData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(2)}};
    }

    @DataProvider
    Object[][] sortLoHiData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(3)}};
    }

    @DataProvider
    Object[][] alphaSortZtoAData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(4)}};
    }

    @DataProvider
    Object[][] alphaSortAtoZData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(5)}};
    }

    @DataProvider
    Object[][] addProdFromDetailsPageData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(6)}};
    }

    @DataProvider
    Object[][] productDetailsVerificationData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(7)}};
    }

    @DataProvider
    Object[][] removeProductErrorUserData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(8)}};
    }

    @DataProvider
    Object[][] addToCartProductErrorUserData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(9)}};
    }


}
