package saucelabs.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutCompletePage {
    WebDriver driver;

    public CheckoutCompletePage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(className ="complete-header")
    WebElement thanksText;

    @FindBy(id="back-to-products")
    WebElement backToProductsButton;

    public void verifyCompletionMessage(String thanksText)
    {
        if(this.thanksText.getText() == thanksText)
        {
            System.out.println("Message is displayed correctly"+ this.thanksText);
        }
    }

    public void proceedBackToProducts() throws Exception {
        try {
            if (backToProductsButton.isDisplayed())
                backToProductsButton.click();
        }catch (Exception ex) {
            throw new Exception("Element not found"+ex.getMessage());
        }
    }

}
