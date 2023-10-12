package com.znsio.rpap.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

public class BasePage extends Page {

    public BasePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void inputDataToElement(By by, String dataToInput) {
        WebElement ele = driver.findElement(by);
        String tagName = ele.getTagName();
        scrollToView(by);
        Map<String, Runnable> inputHandlers = new HashMap<>();
        inputHandlers.put("select", () -> handleSelectInput(ele, dataToInput));
        inputHandlers.put("textarea", () -> handleTextareaInput(ele, dataToInput));
        inputHandlers.put("input", () -> handleTextInput(ele, dataToInput));
        Runnable handler = inputHandlers.get(tagName.toLowerCase());
        if (handler != null) {
            handler.run();
        } else {
            throw new UnsupportedOperationException("Unsupported input type: " + tagName);
        }
    }

    private void handleSelectInput(WebElement element, String dataToInput) {
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(dataToInput);
    }

    private void handleTextareaInput(WebElement element, String dataToInput) {
        element.clear();
        element.sendKeys(dataToInput);
        element.sendKeys(Keys.TAB);
    }

    private void handleTextInput(WebElement element, String dataToInput) {
        String inputType = element.getAttribute("type");

        if (inputType.equals("text") || inputType.equals("email") || inputType.equals("password")) {
            element.clear();
            element.sendKeys(dataToInput);
            element.sendKeys(Keys.TAB);
        } else {
            throw new UnsupportedOperationException("Unsupported input type: " + inputType);
        }
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void scrollToView(By by) {
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        jsDriver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
    }

    public void pageLoadWait(WebDriverWait wait) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };
        try {
            wait.until(expectation);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
