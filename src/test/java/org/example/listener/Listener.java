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



    ThreadLocal<ExtentTest> testInstance = new ThreadLocal<>();
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = ExtentReport.createExtentReport(result.getMethod().getMethodName());
        testInstance.set(test);
        System.out.println("Test Started : " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testInstance.get().pass("********* Test Passed *********** ");
        System.out.println("Test Passed : " + result.getMethod().getMethodName());

    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testInstance.get();
        test.fail(result.getThrowable());
        WebDriver driver = null;

        if (result.getInstance() instanceof WebDriverBaseClass) {
            driver = ((WebDriverBaseClass) result.getInstance()).returnDriver();
        }

        if (driver != null) {
            try {
                String filePath = ScreenshotHandler.takeScreenShot(
                        driver,
                        result.getMethod().getMethodName()
                );
                test.addScreenCaptureFromPath(filePath);
            } catch (IOException e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            System.err.println("Could not capture screenshot because WebDriver was null.");
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = testInstance.get();

        if (result.getAttribute("retry") != null) {
            test.info("Retrying test...");
        } else {
            test.skip(result.getThrowable());
        }
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
        ExtentReport.flushExtentReport();
    }
}
