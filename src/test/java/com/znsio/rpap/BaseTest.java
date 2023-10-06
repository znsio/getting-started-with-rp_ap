package com.znsio.rpap;

import com.znsio.applitools.integration.ApplitoolsInitializer;
import com.znsio.reportportal.integration.properties.Config;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.znsio.rpap.pages.Page;
import com.znsio.rpap.utils.DriverFactory;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Properties;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;
import static com.znsio.rpap.utils.DriverFactory.WEB;

public class BaseTest extends ApplitoolsInitializer {
    protected static WebDriver driver;
    private static WebDriverWait wait;
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    private static final Properties config = Config.loadProperties(System.getProperty("CONFIG"));
    protected Page page;

    @BeforeSuite
    public void suiteSetup() throws MalformedURLException {
        log("Inside @BeforeSuite of " + BaseTest.class.getSimpleName());
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(config.getProperty("PAGE_LOAD_TIME"))));
        if (DriverFactory.getPlatform().equals(WEB)) {
            ApplitoolsInitializer.driverSetupForApplitoolsInitializer(driver);
        } else {
            ApplitoolsInitializer.driverSetupForApplitoolsInitializer((AppiumDriver) driver);
        }
        log("Driver is ready");
    }

    @BeforeMethod
    public void methodSetup() {
        page = new Page(driver, wait);
        log("Page setup is completed");
    }

    @AfterSuite
    public void suiteTearDown() {
        log("Killing driver");
        DriverFactory.killDriver();
    }

    public void log(String message) {
        LOGGER.info(message);
        logInfoMessage(message);
    }
}
