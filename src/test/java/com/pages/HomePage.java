package com.pages;

import com.core.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Home page representation for https://digital.harel-group.co.il/travel-policy
 */
public class HomePage {
    private final WebDriver driver;
    private final WaitUtils wait;

    // XPath selector for "First Purchase" button
    private final By firstPurchaseBtn = By.xpath("//*[@id='root']/div/div/div[1]/div[1]/div/div/div[1]/div/button");

    /**
     * Constructor for HomePage.
     *
     * @param driver WebDriver instance used to interact with the browser
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 20); // 20 seconds timeout for waiting
    }

    /**
     * Open the home page URL.
     *
     * @return this HomePage instance to allow method chaining
     */
    public HomePage open() {
        driver.get("https://digital.harel-group.co.il/travel-policy");
        return this;
    }

    /**
     * Verify the home page is loaded by checking visibility of the first purchase button.
     *
     * @return true if the button is visible, false otherwise
     */
    public boolean isLoaded() {
        WebElement btn = wait.visible(firstPurchaseBtn);
        return btn.isDisplayed();
    }

    /**
     * Click on the "First Purchase" button.
     */
    public void clickFirstPurchase() {
        wait.clickable(firstPurchaseBtn).click();
    }
}
