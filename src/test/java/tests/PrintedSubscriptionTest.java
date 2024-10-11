package tests;

import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.Subscription;

public class PrintedSubscriptionTest {
    public static WebDriver driver;

    // DataProvider to provide browser, site, and resolution combinations
    @DataProvider(name = "provider")
    public Object[][] dataProvider() {
        return new Object[][]{
                {"chrome", "https://odb.org/subscription/jp/", "mobile"},
                {"chrome", "https://odb.org/subscription/jp/", "tablet"},
                {"chrome", "https://odb.org/subscription/jp/", "desktop"},
                {"edge", "https://odb.org/subscription/jp/", "mobile"},
                {"edge", "https://odb.org/subscription/jp/", "tablet"},
                {"edge", "https://odb.org/subscription/jp/", "desktop"}
        };
    }

    /* This is only dummy test to prevent spam
    @Test(dataProvider = "provider")
    void dummyTest(String browser, String site, String resolution){
        driver = BrowserConfig.getDriver(browser);
        ScreenSizeConfig.setScreenSize(driver, resolution);
        driver.get(site);

        String siteTitle = driver.getTitle();
        System.out.println("Site Title :" + siteTitle);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.quit();
    }
    */

    @Test(dataProvider = "provider")
    void testPrintSubs(String browser, String site, String resolution) throws InterruptedException {
        driver = BrowserConfig.getDriver(browser);
        ScreenSizeConfig.setScreenSize(driver, resolution);
        driver.get(site);

        Thread.sleep(5000);
        Subscription subscription = new Subscription(driver);
        subscription.clickPrintSubs();
        subscription.printFieldAll();
        subscription.submitFormPrint();
        Thread.sleep(10000);
        subscription.validateDataPrint();
    }

    @AfterTest
    public static void tearDown(){
        driver.quit();
    }
}