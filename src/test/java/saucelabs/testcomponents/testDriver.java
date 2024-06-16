package saucelabs.testcomponents;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import saucelabs.pageobjects.*;

import java.util.Arrays;
import java.util.List;

public class testDriver {

    public static void main(String[] args) throws Exception {
        String chromePath = "C:\\ChromeTest\\chrome-win64\\chrome.exe";
        ChromeOptions cOptions = new ChromeOptions();
        cOptions.setBinary(chromePath);

        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(cOptions);

        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        //"Sauce Labs Backpack",
        List<String> desiredItems = Arrays.asList("Sauce Labs Bike Light","Sauce Labs Backpack");

        LoginPage loginPageObj = new LoginPage(driver);
        loginPageObj.loginToApp("standard_user","secret_sauce");

        ProductsPage productsPageObj = new ProductsPage(driver);
        for (String desiredItem: desiredItems)
        {
            productsPageObj.selectProductByName(desiredItem);
            productsPageObj.addProductToCart(desiredItem);
        }

        RibbonLandingPage ribbonLandingPageObj = new RibbonLandingPage(driver);
        ribbonLandingPageObj.clickShoppingCartButton();

        YourCartPage yourCartPageObj = new YourCartPage(driver);
        for(String desiredItem: desiredItems)
        {
            //System.out.println("driver class "+desiredItem);
            yourCartPageObj.verifyItemsInCart(desiredItem);
        }
        yourCartPageObj.proceedToCheckout();

        YourInformationPage yourInformationPageObj = new YourInformationPage(driver);
        yourInformationPageObj.providePersonalDetails("fname","lname","78L64");
        yourInformationPageObj.proceedFurther();

        CheckoutOverviewPage checkoutOverviewPageObj = new CheckoutOverviewPage(driver);
        checkoutOverviewPageObj.valueOfCartItemsBeforeTax();
        checkoutOverviewPageObj.valueOfCartItemsAfterTax();
        checkoutOverviewPageObj.proceedToFinish();

        CheckoutCompletePage checkoutCompletePageObj = new CheckoutCompletePage(driver);
        checkoutCompletePageObj.verifyCompletionMessage("Thank you for your order!");
        checkoutCompletePageObj.proceedBackToProducts();
    }
}
