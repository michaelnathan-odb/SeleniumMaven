package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.Subscription;
import utils.TestDataProvider;

import java.time.Duration;

public class EmailSubscriptionTest {
    public static WebDriver driver;

    //report
    static ExtentTest test;
    static ExtentReports extent = new ExtentReports();

    // Dummy test
    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class)
    void dummyTest(String browser, String site, String resolution, String expectedResult){
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(spark);
        driver = BrowserConfig.getDriver(browser);
        ScreenSizeConfig.setScreenSize(driver, resolution);
        driver.get(site);

        test = extent.createTest("Dummy Test", "This test are to get the sites title, running on " + browser + ", " + resolution + ", at " + site);

        String siteTitle = driver.getTitle();
        System.out.println("Site Title: " + siteTitle);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        if (siteTitle != null){
            test.log(Status.PASS,"The site title are " + siteTitle);
        } else {
            test.log(Status.FAIL, "The site title doesn't exist");
        }
        extent.flush();
        driver.quit();
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class)
    void testEmailSubs(String browser, String site, String resolution, String expectedResultEmail) throws InterruptedException {
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
        subscription.validateDataEmail(expectedResultEmail);
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