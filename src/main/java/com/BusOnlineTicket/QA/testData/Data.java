package com.BusOnlineTicket.QA.testData;

import org.testng.annotations.DataProvider;

public class Data {
    //To add new place provide data as
    // originPlace, destinationPlace, date, numberOfSeats : add this info to below object
    @DataProvider(name = "placeDetail")
    public static Object[][] dataSet() {
        return new Object[][] {
                {"Cameron Highlands", "Kuala Lumpur", "24-September-2022", 6}
        };
    }

    //Passengers details data
    @DataProvider(name = "passengerDetails")
    public static Object[][] passengerDetails() {
        return new Object[][] {
                {"ShivaKumar M", "7349295800", "shiva@gmail.com"}
        };
    }
}