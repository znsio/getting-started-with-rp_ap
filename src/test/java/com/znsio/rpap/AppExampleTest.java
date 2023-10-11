package com.znsio.rpap;

import com.znsio.rpap.pages.AppExample;
import com.znsio.rpap.businessLayer.AppBL;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;


public class AppExampleTest extends BaseTest {

    private AppExample appPage;
    private AppBL appBL;
    private static final Logger LOGGER = Logger.getLogger(AppExampleTest.class.getName());

    @BeforeMethod
    private void handleCalculatorPopUps() {
        logInfoMessage("Inside @BeforeMethod of " + AppExampleTest.class.getSimpleName());
        appPage = page.getClassInstance(AppExample.class);
        appBL = new AppBL((AppiumDriver) driver, appPage, eyesOnApp);
        logInfoMessage("Handling if any popUps are shown in the app");
        appPage.handlePopupIfPresent();
        appPage.clearScreen();
    }

    @Test(description = "Perform addition of two no. on calculator", groups = {"visual"})
    public void additionTest() {
        appBL.performOperation("2", "plus", "5");
        appBL.verifyResult("7");
    }

    @Test(description = "Perform subtraction of two no. on calculator", groups = {"visual"})
    public void subtractionTest() {
        appBL.performOperation("8", "minus", "6");
        appBL.verifyResult("2");
    }

    @Test(description = "Perform multiplication of two no. on calculator", groups = {"visual"})
    public void multiplicationTest() {
        appBL.performOperation("2", "mul", "3");
        appBL.verifyResult("6");
    }

    @Test(description = "Perform divison of two no. on calculator", groups = {"visual"})
    public void divisonTest() {
        appBL.performOperation("9", "div", "3");
        appBL.verifyResult("3");
    }
}
