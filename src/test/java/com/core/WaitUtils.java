package com.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Small wrapper for explicit waits and safe interactions.
 */
public class WaitUtils {
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver, long timeoutSec) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    public WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void urlContains(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    public void textPresent(By locator, String expectedText) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }

    public void sleepSilently(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) {}
    }
}
