package com.znsio.rpap;

import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.epam.reportportal.annotations.Step;
import com.znsio.rpap.pages.WebExample;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static com.znsio.reportportal.integration.utils.ReportPortalLogger.captureAndAttachScreenshot;
import static com.znsio.reportportal.integration.utils.ReportPortalLogger.logInfoMessage;

public class WebSteps {
    private static final Logger LOGGER = Logger.getLogger(WebSteps.class.getName());
    private WebDriver webDriver;
    private WebExample webPage;
    private Eyes eyesOnWeb;
    public WebSteps(WebDriver webDriver, WebExample webPage, Eyes eyesOnWeb) {
        this.webDriver = webDriver;
        this.webPage = webPage;
        this.eyesOnWeb = eyesOnWeb;
    }

    @Step
    void verifyPageTitle(String title) {
        captureAndAttachScreenshot(webDriver, "Validating Title of the Screen");
        eyesOnWeb.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
        Assert.assertEquals(webPage.pageTitle(), title);
    }

    @Step
    void performLogin(String username, String password) throws InterruptedException {
        log("Entering username and password");
        webPage.login(username, password);
        eyesOnWeb.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
    }

    @Step
    void verifyMessageAfterLogin(String expectedMessage) {
        captureAndAttachScreenshot(webDriver, "Verifying post login message");
        eyesOnWeb.check(Thread.currentThread().getStackTrace()[1].getMethodName(), Target.window());
        Assert.assertEquals(webPage.getPostSubmitMessage(), expectedMessage);
    }

    private static void log(String message) {
        LOGGER.info(message);
        logInfoMessage(message);
    }
}
