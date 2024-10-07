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
    }

    //TODO:Translate page to english
    /*
    Browser language translate to english (Chrome, Firefox and Edge)

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--lang=en"); // Spanish language
    WebDriver driver = new ChromeDriver(options);

    EdgeOptions options = new EdgeOptions();
    options.addArguments("--lang=es"); // Spanish language
    WebDriver driver = new EdgeDriver(options);

    FirefoxOptions options = new FirefoxOptions();
    options.addPreference("intl.accept_languages", "es"); // Spanish language
    WebDriver driver = new FirefoxDriver(options);
    */

    @Test
    void testEmailSubs() throws InterruptedException {
        ScreenSizeConfig.setScreenSize(driver, "mobile");
        driver.get("https://odb.org/subscription/jp/");
        Subscription subscription = new Subscription(driver);
        subscription.clickEmailSubs();
        subscription.emailFieldAll();
        subscription.submitFormEmail();
        Thread.sleep(10000);
        subscription.validateDataEmail();
    }

    @Test
    void testPrintSubs(){
        ScreenSizeConfig.setScreenSize(driver, "mobile");
        driver.get("https://odb.org/subscription/jp/");
        //TODO: Translate the form
        Subscription subscription = new Subscription(driver);
        subscription.clickPrintSubs();
        //add scroll
        subscription.printFieldAll();
        subscription.submitFormPrint();
        subscription.validateDataPrint();


    }
}
