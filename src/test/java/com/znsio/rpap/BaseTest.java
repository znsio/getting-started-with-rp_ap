package com.znsio.rpap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.znsio.rpap.pages.Page;
import com.znsio.rpap.utils.BrowserFactory;
import com.znsio.rpap.utils.ConfigDataProvider;
import com.znsio.rpap.utils.ExtentReportSetup;
import org.testng.xml.XmlSuite;
import org.testng.xml.Parser;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static com.znsio.rpi.utils.ReportPortalLogger.logInfoMessage;

public class BaseTest {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public ConfigDataProvider prop;
    public Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    public static ExtentReports extent;
    public static ExtentTest test;
    public static ExtentTest childTest;
    public Page page;

    @BeforeSuite
    public void SuiteSetup() throws IOException {
        prop = new ConfigDataProvider();
        Parser parser = new Parser(System.getProperty("user.dir") + File.separator +
                System.getProperty("suiteXmlFile"));
        XmlSuite xmlSuite = parser.parseToList().get(0);
        extent = ExtentReportSetup.initExtentReport(extent, xmlSuite.getName(), xmlSuite.getTests().get(0).getName());
        LOGGER.info("Report initiated and got config data ");
        driver = BrowserFactory.launchApplication(driver, prop.getBrowser(), prop.getAppURL());
        wait = new WebDriverWait(driver, Duration.ofSeconds(prop.getNumber("pageloadtime")));
        LOGGER.info("Browser Ready");
    }

    @BeforeMethod
    public void MethodSetup() {
        page = new Page(driver, wait);
    }

    @AfterMethod
    public void methodTeatDown(ITestResult result) {
        ExtentReportSetup.loadExtentReport(driver, result, childTest);
    }

    @AfterSuite
    public void suiteTearDown() {
        driver.quit();
        extent.flush();
        LOGGER.info("Report created");
    }

    public void log(String message) {
        childTest.log(Status.INFO, message);
        LOGGER.info(message);
        logInfoMessage(message);
    }
}
