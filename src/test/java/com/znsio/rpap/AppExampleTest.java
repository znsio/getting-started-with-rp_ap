package com.znsio.rpap;

import com.applitools.eyes.appium.Target;
import com.epam.reportportal.annotations.Step;
import com.znsio.rpap.pages.AppExample;
import com.znsio.rpap.steps.AppSteps;
import com.znsio.rpap.utils.JsonDataManager;
import com.znsio.rpap.utils.JsonDataProvider;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.captureAndAttachScreenshot;

public class AppExampleTest extends AppBaseTest {

    private AppExample appPage;
    private AppSteps appSteps;
    private static final Logger LOGGER = Logger.getLogger(AppExampleTest.class.getName());

    @BeforeMethod
    private void handleCalculatorPopUps() {
        log("Inside @BeforeMethod of " + AppExampleTest.class.getSimpleName());
        appPage = page.getClassInstanceApp(AppExample.class);
        appPage.handlePopupIfPresent();
        log("Handling calculator popUps");
        appSteps = new AppSteps(appDriver, appPage, eyesOnWeb);
    }

    @Test(dataProvider = "getFromJson", priority = -3, description = "Perform addition of two no. on calculator",
            groups = {"visual"})
    public void AdditionTest(String input1, String operator1, String input2, String operator2, String expectedMsg) {
        appSteps.performOperation(input1, operator1, input2, operator2);
        appSteps.verifyResult(expectedMsg);
        //   performOperation(input1, operator1, input2, operator2);
        //   verifyResult(expectedMsg);
    }

    @Test(dataProvider = "getFromJson", priority = -2, description = "Perform subtraction of two no. on calculator",
            groups = {"visual"})
    public void SubtractionTest(String input1, String operator1, String input2, String operator2, String expectedMsg) {
        appSteps.performOperation(input1, operator1, input2, operator2);
        appSteps.verifyResult(expectedMsg);
        //   performOperation(input1, operator1, input2, operator2);
        //   verifyResult(expectedMsg);
    }

    @Test(dataProvider = "getFromJson", priority = -1, description = "Perform multiplication of two no. on calculator",
            groups = {"visual"})
    public void MultiplicationTest(String input1, String operator1, String input2, String operator2, String expectedMsg) {
        appSteps.performOperation(input1, operator1, input2, operator2);
        appSteps.verifyResult(expectedMsg);
        //   performOperation(input1, operator1, input2, operator2);
        //   verifyResult(expectedMsg);
    }

    @Test(dataProvider = "getFromJson", description = "Perform divison of two no. on calculator",
            groups = {"visual"})
    public void DivisonTest(String input1, String operator1, String input2, String operator2, String expectedMsg) {
        appSteps.performOperation(input1, operator1, input2, operator2);
        appSteps.verifyResult(expectedMsg);
        //   performOperation(input1, operator1, input2, operator2);
        //   verifyResult(expectedMsg);
    }


    @DataProvider(name = "getFromJson")
    public Object[][] getFromJson(Method mtd) throws Exception {
        JsonDataManager jdm = JsonDataProvider.loadData();
        Object[][] dataMap = jdm.getData(getClass(), mtd.getName());
        return dataMap;
    }

/*
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
        //     LOGGER.info("Select Calculator Operation " + operator2);
        log("Select Calculator Operation " + operator2);
        appPage.chooseOperation(operator2);
    }

    @Step
    public void verifyResult(String expected) {
        LOGGER.info("Expected output " + expected);
        String actualOutput = appPage.getCalculatorDisplay();
        LOGGER.info("Actual output " + actualOutput);
        Assert.assertEquals(expected, actualOutput);
        captureAndAttachScreenshot(appDriver, "Final result captured");
        eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
    }
*/


}
