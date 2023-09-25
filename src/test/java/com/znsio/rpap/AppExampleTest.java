package com.znsio.rpap;

import com.applitools.eyes.appium.Target;
import com.epam.reportportal.annotations.Step;
import com.znsio.rpap.pages.AppExample;
import com.znsio.rpap.utils.JsonDataManager;
import com.znsio.rpap.utils.JsonDataProvider;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import static com.znsio.rpi.utils.ReportPortalLogger.captureAndAttachScreenshot;

public class AppExampleTest extends AppBaseTest {

    private AppExample AppPage;
    private static final Logger LOGGER = Logger.getLogger(AppExampleTest.class.getName());

    @Test(dataProvider = "getFromJson", priority = -2, description = "Perform addition of two no. on calculator",
            groups = {"visual"})
    public void AdditionTest(String input1, String operator1, String input2, String operator2, String expectedMsg) {
        performOperation(input1, operator1, input2, operator2);
        verifyResult(expectedMsg);
    }

    @Test(dataProvider = "getFromJson", priority = -1, description = "Perform subtraction of two no. on calculator",
            groups = {"visual"})
    public void SubtractionTest(String input1, String operator1, String input2, String operator2, String expectedMsg) {
        performOperation(input1, operator1, input2, operator2);
        verifyResult(expectedMsg);
    }

    @BeforeMethod
    private void handleCalculatorPopUps() {
        AppPage = page.getClassInstanceApp(AppExample.class);
        AppPage.handlePopupIfPresent();
        log("Handling calculator popUps");
    }

    @Step
    private void verifyResult(String expected) {
        LOGGER.info("Expected output " + expected);
        String actualOutput = AppPage.getCalculatorDisplay();
        LOGGER.info("Actual output " + actualOutput);
        Assert.assertEquals(expected, actualOutput);
        captureAndAttachScreenshot(appDriver, "Final result captured");
        eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
    }

    @Step
    public void performOperation(String input1, String operator1, String input2, String operator2) {
        AppPage.chooseNumber(input1);
        log("Selected No." + input1);
        AppPage.chooseOperation(operator1);
        log("Select Calculator Operation " + operator1);
        AppPage.chooseNumber(input2);
        log("Select No." + input2);
        captureAndAttachScreenshot(appDriver, "Data entered on Calculator");
        eyesOnApp.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
        LOGGER.info("Select Calculator Operation " + operator2);
        log("Select Calculator Operation " + operator2);
        AppPage.chooseOperation(operator2);
    }


    @DataProvider(name = "getFromJson")
    public Object[][] getFromJson(Method mtd) throws Exception {
        JsonDataManager jdm = JsonDataProvider.loadData();
        Object[][] dataMap = jdm.getData(getClass(), mtd.getName());
        return dataMap;
    }
}
