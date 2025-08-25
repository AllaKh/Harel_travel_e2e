package com.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Factory class for creating WebDriver instances using WebDriverManager.
 */
public class DriverFactory {

    /**
     * Creates a WebDriver instance based on the specified browser name.
     *
     * @param browser the name of the browser ("chrome", "firefox", "edge")
     * @return a WebDriver instance
     */
    public static WebDriver createDriver(String browser) {
        if (browser == null) {
            throw new IllegalArgumentException("Browser name must not be null");
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");

                // Create unique user data directory to avoid profile conflicts
                try {
                    Path tempProfile = Files.createTempDirectory("chrome-profile-");
                    chromeOptions.addArguments("--user-data-dir=" + tempProfile.toAbsolutePath().toString());
                } catch (Exception e) {
                    // Log error or ignore if temp directory creation fails
                    e.printStackTrace();
                }

                return new ChromeDriver(chromeOptions);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                return new EdgeDriver(edgeOptions);

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
