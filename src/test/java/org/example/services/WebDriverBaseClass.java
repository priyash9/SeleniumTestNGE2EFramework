package org.example.services;

import org.example.pageobject.ItemsPage;
import org.example.pageobject.LoginPage;
import org.example.utils.ScreenshotHandler;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;


public class WebDriverBaseClass {
    protected DriverManager driverManager;
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected ItemsPage itemsPage;
    protected ScreenshotHandler screenshotHandler;
    private final String ecommerceUrl =
            System.getProperty("URL", "https://demowebshop.tricentis.com/");

    @BeforeMethod(alwaysRun = true)
    public void startDriver() {
        driverManager = new DriverManager();
        driver = driverManager.getDriver();
        driver.get(ecommerceUrl);
        loginPage = new LoginPage(driver);
        itemsPage = new ItemsPage(driver);
        screenshotHandler = new ScreenshotHandler();

    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver returnDriver()
    {
        return driver;
    }


}
