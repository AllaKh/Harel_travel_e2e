package com.tests;

import com.core.BaseTest;
import com.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * E2E test:
 * 1) Open home → click first-purchase
 * 2) Destination: verify title, choose Australia, Next
 * 3) Date: enter start=today+7, end=start+30, verify duration text, Next
 * 4) Travelers: verify page loaded
 */
public class TravelFlowTest extends BaseTest {

    @Test(description = "Happy path: first purchase → destination → date → travelers")
    public void testTravelInsuranceWizard() {
        HomePage home = new HomePage(driver).open();
        Assert.assertTrue(home.isLoaded(), "Home page didn't load or button not visible");
        home.clickFirstPurchase();

        DestinationPage dest = new DestinationPage(driver);
        Assert.assertTrue(dest.isLoaded(), "Destination page did not load or title missing");
        dest.chooseAustralia();
        dest.clickNext();

        DatePage datePage = new DatePage(driver);
        Assert.assertTrue(datePage.isLoaded(), "Date page did not load");

        datePage.selectStartAndEndDates();

        TravelersPage trav = new TravelersPage(driver);
        Assert.assertTrue(trav.isLoaded(), "Travelers page did not load or heading missing");
    }
}
