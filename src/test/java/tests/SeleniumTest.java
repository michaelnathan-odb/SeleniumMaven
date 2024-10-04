package tests;

import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.Subscription;

public class SeleniumTest {

    public static WebDriver driver;
    @BeforeTest
    public static void Setup(){
        driver = BrowserConfig.getDriver("chrome");
        ScreenSizeConfig.setScreenSize(driver,"mobile");
        driver.get("https://odb.org/subscription/jp/");
    }

    @Test
    void test(){
        Subscription.click_EmailSubs();
        Subscription.emailField_all();
        Subscription.submitForm();
    }
}
