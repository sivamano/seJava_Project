package saucelabs.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsPage {

    // initialize driver variable
    WebDriver driver;
    WebElement productName;
    WebElement productDescription;

    // define constructor with Webdriver argument and assign the driver you get when creating obj of this class to this class's driver
    public ProductsPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    // fetching all inventories and storing as list of WebElements
    @FindBy(xpath = "//div[@class=\"inventory_item\"]")
    List<WebElement> inventoryList;

    // this method returns the specific "INVENTORY" based on the name of the inventory
    public WebElement selectProductByName(String prodName){
        productName = inventoryList.stream()
                .filter(inventory -> inventory.findElement(By.xpath(".//div[@class=\"inventory_item_name \"]"))
                        .getText().equals(prodName))
                .findFirst()
                .orElse(null) ;
       return productName;
    }

    // this method clicks the button of INVENTORY based on the name of the inventory
    public void addProductToCart(String prodName){
        WebElement product = selectProductByName(prodName);
        //System.out.println(product.getText());
        product.findElement(By.xpath(".//button")).click();

    }

    WebElement selectProductByDescription(String prodDescription){
        productDescription = inventoryList.stream()
                .filter(inventoryDesc -> inventoryDesc.findElement(By.className("inventory_item_desc"))
                        .getText().equals(prodDescription))
                .findFirst()
                .orElse(null);
        return productDescription;
    }


}
