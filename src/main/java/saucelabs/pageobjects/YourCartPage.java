package saucelabs.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class YourCartPage {
    WebDriver driver;
    WebElement productsInCart;

    public YourCartPage(WebDriver driver){
    this.driver= driver;
    PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//div[@class=\"cart_item\"]")
    List<WebElement> cartItems;

    @FindBy(id="checkout")
    WebElement checkOutButton;


    // //button[text()="Remove"]

    // //div[@class="cart_item"]


    @FindBy(id="continue-shopping")
    public WebElement continueShoppingBtn;

    public WebElement selectCartItemByName(String prodName) {
        productsInCart = cartItems.stream()
                .filter(cartItem -> cartItem.findElement(By.xpath(".//div[@class=\"inventory_item_name\"]"))
                        .getText().equals(prodName))
                .findFirst()
                .orElse(null);
        return productsInCart;
    }

    public boolean verifyItemsInCart(String prodName) {

    boolean x = cartItems.stream().
            anyMatch(cartItem -> cartItem.findElement(By.xpath(".//div[@class=\"inventory_item_name\"]"))
                    .getText()
                    .contains(prodName));
        return x;
    }

    public YourInformationPage proceedToCheckout() {
        checkOutButton.click();
        YourInformationPage yourInformationPageObj = new YourInformationPage(driver);
        return yourInformationPageObj;

    }

    public WebElement removeBtnOfDesiredCartItem(String prodName) {
        WebElement prodNameEle = selectCartItemByName(prodName);
        WebElement removeBtn = prodNameEle.findElement(By.xpath(".//button[text()='Remove']"));
        return removeBtn;
    }
    }