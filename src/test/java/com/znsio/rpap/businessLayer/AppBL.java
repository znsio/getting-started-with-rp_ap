package com.znsio.rpap.businessLayer;

import com.applitools.eyes.appium.Eyes;
import com.applitools.eyes.appium.Target;
import com.epam.reportportal.annotations.Step;
import com.znsio.rpap.pages.AppExample;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.captureAndAttachScreenshot;
import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;

public class AppBL {

    private static final Logger LOGGER = Logger.getLogger(AppBL.class.getName());
    private static final String EQUAL_OPERATOR = "equal";

    private AppiumDriver appDriver;
    private AppExample appPage;
    private Eyes eyesOnApp;

    public AppBL(AppiumDriver appDriver, AppExample appPage, Eyes eyesOnApp) {
        this.appDriver = appDriver;
        this.appPage = appPage;
        this.eyesOnApp = eyesOnApp;
    }

    @Step
    public void performOperation(String operand1, String operator, String operand2) {
        eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
        appPage.chooseNumber(operand1);
        logInfoMessage("Selected No." + operand1);
        eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(),
                Target.region(By.id("digit" + operand1)));
        appPage.chooseOperation(operator);
        logInfoMessage("Select Calculator Operation " + operator);
        appPage.chooseNumber(operand2);
        logInfoMessage("Select No." + operand2);
        eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(),
                Target.region(By.id("digit" + operand2)));
        captureAndAttachScreenshot(appDriver, "Data entered on Calculator");
        logInfoMessage("Select Calculator Operation " + EQUAL_OPERATOR);
        appPage.chooseOperation(EQUAL_OPERATOR);
    }

    @Step
    public void verifyResult(String expected) {
        logInfoMessage("Expected output " + expected);
        String actualOutput = appPage.getCalculatorDisplay();
        logInfoMessage("Actual output " + actualOutput);
        Assert.assertEquals(expected, actualOutput);
        captureAndAttachScreenshot(appDriver, "Final result captured");
    }
}
