package com.pages;

import com.core.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

/**
 * Date step: https://digital.harel-group.co.il/travel-policy/wizard/date
 * Select start date = today + 7 days; end date = start + 30
 * Verify label "סה\"כ: 30 ימים"
 */
public class DatePage {
    private final WebDriver driver;
    private final WaitUtils wait;

    // Container with the total days label (as provided)
    private final By totalDaysContainer = By.cssSelector(
            "#root > div > div.jss700 > div.MuiContainer-root.jss697 > div.jss896 > div.MuiGrid-root.MuiGrid-container.MuiGrid-spacing-xs-3 > div"
    );

    // Calendar "grid" container (to ensure picker is visible/focused)
    private final By calendarContainer = By.cssSelector(
            "#root > div > div.jss700 > div.MuiContainer-root.jss697 > div.jss896 > div.MuiGrid-root.MuiGrid-container.MuiGrid-spacing-xs-3 > div > div > div > div"
    );

    // Often, the "today" day button carries aria-current="date" in MUI pickers
    private final By todayBtn = By.cssSelector("button[aria-current='date']");

    // Fallback: very long XPath provided (may vary, used as last resort)
    private final By fallbackCalendarButton = By.xpath(
            "//*[@id='root']/div/div[2]/div[2]/div[2]/div[1]/div/div/div/div/div[2]/div[1]/div/div/div[2]/div/div[5]/div[2]/div/button/span/span"
    );

    private final By nextButton = By.id("nextButton");

    public DatePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 25);
    }

    public boolean isLoaded() {
        wait.urlContains("/wizard/date");
        // If calendar is visible, we assume page is ready
        wait.visible(calendarContainer);
        return true;
    }

    /**
     * Robust date range selection using keyboard navigation (independent of locale).
     * 1) Focus today's cell, move +startOffset using ARROW_RIGHT, hit ENTER (start).
     * 2) Move +duration days, hit ENTER (end).
     */
    public void selectRangePlus7AndPlus30(int startOffsetDays, int durationDays) {
        // Ensure calendar visible
        WebElement container = wait.visible(calendarContainer);

        WebElement startFocus;
        // Try to focus "today"
        if (!driver.findElements(todayBtn).isEmpty()) {
            startFocus = driver.findElement(todayBtn);
        } else if (!driver.findElements(fallbackCalendarButton).isEmpty()) {
            // Last-resort click within grid (provided xpath)
            startFocus = driver.findElement(fallbackCalendarButton);
        } else {
            // Generic fallback: click container to bring focus into grid, then try to find any day button
            container.click();
            // Try any button inside grid
            startFocus = container.findElement(By.cssSelector("button"));
        }

        Actions actions = new Actions(driver);
        actions.moveToElement(startFocus).click().perform();

        // Move to start date = today + startOffsetDays
        for (int i = 0; i < startOffsetDays; i++) {
            actions.sendKeys(Keys.ARROW_RIGHT).perform();
            wait.sleepSilently(50);
        }
        // Confirm start date
        actions.sendKeys(Keys.ENTER).perform();
        wait.sleepSilently(150);

        // Move to end date = start + durationDays
        for (int i = 0; i < durationDays; i++) {
            actions.sendKeys(Keys.ARROW_RIGHT).perform();
            wait.sleepSilently(50);
        }
        // Confirm end date
        actions.sendKeys(Keys.ENTER).perform();

        // Verify total days text contains exact Hebrew string "סה\"כ: 30 ימים"
        wait.textPresent(totalDaysContainer, "סה\"כ: 30 ימים");
    }

    public void clickNext() {
        wait.clickable(nextButton).click();
    }
}

