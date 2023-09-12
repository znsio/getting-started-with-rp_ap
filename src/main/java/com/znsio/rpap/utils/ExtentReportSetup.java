package com.znsio.rpap.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.znsio.rpi.utils.ScreenShotManager;
import org.testng.util.Strings;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.znsio.rpi.utils.ScreenShotManager.getPrefix;
import static com.znsio.rpi.utils.ScreenShotManager.normaliseScenarioName;

public class ExtentReportSetup {
    private static final Logger LOGGER = Logger.getLogger(ExtentReportSetup.class.getName());

    public static ExtentReports initExtentReport(ExtentReports extent, String reportTitle, String reportName) {
        ConfigDataProvider configData = new ConfigDataProvider();
        String extentReportFileName = normaliseScenarioName(getPrefix() + "-" +
                configData.getData("APP_NAME") + "-" + configData.getData("PLATFORM"));
        String extentReportFilePath = System.getProperty("user.dir") + File.separator +
                configData.getData("TEST_REPORT_DIRECTORY") + File.separator + extentReportFileName + ".html";
        ExtentHtmlReporter htmlReporter;htmlReporter = new ExtentHtmlReporter(extentReportFilePath);
        htmlReporter.config().setDocumentTitle(reportTitle);
        htmlReporter.config().setReportName(reportName);
        htmlReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        try {
            if (Strings.isNotNullAndNotEmpty(InetAddress.getLocalHost().getHostName())) {
                String hostName = InetAddress.getLocalHost().getHostName();
                extent.setSystemInfo("HostName", hostName);
            }
        } catch (UnknownHostException ex) {
            LOGGER.warn("Unable to set Extent Report Attribute for HostName: " + ex);
        }
        extent.setSystemInfo("Environment", configData.getData("TARGET_ENVIRONMENT"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Platform", configData.getData("PLATFORM"));
        return extent;
    }

    public static void loadExtentReport(WebDriver driver, ITestResult result, ExtentTest childTest) {
        if (result.getStatus() == ITestResult.FAILURE) {

            childTest.log(Status.FAIL, MarkupHelper.createLabel("Test Failed", ExtentColor.RED));
            childTest.log(Status.FAIL, result.getThrowable());
            // add screenshot
            String screenshotPath = ScreenShotManager.captureScreenShot(driver, result.getName());
            try {
                childTest.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                childTest.addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            childTest.skip(MarkupHelper.createLabel(result.getName()+" Test case Skipped", ExtentColor.YELLOW));
            childTest.skip(result.getThrowable());

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            childTest.log(Status.PASS, MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
        }
    }
}
