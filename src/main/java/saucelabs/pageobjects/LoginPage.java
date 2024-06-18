package saucelabs.pageobjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    // this class contains details of all objects in the login page of https://www.saucedemo.com/

    // declaring the variable driver
    WebDriver driver;

    //create a constructor to invoke driver
    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // store the locators in the page in the WebElement variables
    @FindBy(id = "user-name")
            WebElement userNameTextBox;

    @FindBy(id = "password")
            WebElement passwordTextBox;

    @FindBy(xpath = "//input[@id=\"login-button\"]")
            WebElement loginButton;

    @FindBy(xpath = "//h3[@data-test=\"error\"]")
    WebElement errorMessageElement;
    //open the application to be tested

    public void openApp()
    {
        driver.get("https://www.saucedemo.com/");
    }

    // create business useful methods to perform actions
    public void loginToApp(String userName, String password)
    {
        userNameTextBox.sendKeys(userName);
        passwordTextBox.sendKeys(password);
        loginButton.click();

    }

    public String loginErrorMessage()
    {
        String errorText = errorMessageElement.getText();
        return errorText;
    }

}
