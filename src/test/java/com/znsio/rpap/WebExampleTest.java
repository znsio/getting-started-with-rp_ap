package com.znsio.rpap;

import com.znsio.rpap.businessLayer.WebBL;
import com.znsio.rpap.pages.WebExample;
import com.znsio.rpap.utils.JsonDataManager;
import com.znsio.rpap.utils.JsonDataProvider;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;


//TODO: Consume BaseTest as a listener instead of an inherited class
public class WebExampleTest {
    private WebExample webPage;
    private WebBL webBL;
    private BaseTest bt;


    @BeforeSuite
    public void getBaseTest(XmlTest suite) throws IOException {
        bt = new BaseTest();
        bt.suiteSetup(suite);
    }

    @BeforeMethod
    public void webPageSetup(Method method) throws MalformedURLException {
        bt.methodSetup(method);
        logInfoMessage("Inside @BeforeMethod of " + WebExampleTest.class.getSimpleName());
        webPage = bt.page.getClassInstance(WebExample.class);
        webBL = new WebBL(bt.driver, webPage, bt.applitoolsInitializer.getWebEyes());
    }

    @Test(dataProvider = "getFromJson", priority = -1, description = "Validate Title of the Screen", groups = {"visual"})
    public void titleTest(String title) {
        webBL.verifyPageTitle(title);
    }

    @Test(dataProvider = "getFromJson", description = "Validating login with valid username and password", groups = {"visual"})
    public void validLoginTest(String username, String password, String expectedMessage) throws InterruptedException {

        webBL.performLogin(username, password);
        webBL.verifyMessageAfterLogin(expectedMessage);
    }

    @Test(dataProvider = "getFromJson", description = "Validating login with invalid username and valid password", groups = {"visual"})
    public void invalidUserTest(String username, String password, String expectedMessage) throws InterruptedException {

        webBL.performLogin(username, password);
        webBL.verifyMessageAfterLogin(expectedMessage);
    }

    @Test(dataProvider = "getFromJson", description = "Validate login with valid username and invalid password", groups = {"visual"})
    public void invalidPasswordTest(String username, String password, String expectedMessage)
            throws InterruptedException {

        webBL.performLogin(username, password);
        webBL.verifyMessageAfterLogin(expectedMessage);
    }

    @DataProvider(name = "getFromJson")
    public Object[][] getFromJson(Method mtd) throws Exception {
        JsonDataManager jdm = JsonDataProvider.loadData();
        Object[][] dataMap = jdm.getData(getClass(), mtd.getName());
        return dataMap;
    }
}