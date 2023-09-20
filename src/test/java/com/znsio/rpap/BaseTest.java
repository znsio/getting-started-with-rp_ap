package com.znsio.rpap;

import com.znsio.api.VisualTest;
import com.znsio.rpi.properties.Config;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.znsio.rpap.pages.Page;
import com.znsio.rpap.utils.BrowserFactory;

import java.time.Duration;
import java.util.Properties;

import static com.znsio.rpi.utils.ReportPortalLogger.logInfoMessage;

public class BaseTest extends VisualTest {
    protected static WebDriver driver;
    private static WebDriverWait wait;
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    private static final Properties config = Config.loadProperties(System.getProperty("CONFIG"));
    protected Page page;

    @BeforeSuite
    public void SuiteSetup() {
        LOGGER.info("Retrieved config data");
        driver = BrowserFactory.launchApplication(driver, config.getProperty(Config.BROWSER),
                config.getProperty("URL"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(config.getProperty("PAGE_LOAD_TIME"))));
        VisualTest.driverSetupForVisualTest(driver);
        LOGGER.info("Browser Ready");
    }

    @BeforeMethod
    public void MethodSetup() {
        page = new Page(driver, wait);
    }

    @AfterSuite
    public void suiteTearDown() {
        driver.quit();
        LOGGER.info("Report created");
    }

    public void log(String message) {
        LOGGER.info(message);
        logInfoMessage(message);
    }
}
