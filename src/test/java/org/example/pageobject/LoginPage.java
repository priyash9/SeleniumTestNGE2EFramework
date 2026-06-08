package org.example.pageobject;

import org.example.services.AbstractBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractBasePage {
    protected WebDriver driver;
    MenuPage menuPage;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        menuPage = new MenuPage(driver);

    }

    @FindBy(css = "[href='/login']")
    WebElement loginLink;

    @FindBy(id = "Email")
    WebElement emailId;

    @FindBy(id = "Password")
    WebElement password;

    @FindBy(css = ".login-button")
    WebElement signInBtn;

    @FindBy(css = "div.validation-summary-errors")
    WebElement loginErrorMsg;

    @FindBy(css = ".field-validation-error")
    WebElement invalidUserNameError;

    public MenuPage logInWithValidUserName(String username, String loginPassword) {
        signIn(username, loginPassword);
        return menuPage;
    }

    public String logInWithUnregisteredUserName(String username, String loginPassword) {
        signIn(username, loginPassword);
        return loginErrorMsg.getText();
    }

    public String logInWithInvalidUserName(String username, String loginPassword) {
        signIn(username, loginPassword);
        return invalidUserNameError.getText();
    }

    private void signIn(String username, String loginPassword) {
        loginLink.click();
        waitForElementToBeClicked(emailId).sendKeys(username);
        password.sendKeys(loginPassword);
        signInBtn.click();
    }

}
