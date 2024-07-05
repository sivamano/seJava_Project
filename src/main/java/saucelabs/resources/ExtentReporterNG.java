package saucelabs.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

    public static ExtentReports getReport(){
        String filePath = System.getProperty("user.dir")+"//reports//index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(filePath);
        reporter.config().setReportName("Basic Test");
        reporter.config().setDocumentTitle("Sauce Labs Test Report");

        ExtentReports extent = new ExtentReports() ;
        extent.attachReporter(reporter);
        extent.setSystemInfo("tester","siva");
        return extent;
    }
}
