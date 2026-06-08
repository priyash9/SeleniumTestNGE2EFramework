package org.example.pageobject;

import org.example.services.AbstractBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ShoppingCartPage extends AbstractBasePage {
    WebDriver driver;
    String countryName = "United States";
    String stateName = "New York";
    final By productAddedToShoppingCart = By.cssSelector(".cart-item-row .product-name");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "CountryId")
    WebElement countryDropDown;

    @FindBy(id = "StateProvinceId")
    WebElement stateProvinceDD;

    @FindBy(id = "termsofservice")
    WebElement termsOfServiceCheckBox;

    @FindBy(id = "checkout")
    WebElement checkout;



    public void verifyItemsAdded(String productToBeAdded)
    {
        waitForElementToBeLocated(productAddedToShoppingCart);
        Assert.assertEquals(driver.findElement(productAddedToShoppingCart).getText(), productToBeAdded);
    }
    public String getItemText()
    {
        return driver.findElement(productAddedToShoppingCart).getText();
    }
    public CheckoutPage checkOutTheAddedProducts()
    {
        selectCountryAndStateOptions(countryName,stateName,countryDropDown,stateProvinceDD);
        termsOfServiceCheckBox.click();
        waitForElementToBeClicked(checkout).click();
        return new CheckoutPage(driver);
    }
    public void selectCountryAndStateOptions(String country, String state, WebElement countryDropDown, WebElement stateProvinceDD) {
        selectOptionByVisibleText(countryDropDown,country);
        waitForElementToBeClicked(stateProvinceDD).click();
        selectOptionByVisibleText(stateProvinceDD,state);
    }

}
