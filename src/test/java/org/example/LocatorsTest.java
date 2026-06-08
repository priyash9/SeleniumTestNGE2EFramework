package org.example;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.swing.*;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;


public class LocatorsTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void intialiseWebDriver() {
        ChromeOptions options = new ChromeOptions();
        // Disable browser notifications
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void loginTest() throws InterruptedException {

        String userName = "Tom";
        driver.get("https://rahulshettyacademy.com/locatorspractice/");

        driver.findElement(By.id("inputUsername")).sendKeys(userName);
        driver.findElement(By.name("inputPassword")).sendKeys("Tom");
        driver.findElement(By.className("submit")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector(".error")).getText(), "* Incorrect username or password");
        driver.findElement(By.cssSelector(".forgot-pwd-container")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".reset-pwd-btn")).click();
        String string1 = (((driver.findElement(By.className("infoMsg")).getText().split("'"))[1]).split("'"))[0];
        System.out.println(string1);
        driver.findElement(By.xpath("//button[@class='go-to-login-btn']")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("inputUsername")).sendKeys("Tom");
        driver.findElement(By.name("inputPassword")).sendKeys(string1);
        driver.findElement(By.className("submit")).click();
        Thread.sleep(2000);
        Assert.assertEquals(driver.findElement(By.cssSelector(".login-container p")).getText(), "You are successfully logged in.");
        if (driver.findElement(By.cssSelector(".login-container h2")).getText().contains(userName))
            Assert.assertEquals(driver.findElement(By.cssSelector(".login-container h2")).getText(), "Hello " + userName + ",");
        driver.close();
    }

    @Test
    public void bookingComTest() throws InterruptedException {
        driver.get("https://www.booking.com/");
        driver.findElement(By.cssSelector("input[id=':R55amr5:']")).sendKeys("Lon");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-testid='autocomplete-results']")));
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.dismiss();

        List<WebElement> locations = driver.findElements(By.xpath("//li[@id='autocomplete-result-1']//div"));
        for (WebElement loc : locations) {
            System.out.println(loc.getText());
            if (loc.getText().contains("Central London")) {
                driver.findElement(By.xpath("//button[@aria-label='Dismiss sign-in info.']")).click();
                Thread.sleep(2000);
                loc.click();
                break;
            }
        }
        System.out.println(driver.findElement(By.cssSelector("input[id=':R55amr5:']")).getText());
    }

    @Test
    public void bookingComTestSite() throws InterruptedException {
        driver.get("https://www.agoda.com/");
        driver.findElement(By.id("textInput")).sendKeys("Mangalore");
        Thread.sleep(2000);
        List<WebElement> destinations = driver.findElements(By.cssSelector("#search-box-autocomplete-id li"));
        for (WebElement e : destinations) {
            // System.out.println(e.getText());
            if (e.getText().contains("Mangalore, India")) {
                e.click();
                break;
            }
        }
        System.out.println(driver.findElement(By.cssSelector(".IconBox__child input")).getDomAttribute("value"));
        driver.findElement(By.cssSelector(".SearchBoxTextDescription--checkIn ")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".SearchBoxTextDescription--checkIn")).click();
        driver.findElement(By.cssSelector("[data-selenium-date$='-05-27']")).click();
        driver.findElement(By.cssSelector("[data-selenium-date$='-05-29']")).click();
        driver.findElement(By.cssSelector("button[aria-label='Add Room']")).click();
        driver.findElement(By.cssSelector("[data-element-name=\"search-button\"]")).click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.cssSelector(" h3[data-element-name='properties-available-text']")).getText());
    }

    @Test
    public void addItemsToCart() throws InterruptedException {
        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
        String[] vegetableToAdd = {"Cucumber", "Pomegranate", "Mushroom", "Walnuts"};
        Set<String> listIfItemsToBeAdded = Arrays.stream(vegetableToAdd).collect(Collectors.toSet());
        List<WebElement> items = driver.findElements(By.xpath("//div[@class='product']"));
        int itemsAdded = 0;
        for (WebElement product : items) {
            String vegetable = (product.findElement(By.tagName("h4")).getText().split("-"))[0].trim();
            if (listIfItemsToBeAdded.contains(vegetable)) {

                System.out.println("Vegetable added: " + vegetable);
                product.findElement(By.xpath(".//button")).click();
                itemsAdded++;
                if (itemsAdded == listIfItemsToBeAdded.size()) {
                    break;
                }

            }

        }

    }

    @Test
    public void getWindowHandles() {
        driver.get("https://rahulshettyacademy.com/loginpagePractise/");
        driver.findElement(By.cssSelector("[href*=\"rahul\"]")).click();
        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> itr = windowHandles.iterator();
        String parentID = itr.next();
        String childID;
        String userName;
        while (itr.hasNext()) {
            childID = itr.next();
            driver.switchTo().window(childID);
            System.out.println(driver.getCurrentUrl());
            userName = driver.findElement(By.cssSelector(".im-para.red")).getText();
            String emailAdd = (((userName.split("at"))[1]).split("with"))[0].trim();
            driver.switchTo().window(parentID);
            driver.findElement(By.id("username")).sendKeys(emailAdd);
            driver.findElement(By.id("signInBtn")).click();
            if (Objects.requireNonNull(driver.findElement(By.cssSelector(".alert-danger")).getAttribute("style")).contains("block")) {
                System.out.println(driver.findElement(By.cssSelector(".alert-danger")).getText());
            }
        }
    }

    //Dragand Drop action
    @Test
    public void dragAndDrop() throws InterruptedException {
        driver.get("https://www.globalsqa.com/demo-site/draganddrop/");
        System.out.println(driver.findElements(By.tagName("iframe")).size());
        driver.switchTo().frame(driver.findElement(By.className("demo-frame")));
        List<WebElement> lst = driver.findElements(By.cssSelector(".ui-widget-content h5"));
        WebElement source = null;
        WebElement destination = driver.findElement(By.id("trash"));
        Actions actions = new Actions(driver);
        for (WebElement w : lst) {
            if (w.getText().contains("High Tatras 2")) {
                source = w;
                break;
            }

        }
        actions.dragAndDrop(source, destination).build().perform();

        Thread.sleep(5000);
    }

    //Dragand Drop action
    @Test
    public void dragAndDropImproved() throws InterruptedException {
        driver.get("https://www.globalsqa.com/demo-site/draganddrop/");
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.className("demo-frame")));
        WebElement source = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='High Tatras 2']")));
        WebElement destination = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("trash")));
        actions.dragAndDrop(source, destination).perform();
        Thread.sleep(5000);
    }

    @Test
    public void arrangeVegInSortedOrder() throws InterruptedException {
        final By topDealsLocator = By.cssSelector(".cart a:nth-child(2)");
        final By fruitNameHeaderLocator = By.xpath(".//tr/th[1]");
        final By fruitNameColumnLocator = By.xpath("//tbody//tr/td[1]");
        final By pageSizeDropDown = By.id("page-menu");

        driver.get("https://rahulshettyacademy.com/seleniumPractise/#");
        driver.findElement(topDealsLocator).click();
        String parentWindow = driver.getWindowHandle();

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(parentWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        System.out.println(driver.getCurrentUrl());
        new Select(driver.findElement(pageSizeDropDown)).selectByValue("20");
        List<String> originallist = driver.findElements(fruitNameColumnLocator).stream().map(WebElement::getText).toList();
        List<String> sortedlist = originallist.stream().sorted().toList();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        fruitNameHeaderLocator)
        ).click();

        List<String> newList = driver.findElements(fruitNameColumnLocator).stream().map(WebElement::getText).toList();
        Assert.assertTrue(newList.equals(sortedlist));

        //get price of particular item

        String[] vegetableToAdd = {"Cheese", "Banana", "Guava", "Tomato"};
        Set<String> veggies = new LinkedHashSet<>(List.of(vegetableToAdd));

        List<String> price = driver.findElements(fruitNameColumnLocator).stream().
                filter(i->veggies.contains(i.getText())).map(this::getPrice).collect(Collectors.toList());
        price.forEach(System.out::println);

    }

    private String getPrice(WebElement a)  {
        final By priceHeader = By.xpath("following-sibling::td[1]");
        return  a.findElement(priceHeader).getText();
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        driver.quit();
    }
}
