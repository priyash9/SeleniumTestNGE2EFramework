package org.example.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {

    public static ExtentReports extentReport;
    public static ExtentSparkReporter extentSparkReporter;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports getReportDetails() {
        if (extentReport == null) {
            extentSparkReporter = new ExtentSparkReporter("target/ExtentReport.html");
            extentSparkReporter.config().setReportName("E2E Test Report");
            extentSparkReporter.config().setDocumentTitle("Selenium Test");
            extentReport = new ExtentReports();
            extentReport.attachReporter(extentSparkReporter);
        }
        return extentReport;
    }

    public static ExtentTest createExtentReport(String testName) {
        ExtentTest test = getReportDetails().createTest(testName);
        extentTest.set(test);
        return test;
    }

    public static void flushExtentReport() {
        if (extentReport != null) {
            extentReport.flush();
        }
    }
}

