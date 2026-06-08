package org.example.pageobject;

import org.example.services.AbstractBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MenuPage extends AbstractBasePage {
    WebDriver driver;
    ShoppingCartPage shoppingCartPage;
    final By accountDetails = By.cssSelector(".header-links .account");

    public MenuPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        shoppingCartPage = new ShoppingCartPage(this.driver);

    }

    @FindBy(xpath = "//input[@name='removefromcart']")
    List<WebElement> removeCheckboxes;

    @FindBy(css = ".update-cart-button")
    WebElement updateCart;

    final By shoppingCart = By.cssSelector(".ico-cart .cart-label");

    public String getUserDetails() {
        return waitForElementToBeLocated(accountDetails).getText();
    }

    public void clearCart() {
        waitForElementToBeClicked(shoppingCart).click();
        //waitForAllElementToBeVisible(removeCheckboxes);
        if (!removeCheckboxes.isEmpty()) {
            removeCheckboxes.forEach(WebElement::click);
            updateCart.click();
        }
        driver.navigate().refresh();
    }

    public ShoppingCartPage clickShoppingCartOption() {
        waitForElementToBeClicked(shoppingCart).click();
        return shoppingCartPage;
    }
}
