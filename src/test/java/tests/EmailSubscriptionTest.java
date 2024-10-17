package tests;

import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.Subscription;
import utils.TestDataProvider;

public class EmailSubscriptionTest {
    public static WebDriver driver;

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
        subscription.submitFormEmail();
        Thread.sleep(8000);
        subscription.validateDataEmail(site);
    }

    @AfterMethod
    public static void tearDown(){
        driver.quit();
    }
}

/*
    // Dummy test
    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class)
    void dummyTest(String expectedResult, String browser, String site, String resolution){
        driver = BrowserConfig.getDriver(browser);
        ScreenSizeConfig.setScreenSize(driver, resolution);
        driver.get(site);

        String siteTitle = driver.getTitle();
        System.out.println("Site Title: " + siteTitle);
        System.out.println("Expected Result: " + expectedResult);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String expectedTitle = getExpectedTitle(site);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(siteTitle, expectedTitle);
        softAssert.assertAll();
        driver.quit();
    }

    //assert for dummy test
    public String getExpectedTitle(String site){
        return switch (site) {
            case "https://odb.org/subscription/jp/" -> "Japan | Our Daily Bread";
            case "https://odb.org/subscription/id/" -> "Indonesia | Our Daily Bread";
            case "https://traditional-odb.org/subscription/id/" -> "Indonesia | 靈命日糧繁體中文網站";
            case "https://traditional-odb.org/subscription/my/" -> "Malaysia | 靈命日糧繁體中文網站";
            default -> "Default Title";
        };
    }
    */