package com.znsio.rpap.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebExample extends BasePage {

    public WebExample(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        pageLoadWait(wait);
    }

    By usernameField = By.id("username");
    By passwordField = By.id("password");
    By submitButton = By.id("submit");
    By postSubmitSuccessMessage = By.xpath("//h1[text()='Logged In Successfully']");
    By postSubmitErrorMessage = By.xpath("//div[@id='error']");


    public String pageTitle() {
        return driver.getTitle();
    }

    public void login(String username, String password) throws InterruptedException {
        inputDataToElement(usernameField, username);
        inputDataToElement(passwordField, password);
        driver.findElement(submitButton).click();
        Thread.sleep(200);
    }

    public String getPostSubmitMessage() {
        if (driver.findElements(postSubmitErrorMessage).size() > 0) {
            return driver.findElement(postSubmitErrorMessage).getText();
        } else {
            return driver.findElement(postSubmitSuccessMessage).getText();
        }
    }
}
