package com.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

/**
 * Factory class for creating WebDriver instances using WebDriverManager.
 * Supports Chrome, Firefox, and Edge browsers.
 */
public class DriverFactory {

    /**
     * Container class holding WebDriver instance and optional Chrome profile directory path.
     */
    public static class DriverWithProfile {
        public final WebDriver driver;
        public final java.nio.file.Path chromeProfilePath;  // null because we don't use temp profile now

        public DriverWithProfile(WebDriver driver, java.nio.file.Path chromeProfilePath) {
            this.driver = driver;
            this.chromeProfilePath = chromeProfilePath;
        }
    }

    /**
     * Creates a WebDriver instance based on the specified browser name.
     *
     * @param browser the browser name ("chrome", "firefox", or "edge")
     * @return DriverWithProfile containing the WebDriver and null profile path
     * @throws IllegalArgumentException if the browser is null or unsupported
     */
    public static DriverWithProfile createDriverWithProfile(String browser) {
        if (browser == null) {
            throw new IllegalArgumentException("Browser name must not be null");
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                // --user-data-dir argument removed, no temp profile created

                return new DriverWithProfile(new ChromeDriver(chromeOptions), null);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                return new DriverWithProfile(new FirefoxDriver(firefoxOptions), null);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                return new DriverWithProfile(new EdgeDriver(edgeOptions), null);

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
