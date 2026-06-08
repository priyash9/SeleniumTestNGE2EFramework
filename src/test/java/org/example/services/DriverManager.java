package org.example.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public DriverManager() {
        driver.set(startDriverAndBrowser());
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public WebDriver startDriverAndBrowser() {
        String BROWSER = System.getProperty("browser", "chrome");
        WebDriver driver;
        driver = switch (BROWSER) {
            case "firefox" -> new FirefoxDriver();
            case "edge" -> new EdgeDriver();
            case "safari" -> new SafariDriver();
            case "headless" -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                yield new ChromeDriver(options);
            }
            case "chrome" -> new ChromeDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + BROWSER);
        };
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

}
