package com.animecatalog.ui;

import com.animecatalog.config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Author: cyqatester
 */

public final class DriverFactory {
    private DriverFactory() {}

    public static WebDriver createDriver() {
        String browser = Config.browser().toLowerCase();
        WebDriver driver;
        switch (browser) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (Config.headless()) options.addArguments("--headless=new");
                options.addArguments("--window-size=1440,1000", "--disable-notifications");
                driver = new ChromeDriver(options);
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (Config.headless()) options.addArguments("-headless");
                driver = new FirefoxDriver(options);
            }
            default -> throw new IllegalArgumentException("Unsupported BROWSER: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }
}
