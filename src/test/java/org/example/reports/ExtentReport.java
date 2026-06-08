package org.example.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {

    public static ExtentReports extentReport;
    public static ExtentSparkReporter extentSparkReporter;
    public static ExtentTest test;

    public static ExtentReports getReportDetails(String testName) {
        if (extentReport == null) {
            extentSparkReporter = new ExtentSparkReporter("ExtentReport.html");
            extentSparkReporter.config().setReportName("E2E Test Report");
            extentReport = new ExtentReports();
            extentReport.attachReporter(extentSparkReporter);
        }
        return extentReport;
    }

    public static ExtentTest getExtentReport(String testName) {
        return getReportDetails(testName).createTest(testName);
    }

    public static void flushExtentReport() {
        if (extentReport != null) {
            extentReport.flush();
        }
    }
}

