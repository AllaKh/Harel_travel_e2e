package com.pages;

import com.core.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Destination step: https://digital.harel-group.co.il/travel-policy/wizard/destination
 */
public class DestinationPage {
    private final WebDriver driver;
    private final WaitUtils wait;

    // Title "בחרו יעד נסיעה"
    private final By screenTitle = By.cssSelector("#screen_title");

    // Australia button
    private final By australiaBtn = By.cssSelector("#destination-6 > svg");

    // "Next" button (as provided)
    private final By nextBtn = By.cssSelector("#root > div > div.jss700 > div.MuiContainer-root.jss697 > div.jss851 > div.jss798 > div > button");

    public DestinationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 20);
    }

    public boolean isLoaded() {
        wait.urlContains("/wizard/destination");
        return wait.visible(screenTitle).getText().trim().contains("בחרו יעד נסיעה");
    }

    public void chooseAustralia() {
        wait.clickable(australiaBtn).click();
    }

    public void clickNext() {
        wait.clickable(nextBtn).click();
    }
}
