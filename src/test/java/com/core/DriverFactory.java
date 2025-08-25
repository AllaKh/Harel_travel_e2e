package com.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Factory class for creating WebDriver instances using WebDriverManager.
 * Supports Chrome, Firefox, and Edge browsers.
 */
public class DriverFactory {

    /**
     * Container class holding WebDriver instance and optional Chrome user data directory path.
     */
    public static class DriverWithProfile {
        public final WebDriver driver;
        public final Path chromeProfilePath;  // null if not Chrome

        public DriverWithProfile(WebDriver driver, Path chromeProfilePath) {
            this.driver = driver;
            this.chromeProfilePath = chromeProfilePath;
        }
    }

    /**
     * Creates a WebDriver instance based on the specified browser name.
     * For Chrome, creates a unique temporary user data directory to avoid conflicts.
     *
     * @param browser the browser name ("chrome", "firefox", or "edge")
     * @return DriverWithProfile containing the WebDriver and optional Chrome profile path
     * @throws IllegalArgumentException if the browser parameter is null or unsupported
     * @throws RuntimeException if temporary directory creation fails for Chrome profile
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

                Path tempProfile;
                try {
                    tempProfile = Files.createTempDirectory("chrome-profile-" + UUID.randomUUID());
                    System.out.println("Using Chrome user-data-dir: " + tempProfile.toAbsolutePath());
                    chromeOptions.addArguments("--user-data-dir=" + tempProfile.toAbsolutePath().toString());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to create temporary directory for Chrome profile", e);
                }

                return new DriverWithProfile(new ChromeDriver(chromeOptions), tempProfile);

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
