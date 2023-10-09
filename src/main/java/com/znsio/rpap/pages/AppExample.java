package com.znsio.rpap.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppExample extends BasePage {

    private static final By ByCalculatorScreenXpath = By.xpath("//android.widget.EditText");
    private static final By ByCheckForUpdateId = By.id("android:id/button1");
    private static final By ByWelcomeMsgId = By.id("com.android2.calculator3:id/cling_dismiss");
    private static final Logger LOGGER = Logger.getLogger(AppExample.class.getName());
    private static final By ByDeleteBtnId = By.id("del");
    private static final By ByClearBtnId = By.id("clear");

    public AppExample(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void handlePopupIfPresent() {
        boolean isUpgradeAppNotificationElement = isElementPresent(ByCheckForUpdateId);
        if (isUpgradeAppNotificationElement) {
            driver.findElement(ByCheckForUpdateId).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(ByWelcomeMsgId));
        }
        boolean isClingElementPresent = isElementPresent(ByWelcomeMsgId);
        if (isClingElementPresent) {
            driver.findElement(ByWelcomeMsgId).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(ByDeleteBtnId));
        }
    }

    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public void chooseNumber(String number) {
        driver.findElement(By.id("digit" + number)).click();
    }

    public void chooseOperation(String operation) {
        if (operation == null) {
            throw new RuntimeException("Operation " + operation + " is not supported");
        }
        driver.findElement(By.id(operation)).click();
    }

    public String getCalculatorDisplay() {
        return driver.findElement(ByCalculatorScreenXpath).getText().trim();
    }

    public void clearScreen() {
        boolean isDeleteButtonPresent = isElementPresent(ByDeleteBtnId);
        if (isDeleteButtonPresent) {
            driver.findElement(ByDeleteBtnId).click();
        }
        boolean isClearButtonPreset = isElementPresent(ByClearBtnId);
        if (isClearButtonPreset) {
            driver.findElement(ByClearBtnId).click();
        }
    }
}
