package com.znsio.rpap;

import com.znsio.api.VisualTest;
import com.znsio.rpap.pages.Page;
import com.znsio.rpap.utils.BrowserFactory;
import com.znsio.rpi.properties.Config;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Properties;
import static com.znsio.rpi.utils.ReportPortalLogger.logInfoMessage;

public class AppBaseTest extends VisualTest {

    protected static AppiumDriver appDriver;
    private static WebDriverWait wait;
    private static final Logger LOGGER = Logger.getLogger(AppBaseTest.class.getName());
    private static final Properties config = Config.loadProperties(System.getProperty("CONFIG"));
    private static AppiumDriverLocalService localAppiumServer;
    private static String APPIUM_SERVER_URL = "http://localhost:4723/wd/hub/";
    protected Page page;

    @BeforeSuite
    public void SuiteSetup() throws MalformedURLException {
        LOGGER.info("Retrieved config data");
        String dynamicAppiumUrl = startAppiumServer();
        appDriver = BrowserFactory.launchMobileApp(config.getProperty("PLATFORM"), config.getProperty("AUTOMATION_NAME"), config.getProperty("APP_PACKAGE_NAME"),config.getProperty("APP_ACTIVITY"),dynamicAppiumUrl);
        wait = new WebDriverWait(appDriver, Duration.ofSeconds(Long.parseLong(config.getProperty("PAGE_LOAD_TIME"))));
        VisualTest.driverSetupForVisualTest(appDriver);
        LOGGER.info("Android App Ready");
    }

    @BeforeMethod
    public void MethodSetup() {
        page = new Page(appDriver, wait);
    }

    @AfterSuite
    public void suiteTearDown() {
        LOGGER.info("Stopping the local Appium server running on: " +APPIUM_SERVER_URL);
        if(null != localAppiumServer) {
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
