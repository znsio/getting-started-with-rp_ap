package com.znsio.rpap.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class.getName());

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

    public static AppiumDriver launchMobileApp(String platformName, String automationName, String appPackage, String appActivity, String apkLocation, String appiumServerUrl) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
        desiredCapabilities.setCapability(MobileCapabilityType.APP, apkLocation);
        desiredCapabilities.setCapability("appPackage", appPackage);
        desiredCapabilities.setCapability("appActivity", appActivity);
        desiredCapabilities.setCapability("autoGrantPermissions",true);
        desiredCapabilities.setCapability("fullReset", true);
        AppiumDriver driver = new AndroidDriver(new URL(appiumServerUrl), desiredCapabilities);
        return driver;

    }
}
