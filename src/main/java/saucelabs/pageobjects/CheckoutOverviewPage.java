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
        public float[] valueOfCartItemsBeforeTax(){
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

            float totalB4Tax[] = {totalBeforeTax_Computed,totalBeforeTax_Display};
            return totalB4Tax;

        }

        public float[] valueOfCartItemsAfterTax()
        {
           // double totalBeforeTax_Display = Double.parseDouble(totalBeforeTax_DisplayElement.getText().replaceAll("[a-zA-Z\\s$:]",""));
            float totalTax = Float.parseFloat(totalTax_DisplayElement.getText().replaceAll("[a-zA-Z\\s$:]",""));
            float totalValueComputed_noRounding = (float) (totalBeforeTax_Computed+totalTax);
            String roundedNumberStr = String.format("%.2f", totalValueComputed_noRounding);
            float totalValueComputed =  Float.parseFloat(roundedNumberStr);
            float totalValueDisplayed = Float.parseFloat(totalValue_DisplayElement.getText().replaceAll("[a-zA-Z\\s$:]",""));

            float totalValue[] ={totalValueComputed,totalValueDisplayed};
            return totalValue;

        }

        public CheckoutCompletePage proceedToFinish() throws Exception {
            try{
                finishButton.click();
                CheckoutCompletePage checkoutCompletePageObj = new CheckoutCompletePage(driver);
                return checkoutCompletePageObj;
            }
            catch(Exception ex){
                throw new Exception("element not found");
            }
        }



    }

