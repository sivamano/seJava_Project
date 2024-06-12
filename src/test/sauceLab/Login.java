import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.http.UrlTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {
    public static void main(String[] args)
    {
        String chromePath = "C:\\ChromeTest\\chrome-win64\\chrome.exe";
        ChromeOptions cOptions = new ChromeOptions();
        cOptions.setBinary(chromePath);

        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(cOptions);

        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        Login(driver);
    }
//Login as standardUser, choose a single product, checkout, order, go back to homepage
    public static void Login(WebDriver driver)
    {
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
        if(inventoryText.equals("Sauce Labs Backpack"))
        {
            System.out.println("Item displayed correctly in cart");
        }
        else{
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
        String itemTotalB4TaxString = itemTotalB4TaxText.replaceAll("[a-zA-Z\\s:$]","");
        float itemTotalB4Tax = Float.parseFloat(itemTotalB4TaxString);
        //System.out.println(itemTotalB4Tax);


        WebElement totalTaxFull = driver.findElement(By.xpath("//div[@class=\"summary_info\"]/div[@data-test=\"tax-label\"]"));
        String totalTaxText = totalTaxFull.getText();
        String totalTaxTextString = totalTaxText.replaceAll("[a-zA-Z\\s:$]","");
        float totalTax = Float.parseFloat(totalTaxTextString);
        //System.out.println(totalTax);

        float totalCartValue = totalTax+itemTotalB4Tax;
        System.out.println(totalCartValue);


        WebElement totalFull = driver.findElement(By.xpath("//div[@class=\"summary_info\"]/div[@data-test=\"total-label\"]"));
        String totalText = totalFull.getText();
        String totalTextString = totalText.replaceAll("[a-zA-Z\\s:$]","");
        float total = Float.parseFloat(totalTextString);
        System.out.println(total);

        if(totalCartValue == total )
        {
           System.out.println("Total amount is displayed correctly as, $"+total);
        }
        else
        {
            System.out.println("oops! something seems off with the total amount");
        }

        WebElement finishBtn = driver.findElement(By.xpath("//button[@data-test=\"finish\"]"));
        finishBtn.click();

        ////h2[@data-test="complete-header"]
        WebElement thankUMsg = driver.findElement(By.xpath("//h2[@data-test=\"complete-header\"]"));
        String thankUMsgText = thankUMsg.getText();
        System.out.println(thankUMsgText);

        if(thankUMsgText.equals("Thank you for your order!")){
            System.out.println("Message is displayed correctly");
        }
        else{
            System.out.println("Something wrong with thank you message");
        }

        WebElement backToHmeBtn = driver.findElement(By.xpath("//button[@id=\"back-to-products\"]"));
        backToHmeBtn.click();




    }
}
