package com.znsio.rpap.utils;

import com.znsio.reportportal.integration.properties.Config;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import static com.znsio.reportportal.integration.utils.OverriddenVariable.getOverriddenStringValue;

public class DriverFactory {
    public static final String ANDROID = "android";
    public static final String WEB = "web";
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class.getName());
    private static final Properties config = Config.loadProperties(System.getProperty("CONFIG"));
    private static WebDriver webDriver;
    private static AppiumDriverLocalService localAppiumServer;
    private static String APPIUM_SERVER_URL;


    public static WebDriver getDriver() throws MalformedURLException {
        if (getPlatform().equals(WEB)) {
            return launchWebApplication(config.getProperty(Config.BROWSER),
                    config.getProperty("URL"));
        } else {
            return getAppDriver();
        }
    }

    public static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(config.getProperty("PAGE_LOAD_TIME"))));
    }

    public static AppiumDriver getAppDriver() throws MalformedURLException {
        String appActivity = config.getProperty("APP_ACTIVITY");
        String appPackage = config.getProperty(Config.APP_PACKAGE_NAME);
        String automationDriver = config.getProperty("AUTOMATION_DRIVER");
        String platform = config.getProperty(Config.PLATFORM);
        String appPackageLocation = getOverriddenStringValue("APP_PACKAGE_LOCATION",
                config.getProperty("APP_PACKAGE_LOCATION"));
        return launchMobileApplication(platform, automationDriver, appPackage, appActivity, appPackageLocation);
    }

    public static void killDriver() {
        if (getPlatform().equals(WEB)) {
            webDriver.quit();
        } else {
            stopAppiumServer();
        }
    }

    public static String getPlatform() {
        return config.getProperty(Config.PLATFORM).toLowerCase();
    }

    private static WebDriver launchWebApplication(String browserName, String appURL) {
        if (browserName.equalsIgnoreCase("firefox")) {
            webDriver = new FirefoxDriver();
        } else {
            webDriver = new ChromeDriver();
        }
        webDriver.get(appURL);
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        DriverManager.addDriver(webDriver);
        return webDriver;
    }

    private static AppiumDriver launchMobileApplication(String platform, String automationDriver, String
            appPackage, String appActivity, String appPackageLocation) throws MalformedURLException {
        if (getPlatform().equals(ANDROID)) {
            String dynamicAppiumUrl = startAppiumServer();
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationDriver);
            desiredCapabilities.setCapability(MobileCapabilityType.APP, appPackageLocation);
            desiredCapabilities.setCapability("appPackage", appPackage);
            desiredCapabilities.setCapability("appActivity", appActivity);
            desiredCapabilities.setCapability("autoGrantPermissions", true);
            desiredCapabilities.setCapability("fullReset", true);
            AppiumDriver driver = new AndroidDriver(new URL(dynamicAppiumUrl), desiredCapabilities);
            return driver;
        } else {
            throw new NotImplementedException("Unsupported Platform: " + config.getProperty(Config.PLATFORM));
        }

    }

    private static String startAppiumServer() {
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

    private static void stopAppiumServer() {
        LOGGER.info("Stopping the local Appium server running on: " + APPIUM_SERVER_URL);
        if (null != localAppiumServer) {
            localAppiumServer.stop();
            LOGGER.info("Is Appium server running? " + localAppiumServer.isRunning());
        }
    }
}
