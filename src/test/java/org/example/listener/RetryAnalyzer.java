package org.example.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {

        int maxTry = 1;
        if (count < maxTry) {
            count++;
            return true;
        }
        return false;
    }
}
