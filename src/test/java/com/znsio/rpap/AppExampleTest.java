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

    @Test(dataProvider = "getFromJson", priority = -1, description = "Perform operations on two no. on calculator",
            groups = {"visual"})
    public void calculatorTest(String input1, String operator1, String input2, String operator2, String expectedMsg) {

        AppPage = page.getClassInstanceApp(AppExample.class);
        captureScreenShot(appDriver, "Validating Calculator screen");
        startCalculator();
        selectNumber(input1);
        pressOperator(operator1);
        selectNumber(input2);
        pressOperator(operator2);
        verifyResult(expectedMsg);
    }

    @Step
    private void startCalculator() {
        log("Handling calculator popUps");
        AppPage.handlePopupIfPresent();
        captureScreenShot(appDriver, "Pop Ups handled on calculator");
    }

    @Step
    private void selectNumber(String number) {
        LOGGER.info("Select No." + number);
        AppPage.chooseNumber(number);
        captureScreenShot(appDriver, "Number selected " + number);

    }

    @Step
    private void pressOperator(String operation) {
        LOGGER.info("Select Calculator Operation " + operation);
        AppPage.chooseOperation(operation);
        captureScreenShot(appDriver, "Operator selected " + operation);

    }

    @Step
    private void verifyResult(String expected) {
        LOGGER.info("Validating calculator output ");
        LOGGER.info("Expected output " + expected);
        String actualOutput = AppPage.getCalculatorDisplay();
        LOGGER.info("Actual output " + actualOutput);
        Assert.assertEquals(expected, actualOutput);
        captureScreenShot(appDriver, "Final result captured");

    }


    @DataProvider(name = "getFromJson")
    public Object[][] getFromJson(Method mtd) throws Exception {
        JsonDataManager jdm = JsonDataProvider.loadData();
        Object[][] dataMap = jdm.getData(getClass(), mtd.getName());
        return dataMap;
    }
}
