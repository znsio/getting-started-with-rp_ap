package com.znsio.rpap.pages;

import com.znsio.rpap.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebExample extends BasePage {

    public WebExample(WebDriver driver, WebDriverWait wait) {
        super(DriverManager.getDriver(), wait);
        pageLoadWait(wait);
    }

    By usernameField = By.id("username");
    By passwordField = By.id("password");
    By submitButton = By.id("submit");
    By postSubmitSuccessMessage = By.xpath("//h1[text()='Logged In Successfully']");
    By postSubmitErrorMessage = By.xpath("//div[@id='error']");


    public String pageTitle() {
        return DriverManager.getDriver().getTitle();
    }

    public void login(String username, String password) throws InterruptedException {
        inputDataToElement(usernameField, username);
        inputDataToElement(passwordField, password);
        DriverManager.getDriver().findElement(submitButton).click();
        Thread.sleep(200);
    }

    public String getPostSubmitMessage() {
        if (DriverManager.getDriver().findElements(postSubmitErrorMessage).size() > 0) {
            return DriverManager.getDriver().findElement(postSubmitErrorMessage).getText();
        } else {
            return DriverManager.getDriver().findElement(postSubmitSuccessMessage).getText();
        }
    }
}
