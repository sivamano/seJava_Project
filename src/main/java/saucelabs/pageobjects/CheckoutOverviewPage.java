package saucelabs.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutOverviewPage {
    WebDriver driver;
    private float totalBeforeTax_Computed;

    public CheckoutOverviewPage(WebDriver driver){

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(className = "cart_item")
    List<WebElement> cartItems;

    @FindBy(className = "summary_subtotal_label")
    WebElement totalBeforeTax_DisplayElement;

    @FindBy(className =  "summary_tax_label")
    WebElement totalTax_DisplayElement;

    @FindBy(className = "summary_total_label")
    WebElement totalValue_DisplayElement;

    @FindBy(id = "finish")
    WebElement finishButton;

    //to fetch the value of each item and add it
        public void valueOfCartItemsBeforeTax(){
           totalBeforeTax_Computed = (float) cartItems
                    .stream()
                    .mapToDouble(cartItem ->
                    {
                        WebElement priceElement = cartItem.findElement(By.xpath(".//div[@class=\"inventory_item_price\"]"));
                        String priceText = priceElement.getText().replaceAll("[a-zA-Z\\s:$]", "");
                        return Double.parseDouble(priceText);

                    })
                   .sum();

            float totalBeforeTax_Display =  Float
                    .parseFloat(totalBeforeTax_DisplayElement.getText().replaceAll("[a-zA-Z\\s$:]",""));

            if(totalBeforeTax_Computed==totalBeforeTax_Display)
            {
                System.out.println("Total value of cart items (before tax) is displayed correctly as $ "+totalBeforeTax_Computed);
            }
            else
            {
                System.out.println("The value of cart item (before tax) displayed $"+totalBeforeTax_Display+" differs from the computation value of $"+totalBeforeTax_Computed+".");
            }
        }

        public void valueOfCartItemsAfterTax()
        {
           // double totalBeforeTax_Display = Double.parseDouble(totalBeforeTax_DisplayElement.getText().replaceAll("[a-zA-Z\\s$:]",""));
            float totalTax = Float.parseFloat(totalTax_DisplayElement.getText().replaceAll("[a-zA-Z\\s$:]",""));
            float totalValueComputed = totalBeforeTax_Computed+totalTax;
            float totalValueDisplayed = Float.parseFloat(totalValue_DisplayElement.getText().replaceAll("[a-zA-Z\\s$:]",""));
            if(totalValueComputed == totalValueDisplayed)
            {
                System.out.println("Total value of cart items is displayed correctly as $ "+totalValueComputed);
            }
            else
            {
                System.out.println("The value of cart item displayed $"+totalValueDisplayed+" differs from the computation value of $"+totalValueComputed+".");
            }
        }

        public void proceedToFinish() throws Exception {
            try{
                finishButton.click();
            }
            catch(Exception ex){
                throw new Exception("element not found");
            }
        }



    }

