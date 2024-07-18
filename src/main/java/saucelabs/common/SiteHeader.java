package saucelabs.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import saucelabs.pageobjects.YourCartPage;

public class SiteHeader {
    WebDriver driver;

    public SiteHeader(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "shopping_cart_link")
    WebElement shoppingCartLink;

    public YourCartPage clickShoppingCartLink(){
        shoppingCartLink.click();
        YourCartPage yourCartPageObj = new YourCartPage(driver);
        return yourCartPageObj;
    }

}

