package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.Subscription;

public class SeleniumTest {
    public static WebDriver driver;

    static ExtentReports report;
    public static ExtentTest test;
    static ExtentReports extent = new ExtentReports();

    @BeforeSuite
    public static void Setup(){
        driver = BrowserConfig.getDriver("chrome");
        ExtentSparkReporter spark = new ExtentSparkReporter("target/testng.html");
        extent.attachReporter(spark);

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
        test = extent.createTest("Email Subs","This test fill out the Email subscription and verify the data");
        driver.get("https://odb.org/subscription/jp/");
        Subscription subscription = new Subscription(driver);
        subscription.clickEmailSubs();
        subscription.emailFieldAll();
        subscription.submitFormEmail();
        Thread.sleep(10000);
        subscription.validateDataEmail();
    }

    @Test
    void testPrintSubs() throws InterruptedException {
        ScreenSizeConfig.setScreenSize(driver, "mobile");
        test = extent.createTest("Printed Subs","This test fill out the Print subscription and verify the data");
        driver.get("https://odb.org/subscription/jp/");
        //TODO: Translate the form
        Subscription subscription = new Subscription(driver);
        subscription.clickPrintSubs();
        subscription.printFieldAll();
        subscription.submitFormPrint();
        Thread.sleep(10000);
        subscription.validateDataPrint();
    }

    @AfterTest
    public static void CloseBrowser(){
        driver.quit();
    }

    @AfterSuite
    public static void CleanUp(){
        extent.flush();
    }
}
