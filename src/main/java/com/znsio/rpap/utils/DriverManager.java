package com.znsio.rpap.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.WebDriver;

public class DriverManager {
	
	private static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
	private static ThreadLocal<AppiumDriver> appiumDrivers = new ThreadLocal<>();
	private static ThreadLocal<String> tagName = new ThreadLocal<>();

	public static void addDriver(WebDriver driver) {
		drivers.set(driver);
	}
	
	public static WebDriver getDriver() {
		return drivers.get();
	}
	
	public static void addAppiumService(AppiumDriver driver) {
		appiumDrivers.set(driver);
	}
	
	public static AppiumDriver getAppiumService() {
		return appiumDrivers.get();
	}
	
	public static void addTagNAme(String tagname) {
		tagName.set(tagname);
	}
	
	public static String getTagName() {
		return tagName.get();
	}

}
