package saucelabs.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailsPage {
    WebDriver driver;
    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "button#back-to-products")
    WebElement backToProductsBtn;

    @FindBy(css = "div[data-test=\"inventory-item-name\"]")
    WebElement productNameEle;

    @FindBy(css = "div[data-test=\"inventory-item-desc\"]")
    WebElement productDescriptionEle;

    @FindBy(css = ".inventory_details_price")
    WebElement productPriceEle;

    @FindBy(css = "#add-to-cart")
    public WebElement addToCartBtn;

    @FindBy(css = "#remove")
    public WebElement removeBtn;

    public void verifyValues(String prodName, String prodDescription, String prodPrice) {
        productNameEle.getText().equals(prodName);
        productDescriptionEle.getText().equals(prodDescription);
        productPriceEle.getText().equals(prodPrice);
    }

    public String[] getProductValuesFromDetailsPage () {

        String prodName = productNameEle.getText();
        String prodDesc = productDescriptionEle.getText();
        String prodPrice = productPriceEle.getText();
        return new String[] {prodName,prodDesc, prodPrice};
    }

    public void addProductToCart() {
        addToCartBtn.click();
    }

    public ProductsPage backToProducts(){
        backToProductsBtn.click();
        ProductsPage productsPageObj = new ProductsPage(driver);
        return productsPageObj;
    }




}
