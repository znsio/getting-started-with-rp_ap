package com.znsio.rpap;

import com.znsio.applitools.integration.ApplitoolsInitializer;
import com.znsio.reportportal.integration.properties.Config;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.znsio.rpap.pages.Page;
import com.znsio.rpap.utils.DriverFactory;

import java.time.Duration;
import java.util.Properties;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;

public class BaseTest extends ApplitoolsInitializer {
    protected static WebDriver webDriver;
    private static WebDriverWait wait;
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    private static final Properties config = Config.loadProperties(System.getProperty("CONFIG"));
    protected Page page;

    @BeforeSuite
    public void suiteSetup() {
        LOGGER.info("Inside @BeforeSuite of " + BaseTest.class.getSimpleName());
        webDriver = DriverFactory.launchWebApplication(webDriver, config.getProperty(Config.BROWSER),
                config.getProperty("URL"));
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(Long.parseLong(config.getProperty("PAGE_LOAD_TIME"))));
        ApplitoolsInitializer.driverSetupForApplitoolsInitializer(webDriver);
        LOGGER.info("Browser Ready");
    }

    @BeforeMethod
    public void methodSetup() {
        page = new Page(webDriver, wait);
    }

    @AfterSuite
    public void suiteTearDown() {
        webDriver.quit();
    }

    public void log(String message) {
        LOGGER.info(message);
        logInfoMessage(message);
    }
}
