package com.pages;

import com.core.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Home page: https://digital.harel-group.co.il/travel-policy
 */
public class HomePage {
    private final WebDriver driver;
    private final WaitUtils wait;

    // "לרכישה בפעם הראשונה" button selector (as given)
    private final By firstPurchaseBtn = By.cssSelector(
            "#root > div > div > div.jss613 > div.jss614 > div > div > div:nth-child(1) > div > button"
    );

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 20);
    }

    public HomePage open() {
        driver.get("https://digital.harel-group.co.il/travel-policy");
        return this;
    }

    /** Verifies the page is loaded by checking the presence of the specific button. */
    public boolean isLoaded() {
        WebElement btn = wait.visible(firstPurchaseBtn);
        return btn.isDisplayed();
    }

    /** Clicks "לרכישה בפעם הראשונה". */
    public void clickFirstPurchase() {
        wait.clickable(firstPurchaseBtn).click();
    }
}
