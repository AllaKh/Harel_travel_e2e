package com.core;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Base test class providing WebDriver setup, teardown, and failure handling.
 */
public class BaseTest {

    protected WebDriver driver;
    private Path chromeProfilePath;  // Temporary Chrome profile directory path, null if not Chrome
    protected final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    /**
     * Initializes WebDriver before each test method based on the browser parameter.
     * Stores Chrome temporary profile path for later cleanup.
     *
     * @param browser browser name ("chrome" by default)
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        logger.info("Starting WebDriver for browser: {}", browser);
        DriverFactory.DriverWithProfile driverWithProfile = DriverFactory.createDriverWithProfile(browser);
        this.driver = driverWithProfile.driver;
        this.chromeProfilePath = driverWithProfile.chromeProfilePath;
    }

    /**
     * Tear down method executed after each test.
     * Takes screenshot and page source on failure,
     * quits WebDriver, and deletes Chrome temp profile directory if exists.
     *
     * @param result TestNG test result instance
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (!result.isSuccess()) {
                logger.error("Test {} FAILED", result.getName());

                // Capture screenshot
                byte[] screenshot = takeScreenshot();
                if (screenshot != null) {
                    saveScreenshotToAllure(screenshot);
                }

                // Capture page source
                savePageSource();
            } else {
                logger.info("Test {} PASSED", result.getName());
            }
        } finally {
            if (driver != null) {
                logger.info("Closing WebDriver");
                driver.quit();
            }

            // Delete temp Chrome profile directory if exists
            if (chromeProfilePath != null) {
                try {
                    logger.info("Deleting Chrome profile temp directory: {}", chromeProfilePath);
                    Files.walk(chromeProfilePath)
                            .sorted((p1, p2) -> p2.compareTo(p1)) // delete children before parent
                            .map(Path::toFile)
                            .forEach(java.io.File::delete);
                } catch (IOException e) {
                    logger.warn("Failed to delete Chrome profile temp directory: {}", e.getMessage());
                }
            }
        }
    }

    /**
     * Take screenshot using WebDriver.
     *
     * @return screenshot bytes
     */
    @Attachment(value = "Failure Screenshot", type = "image/png")
    protected byte[] takeScreenshot() {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Attach screenshot to Allure report.
     *
     * @param screenshot screenshot bytes
     */
    protected void saveScreenshotToAllure(byte[] screenshot) {
        Allure.addAttachment("Screenshot on failure", new ByteArrayInputStream(screenshot));
    }

    /**
     * Attach page source to Allure report.
     *
     * @return page source bytes
     */
    @Attachment(value = "Page Source", type = "text/html")
    protected byte[] savePageSource() {
        try {
            return driver.getPageSource().getBytes();
        } catch (Exception e) {
            logger.error("Failed to capture page source: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Get active WebDriver instance.
     *
     * @return WebDriver
     */
    public WebDriver getDriver() {
        return driver;
    }
}
