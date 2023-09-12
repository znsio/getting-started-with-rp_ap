package com.znsio.rpap;

import com.znsio.rpap.pages.WebExample;
import com.znsio.rpap.utils.JsonDataManager;
import com.znsio.rpap.utils.JsonDataProvider;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.lang.reflect.Method;


public class WebExampleTest extends BaseTest{
    public WebExample WebPage;
    @Test(dataProvider="getFromJson", priority=-1)
    public void titleTest(String title) {
        test = extent.createTest("Automation Example");
        test.assignCategory("Base Test");
        childTest = test.createNode("Validate Title of the Screen");
        log("Validating Title of the Screen");

        WebPage = page.getClassInstance(WebExample.class);
        Assert.assertEquals(WebPage.pageTitle(), title);
    }
    @Test(dataProvider="getFromJson")
    public void validLoginTest(String username, String password, String expectedMessage) throws InterruptedException {
        test.assignCategory("Positive Scenario");
        childTest = test.createNode("Validate login with valid username and password");
        log("Validating login with valid username and password");

        WebPage.login(username, password);
        Assert.assertEquals(WebPage.getPostSubmitMessage(), expectedMessage);
    }
    @Test(dataProvider="getFromJson")
    public void invalidUserTest(String username, String password, String expectedMessage) throws InterruptedException {
        test.assignCategory("Negative Scenario");
        childTest = test.createNode("Validate login with invalid username and valid password");
        log("Validating login with invalid username and valid password");

        WebPage.login(username, password);
        Assert.assertEquals(WebPage.getPostSubmitMessage(), expectedMessage);
    }
    @Test(dataProvider="getFromJson")
    public void invalidPasswordTest(String username, String password, String expectedMessage)
            throws InterruptedException {
        test.assignCategory("Negative Scenario");
        childTest = test.createNode("Validate login with valid username and invalid password");
        log("Validating login with valid username and invalid password");

        WebPage.login(username, password);
        Assert.assertEquals(WebPage.getPostSubmitMessage(), expectedMessage);
    }
    @DataProvider(name = "getFromJson")
    public Object[][] getFromJson(Method mtd) throws Exception {
        JsonDataManager jdm = JsonDataProvider.loadData();
        Object[][] dataMap = jdm.getData(getClass(), mtd.getName());
        return dataMap;
    }
}