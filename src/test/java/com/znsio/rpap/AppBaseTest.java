package com.znsio.rpap;

import com.znsio.applitools.integration.ApplitoolsInitializer;
import com.znsio.rpap.pages.Page;
import com.znsio.rpap.utils.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import com.znsio.reportportal.integration.properties.Config;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.io.File;
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
    private static AppiumDriverLocalService localAppiumServer;
    private static String APPIUM_SERVER_URL = "http://localhost:4723/wd/hub/";
    protected Page page;

    @BeforeSuite
    public void SuiteSetup() throws MalformedURLException {
        log("Retrieved config data");
        String dynamicAppiumUrl = startAppiumServer();
        appDriver = DriverFactory.launchMobileApp(PLATFORM, AUTOMATION_NAME, APP_PACKAGE_NAME, APP_ACTIVITY, APK_LOCATION, dynamicAppiumUrl);
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
        LOGGER.info("Stopping the local Appium server running on: " + APPIUM_SERVER_URL);
        if (null != localAppiumServer) {
            localAppiumServer.stop();
            LOGGER.info("Is Appium server running? " + localAppiumServer.isRunning());
        }
    }

    public void log(String message) {
        LOGGER.info(message);
        logInfoMessage(message);
    }

    public static String startAppiumServer() {
        LOGGER.info("Start local Appium server");
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.usingAnyFreePort();
        serviceBuilder.withLogFile(new File("./target/appium_logs.txt"));
        serviceBuilder.withArgument(GeneralServerFlag.ALLOW_INSECURE, "adb_shell");
        serviceBuilder.withArgument(GeneralServerFlag.RELAXED_SECURITY);
        localAppiumServer = AppiumDriverLocalService.buildService(serviceBuilder);
        localAppiumServer.start();
        APPIUM_SERVER_URL = localAppiumServer.getUrl().toString();
        LOGGER.info("Appium server started on url :" + APPIUM_SERVER_URL);
        return APPIUM_SERVER_URL;
    }
}
