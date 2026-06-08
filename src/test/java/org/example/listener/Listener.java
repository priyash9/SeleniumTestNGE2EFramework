package org.example.listener;

import com.aventstack.extentreports.ExtentTest;
import org.example.services.WebDriverBaseClass;
import org.example.utils.ScreenshotHandler;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.example.reports.ExtentReport;

import java.io.IOException;

public class Listener implements ITestListener {

    ExtentTest test;

    ThreadLocal<ExtentTest> testInstance = new ThreadLocal<>();
    @Override
    public void onTestStart(ITestResult result) {
        test = ExtentReport.getExtentReport(result.getMethod().getMethodName());
        testInstance.set(test);
        System.out.println("Test Started : " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed : " + result.getMethod().getMethodName());

    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = null;
        test = testInstance.get();

        if (result.getInstance() instanceof WebDriverBaseClass) {
            driver = ((WebDriverBaseClass) result.getInstance()).returnDriver();
        }

        // Log the failure to Extent Reports
        if (test != null) {
            test.fail(result.getThrowable());
        }
        if (driver != null) {
            try {
                String filePath = ScreenshotHandler.takeScreenShot(
                        driver,
                        result.getMethod().getMethodName()
                );
                test.addScreenCaptureFromBase64String(filePath);
            } catch (IOException e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            System.err.println("Could not capture screenshot because WebDriver was null.");
        }

    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReport.flushExtentReport();
    }
}
