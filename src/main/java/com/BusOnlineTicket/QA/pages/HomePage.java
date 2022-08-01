package com.BusOnlineTicket.QA.pages;

import com.BusOnlineTicket.QA.base.BaseTest;
import com.BusOnlineTicket.QA.utils.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;

public class HomePage extends BaseTest {
    public HomePage() {
        super();
        PageFactory.initElements(driver, this);
    }

    //Web objects(Object Repository) or all web elements
    @FindBy(id = "txtOriginGeneral")
    WebElement originInputField;

    @FindBy(id = "txtDestinationGeneral")
    WebElement destinationInputField;

    @FindBy(id = "txtDepartDateBooking")
    WebElement departDate;

    @FindBy(xpath = "select2-result-label")
    WebElement listUponClick;

    @FindBy(xpath = "//div[@id='ui-datepicker-div']")
    WebElement calendarVisible;

    @FindBy(xpath = "/html/body/div/div/a[2]")
    WebElement calendarNextButton;

    @FindBy(xpath = "//div[@class='ui-datepicker-title']")
    WebElement calendarMothYear;

    @FindBy(id = "btnBusSearchNewGeneral")
    WebElement searchButton;

    @FindBy(xpath = "//h1[@class='select-bus-header']")
    WebElement originToDestHeader;

    @FindBy(xpath = "//span[@class='seat_qty']")
    WebElement numberTicketsSelector;

    @FindBy(xpath = "//input[@class='seatproceed']")
    WebElement proceedButton;

    @FindBy(xpath = "//input[@class='payment_textName form-control pay-form-control']")
    WebElement fullNameInputField;

    @FindBy(xpath = "//input[@class='payment_txtPhoneLogin form-control pay-form-control']")
    WebElement phoneNumberInputField;

    @FindBy(xpath = "//input[@class='payment_txtEmail form-control pay-form-control']")
    WebElement emailInputField;

    @FindBy(xpath = "//a[@id='btnNext']")
    WebElement nextButton;

    @FindBy(xpath = "//span[@class='text-bold text-uppercase hidden-xs']")
    WebElement paymentMethodHeader;

    @FindBy(xpath = "//input[@id='payment_btnProceedPayment']")
    WebElement proceedToPaymentButton;

    @FindBy(xpath = "//div[@class='mt-4 text-semibold']")
    WebElement popUpMessageSelector;

    //Actions or methods
    public String getHomePageTitle() {
        return driver.getTitle();
    }

    public String getHomeUrl() {
        return driver.getCurrentUrl();
    }

    public void selectOriginPlace(String originPlace) {
        originInputField.isEnabled();
        originInputField.click();
        originInputField.sendKeys(originPlace);
        TestUtil.selectDropDown(originPlace);
    }

    public void selectDestinationField(String destPlace) {
        destinationInputField.isEnabled();
        TestUtil.selectDropDown(destPlace);
    }

    public void datePicker(String date) {
        int day = Integer.parseInt(date.split("-")[0]);
        String expMonth = date.split("-")[1];
        String expYear = date.split("-")[2];

        //Explicit wait: Waiting for calendar to show on page
        new WebDriverWait(driver, Duration.ofSeconds(200))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='ui-datepicker-div']")));

        String monthYear = calendarVisible.getText();
        String month = monthYear.split(" ")[0].trim();
        String year = monthYear.split(" ")[1].trim();

        while(!(month.equals(expMonth) && year.equals(expYear))) {
            calendarNextButton.click();
            monthYear = calendarMothYear.getText();
            month = monthYear.split(" ")[0].trim();
            year = monthYear.split(" ")[1].trim();
        }
        driver.findElement(By.xpath("//a[contains(text(),'"+day+"')]")).click();
    }

    public void clickSearchBusButton() {
        searchButton.click();
    }

    public void verifyHeaderName() {
        Assert.assertEquals(originToDestHeader.getText(), "Cameron Highlands to Kuala Lumpur Bus Tickets");
    }

    //Checks least price amount and click respective select button
    public void selectLeastPricedBus() {
        //getLeastBusPrice() - return least price from the available prices
       String leastBusFee = String.valueOf(TestUtil.getLeastBusPrice());

       List<WebElement> priceList = driver.findElements(By.xpath("//tbody/tr/td[5]/div[2]/div[1]/b[1]"));
       int sizeOfPriceList = priceList.size();

       //Iterating through price list to select lest price and select respective "select" button
        for (int i = 1; i <= sizeOfPriceList; i++) {
            String priceIndividual = (driver.findElement(By.xpath("//tbody/tr["+i+"]/td[5]/div[2]/div[1]/b[1]")).getText()).split(" ")[1].split("\\.")[0];
            if(priceIndividual.equals(leastBusFee)) {
                driver.findElement(By.xpath("//tbody/tr["+i+"]/td[6]/a[1]")).click();
                break;
            }
        }
    }

    //Funtion to select available seats of N
    public int selectSeats(int seatsToSelect) {
        int count = 0;
        //All available seats web elements
        List<WebElement> availableSeats = driver.findElements(By.xpath("//div[@class='seat_available']"));
        //Total size
        int totalAvailableSeats = availableSeats.size();

        //Select N seats if totalAvailableSeats >= N or select all available seats
        //here N is seats to select by customer
        if(totalAvailableSeats >= seatsToSelect) {
            for (int i = 1; i <= TestUtil.TOTAL_NUMBER_SEATS; i++) {
                String seatStatus = driver.findElement(By.xpath("//div[normalize-space()='"+ i +"']")).getAttribute("class");

                if (seatStatus.equals("seat_available")) {
                    driver.findElement(By.xpath("//div[normalize-space()='" + i + "']")).click();
                    count++;
                }
                if(count == seatsToSelect) break;
            }
        }
        else {
            for (int i = 1; i <= TestUtil.TOTAL_NUMBER_SEATS ; i++) {
                String seatStatus = driver.findElement(By.xpath("//div[normalize-space()='"+i+"']")).getAttribute("class");
                if(seatStatus.equals("seat_available")) {
                    driver.findElement(By.xpath("//div[normalize-space()='" + i + "']")).click();
                    count++;
                }
            }
        }
        return count;
    }

    public void verifyNumberOfSeats(int seatsToSelect) {
        int actualResult = Integer.parseInt(numberTicketsSelector.getText());
        Assert.assertEquals(actualResult, seatsToSelect);
    }

    public void clickOnProceed() {
        proceedButton.click();
    }

    //Function to fill passengers details
    public void addPassengerDetails(String fullName, String phoneNumber, String email) {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Passenger Details')]")));

        fullNameInputField.sendKeys(fullName);
        phoneNumberInputField.sendKeys(phoneNumber);
        emailInputField.sendKeys(email);
        nextButton.click();
    }

    //Function to clickOnProceed Button and verify the message
    public void clickOnProceedToPaymentAndVerifyAlertPopup() {
        paymentMethodHeader.isDisplayed();
        proceedToPaymentButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='offer-trip-wrapper']")));

       String actualPopUpText =  popUpMessageSelector.getText();
        Assert.assertEquals(actualPopUpText, "Please select a payment method");
        System.out.println(actualPopUpText);
    }
}