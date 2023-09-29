package com.znsio.rpap;


import com.znsio.rpap.pages.AppExample;
import com.znsio.rpap.steps.AppSteps;
import com.znsio.rpap.utils.JsonDataManager;
import com.znsio.rpap.utils.JsonDataProvider;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import java.lang.reflect.Method;


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
        appSteps = new AppSteps(appDriver, appPage, eyesOnApp);
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
