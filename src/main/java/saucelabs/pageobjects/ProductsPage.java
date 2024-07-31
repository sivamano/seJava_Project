package saucelabs.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductsPage {

    // initialize driver variable
    WebDriver driver;
    WebElement productName;
    WebElement productDescription;

    // define constructor with Webdriver argument and assign the driver you get when creating obj of this class to this class's driver
    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // fetching all inventories and storing as list of WebElements
    @FindBy(xpath = "//div[@class=\"inventory_item\"]")
    List<WebElement> inventoryList;

    //sort dropdown
    @FindBy(xpath = "//select[@class=\"product_sort_container\"]")
    WebElement productSortDropdown;

    //fetching the prices of inventory
    @FindBy(xpath = "//div[@class=\"inventory_item_price\"]")
    List<WebElement> itemPricesEle;

    @FindBy(xpath = "//div[@class=\"inventory_item_name \"]")
    List<WebElement> inventoryNamesEle;



    // this method returns the specific "INVENTORY" based on the name of the inventory
    public WebElement selectProductByName(String prodName) {
        productName = inventoryList.stream()
                .filter(inventory -> inventory.findElement(By.xpath(".//div[@class=\"inventory_item_name \"]"))
                        .getText().equals(prodName))
                .findFirst()
                .orElse(null);
        return productName;
    }
    // this method returns the WebElement of description of the desired product
    public WebElement selectProductByDescription(String prodDescription) {
        productDescription = inventoryList.stream()
                .filter(inventoryDesc -> inventoryDesc.findElement(By.className("inventory_item_desc"))
                        .getText().equals(prodDescription))
                .findFirst()
                .orElse(null);
        return productDescription;
    }
    // retrieve the details of the desired product
    public String[] getProductDetails (String prodName) {

        WebElement desiredProdName = selectProductByName(prodName);
        String prodDesc = desiredProdName.findElement(By.xpath(".//div[@class=\"inventory_item_desc\"]")).getText();
        String prodPrice = desiredProdName.findElement(By.xpath(".//div[@class=\"inventory_item_price\"]")).getText();
        return new String[] {prodName,prodDesc, prodPrice};
    }
    //click on desired prodName
    public ProductDetailsPage clickOnProdName(String prodName){
        WebElement desiredProdName = selectProductByName(prodName);
        desiredProdName.findElement(By.xpath(".//div[@class=\"inventory_item_name \"]")).click();
        ProductDetailsPage productDetailsPageObj = new ProductDetailsPage(driver);
        return productDetailsPageObj;
    }
    //the below method will select the product based on the index provided
    public WebElement selectProductByIndex(int index)
    {
        return inventoryList.get(index);
    }
    // this method clicks the button of INVENTORY based on the name of the inventory
    public void addProductToCart(String prodName) {
        WebElement product = selectProductByName(prodName);
        product.findElement(By.xpath(".//button")).click();

    }
    // this method adds the product to cart based on index
    public void addProductToCart(int index) {
        WebElement product = selectProductByIndex(index);
        product.findElement(By.xpath(".//button")).click();

    }
    public void selectFromDropdown(String option) {
        Select fromDropdown = new Select(productSortDropdown);
        fromDropdown.selectByVisibleText(option);
    }
    public boolean isSortedHiToLo() {
        float prevValue = Float.parseFloat(itemPricesEle.get(0).getText().replaceAll("[a-zA-Z\\s$:]", ""));

        for (WebElement itemPriceEle : itemPricesEle) {
            float itemPrice = Float.parseFloat(itemPriceEle.getText().replaceAll("[a-zA-Z\\s$:]", ""));
            if (prevValue < itemPrice) {
                return false;
            }
            prevValue = itemPrice;
        }

        return true;
    }
    public boolean isSortedLoToHi() {
        float prevValue = Float.parseFloat(itemPricesEle.get(0).getText().replaceAll("[a-zA-Z\\s$:]", ""));

        for (WebElement itemPriceEle : itemPricesEle) {
            float itemPrice = Float.parseFloat(itemPriceEle.getText().replaceAll("[a-zA-Z\\s$:]", ""));
            if (prevValue > itemPrice ) {
                return false;
            }
            prevValue = itemPrice;
        }

        return true;
    }
    public boolean isAlphaSortedZtoA() {
        String[] inventoryNames = new String[inventoryNamesEle.size()];
        for (int j = 0; j < inventoryNamesEle.size(); j++)
        {
            inventoryNames[j] = inventoryNamesEle.get(j).getText();
        }
        for (int k = 0; k < (inventoryNames.length - 1); k++)
        {
            if (inventoryNames[k].compareToIgnoreCase(inventoryNames[k+1])<=0)
            {
                System.out.println(inventoryNames[k]+" is lesser than "+ inventoryNames[k+1]);
                return false;
            }
        }
        return true;
    }
    public boolean isAlphaSortedAtoZ(){
        String[] inventoryNames = new String[inventoryNamesEle.size()];
        // to get the text values from webelement and store it in a string array
        for(int i=0; i< (inventoryNamesEle.size()); i++)
        {
            inventoryNames[i] = inventoryNamesEle.get(i).getText();
        }
        // to iterate through the string array and check if the values are sorted from A to Z
        for(int j=0;j< (inventoryNames.length -1); j++)
        {
            if(inventoryNames[j].compareToIgnoreCase(inventoryNames[j+1])>0)
            {
                System.out.println(inventoryNames[j]+" is greater than "+inventoryNames[j+1]);
                return false;
            }
        }
        return true;
    }
    public WebElement removeBtnOfDesiredProduct(String prodName) {
        WebElement prodNameEle = selectProductByName(prodName);
        WebElement removeBtn = prodNameEle.findElement(By.xpath(".//button[text()='Remove']"));
        return removeBtn;
    }

    public WebElement addTOCartBtnOfDesiredProduct (String prodName) {
        try {
            WebElement prodNameEle = selectProductByName(prodName);
            WebElement addToCartBtn = prodNameEle.findElement(By.xpath(".//button[text()='Add to cart']"));
            return addToCartBtn;
        }
        catch (NoSuchElementException ex) {
            return null;
        }
    }





}
