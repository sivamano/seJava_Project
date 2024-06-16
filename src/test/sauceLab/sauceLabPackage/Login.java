package sauceLabPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


public class Login {
    public static void main(String[] args) {
        String chromePath = "C:\\ChromeTest\\chrome-win64\\chrome.exe";
        ChromeOptions cOptions = new ChromeOptions();
        cOptions.setBinary(chromePath);

        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(cOptions);

        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        //login(driver);
        justLogin(driver);
        listOfInventory(driver);
        checkedOutItems(driver);
        personalDetails(driver);
        cartValueCalculation(driver);
        //driver.quit();
    }




    //Login as standardUser, choose a single product, checkout, order, go back to homepage
    public static void happyPathSimple(WebDriver driver) {
        WebElement userNameTbox = driver.findElement(By.id("user-name"));
        userNameTbox.sendKeys("standard_user");

        WebElement passwordTbox = driver.findElement(By.id("password"));
        passwordTbox.sendKeys("secret_sauce");

        WebElement passwordBtn = driver.findElement(By.xpath("//input[@id=\"login-button\"]"));
        passwordBtn.click();

        WebElement addToCartBtn = driver.findElement(By.xpath("//div[@class=\"inventory_list\"]//div[@class=\"pricebar\"]//button[@id=\"add-to-cart-sauce-labs-backpack\"]"));
        addToCartBtn.click();

        WebElement removeFromCartBtn = driver.findElement(By.xpath("//div[@class=\"inventory_list\"]//div[@class=\"pricebar\"]//button[@id=\"remove-sauce-labs-backpack\"]"));
        removeFromCartBtn.isDisplayed();

        WebElement shoppingCartBtn = driver.findElement(By.xpath("//a[@class=\"shopping_cart_link\"]"));
        shoppingCartBtn.click();

        WebElement inventoryItemList = driver.findElement(By.xpath("//div[@class=\"inventory_item_name\"]"));
        String inventoryText = inventoryItemList.getText();
        System.out.println(inventoryText);
        if (inventoryText.equals("Sauce Labs Backpack")) {
            System.out.println("Item displayed correctly in cart");
        } else {
            System.out.println("Item is NOT displayed correctly in cart");
        }

        WebElement checkoutBtn = driver.findElement(By.id("checkout"));
        checkoutBtn.click();

        ////input[@id="first-name"] last-name postal-code id="continue"

        WebElement firstNameTbox = driver.findElement(By.id("first-name"));
        firstNameTbox.sendKeys("RandomUser");

        WebElement lastNameTbox = driver.findElement(By.id("last-name"));
        lastNameTbox.sendKeys("Sauce");

        WebElement postalCodeTbox = driver.findElement(By.id("postal-code"));
        postalCodeTbox.sendKeys("45879");

        WebElement continueBtn = driver.findElement(By.id("continue"));
        continueBtn.click();


        WebElement itemTotalB4TaxFull = driver.findElement(By.xpath("//div[@class=\"summary_info\"]/div[@data-test=\"subtotal-label\"]"));
        String itemTotalB4TaxText = itemTotalB4TaxFull.getText();
        String itemTotalB4TaxString = itemTotalB4TaxText.replaceAll("[a-zA-Z\\s:$]", "");
        float itemTotalB4Tax = Float.parseFloat(itemTotalB4TaxString);
        //System.out.println(itemTotalB4Tax);


        WebElement totalTaxFull = driver.findElement(By.xpath("//div[@class=\"summary_info\"]/div[@data-test=\"tax-label\"]"));
        String totalTaxText = totalTaxFull.getText();
        String totalTaxTextString = totalTaxText.replaceAll("[a-zA-Z\\s:$]", "");
        float totalTax = Float.parseFloat(totalTaxTextString);
        //System.out.println(totalTax);

        float totalCartValue = totalTax + itemTotalB4Tax;
        System.out.println(totalCartValue);


        WebElement totalFull = driver.findElement(By.xpath("//div[@class=\"summary_info\"]/div[@data-test=\"total-label\"]"));
        String totalText = totalFull.getText();
        String totalTextString = totalText.replaceAll("[a-zA-Z\\s:$]", "");
        float total = Float.parseFloat(totalTextString);
        System.out.println(total);

        if (totalCartValue == total) {
            System.out.println("Total amount is displayed correctly as, $" + total);
        } else {
            System.out.println("oops! something seems off with the total amount");
        }

        WebElement finishBtn = driver.findElement(By.xpath("//button[@data-test=\"finish\"]"));
        finishBtn.click();

        ////h2[@data-test="complete-header"]
        WebElement thankUMsg = driver.findElement(By.xpath("//h2[@data-test=\"complete-header\"]"));
        String thankUMsgText = thankUMsg.getText();
        System.out.println(thankUMsgText);

        if (thankUMsgText.equals("Thank you for your order!")) {
            System.out.println("Message is displayed correctly");
        } else {
            System.out.println("Something wrong with thank you message");
        }

        WebElement backToHmeBtn = driver.findElement(By.xpath("//button[@id=\"back-to-products\"]"));
        backToHmeBtn.click();

    }
    static List<String> desiredItems = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");

