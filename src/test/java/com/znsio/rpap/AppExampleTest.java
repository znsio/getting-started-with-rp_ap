package com.znsio.rpap;

import com.epam.reportportal.annotations.Step;
import com.znsio.rpap.pages.AppExample;
import com.znsio.rpap.utils.JsonDataManager;
import com.znsio.rpap.utils.JsonDataProvider;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import static com.znsio.rpi.utils.ScreenShotManager.captureScreenShot;

public class AppExampleTest extends AppBaseTest {

    private AppExample AppPage;

    private static final Logger LOGGER = Logger.getLogger(AppExampleTest.class.getName());

    @Test(dataProvider = "getFromJson", priority = -2, description = "Perform addition of two no. on calculator",
            groups = {"visual"})
    public void AdditionTest(String input1, String operator1, String input2, String operator2, String expectedMsg) {
        AppPage = page.getClassInstanceApp(AppExample.class);
        captureScreenShot(appDriver, "Validating Calculator screen");
        startCalculator();
        performOperation(input1, operator1, input2, operator2);
        verifyResult(expectedMsg);
    }

    @Test(dataProvider = "getFromJson", priority = -1, description = "Perform subtraction of two no. on calculator",
            groups = {"visual"})
    public void SubtractionTest(String input1, String operator1, String input2, String operator2, String expectedMsg) {
        captureScreenShot(appDriver, "Validating Calculator screen");
        startCalculator();
        performOperation(input1, operator1, input2, operator2);
        verifyResult(expectedMsg);
    }

    @Step
    private void startCalculator() {
        log("Handling calculator popUps");
        AppPage.handlePopupIfPresent();
        captureScreenShot(appDriver, "Pop Ups handled on calculator");
        //     eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
    }

    @Step
    private void verifyResult(String expected) {
        LOGGER.info("Validating calculator output ");
        LOGGER.info("Expected output " + expected);
        String actualOutput = AppPage.getCalculatorDisplay();
        LOGGER.info("Actual output " + actualOutput);
        Assert.assertEquals(expected, actualOutput);
        captureScreenShot(appDriver, "Final result captured");
        //     eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
    }

    @Step
    public void performOperation(String input1, String operator1, String input2, String operator2) {
        LOGGER.info("Select No." + input1);
        AppPage.chooseNumber(input1);
        captureScreenShot(appDriver, "Number selected " + input1);
        LOGGER.info("Select Calculator Operation " + operator1);
        AppPage.chooseOperation(operator1);
        captureScreenShot(appDriver, "Operator selected " + operator1);
        LOGGER.info("Select No." + input2);
        AppPage.chooseNumber(input2);
        captureScreenShot(appDriver, "Number selected " + input2);
        LOGGER.info("Select Calculator Operation " + operator2);
        AppPage.chooseOperation(operator2);
        captureScreenShot(appDriver, "Operator selected " + operator2);
    }


    @DataProvider(name = "getFromJson")
    public Object[][] getFromJson(Method mtd) throws Exception {
        JsonDataManager jdm = JsonDataProvider.loadData();
        Object[][] dataMap = jdm.getData(getClass(), mtd.getName());
        return dataMap;
    }
}
