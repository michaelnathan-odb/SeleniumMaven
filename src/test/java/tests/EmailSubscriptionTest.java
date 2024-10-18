package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.Subscription;
import utils.TestDataProvider;

public class EmailSubscriptionTest {
    public static WebDriver driver;

    //report
    static ExtentReports extent = new ExtentReports();
    static ExtentSparkReporter sparkReporter;
    static ExtentTest siteTest;
    static ExtentTest browserTest;
    static ExtentTest resolutionTest;

    @BeforeSuite
    public void setupSuite(){
        extent = new ExtentReports();
        sparkReporter = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(sparkReporter);
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class)
    void testEmailSubs(String browser, String site, String resolution, String expectedResultEmail){
        try{
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            if (browserTest == null || !browserTest.getModel().getName().equals("Browser: " + browser)){
                browserTest = siteTest.createNode("Browser: " + browser);
            }
            resolutionTest = browserTest.createNode( "Resolution: " + resolution);

            resolutionTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

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

            resolutionTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            resolutionTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                driver.quit();
            }
        }
    }

    @AfterSuite
    public void tearDownSuite(){
        extent.flush();
    }
}

/*
    // Dummy test
    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class)
    void dummyTest(String browser, String site, String resolution, String expectedResult){
        try{
            if (parentTest == null || !parentTest.getModel().getName().equals(site)){
                parentTest = extent.createTest(site);
            }
            childTest = parentTest.createNode("Browser: " + browser + ", Resolution: " + resolution);
            childTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);

            String siteTitle = driver.getTitle();
            System.out.println("Site Title: " + siteTitle);
            childTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e){
            childTest.log(Status.FAIL, "Test Failed");
            e.printStackTrace();
        } finally {
            if (driver != null){
            driver.quit();
            }
        }
    }
    */