package saucelabs.testcomponents;


import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucelabs.common.MenuBar;
import saucelabs.common.SiteHeader;
import saucelabs.pageobjects.LoginPage;
import saucelabs.pageobjects.ProductsPage;
import saucelabs.pageobjects.YourCartPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YourCartTest extends BaseTest {

    //declare repetitive variables if any
    String dataFilePath = System.getProperty("user.dir") + "/src/test/java/saucelabs/data/happypath/yourCart.json";

    @Test(dataProvider = "editItemsFromProductsPageData")
    void editItemsFromProductsPage(HashMap<String, Object> input) {

        //declarations


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

        //5. go back to products page
        yourCartPageObj.continueShoppingBtn.click();

        //6.remove a product from products page
        String productToBeRemoved = (String) input.get("productToBeRemoved");
        WebElement removeBtn = productsPageObj.removeBtnOfDesiredProduct(productToBeRemoved);
        removeBtn.click();

        //7.navigate back to yourCartPage and verify the product is removed
        siteHeaderObj.clickShoppingCartLink();
        Assert.assertFalse(yourCartPageObj.verifyItemsInCart(productToBeRemoved), "Product '" + productToBeRemoved + "' is not present in yourCart page after removal in products page");


    }

    @Test(dataProvider = "removeAllItemsFromProductsPageData")
    void removeAllItemsFromProductsPage(HashMap<String, Object> input) {
        //1.Login as Standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        //2.Add a product to cart
        String product = (String) input.get("product");
        productsPageObj.addProductToCart(product);
        //3.Go to ""Your Cart"" and verify the product
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();
        Assert.assertTrue(yourCartPageObj.verifyItemsInCart(product), product + " is present in the cart");
        yourCartPageObj.continueShoppingBtn.click();
        //4.Navigate back to products page remove the added item
        WebElement removeBtnOfProduct = productsPageObj.removeBtnOfDesiredProduct(product);
        removeBtnOfProduct.click();
        //5.Go back to ""Your Cart""  verify if the cart is empty"
        siteHeaderObj.clickShoppingCartLink();
        Assert.assertFalse(yourCartPageObj.verifyItemsInCart(product), product + " is now removed from cart");

    }

    @Test(dataProvider = "cartBadgeCountData")
    void cartBadgeCount(HashMap<String, Object> input) {
        // 1.Login as Standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        // 2.Add multiple products to cart
        ArrayList<String> desiredItems = (ArrayList<String>) input.get("products");
        for (String desiredItem : desiredItems) {
            productsPageObj.addProductToCart(desiredItem);
        }
        //3. get the count of products added to cart
        int productsAddedToCart = desiredItems.size();

        //4. get the actual badge count present in the shopping cart link
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        int actualCartBadgeCount = siteHeaderObj.getCartBadgeCount();

        //5.Verify if the badge count is equivalent to number of items in the cart
        Assert.assertEquals(productsAddedToCart, actualCartBadgeCount, "Cart badge count is matching with the no of products added to cart");

        //6. **SANITY STEP remove items from cart
        for (String desiredItem : desiredItems) {
            WebElement removeBtnOfDesiredProduct = productsPageObj.removeBtnOfDesiredProduct(desiredItem);
            removeBtnOfDesiredProduct.click();
        }
    }

    @Test(dataProvider = "verifyItemsInCartAfterReLoginData")
    void verifyItemsInCartAfterReLogin (HashMap<String,Object> input) throws InterruptedException {
        //1.Login as Standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));

        //2.Add multiple products to cart
        ArrayList<String> desiredItems = (ArrayList<String>) input.get("products");
        for(String desiredItem : desiredItems) {
            productsPageObj.addProductToCart(desiredItem);
        }
        //3.Log out
        MenuBar menuBarObj = new MenuBar(driver);
        LoginPage loginPageObj = menuBarObj.logoutFromApp();


        //4.Login with the same credentials
        loginPageObj.loginToApp((String) input.get("username"), (String) input.get("password"));

        //5.Verify the items added are still present in cart
        for(String desiredItem : desiredItems) {
            WebElement removeBtnOfDesiredProduct = productsPageObj.removeBtnOfDesiredProduct(desiredItem);
            Assert.assertTrue(removeBtnOfDesiredProduct.isDisplayed(),"Product"+desiredItem+" is present in cart after re-login");
        }

    }

    @Test(dataProvider = "editItemsInYourCartPageData")
    void editItemsInYourCartPage(HashMap<String,Object> input){

        //declarations

        //1.Login as Standard user
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        //2.Add a product to cart
        ArrayList<String> desiredItems = (ArrayList<String>) input.get("products");
        for(String desiredItem : desiredItems) {
            productsPageObj.addProductToCart(desiredItem);
        }
        //3.Go to ""Your Cart"" and verify the product
        SiteHeader siteHeaderObj = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeaderObj.clickShoppingCartLink();

        //4. verify the products
        for(String desiredItem : desiredItems) {
            yourCartPageObj.verifyItemsInCart(desiredItem);
        }
        //5.Remove a product in ""Your Cart"" Page"
        String itemToBeRemoved = (String) input.get("cartItemToBeRemoved");
        WebElement removeBtnOfItemToBeRemoved = yourCartPageObj.removeBtnOfDesiredCartItem(itemToBeRemoved);
        removeBtnOfItemToBeRemoved.click();

        //6.verify if the items are removed from the cart
        Assert.assertFalse(yourCartPageObj.verifyItemsInCart(itemToBeRemoved));
    }



    @DataProvider
    Object[][] editItemsFromProductsPageData () throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(0)}};
    }

    @DataProvider
    Object[][] removeAllItemsFromProductsPageData () throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(1)}};
    }

    @DataProvider
    Object[][] cartBadgeCountData () throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(2)}};

    }

    @DataProvider
    Object[][] verifyItemsInCartAfterReLoginData () throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(3)}};

    }

    @DataProvider
    Object[][] editItemsInYourCartPageData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(4)}};

    }
}

