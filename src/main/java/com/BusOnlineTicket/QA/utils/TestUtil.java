package com.BusOnlineTicket.QA.utils;

import com.BusOnlineTicket.QA.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtil extends BaseTest {
    public static long PAGE_LOAD_TIMEOUT = 30;
    public static long IMPLICIT_WAIT = 20;
    public static int TOTAL_NUMBER_SEATS = 30;

    public static void selectDropDown(String place) {
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='select2-result-label']"));
        for (WebElement e:list) {
            if(e.getText().equalsIgnoreCase(place)) {
                e.click();
                break;
            }
        }
    }

    public static void explicitWait(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.valueOf(element))));
    }

    public static int getLeastBusPrice() {
        List<WebElement> listOfPrice =  driver.findElements(By.xpath("//b[@class='busprice1']"));
        ArrayList<String> priceList = new ArrayList();
        ArrayList<String> onlyPrice = new ArrayList();
        ArrayList<Integer> price = new ArrayList();
        
        int i =0;
        for (WebElement e:listOfPrice) {
            priceList.add(i, e.getText());
            i++;
        }

        int j = 0;
        for (String element:priceList) {
            onlyPrice.add(j, element.split(" ")[1]);
            j++;
        }

        int k = 0;
        for (String element:onlyPrice) {
            price.add(k, Integer.parseInt(element.split("\\.")[0]));
            k++;
        }
        Collections.sort(price);
        return price.get(0);
    }
}