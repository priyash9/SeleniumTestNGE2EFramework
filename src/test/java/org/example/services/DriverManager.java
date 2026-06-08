package org.example.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class DriverManager {

    private static String BROWSER = System.getProperty("browser", "chrome");
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public DriverManager() {
        driver.set(startDriverAndBrowser());
    }

    public WebDriver getDriver()
    {
        return driver.get();
    }

    public WebDriver startDriverAndBrowser() {
        WebDriver driver ;
        ChromeOptions options = new ChromeOptions();
        driver = switch (BROWSER) {
            case "firefox" -> new FirefoxDriver();
            case "safari" -> new SafariDriver();
            case "edge" -> new EdgeDriver();
            case "headless" -> {
                options.addArguments("--headless");
                yield new ChromeDriver(options);
            }
            default -> new ChromeDriver();
        };
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
        }

}
