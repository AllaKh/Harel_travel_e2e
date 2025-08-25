package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Page Object for the travel dates selection step.
 * Fills in start and end dates, verifies the summary text, and proceeds.
 */
public class DatePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Page elements
    private static final By START_LABEL = By.xpath("//h2[@data-hrl-bo='travel_start_date']");
    private static final By END_LABEL = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[2]/div[1]/div/div/div/div/div[1]/div[2]/h2");

    private static final By START_INPUT = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div[2]/div[1]/div/div/div/div/div[1]/div[1]/div/div/input");
    private static final By END_INPUT = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div[2]/div[1]/div/div/div/div/div[1]/div[2]/div/div/input");

    private static final By SUMMARY_TEXT = By.cssSelector("#root > div > div.jss109 > div.MuiContainer-root.jss106 > div.jss217 > div.jss218 > span");
    private static final By NEXT_BUTTON = By.id("nextButton");

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Constructor to initialize the driver and wait instance.
     * @param driver WebDriver instance
     */
    public DatePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Waits for the date selection section to load.
     * @return true if key elements are visible
     */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(START_LABEL));
            wait.until(ExpectedConditions.visibilityOfElementLocated(END_LABEL));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Enters start and end dates into the form.
     * Start date is 7 days from today.
     * End date is 30 days total (start + 29).
     * Verifies the summary text and proceeds.
     */
    public void selectStartAndEndDates() {
        LocalDate startDate = LocalDate.now().plusDays(7);
        LocalDate endDate = startDate.plusDays(29); // To get exactly 30 total days including start

        String formattedStart = startDate.format(formatter);
        String formattedEnd = endDate.format(formatter);

        // Fill in the start date
        WebElement startInput = wait.until(ExpectedConditions.elementToBeClickable(START_INPUT));
        startInput.clear();
        startInput.sendKeys(formattedStart);

        // Fill in the end date
        WebElement endInput = wait.until(ExpectedConditions.elementToBeClickable(END_INPUT));
        endInput.clear();
        endInput.sendKeys(formattedEnd);

        // Verify summary text is exactly "סה\"כ: 30 ימים"
        wait.until(ExpectedConditions.textToBe(SUMMARY_TEXT, "סה\"כ: 30 ימים"));

        // Click the next button
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON));
        nextButton.click();
    }
}
