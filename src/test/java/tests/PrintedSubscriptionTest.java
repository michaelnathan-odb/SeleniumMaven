package tests;

import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.Subscription;
import utils.TestDataProvider;

public class PrintedSubscriptionTest {
    public static WebDriver driver;

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class)
    void testPrintSubs(String browser, String site, String resolution, String expectedResultPrint) throws InterruptedException {
        driver = BrowserConfig.getDriver(browser);
        ScreenSizeConfig.setScreenSize(driver, resolution);
        driver.get(site);
        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);

        Thread.sleep(3000);
        Subscription subscription = new Subscription(driver);
        subscription.clickPrintSubs();
        subscription.printFieldAll();
        subscription.submitFormPrint();
        Thread.sleep(10000);
        subscription.validateSuccessMessagePrinted(expectedResultPrint);
    }

    @AfterMethod
    public static void tearDown(){
        driver.quit();
    }
}