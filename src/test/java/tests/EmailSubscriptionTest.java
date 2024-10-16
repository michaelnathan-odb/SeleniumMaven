package tests;

import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.Subscription;
import utils.TestDataProvider;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

public class EmailSubscriptionTest {
    public static WebDriver driver;

    // Dummy test
    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class)
    void dummyTest(String browser, String site, String resolution){
        driver = BrowserConfig.getDriver(browser);
        ScreenSizeConfig.setScreenSize(driver, resolution);
        driver.get(site);

        String siteTitle = driver.getTitle();
        System.out.println("Site Title :" + siteTitle);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String expectedTitle = getExpectedTitle(site);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(siteTitle, expectedTitle);
//        try {
//            softAssert.assertEquals(siteTitle, expectedTitle);
//            System.out.println("Test Passed");
//        } catch (AssertionError e){
//            System.out.println("Test Failed: Assertion fail because" + e);
//            throw e;
//        }
        softAssert.assertAll();
        driver.quit();
    }

    //assert for dummy test
    public String getExpectedTitle(String site){
        return switch (site) {
            case "https://odb.org/subscription/jp/" -> "Japan | Our Daily Bread";
            case "https://odb.org/subscription/id/" -> "Indonesia | Our Daily Bread";
            case "https://traditional-odb.org/subscription/id/" -> "Indonesia | 靈命日糧繁體中文網站";
            case "https://traditional-odb.org/subscription/my/" -> "Malaysia | 靈命日糧繁體中文網";
            default -> "Default Title";
        };
    }


    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class)
    void testEmailSubs(String browser, String site, String resolution) throws InterruptedException {
        driver = BrowserConfig.getDriver(browser);
        ScreenSizeConfig.setScreenSize(driver, resolution);
        driver.get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Thread.sleep(3000);
        Subscription subscription = new Subscription(driver);
        subscription.clickEmailSubs();
        subscription.emailFieldAll();
//        subscription.submitFormEmail();
//        Thread.sleep(8000);
//        subscription.validateDataEmail();
    }


    @AfterMethod
    public static void tearDown(){
        driver.quit();
    }
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
