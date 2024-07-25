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

    @FindBy(css = "span.shopping_cart_badge")
    public WebElement cartBadge;


    public YourCartPage clickShoppingCartLink(){
        shoppingCartLink.click();
        YourCartPage yourCartPageObj = new YourCartPage(driver);
        return yourCartPageObj;
    }

    public int getCartBadgeCount()
    {
        int cartBadgeCount = Integer.parseInt(cartBadge.getText());
        return cartBadgeCount;
    }

}

