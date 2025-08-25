package com.pages;

import com.core.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Travelers step of the wizard.
 * URL: https://digital.harel-group.co.il/travel-policy/wizard/travelers
 * Verifies that the expected heading is visible on the page.
 */
public class TravelersPage {

    private final WebDriver driver;
    private final WaitUtils wait;

    // Heading element containing the expected text
    private static final By HEADING = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[2]/h2");

    private static final String EXPECTED_HEADING_TEXT = "נשמח להכיר את הנוסעים שנבטח הפעם";

    /**
     * Constructor for the Travelers page.
     * @param driver WebDriver instance
     */
    public TravelersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 20);
    }

    /**
     * Verifies that the Travelers page is loaded.
     * Waits for the correct URL and expected heading text to appear.
     * @return true if heading is present and matches expected text
     */
    public boolean isLoaded() {
        wait.urlContains("/wizard/travelers");
        return wait.visible(HEADING).getText().trim().equals(EXPECTED_HEADING_TEXT);
    }
}
