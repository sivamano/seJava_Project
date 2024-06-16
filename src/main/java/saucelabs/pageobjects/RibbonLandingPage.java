package saucelabs.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RibbonLandingPage {
WebDriver driver;

public RibbonLandingPage(WebDriver driver){
    this.driver=driver;
    PageFactory.initElements(driver, this);
}

// shopping cart button
@FindBy(xpath = "//a[@class=\"shopping_cart_link\"]")
    WebElement shoppingCartButton;

public void clickShoppingCartButton(){
    shoppingCartButton.click();
}
}
