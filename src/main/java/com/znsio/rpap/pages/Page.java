package com.znsio.rpap.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public Page(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public <T extends BasePage> T getClassInstance(Class<T> pageclass) {
        try {
            return pageclass.getDeclaredConstructor(WebDriver.class, WebDriverWait.class)
                    .newInstance(this.driver, this.wait);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends BasePage> T getClassInstanceApp(Class<T> pageclass) {
        try {
            return pageclass.getDeclaredConstructor(AppiumDriver.class, WebDriverWait.class)
                    .newInstance(this.driver, this.wait);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
