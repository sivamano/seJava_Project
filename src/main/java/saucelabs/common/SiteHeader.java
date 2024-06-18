package saucelabs.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SiteHeader {
    WebDriver driver;

    public SiteHeader(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "shopping_cart_link")
    WebElement shoppingCartLink;

    public void clickShoppingCartLink(){
        shoppingCartLink.click();
    }

}

