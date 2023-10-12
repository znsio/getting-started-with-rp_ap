package com.znsio.rpap;

import com.znsio.applitools.integration.ApplitoolsInitializer;
import com.znsio.rpap.pages.Page;
import com.znsio.rpap.utils.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;
import static com.znsio.rpap.utils.DriverFactory.WEB;

public class BaseTest {
    protected WebDriver driver;
    protected AppiumDriver appiumDriver;
    private static WebDriverWait wait;
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    protected Page page;
    protected ApplitoolsInitializer applitoolsInitializer;

    @BeforeSuite
    public void suiteSetup(XmlTest suite) throws IOException {
        logInfoMessage("Inside @BeforeSuite of " + BaseTest.class.getSimpleName());
        applitoolsInitializer = new ApplitoolsInitializer();
        applitoolsInitializer.setUpApplitoolsInitializer(suite);
    }

    @BeforeMethod
    public void methodSetup(Method method) throws MalformedURLException {
        logInfoMessage("Inside @BeforeMethod of " + BaseTest.class.getSimpleName());
        driver = DriverFactory.getDriver();
        wait = DriverFactory.getWait(driver);
        logInfoMessage("Driver is ready");
        page = new Page(driver, wait);
        logInfoMessage("Page setup is completed");
        if (DriverFactory.getPlatform().equals(WEB)) {
            applitoolsInitializer.driverSetupForApplitoolsInitializer(driver);
        } else {
            applitoolsInitializer.driverSetupForApplitoolsInitializer((AppiumDriver) driver);
        }
        applitoolsInitializer.initiateApplitoolsInitializer(method, driver);
    }

    @AfterMethod
    public void methodTearDown(ITestResult iTestResult, Method method) {
        logInfoMessage("Inside @AfterMethod of " + BaseTest.class.getSimpleName());
        applitoolsInitializer.closeApplitoolsInitializer(iTestResult);
        DriverFactory.killDriver();
    }

    @AfterSuite
    public void suiteTearDown() {
        logInfoMessage("Inside @AfterSuite of " + BaseTest.class.getSimpleName());
        applitoolsInitializer.closeBatch();
    }

    public void log(String message) {
        LOGGER.info(message);
        logInfoMessage(message);
    }
}
