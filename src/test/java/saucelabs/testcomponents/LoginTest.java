package saucelabs.testcomponents;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LoginTest extends BaseTest{
    //declare any global variable
    String dataFilePath = System.getProperty("user.dir")+"/src/test/java/saucelabs/data/login.json";

    //test cases
    @Test(dataProvider = "wrongUserNameData")
    void wrongUserName(HashMap<String,Object> input) {
        loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        String errorMessageDisplayed = loginPage.loginErrorMessage();
        String expectedErrorMessage = (String) input.get("loginErrorMessage");
        Assert.assertTrue(errorMessageDisplayed.contains(expectedErrorMessage),"Error message is displayed correctly");
    }
    @Test(dataProvider = "wrongPasswordData")
    void wrongPassword(HashMap<String,Object> input) {
        loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        String errorMessageDisplayed = loginPage.loginErrorMessage();
        String expectedErrorMessage = (String) input.get("loginErrorMessage");
        Assert.assertTrue(errorMessageDisplayed.contains(expectedErrorMessage),"Error message is displayed correctly");
    }
    @Test(dataProvider = "lockedOutUserTestData")
    void lockedOutUserTest(HashMap<String,Object> input){
        loginPage.loginToApp((String) input.get("username"), (String) input.get("password"));
        String errorMessageDisplayed = loginPage.loginErrorMessage();
        String expectedErrorMessage = (String) input.get("loginErrorMessage");
        Assert.assertTrue(errorMessageDisplayed.contains(expectedErrorMessage),"Error message is displayed correctly");
    }

    //data for test cases
    @DataProvider
    Object[][] wrongUserNameData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(0)}};
    }
    @DataProvider
    Object[][] wrongPasswordData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][]{{data.get(1)}};
    }
    @DataProvider
    Object[][] lockedOutUserTestData() throws IOException {
        List<HashMap<String, Object>> data = getDataToMap(dataFilePath);
        return new Object[][] {{data.get(2)}};
    }
}
