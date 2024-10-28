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
import utils.SendEmailReport;
import utils.TestDataProvider;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmailSubscriptionTest {
    //report
    static ExtentReports extent = new ExtentReports();
    static ExtentSparkReporter sparkReporter;

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    private ExtentTest scenarioTest = null;
    private final Map<String, ExtentTest> siteMap = new HashMap<>();
    private final Map<String, ExtentTest> browserMap = new HashMap<>();
    private final Map<String, ExtentTest> resolutionMap = new HashMap<>();

    private void createTestNodes(String site, String browser, String resolution, String scenario){
        ExtentTest siteTest;
        if (siteMap.containsKey(site)) {
            siteTest = siteMap.get(site);
        } else {
            siteTest = extent.createTest(site);
            siteMap.put(site, siteTest); // Store site node in the map
        }

        ExtentTest browserTest;
        String browserKey = site + "_" + browser;
        if(browserMap.containsKey(browserKey)){
            browserTest = browserMap.get(browserKey);
        } else {
            browserTest = siteTest.createNode("Browser: " + browser);
            browserMap.put(browserKey, browserTest);
        }

        ExtentTest resolutionTest;
        String resolutionKey = browserKey + "_" + resolution;
        if(resolutionMap.containsKey(resolutionKey)){
            resolutionTest = resolutionMap.get(resolutionKey);
        } else {
            resolutionTest = browserTest.createNode("Resolution: " + resolution);
            resolutionMap.put(resolutionKey, resolutionTest);
        }
        scenarioTest = resolutionTest.createNode(scenario);
        scenarioTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site + ".");
    }

    @BeforeSuite(alwaysRun = true)
    public void setupSuite(){
        System.out.println("Setting up the report");
        extent = new ExtentReports();
        sparkReporter = new ExtentSparkReporter("report/Spark.html");
        extent.attachReporter(sparkReporter);
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubs01(String browser, String site, String resolution, String expectedResultEmail){
        String scenarioName = "Scenario Test: Successful subscription with valid input";
        createTestNodes(site, browser, resolution, scenarioName);

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());
        subscription.clickEmailSubs();
        subscription.emailFieldFill();
        subscription.submitFormEmail();
        subscription.validateDataEmail(expectedResultEmail);
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs02(String browser, String site, String resolution, String expectedResultEmail) throws InterruptedException {
        String scenarioName = "Scenario Test: Subscription with optional fields left blank (without first name and last name)";
        createTestNodes(site, browser, resolution, scenarioName);

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());

        subscription.clickEmailSubs();
        subscription.emailRed01();
        subscription.submitFormEmail();

        boolean alertFNameDisplayed = subscription.isFirstNameAlertPresent();
        boolean alertLNameDisplayed = subscription.isLastnameAlertPresent();

        if (alertFNameDisplayed && alertLNameDisplayed){
            scenarioTest.log(Status.PASS, "Test Succeed");
        } else {
            scenarioTest.log(Status.FAIL, "Test failed because of the alert not presented");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubs03(String browser, String site, String resolution, String expectedResultEmail){
        String scenarioName = "Scenario Test: Subscription with a different country selection";
        createTestNodes(site, browser, resolution, scenarioName);

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());

        subscription.clickEmailSubs();
        subscription.emailRed02();
        subscription.submitFormEmail();
        subscription.validateDataEmail(expectedResultEmail);
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs04(String browser, String site, String resolution, String expectedResultEmail){
        String scenarioName = "Scenario Test: Subscription with an invalid email format";
        createTestNodes(site, browser, resolution, scenarioName);

         driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());

        subscription.clickEmailSubs();
        subscription.emailRed03();
        subscription.submitFormEmail();

        boolean isAlertDisplayed = subscription.isEmailAlertPresent();

        if (isAlertDisplayed){
            scenarioTest.log(Status.PASS, "Test Succeed");
        } else {
            scenarioTest.log(Status.FAIL, "Test failed because of the alert not presented");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs05(String browser, String site, String resolution, String expectedResultEmail){
        String scenarioName = "Scenario Test: Subscription without checking the agreement checkbox";
        createTestNodes(site, browser, resolution, scenarioName);

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());

        subscription.clickEmailSubs();
        subscription.emailRed04();
        subscription.submitFormEmail();

        boolean isCheckboxAlertDisplayed = subscription.isCheckboxAlertPresent();

        if (isCheckboxAlertDisplayed){
            scenarioTest.log(Status.PASS, "Test Succeed");
        } else {
            scenarioTest.log(Status.FAIL, "Test failed because of the alert not presented");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs06(String browser, String site, String resolution, String expectedResultEmail){
        String scenarioName = "Scenario Test: Submission with blank email";
        createTestNodes(site, browser, resolution, scenarioName);

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());

        subscription.clickEmailSubs();
        subscription.emailRed05();
        subscription.submitFormEmail();

        boolean isAlertDisplayed = subscription.isEmailAlertPresent();

        if (isAlertDisplayed){
            scenarioTest.log(Status.PASS, "Test Succeed");
        } else {
            scenarioTest.log(Status.FAIL, "Test failed because of the alert not presented");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs07(String browser, String site, String resolution, String expectedResultEmail){
        String scenarioName = "Scenario Test: Submission without country selection";
        createTestNodes(site, browser, resolution, scenarioName);

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());

        subscription.clickEmailSubs();

        subscription.emailRed06();
        subscription.submitFormEmail();

        boolean isAlertDisplayed = subscription.isCountryAlertPresent();

        if (isAlertDisplayed){
            scenarioTest.log(Status.PASS, "Test Succeed");
        } else {
            scenarioTest.log(Status.FAIL, "Test failed because of the alert not presented");
        }
    }

    //Attempt to submit without filling in any fields (groupJ)
    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs08(String browser, String site, String resolution, String expectedResultEmail){
        String scenarioName = "Scenario Test: Attempt to submit without filling in any fields";
        createTestNodes(site, browser, resolution, scenarioName);

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());

        subscription.clickEmailSubs();
        subscription.submitFormEmail();

        boolean alertEmailDisplayed = subscription.isEmailAlertPresent();
        boolean alertFNameDisplayed = subscription.isFirstNameAlertPresent();
        boolean alertLNameDisplayed = subscription.isLastnameAlertPresent();
        boolean alertCheckboxDisplayed = subscription.isCheckboxAlertPresent();
        boolean alertCountryDisplayed = subscription.isCountryAlertPresent();

        if (alertEmailDisplayed && alertFNameDisplayed && alertLNameDisplayed && alertCheckboxDisplayed && alertCountryDisplayed){
            scenarioTest.log(Status.PASS, "Test Succeed");
        } else {
            scenarioTest.log(Status.FAIL, "Test failed because of the alert not presented");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubs09(String browser, String site, String resolution, String expectedResultEmail){
        String scenarioName = "Scenario Test: Subscription with extra long information";
        createTestNodes(site, browser, resolution, scenarioName);

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());

        subscription.clickEmailSubs();
        subscription.emailRed07();
        subscription.submitFormEmail();
        subscription.validateDataEmail(expectedResultEmail);
    }

    //browser not closing after test
    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        System.out.println("Tear down method has been executed");
        driver.get().close();
        driver.remove();
    }

    @AfterSuite(alwaysRun = true)
    public void endTest()throws MessagingException, IOException{
        System.out.println("Email sending report has been executed");
        extent.flush();
        String reportPath = "report/Spark.html";
        SendEmailReport.sendReport(reportPath);
    }
}