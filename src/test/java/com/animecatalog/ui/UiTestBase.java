package com.animecatalog.ui;

import com.animecatalog.config.Config;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.SkipException;

/**
 * Author: cyqatester
 */

public abstract class UiTestBase {
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUpBrowser() {
        if (Config.uiBaseUrl().isBlank()) {
            throw new SkipException("Set UI_BASE_URL before running Selenium UI tests.");
        }
        driver = DriverFactory.createDriver();
        driver.get(Config.uiBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
