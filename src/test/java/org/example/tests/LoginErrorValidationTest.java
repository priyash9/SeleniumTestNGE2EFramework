package org.example.tests;


import org.example.listener.RetryAnalyzer;
import org.example.services.WebDriverBaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginErrorValidationTest extends WebDriverBaseClass {
    private static final String LOG_IN_ERROR = "Login was unsuccessful. Pleas correct the errors and try again.\n" +
            "The credentials provided are incorrect";
    private static final String INVALID_USERNAME_ERROR = "Pleas enter a valid email address.";

    @Test(groups = "error_validation",retryAnalyzer = RetryAnalyzer.class)
    public void validateNonRegisteredUser() {
        final String username = "tim@abc.com";
        final String loginPassword = "helloworld";
        Assert.assertEquals(loginPage.logInWithUnregisteredUserName(username, loginPassword), LOG_IN_ERROR);
    }

    @Test(groups = "error_validation")
    public void invalidUserName() {
        Assert.assertEquals(loginPage.logInWithInvalidUserName("XXXX", ""),
                INVALID_USERNAME_ERROR);
    }
}
