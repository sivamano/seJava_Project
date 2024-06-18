package saucelabs.testcomponents;


import org.testng.annotations.Test;
import saucelabs.common.SiteHeader;
import saucelabs.pageobjects.*;

import java.util.Arrays;
import java.util.List;

public class happyPath extends BaseTest{

    @Test
      public void demoTest() throws Exception {

        //Items to be ordered
        List<String> desiredItems = Arrays.asList("Sauce Labs Bike Light","Sauce Labs Backpack");

        //Open app, login

        loginPage.loginToApp("standard_user","secret_sauce");

        //add desired items in cart
        ProductsPage productsPageObj = new ProductsPage(driver);
        for (String desiredItem: desiredItems)
        {
            productsPageObj.selectProductByName(desiredItem);
            productsPageObj.addProductToCart(desiredItem);
        }

        //go to cart section
        SiteHeader siteHeader = new SiteHeader(driver);
        siteHeader.clickShoppingCartLink();

        //verify the information in cartpage
        YourCartPage yourCartPageObj = new YourCartPage(driver);
        for(String desiredItem: desiredItems)
        {
            //System.out.println("driver class "+desiredItem);
            yourCartPageObj.verifyItemsInCart(desiredItem);
        }
        yourCartPageObj.proceedToCheckout();

        //provide your personal info to proceed to order
        YourInformationPage yourInformationPageObj = new YourInformationPage(driver);
        yourInformationPageObj.providePersonalDetails("fname","lname","78L64");
        yourInformationPageObj.proceedFurther();

        //finally verify all your details
        CheckoutOverviewPage checkoutOverviewPageObj = new CheckoutOverviewPage(driver);
        checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        checkoutOverviewPageObj.proceedToFinish();

        //compete checkout and return to products page
        CheckoutCompletePage checkoutCompletePageObj = new CheckoutCompletePage(driver);
        checkoutCompletePageObj.verifyCompletionMessage("Thank you for your order!");
        checkoutCompletePageObj.proceedBackToProducts();
    }
}
