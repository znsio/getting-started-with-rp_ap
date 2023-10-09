package com.znsio.rpap;

import com.znsio.rpap.pages.WebExample;
import com.znsio.rpap.steps.WebSteps;
import com.znsio.rpap.utils.JsonDataManager;
import com.znsio.rpap.utils.JsonDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;


//TODO: Consume BaseTest as a listener instead of an inherited class
public class WebExampleTest extends BaseTest {
    private WebExample webPage;
    private WebSteps webSteps;

    @BeforeMethod
    public void webPageSetup() {

        logInfoMessage("Inside @BeforeMethod of " + WebExampleTest.class.getSimpleName());
        webPage = page.getClassInstance(WebExample.class);
        webSteps = new WebSteps(driver, webPage, eyesOnWeb);
    }

    @Test(dataProvider = "getFromJson", priority = -1, description = "Validate Title of the Screen",
            groups = {"visual"})
    public void titleTest(String title) {
        webSteps.verifyPageTitle(title);
    }

    @Test(dataProvider = "getFromJson", description = "Validating login with valid username and password",
            groups = {"visual"})
    public void validLoginTest(String username, String password, String expectedMessage) throws InterruptedException {

        webSteps.performLogin(username, password);
        webSteps.verifyMessageAfterLogin(expectedMessage);
    }

    @Test(dataProvider = "getFromJson", description = "Validating login with invalid username and valid password",
            groups = {"visual"})
    public void invalidUserTest(String username, String password, String expectedMessage) throws InterruptedException {

        webSteps.performLogin(username, password);
        webSteps.verifyMessageAfterLogin(expectedMessage);
    }

    @Test(dataProvider = "getFromJson", description = "Validate login with valid username and invalid password",
            groups = {"visual"})
    public void invalidPasswordTest(String username, String password, String expectedMessage)
            throws InterruptedException {

        webSteps.performLogin(username, password);
        webSteps.verifyMessageAfterLogin(expectedMessage);
    }

    @DataProvider(name = "getFromJson")
    public Object[][] getFromJson(Method mtd) throws Exception {
        JsonDataManager jdm = JsonDataProvider.loadData();
        Object[][] dataMap = jdm.getData(getClass(), mtd.getName());
        return dataMap;
    }
}