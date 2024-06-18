package saucelabs.testcomponents;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidations extends BaseTest{

    @Test
    void lockedOutUser()
    {
        loginPage.loginToApp("locked_out_user","secret_sauce");

        Assert.assertEquals(loginPage.loginErrorMessage(),"Epic sadface: Sorry, this user has been locked out.");
    }

    @Test
    void incorrectPassword()
    {
        loginPage.loginToApp("standard_user","Test");
        Assert.assertEquals(loginPage.loginErrorMessage(),"Epic sadface: Username and password do not match any user in this service");
    }




}
