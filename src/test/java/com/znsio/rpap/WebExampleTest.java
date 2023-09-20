package com.znsio.rpap;

import com.applitools.eyes.selenium.fluent.Target;
import com.epam.reportportal.annotations.Step;
import com.znsio.rpap.pages.WebExample;
import com.znsio.rpap.utils.JsonDataManager;
import com.znsio.rpap.utils.JsonDataProvider;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.znsio.rpi.utils.ScreenShotManager.captureScreenShot;


public class WebExampleTest extends BaseTest {
    private WebExample WebPage;

    @Test(dataProvider = "getFromJson", priority = -1, description = "Validate Title of the Screen",
            groups = {"visual"})
    public void titleTest(String title) {

        WebPage = page.getClassInstance(WebExample.class);
        verifyPageTitle(title);
    }

    @Test(dataProvider = "getFromJson", description = "Validating login with valid username and password",
            groups = {"visual"})
    public void validLoginTest(String username, String password, String expectedMessage) throws InterruptedException {

        performLogin(username, password);
        verifyMessageAfterLogin(expectedMessage);
    }

    @Test(dataProvider = "getFromJson", description = "Validating login with invalid username and valid password",
            groups = {"visual"})
    public void invalidUserTest(String username, String password, String expectedMessage) throws InterruptedException {

        performLogin(username, password);
        verifyMessageAfterLogin(expectedMessage);
    }

    @Test(dataProvider = "getFromJson", description = "Validate login with valid username and invalid password",
            groups = {"visual"})
    public void invalidPasswordTest(String username, String password, String expectedMessage)
            throws InterruptedException {

        performLogin(username, password);
        verifyMessageAfterLogin(expectedMessage);
    }

    @Step
    private void verifyPageTitle(String title) {
        captureScreenShot(webDriver, "Validating Title of the Screen");
        eyesOnWeb.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
        Assert.assertEquals(WebPage.pageTitle(), title);
    }

    @Step
    private void performLogin(String username, String password) throws InterruptedException {
        log("Entering username and password");
        WebPage.login(username, password);
        eyesOnWeb.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
    }

    @Step
    private void verifyMessageAfterLogin(String expectedMessage) {
        captureScreenShot(webDriver, "Verifying post login message");
        eyesOnWeb.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
        Assert.assertEquals(WebPage.getPostSubmitMessage(), expectedMessage);
    }

    @DataProvider(name = "getFromJson")
    public Object[][] getFromJson(Method mtd) throws Exception {
        JsonDataManager jdm = JsonDataProvider.loadData();
        Object[][] dataMap = jdm.getData(getClass(), mtd.getName());
        return dataMap;
    }
}