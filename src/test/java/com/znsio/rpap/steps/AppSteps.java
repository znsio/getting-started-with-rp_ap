package com.znsio.rpap.steps;

import com.applitools.eyes.appium.Eyes;
import com.applitools.eyes.appium.Target;
import com.epam.reportportal.annotations.Step;
import com.znsio.rpap.pages.AppExample;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.testng.Assert;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.captureAndAttachScreenshot;
import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;

public class AppSteps {

    private static final Logger LOGGER = Logger.getLogger(AppSteps.class.getName());
    private static final String EQUAL_OPERATOR = "equals";
    private AppiumDriver appDriver;
    private AppExample appPage;
    private Eyes eyesOnApp;

    public AppSteps(AppiumDriver appDriver, AppExample appPage, Eyes eyesOnApp) {
        this.appDriver = appDriver;
        this.appPage = appPage;
        this.eyesOnApp = eyesOnApp;
    }

    @Step
    public void performOperation(String operand1, String operator1, String operand2) {
        appPage.chooseNumber(operand1);
        log("Selected No." + operand1);
        appPage.chooseOperation(operator1);
        log("Select Calculator Operation " + operator1);
        appPage.chooseNumber(operand2);
        log("Select No." + operand2);
        captureAndAttachScreenshot(appDriver, "Data entered on Calculator");
        eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
        log("Select Calculator Operation " + EQUAL_OPERATOR);
        appPage.chooseOperation(EQUAL_OPERATOR);
    }

    @Step
    public void verifyResult(String expected) {
        log("Expected output " + expected);
        String actualOutput = appPage.getCalculatorDisplay();
        log("Actual output " + actualOutput);
        Assert.assertEquals(expected, actualOutput);
        captureAndAttachScreenshot(appDriver, "Final result captured");
        eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
    }


    private static void log(String message) {
        LOGGER.info(message);
        logInfoMessage(message);
    }
}
