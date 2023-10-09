package com.znsio.rpap;

import com.znsio.applitools.integration.ApplitoolsInitializer;
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

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;
import static com.znsio.rpap.utils.DriverFactory.WEB;

public class BaseTest extends ApplitoolsInitializer {
    protected static WebDriver driver;
    private static WebDriverWait wait;
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    protected Page page;

    @BeforeSuite
    public void suiteSetup() throws MalformedURLException {
        logInfoMessage("Inside @BeforeSuite of " + BaseTest.class.getSimpleName());
        driver = DriverFactory.getDriver();
        wait = DriverFactory.getWait(driver);
        if (DriverFactory.getPlatform().equals(WEB)) {
            ApplitoolsInitializer.driverSetupForApplitoolsInitializer(driver);
        } else {
            ApplitoolsInitializer.driverSetupForApplitoolsInitializer((AppiumDriver) driver);
        }
        logInfoMessage("Driver is ready");
    }

    @BeforeMethod
    public void methodSetup() {
        page = new Page(driver, wait);
        logInfoMessage("Page setup is completed");
    }

    @AfterSuite
    public void suiteTearDown() {
        logInfoMessage("Killing driver");
        DriverFactory.killDriver();
    }
}
