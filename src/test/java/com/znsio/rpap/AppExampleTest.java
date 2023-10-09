package com.znsio.rpap;

import com.znsio.rpap.pages.AppExample;
import com.znsio.rpap.steps.AppSteps;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;


public class AppExampleTest extends BaseTest {

    private AppExample appPage;
    private AppSteps appSteps;
    private static final Logger LOGGER = Logger.getLogger(AppExampleTest.class.getName());

    @BeforeMethod
    private void handleCalculatorPopUps() {
        logInfoMessage("Inside @BeforeMethod of " + AppExampleTest.class.getSimpleName());
        appPage = page.getClassInstance(AppExample.class);
        appSteps = new AppSteps((AppiumDriver) driver, appPage, eyesOnApp);
        logInfoMessage("Handling if any popUps are shown in the app");
        appPage.handlePopupIfPresent();
        appPage.clearScreen();
    }

    @Test(description = "Perform addition of two no. on calculator", groups = {"visual"})
    public void additionTest() {
        appSteps.performOperation("2", "plus", "5");
        appSteps.verifyResult("7");
    }

    @Test(description = "Perform subtraction of two no. on calculator", groups = {"visual"})
    public void subtractionTest() {
        appSteps.performOperation("8", "minus", "6");
        appSteps.verifyResult("2");
    }

    @Test(description = "Perform multiplication of two no. on calculator", groups = {"visual"})
    public void multiplicationTest() {
        appSteps.performOperation("2", "mul", "3");
        appSteps.verifyResult("6");
    }

    @Test(description = "Perform divison of two no. on calculator", groups = {"visual"})
    public void divisonTest() {
        appSteps.performOperation("9", "div", "3");
        appSteps.verifyResult("3");
    }
}
