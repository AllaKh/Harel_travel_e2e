package com.pages;

import com.core.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Destination step of the travel insurance wizard:
 * https://digital.harel-group.co.il/travel-policy/wizard/destination
 */
public class DestinationPage {
    private final WebDriver driver;
    private final WaitUtils wait;

    // Page title element: "בחרו יעד נסיעה"
    private final By screenTitle = By.cssSelector("#screen_title");

    // Australia destination button
    private final By australiaBtn = By.cssSelector("#destination-6 > svg");

    // "Next" button using stable XPath
    private final By nextBtn = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[2]/div[3]/div/button");

    /**
     * Constructor to initialize WebDriver and WaitUtils.
     *
     * @param driver WebDriver instance
     */
    public DestinationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 20);
    }

    /**
     * Verifies that the destination page is loaded by checking the page title.
     *
     * @return true if the title is visible and contains the expected text
     */
    public boolean isLoaded() {
        wait.urlContains("/wizard/destination");
        return wait.visible(screenTitle).getText().trim().contains("בחרו יעד נסיעה");
    }

    /**
     * Selects "Australia" as the destination.
     */
    public void chooseAustralia() {
        wait.clickable(australiaBtn).click();
    }

    /**
     * Clicks the "Next" button to proceed to the next step.
     */
    public void clickNext() {
        wait.clickable(nextBtn).click();
    }
}
