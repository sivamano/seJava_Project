package saucelabs.testcomponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import saucelabs.resources.ExtentReporterNG;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReport();
        /* *****THREAD SAFE ALTERNATIVE CODE****:
        Thread safe code, useful to avoid "concurrency" issues, when tests are executed in parallel

    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

         */

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        test = extent.createTest(result.getMethod().getMethodName());
        //*****THREAD SAFE ALTERNATIVE CODE****:
        // use this code during parallel execution, this creates a unique thread id for the "test"
        // extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
       ITestListener.super.onTestSuccess(result);
        test.log(Status.PASS,"Test passed");
        // *****THREAD SAFE ALTERNATIVE CODE****:
        // now replace the ExtentTest object "test"  with "extentTest.get()"
        //    extentTest.get().log(Status.PASS,result.getThrowable());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
           driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        test.fail(result.getThrowable());

        // *****THREAD SAFE ALTERNATIVE CODE****:
        // now replace the ExtentTest object "test"  with "extentTest.get()"
        // extentTest.get().fail(result.getThrowable());

        String screenshotPath = null;
        try {
            screenshotPath = takeScreenshot(result.getMethod().getMethodName(),driver);
        } catch (IOException e) {
            e.printStackTrace();
        }
        test.addScreenCaptureFromPath(screenshotPath,result.getMethod().getMethodName());

        // *****THREAD SAFE ALTERNATIVE CODE****:
        // now replace the ExtentTest object "test"  with "extentTest.get()"
        // extentTest.get().addScreenCaptureFromPath(screenshotPath,result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        extent.flush();
    }
}
