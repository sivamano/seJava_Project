package saucelabs.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import saucelabs.pageobjects.LoginPage;
import utilities.MathFunctions;


public class MenuBar {

    //declare driver variable
    WebDriver driver;
    MathFunctions mathFunctionsObj = new MathFunctions();

    //give life to driver via the constructor
    public MenuBar(WebDriver driver)
    {
    this.driver = driver;
    PageFactory.initElements(driver,this);
    }

    @FindBy(css="button#react-burger-menu-btn")
    WebElement menuBtn;

    @FindBy(xpath = "//img[@data-test=\"open-menu\"]")
    WebElement menuBtnImg;

    @FindBy(css="a#inventory_sidebar_link")
    WebElement allItemsBtn;


    @FindBy(css="a#about_sidebar_link")
    WebElement   aboutBtn;

    @FindBy(css= "a#logout_sidebar_link")
    WebElement logoutBtn;

    @FindBy(css="a#reset_sidebar_link")
    WebElement resetAppBtn;

    public LoginPage logoutFromApp() throws InterruptedException {
        menuBtn.click();
        Thread.sleep(1000);
        logoutBtn.click();
        LoginPage loginPageObj = new LoginPage(driver);
        return loginPageObj;
    }

    public String getMenuTransformProperty(){
        String transformValue = menuBtnImg.getCssValue("transform");
        String rotationResult = mathFunctionsObj.checkElementRotation(transformValue);
        return rotationResult;

    }

}
