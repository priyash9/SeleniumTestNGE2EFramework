package org.example.pageobject;

import org.example.services.AbstractBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CheckoutPage extends AbstractBasePage {
    WebDriver driver;
    ShoppingCartPage shoppingCartPage;
    String countryName = "United States";
    String stateName = "New York";
    WebDriverWait wait;
    private static final String EXPECTED_PAYMENT_INFO = "You will pay by COD";
    final By checkOutTitle = By.cssSelector(".checkout-data .title");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(css = ".page-title")
    WebElement checkoutPageTitle;
    @FindBy(id = "BillingNewAddress_FirstName")
    WebElement firstName;
    @FindBy(id = "BillingNewAddress_LastName")
    WebElement lastName;
    @FindBy(id = "BillingNewAddress_Email")
    WebElement emailId;
    @FindBy(id = "BillingNewAddress_CountryId")
    WebElement country;
    @FindBy(id = "BillingNewAddress_StateProvinceId")
    WebElement state;
    @FindBy(id = "BillingNewAddress_City")
    WebElement city;
    @FindBy(id = "BillingNewAddress_Address1")
    WebElement address1;
    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    WebElement postalCode;
    @FindBy(id = "BillingNewAddress_PhoneNumber")
    WebElement contactNo;
    @FindBy(css = "#billing-buttons-container [title='Continue']")
    WebElement continueBillingAddress;
    @FindBy(id = "billing-address-select")
    WebElement newAddress;

    @FindBy(css = "#shipping-buttons-container [title='Continue']")
    WebElement continueShippingAddress;
    @FindBy(css = "#shipping-method-buttons-container [value='Continue']")
    WebElement continueShippingMethod;
    @FindBy(css = "#payment-method-buttons-container [value='Continue']")
    WebElement continuePaymentMethod;
    @FindBy(css = "#payment-info-buttons-container [value='Continue']")
    WebElement continueInfoMethod;
    @FindBy(css = "#confirm-order-buttons-container input")
    WebElement confirmOrder;
    @FindBy(css = "#checkout-payment-info-load p")
    WebElement paymentInfo;



    public String validateCheckOutPage() {
        waitForTextToBePresentInElement(checkoutPageTitle, "Checkout");
        fillBillingAddress();
        return returnCheckOutPageConfirmationMessage();
    }

    public void fillBillingAddress() {
        shoppingCartPage = new ShoppingCartPage(driver);
        selectOptionByVisibleText(newAddress, "New Address");
        shoppingCartPage.selectCountryAndStateOptions(countryName, stateName, country, state);
        waitForElementToBeVisible(city).sendKeys("New York");
        waitForElementToBeVisible(address1).sendKeys("4040 Bell Street");
        waitForElementToBeVisible(postalCode).sendKeys("10004");
        waitForElementToBeVisible(contactNo).sendKeys("657888");
        continueBillingAddress.click();
        confirmOrderDetails();
        waitForTextToBePresentInElementLocated(checkOutTitle, "Your order has been successfully processed!");
    }

    private void confirmOrderDetails() {
        waitForElementToBeClicked(continueShippingAddress).click();
        waitForElementToBeClicked(continueShippingMethod).click();
        waitForElementToBeClicked(continuePaymentMethod).click();
        waitForTextToBePresentInElement(paymentInfo,EXPECTED_PAYMENT_INFO);
        waitForElementToBeClicked(continueInfoMethod).click();
        waitForElementToBeClicked(confirmOrder).click();

    }
    public String returnCheckOutPageConfirmationMessage() {
        return waitForElementToBeVisible(checkoutPageTitle).getText();
    }
}
