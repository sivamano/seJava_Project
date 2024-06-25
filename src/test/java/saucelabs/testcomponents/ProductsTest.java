package saucelabs.testcomponents;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ProductsTest extends BaseTest {

    @Test()
    public void productDetailsVerification(){
        //1.Login as a standard user
        loginPage.loginToApp("standard_user","secret_sauce");

        //2.Verify the following product details:
            //product name

    }


}
