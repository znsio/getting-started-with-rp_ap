package com.znsio.rpap.businessLayer;

import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.epam.reportportal.annotations.Step;
import com.znsio.rpap.pages.WebExample;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.captureAndAttachScreenshot;
import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;

public class WebBL {
    private static final Logger LOGGER = Logger.getLogger(WebBL.class.getName());
    private WebDriver webDriver;
    private WebExample webPage;
    private Eyes eyesOnWeb;
    public WebBL(WebDriver webDriver, WebExample webPage, Eyes eyesOnWeb) {
        this.webDriver = webDriver;
        this.webPage = webPage;
        this.eyesOnWeb = eyesOnWeb;
    }

    @Step
    public void verifyPageTitle(String title) {
        captureAndAttachScreenshot(webDriver, "Validating Title of the Screen");
        eyesOnWeb.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
        Assert.assertEquals(webPage.pageTitle(), title);
    }

    @Step
    public void performLogin(String username, String password) throws InterruptedException {
        logInfoMessage("Entering username and password");
        webPage.login(username, password);
        eyesOnWeb.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
    }

    @Step
    public void verifyMessageAfterLogin(String expectedMessage) {
        captureAndAttachScreenshot(webDriver, "Verifying post login message");
        eyesOnWeb.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
        Assert.assertEquals(webPage.getPostSubmitMessage(), expectedMessage);
    }
}
