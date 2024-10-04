package tests;

import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.Subscription;

import java.time.Duration;

public class SeleniumTest {
    public static WebDriver driver;
    @BeforeTest
    public static void Setup(){
        driver = BrowserConfig.getDriver("chrome");
    }

    @Test
    void testEmailSubs() throws InterruptedException {
        ScreenSizeConfig.setScreenSize(driver, "mobile");
        driver.get("https://odb.org/subscription/jp/");
        Subscription subscription = new Subscription(driver);
        subscription.clickEmailSubs();
        subscription.emailFieldAll();
        subscription.submitForm();
        Thread.sleep(10000);
        subscription.validateData();
    }
}
