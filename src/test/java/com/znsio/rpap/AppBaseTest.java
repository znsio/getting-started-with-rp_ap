package com.znsio.rpap;

import com.znsio.applitools.integration.ApplitoolsInitializer;
import com.znsio.rpap.pages.Page;
import com.znsio.rpap.utils.DriverFactory;
import io.appium.java_client.AppiumDriver;
import com.znsio.reportportal.integration.properties.Config;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Properties;
import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;


public class AppBaseTest extends ApplitoolsInitializer {

    protected static AppiumDriver appDriver;
    private static WebDriverWait wait;
    private static final Logger LOGGER = Logger.getLogger(AppBaseTest.class.getName());
    private static final Properties config = Config.loadProperties(System.getProperty("CONFIG"));
    public static final String PAGE_LOAD_TIME = config.getProperty("PAGE_LOAD_TIME");
    private static final String APP_ACTIVITY = config.getProperty("APP_ACTIVITY");
    private static final String APP_PACKAGE_NAME = config.getProperty("APP_PACKAGE_NAME");
    private static final String AUTOMATION_NAME = config.getProperty("AUTOMATION_NAME");
    private static final String PLATFORM = config.getProperty("PLATFORM");
    private static final String APK_LOCATION = config.getProperty("APP");
    protected Page page;

    @BeforeSuite
    public void SuiteSetup() throws MalformedURLException {
        log("Retrieved config data");
        appDriver = DriverFactory.launchMobileApp(PLATFORM, AUTOMATION_NAME, APP_PACKAGE_NAME, APP_ACTIVITY, APK_LOCATION);
        wait = new WebDriverWait(appDriver, Duration.ofSeconds(Long.parseLong(PAGE_LOAD_TIME)));
        ApplitoolsInitializer.driverSetupForApplitoolsInitializer(appDriver);
        log("Android App is Ready");
    }

    @BeforeMethod
    public void MethodSetup() {
        page = new Page(appDriver, wait);
        log("App setup is completed");
    }

    @AfterSuite
    public void suiteTearDown() {
        log("Stoping appium server");
        DriverFactory.stopAppiumServer();
    }

    public void log(String message) {
        LOGGER.info(message);
        logInfoMessage(message);
    }
}
