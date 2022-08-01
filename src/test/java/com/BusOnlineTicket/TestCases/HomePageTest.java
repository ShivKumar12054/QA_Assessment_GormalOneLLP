package com.BusOnlineTicket.TestCases;

import com.BusOnlineTicket.QA.base.BaseTest;
import com.BusOnlineTicket.QA.pages.HomePage;
import com.BusOnlineTicket.QA.testData.Data;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {
    HomePage homePage;

    public HomePageTest() {
        super();
    }

    @BeforeTest
    public void setUp() {
        BaseTest.initialization();
        homePage = new HomePage();
    }

    @Test(priority = 1)
    public void verifyHomeUrl() {
        String actualHomeUrl = homePage.getHomeUrl();
        Assert.assertEquals(actualHomeUrl, "https://www.busonlineticket.com/booking/bus-tickets.aspx");
    }

    @Test(priority = 2)
    public void verifyHomePageTitle() {
        String homePageTitle = homePage.getHomePageTitle();
        Assert.assertEquals(homePageTitle, "Bus Online Booking Services at BusOnlineTicket.com Malaysia & Singapore");
    }

    @Test(priority = 3, dataProviderClass = Data.class, dataProvider = "placeDetail")
    public void bookBusTicket(String origin, String destination, String date, int seatsToSelect) {
        homePage.selectOriginPlace(origin);
        homePage.selectDestinationField(destination);
        homePage.datePicker(date);
        homePage.clickSearchBusButton();
        homePage.verifyHeaderName();
        homePage.selectLeastPricedBus();
        int numberOfSeatSelected = homePage.selectSeats(seatsToSelect);
        homePage.verifyNumberOfSeats(numberOfSeatSelected);
        homePage.clickOnProceed();
    }

    @Test(priority = 4, dataProviderClass = Data.class, dataProvider = "passengerDetails")
    public void fillPassengerDetails(String fullName, String phoneNumber, String email) {
        homePage.addPassengerDetails(fullName, phoneNumber, email);
    }

    @Test(priority = 5)
    public void verifyAlertPopupAndPrintAlertMsg() {
        homePage.clickOnProceedToPaymentAndVerifyAlertPopup();
    }
}