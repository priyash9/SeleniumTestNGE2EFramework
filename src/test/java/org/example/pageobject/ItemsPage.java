package org.example.pageobject;

import org.example.services.AbstractBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ItemsPage extends AbstractBasePage {
    WebDriver driver;
    //final By appAndShoesLink = By.cssSelector("[class='top-menu'] [href='/apparel-shoes']");
    final By itemNames = By.cssSelector(".product-title");
    final By shoppingCart = By.cssSelector(".ico-cart .cart-label");

    public ItemsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(css = "[class='top-menu'] [href='/apparel-shoes']")
    WebElement appAndShoesLink;

    @FindBy(css = "#bar-notification .content")
    WebElement validationMessage;


    public void addItemToCart(String productToBeAdded)
    {
        waitForElementToBeClicked(appAndShoesLink).click();
        waitForElementToBeClicked(
                addToCartButton(productToBeAdded)
        ).click();
    }
    public String  validateMessage()
    {
        return waitForVisiblityOfElement(validationMessage).getText();

    }

    private By addToCartButton(String productName) {
        return By.xpath(
                "//h2[contains(@class,'product-title')][contains(.,'" + productName + "')]" +
                        "/following-sibling::div[contains(@class,'add-info')]" +
                        "//input[contains(@class,'product-box-add-to-cart-button')]"
        );
    }
}
