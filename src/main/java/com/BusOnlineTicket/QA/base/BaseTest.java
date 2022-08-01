package com.BusOnlineTicket.QA.base;

import com.BusOnlineTicket.QA.utils.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
     public static WebDriver driver;
     public static Properties properties;

    protected BaseTest() {
        try {
            //To make file available on stream
            FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/BusOnlineTicket/QA/config/config.properties");

            //To access data on properties file
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Create a browser compatible instance and initialize
    public static void initialization() {
        String browserName = properties.getProperty("browserName");

        //Selecting the browser
        if(browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if(browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if(browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        //Waits for driver to wait if page not loaded
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));

        //Waits for element to present until the time specified
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));

        //Maximize the window
        driver.manage().window().maximize();

        //To delete all cookies
        driver.manage().deleteAllCookies();

        //Visit the browser
        driver.get(properties.getProperty("baseUrl"));
    }

    public static void tearDown() {
        driver.quit();
    }
}