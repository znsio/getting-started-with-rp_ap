package com.znsio.rpap.pages;

import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.HashMap;
import java.util.Map;


public class AppExample extends BasePage {

    public static final By ByCalculatorScreenXpath = By.xpath("//android.widget.EditText");
    private static final By ByCheckForUpdateId = By.id("android:id/button1");
    public static final By ByWelcomeMsgId = By.id("com.android2.calculator3:id/cling_dismiss");
    private static final Logger LOGGER = Logger.getLogger(AppExample.class.getName());
    private static final Map<String, String> OPERATION_MAP = new HashMap<>();

    static {
        OPERATION_MAP.put("plus", "plus");
        OPERATION_MAP.put("subtract", "minus");
        OPERATION_MAP.put("multiply", "mul");
        OPERATION_MAP.put("divide", "div");
        OPERATION_MAP.put("equals", "equal");
    }

    public AppExample(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void handlePopupIfPresent() {
        boolean isUpgradeAppNotificationElement = isElementPresent(ByCheckForUpdateId);
        if (isUpgradeAppNotificationElement) {
            driver.findElement(ByCheckForUpdateId).click();
            waitFor(1);
        }
        boolean isClingElementPresent = isElementPresent(ByWelcomeMsgId);
        if (isClingElementPresent) {
            driver.findElement(ByWelcomeMsgId).click();
            waitFor(1);
        }
    }

    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public synchronized static void waitFor(int seconds) {
        LOGGER.info("Wait for " + seconds + " seconds");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
        }
    }

    public void chooseNumber(String number) {
        driver.findElement(By.id("digit" + number)).click();
    }

    public void chooseOperation(String operation) {
        String mappedOperation = OPERATION_MAP.get(operation.toLowerCase());
        if (mappedOperation == null) {
            throw new RuntimeException("Operation " + operation + " is not supported");
        }
        driver.findElement(By.id(mappedOperation)).click();
    }

    public String getCalculatorDisplay() {
        return driver.findElement(ByCalculatorScreenXpath).getText().trim();
    }
}
