package com.znsio.rpap;


import com.znsio.rpap.pages.AppExample;
import com.znsio.rpap.steps.AppSteps;
import com.znsio.rpap.utils.JsonDataManager;
import com.znsio.rpap.utils.JsonDataProvider;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import java.lang.reflect.Method;



public class AppExampleTest extends BaseTest {

    private AppExample appPage;
    private AppSteps appSteps;
    private static final Logger LOGGER = Logger.getLogger(AppExampleTest.class.getName());

    @BeforeMethod
    private void handleCalculatorPopUps() {
        log("Inside @BeforeMethod of " + AppExampleTest.class.getSimpleName());
        appPage = page.getClassInstance(AppExample.class);
        appSteps = new AppSteps((AppiumDriver) driver, appPage, eyesOnApp);
        log("Handling if any popUps are shown in the app");
        appPage.handlePopupIfPresent();
        appPage.clearScreen();
    }

    @Test(dataProvider = "getFromJson", description = "Perform addition of two no. on calculator",
            groups = {"visual"})
    public void additionTest(String operand1, String operator1, String operand2, String expectedMsg) {
        appSteps.performOperation(operand1, operator1, operand2);
        appSteps.verifyResult(expectedMsg);
    }

    @Test(dataProvider = "getFromJson", description = "Perform subtraction of two no. on calculator",
            groups = {"visual"})
    public void subtractionTest(String operand1, String operator1, String operand2, String expectedMsg) {
        appSteps.performOperation(operand1, operator1, operand2);
        appSteps.verifyResult(expectedMsg);
    }

    @Test(dataProvider = "getFromJson", description = "Perform multiplication of two no. on calculator",
            groups = {"visual"})
    public void multiplicationTest(String operand1, String operator1, String operand2, String expectedMsg) {
        appSteps.performOperation(operand1, operator1, operand2);
        appSteps.verifyResult(expectedMsg);
    }

    @Test(dataProvider = "getFromJson", description = "Perform divison of two no. on calculator",
            groups = {"visual"})
    public void divisonTest(String operand1, String operator1, String operand2, String expectedMsg) {
        appSteps.performOperation(operand1, operator1, operand2);
        appSteps.verifyResult(expectedMsg);
    }


    @DataProvider(name = "getFromJson")
    public Object[][] getFromJson(Method mtd) throws Exception {
        JsonDataManager jdm = JsonDataProvider.loadData();
        Object[][] dataMap = jdm.getData(getClass(), mtd.getName());
        return dataMap;
    }
}
