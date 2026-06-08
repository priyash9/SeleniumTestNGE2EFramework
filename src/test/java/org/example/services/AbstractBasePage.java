package org.example.services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AbstractBasePage {
    WebDriver driver;
    WebDriverWait wait;
    Select select;

    public AbstractBasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public WebElement waitForElementToBeClicked(WebElement elementID) {
        return wait.until(ExpectedConditions.elementToBeClickable(elementID));
    }
    public WebElement waitForElementToBeClicked(By id) {
        return wait.until(ExpectedConditions.elementToBeClickable(id));
    }

    public WebElement waitForElementToBeLocated(By elementID) {
       return wait.until(ExpectedConditions.visibilityOfElementLocated(elementID));
    }

    public WebElement waitForVisiblityOfElement(WebElement elementID) {
        return wait.until(ExpectedConditions.visibilityOf(elementID));
    }
    public WebElement waitForElementToBeVisible(WebElement elementID)
    {
        return wait.until(ExpectedConditions.visibilityOf(elementID));
    }
    public List<WebElement> waitForAllElementToBeVisible(List<WebElement> elementID)
    {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elementID));
    }
    public void waitForTextToBePresentInElementLocated(By element, String text)
    {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(element, text));
    }
    public void waitForTextToBePresentInElement(WebElement element, String text)
    {
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }
    public void selectOptionByVisibleText(WebElement ddOption,String text)
    {
        new Select(ddOption).selectByVisibleText(text);
    }

}
