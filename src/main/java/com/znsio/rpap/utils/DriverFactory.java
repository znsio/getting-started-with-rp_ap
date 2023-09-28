package com.znsio.rpap.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class.getName());
    private static AppiumDriverLocalService localAppiumServer;
    private static String APPIUM_SERVER_URL = "http://localhost:4723/wd/hub/";


    public static WebDriver launchWebApplication(WebDriver driver, String browserName, String appURL) {

        if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            LOGGER.info("Test not support to browser: " + browserName);
        }
        driver.get(appURL);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        return driver;
    }

    public static AppiumDriver launchMobileApp(String platformName, String automationName, String appPackage, String appActivity, String apkLocation) throws MalformedURLException {
        String dynamicAppiumUrl = startAppiumServer();
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
        desiredCapabilities.setCapability(MobileCapabilityType.APP, apkLocation);
        desiredCapabilities.setCapability("appPackage", appPackage);
        desiredCapabilities.setCapability("appActivity", appActivity);
        desiredCapabilities.setCapability("autoGrantPermissions", true);
        desiredCapabilities.setCapability("fullReset", true);
        AppiumDriver driver = new AndroidDriver(new URL(dynamicAppiumUrl), desiredCapabilities);
        return driver;

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

    public static void stopAppiumServer() {
        LOGGER.info("Stopping the local Appium server running on: " + APPIUM_SERVER_URL);
        if (null != localAppiumServer) {
            localAppiumServer.stop();
            LOGGER.info("Is Appium server running? " + localAppiumServer.isRunning());
        }
    }
}
