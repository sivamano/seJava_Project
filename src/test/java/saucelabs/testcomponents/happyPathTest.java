package saucelabs.testcomponents;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucelabs.common.SiteHeader;
import saucelabs.pageobjects.*;


import java.io.IOException;
import java.util.*;

public class happyPathTest extends BaseTest{

    @Test(dataProvider = "getData")
      public void demoTest(HashMap<String,Object> input) throws Exception {

        //Items to be ordered

        ArrayList<String> desiredItems = (ArrayList<String>) input.get("products");

        //Open app, login

        loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        //loginPage.loginToApp("siva", "password");

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
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        yourInformationPageObj.proceedFurther();

        //finally verify all your details
        CheckoutOverviewPage checkoutOverviewPageObj = new CheckoutOverviewPage(driver);
        checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        checkoutOverviewPageObj.proceedToFinish();

        //compete checkout and return to products page
        CheckoutCompletePage checkoutCompletePageObj = new CheckoutCompletePage(driver);
        Assert.assertEquals(checkoutCompletePageObj.getThanksText(),input.get("thanksText"));
        checkoutCompletePageObj.proceedBackToProducts();
    }

    @Test

    @DataProvider
            public Object[][] getData() throws IOException {
      String dataFilePath = System.getProperty("user.dir")+"/src/test/java/saucelabs/data/happypath/happypath.json";
      List<HashMap<String,Object>> data = getDataToMap(dataFilePath);
      return new Object[][] {{data.get(0)}};
    }


}