    public static void listOfInventory(WebDriver driver)
    {
        List<WebElement> inventoryList = driver.findElements(By.xpath("//div[@class=\"inventory_item\"]"));

        for(String x: desiredItems) {
            inventoryList.stream()
                    .filter(inventory ->
                            inventory.findElement(By.xpath(".//div[@data-test='inventory-item-name']"))
                                    .getText().equals(x))
                    .findFirst().ifPresent(inventory -> inventory.findElement(By.xpath(".//button")).click());
        }

        driver.findElement(By.xpath("//a[@class=\"shopping_cart_link\"]")).click();


    }

    public static void checkedOutItems(WebDriver driver){
        List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class=\"cart_item\"]"));
        for(String x: desiredItems) {
           boolean value = cartItems.stream().anyMatch(cartItem -> cartItem.findElement(By.xpath(".//div[@class=\"inventory_item_name\"]")).getText().equals(x));
           if(value)
           {
               System.out.println(x+" is present in cart");
           }
        }
        WebElement checkoutBtn = driver.findElement(By.id("checkout"));
        checkoutBtn.click();
    }

    public static void personalDetails(WebDriver driver)
    {
        WebElement firstNameTbox = driver.findElement(By.id("first-name"));
        firstNameTbox.sendKeys("RandomUser");

        WebElement lastNameTbox = driver.findElement(By.id("last-name"));
        lastNameTbox.sendKeys("Sauce");

        WebElement postalCodeTbox = driver.findElement(By.id("postal-code"));
        postalCodeTbox.sendKeys("45879");

        WebElement continueBtn = driver.findElement(By.id("continue"));
        continueBtn.click();
    }
    public static void cartValueCalculation(WebDriver driver)
    {
    List<WebElement> cart_items = driver.findElements(By.xpath("//div[@class=\"cart_item\"]"));

    List<String> valueOfItems = cart_items.stream()
            .map(cart_item -> cart_item.findElement(By.xpath(".//div[@class='inventory_item_price']")))
            .map(WebElement::getText).toList();
    float total = 0;
        for(String i: valueOfItems)
        {
            total+= Float.parseFloat(i.replaceAll("[a-zA-Z\\s:$]",""));
        }
        System.out.println(total);

        String totalB4TaxText = driver.findElement(By.xpath("//div[@class=\"summary_subtotal_label\"]")).getText();
        String totalB4TaxTextString = totalB4TaxText.replaceAll("[a-zA-Z\\s$:]","");
        float totalB4tax = Float.parseFloat(totalB4TaxTextString);
        Assert.assertEquals(total,totalB4tax);
        float totalTax = Float.parseFloat((driver.findElement(By.xpath("//div[@class=\"summary_tax_label\"]")).getText().replaceAll("[a-zA-Z\\s:$]","")));
        float totalValue = totalB4tax+totalTax;
        float totalValueDisplayed = Float.parseFloat(driver.findElement(By.xpath("//div[@class=\"summary_total_label\"]")).getText().replaceAll("[a-zA-Z\\s:$]",""));
        System.out.println(totalValue);
        Assert.assertEquals(totalValue,totalValueDisplayed);


        driver.findElement(By.xpath("//button[@data-test=\"finish\"]")).click();
    }


    public static void justLogin(WebDriver driver)
    {
        WebElement userNameTbox = driver.findElement(By.id("user-name"));
        userNameTbox.sendKeys("standard_user");

        WebElement passwordTbox = driver.findElement(By.id("password"));
        passwordTbox.sendKeys("secret_sauce");

        WebElement passwordBtn = driver.findElement(By.xpath("//input[@id=\"login-button\"]"));
        passwordBtn.click();
    }
}
