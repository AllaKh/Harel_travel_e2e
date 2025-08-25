package com.pages;

import com.core.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Travelers step: https://digital.harel-group.co.il/travel-policy/wizard/travelers
 * Verify heading "נשמח להכיר את הנוסעים שנבטח הפעם"
 */
public class TravelersPage {
    private final WebDriver driver;
    private final WaitUtils wait;

    private final By heading = By.cssSelector(
            "#root > div > div.jss700 > div.MuiContainer-root.jss697 > div.jss937 > h2"
    );

    public TravelersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 20);
    }

    public boolean isLoaded() {
        wait.urlContains("/wizard/travelers");
        return wait.visible(heading).getText().trim().contains("נשמח להכיר את הנוסעים שנבטח הפעם");
    }
}
