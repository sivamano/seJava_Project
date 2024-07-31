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

public class happyPathTest extends BaseTest {

    @Test(dataProvider = "getData")
    public void demoTest(HashMap<String, Object> input) throws Exception {

        //Items to be ordered
        ArrayList<String> desiredItems = (ArrayList<String>) input.get("products");

        //Open app, login
        ProductsPage productsPageObj = loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("/inventory.html"));

        //add desired items in cart
        for (String desiredItem : desiredItems) {
            WebElement productName = productsPageObj.selectProductByName(desiredItem);
            Assert.assertEquals(desiredItem, productName.findElement(By.xpath(".//div[@class=\"inventory_item_name \"]")).getText());
            productsPageObj.addProductToCart(desiredItem);
        }

        //go to cart section
        SiteHeader siteHeader = new SiteHeader(driver);
        YourCartPage yourCartPageObj = siteHeader.clickShoppingCartLink();


        //verify the information in cartPage
        for (String desiredItem : desiredItems) {
            //System.out.println("driver class "+desiredItem);
            boolean x = yourCartPageObj.verifyItemsInCart(desiredItem);
            Assert.assertTrue(x, desiredItem + " is present in cart");

        }
        YourInformationPage yourInformationPageObj = yourCartPageObj.proceedToCheckout();

        //provide your personal info to proceed to order
        yourInformationPageObj.providePersonalDetails((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("postalCode"));
        CheckoutOverviewPage checkoutOverviewPageObj = yourInformationPageObj.proceedFurther();

        //finally, verify all your details
        float totalB4Tax[] = checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        Assert.assertEquals(totalB4Tax[0], totalB4Tax[1]);
        float totalValue[] = checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        Assert.assertEquals(totalValue[0], totalValue[1]);
        CheckoutCompletePage checkoutCompletePageObj = checkoutOverviewPageObj.proceedToFinish();

        //compete checkout and return to products page
        Assert.assertEquals(checkoutCompletePageObj.getThanksText(), input.get("thanksText"));
        checkoutCompletePageObj.proceedBackToProducts();
    }

    @Test

    @DataProvider
    public Object[][] getData() throws IOException {
        String dataFilePath = System.getProperty("user.dir") + "/src/test/java/saucelabs/data/happypath.json";
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(0)}};
    }


}
