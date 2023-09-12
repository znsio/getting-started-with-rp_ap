package com.znsio.rpap;

import com.epam.reportportal.annotations.Step;
import com.znsio.rpap.pages.WebExample;
import com.znsio.rpap.utils.JsonDataManager;
import com.znsio.rpap.utils.JsonDataProvider;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.znsio.rpi.utils.ScreenShotManager.captureScreenShot;


public class WebExampleTest extends BaseTest {
    private WebExample WebPage;

    @Test(dataProvider = "getFromJson", priority = -1, description = "Validate Title of the Screen")
    public void titleTest(String title) {
        test = extent.createTest("Web Automation Tests");
        test.assignCategory("Base Test");
        childTest = test.createNode(Reporter.getCurrentTestResult().getMethod().getDescription());

        WebPage = page.getClassInstance(WebExample.class);
        verifyPageTitle(title);
    }

    @Test(dataProvider = "getFromJson", description = "Validating login with valid username and password")
    public void validLoginTest(String username, String password, String expectedMessage) throws InterruptedException {
        test.assignCategory("Positive Scenario");
        childTest = test.createNode(Reporter.getCurrentTestResult().getMethod().getDescription());

        performLogin(username, password);
        verifyMessageAfterLogin(expectedMessage);
    }

    @Test(dataProvider = "getFromJson", description = "Validating login with invalid username and valid password")
    public void invalidUserTest(String username, String password, String expectedMessage) throws InterruptedException {
        test.assignCategory("Negative Scenario");
        childTest = test.createNode(Reporter.getCurrentTestResult().getMethod().getDescription());

        performLogin(username, password);
        verifyMessageAfterLogin(expectedMessage);
    }

    @Test(dataProvider = "getFromJson", description = "Validate login with valid username and invalid password")
    public void invalidPasswordTest(String username, String password, String expectedMessage)
            throws InterruptedException {
        test.assignCategory("Negative Scenario");
        childTest = test.createNode(Reporter.getCurrentTestResult().getMethod().getDescription());

        performLogin(username, password);
        verifyMessageAfterLogin(expectedMessage);
    }

    @Step
    private void verifyPageTitle(String title) {
        captureScreenShot(driver, "Validating Title of the Screen");
        Assert.assertEquals(WebPage.pageTitle(), title);
    }

    @Step
    private void performLogin(String username, String password) throws InterruptedException {
        log("Entering username and password");
        WebPage.login(username, password);
    }

    @Step
    private void verifyMessageAfterLogin(String expectedMessage) {
        captureScreenShot(driver, "Verifying post login message");
        Assert.assertEquals(WebPage.getPostSubmitMessage(), expectedMessage);
    }

    @DataProvider(name = "getFromJson")
    public Object[][] getFromJson(Method mtd) throws Exception {
        JsonDataManager jdm = JsonDataProvider.loadData();
        Object[][] dataMap = jdm.getData(getClass(), mtd.getName());
        return dataMap;
    }
}