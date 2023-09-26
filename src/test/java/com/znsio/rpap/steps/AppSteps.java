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
    private AppiumDriver appDriver;
    private AppExample appPage;
    private Eyes eyesOnApp;

    public AppSteps(AppiumDriver appDriver, AppExample appPage, Eyes eyesOnApp) {
        this.appDriver = appDriver;
        this.appPage = appPage;
        this.eyesOnApp = eyesOnApp;
    }

    @Step
    public void performOperation(String input1, String operator1, String input2, String operator2) {
        appPage.chooseNumber(input1);
        log("Selected No." + input1);
        appPage.chooseOperation(operator1);
        log("Select Calculator Operation " + operator1);
        appPage.chooseNumber(input2);
        log("Select No." + input2);
        captureAndAttachScreenshot(appDriver, "Data entered on Calculator");
        eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
        log("Select Calculator Operation " + operator2);
        appPage.chooseOperation(operator2);
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
