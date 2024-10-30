package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.Subscription;
import utils.SendEmailReport;
import utils.TestDataProvider;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailSubscriptionTest {
    //report
    static ExtentReports extent = new ExtentReports();
    static ExtentSparkReporter sparkReporter;

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    private final Map<String, ExtentTest> siteMap = new HashMap<>();
    private final Map<String, ExtentTest> browserMap = new HashMap<>();
    private final Map<String, ExtentTest> resolutionMap = new HashMap<>();
    private List<ReportData> scenarioTest = new ArrayList<>();
    //TODO: Make the scenario test to Thread Local Safe

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        System.out.println("Setting up the report");
        extent = new ExtentReports();
        sparkReporter = new ExtentSparkReporter("report/Spark.html");
        extent.attachReporter(sparkReporter);
    }

    private ExtentTest createTestNodes(String site, String browser, String resolution, String scenario) {
        ExtentTest test = extent.createTest(site)
                .createNode("Browser: " + browser)
                .createNode("Resolution: " + resolution)
                .createNode(scenario);
        ReportData reportData = new ReportData(test, browser, site, resolution, scenario);
        scenarioTest.add(reportData);
        return test;
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubs01(String browser, String site, String resolution, String expectedResultEmail) {
        ExtentTest test = createTestNodes(site, browser, resolution, "Scenario Test: Successful subscription with valid input");

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());
        subscription.clickEmailSubs();

        subscription.fillEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillCountryField();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();
        subscription.validateDataEmail(expectedResultEmail);
        boolean success = subscription.validateDataEmail(expectedResultEmail);

        if (success) {
            test.pass("Subscription with valid email was successful.");
        } else {
            test.fail("Subscription with valid email failed.");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs02(String browser, String site, String resolution, String expectedResultEmail) throws InterruptedException {
        ExtentTest test = createTestNodes(site, browser, resolution, "Scenario Test: Subscription with optional fields left blank (without first name and last name)");

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());
        subscription.clickEmailSubs();

        subscription.fillEmailField();
        subscription.fillCountryField();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();

        boolean alertFNameDisplayed = subscription.isFirstNameAlertPresent();
        boolean alertLNameDisplayed = subscription.isLastnameAlertPresent();

        if (alertFNameDisplayed && alertLNameDisplayed) {
            test.pass("Test Succeed, form can't be submitted because first and last name are blank");
        } else {
            test.fail("Test failed because of the alert not presented");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubs03(String browser, String site, String resolution, String expectedResultEmail) {
        ExtentTest test = createTestNodes(site, browser, resolution, "Scenario Test: Subscription with a different country selection");

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());
        subscription.clickEmailSubs();

        subscription.fillEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillCountryField2();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();
        subscription.validateDataEmail(expectedResultEmail);

        boolean success = subscription.validateDataEmail(expectedResultEmail);

        if (success) {
            test.pass("Subscription with a different country selection was successful.");
        } else {
            test.fail("Subscription with a different country selection failed.");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs04(String browser, String site, String resolution, String expectedResultEmail) {
        ExtentTest test = createTestNodes(site, browser, resolution, "Scenario Test: Subscription with an invalid email format");

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());
        subscription.clickEmailSubs();

        subscription.fillInvalidEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillCountryField2();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();

        boolean isAlertDisplayed = subscription.isEmailAlertPresent();

        if (isAlertDisplayed) {
            test.pass("Test Succeed, form can't be submitted because email are invalid");
        } else {
            test.fail("Test failed because of the alert not presented");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs05(String browser, String site, String resolution, String expectedResultEmail) {
        ExtentTest test = createTestNodes(site, browser, resolution, "Scenario Test: Subscription without checking the agreement checkbox");

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());
        subscription.clickEmailSubs();

        subscription.fillEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillCountryField();

        subscription.submitFormEmail();

        boolean isCheckboxAlertDisplayed = subscription.isCheckboxAlertPresent();

        if (isCheckboxAlertDisplayed) {
            test.pass("Test Succeed, subscription without checking the agreement checkbox can't be submitted.");
        } else {
            test.fail("Test failed because of the alert not presented");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs06(String browser, String site, String resolution, String expectedResultEmail) {
        ExtentTest test = createTestNodes(site, browser, resolution, "Scenario Test: Submission with blank email");

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());
        subscription.clickEmailSubs();

        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillCountryField();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();

        boolean isAlertDisplayed = subscription.isEmailAlertPresent();

        if (isAlertDisplayed) {
            test.pass("Test Succeed, form can't be submitted because email field are blank.");
        } else {
            test.fail("Test failed because of the alert not presented.");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs07(String browser, String site, String resolution, String expectedResultEmail) {
        ExtentTest test = createTestNodes(site, browser, resolution, "Scenario Test: Submission without country selection");

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());
        subscription.clickEmailSubs();

        subscription.fillEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();

        boolean isAlertDisplayed = subscription.isCountryAlertPresent();

        if (isAlertDisplayed) {
            test.pass("Test Succeed, form can't be submitted without fill the country field.");
        } else {
            test.fail("Test failed because of the alert not presented.");
        }
    }

    //Attempt to submit without filling in any fields
    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs08(String browser, String site, String resolution, String expectedResultEmail) {
        ExtentTest test = createTestNodes(site, browser, resolution, "Scenario Test: Attempt to submit without filling in any fields");

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

        if (alertEmailDisplayed && alertFNameDisplayed && alertLNameDisplayed && alertCheckboxDisplayed && alertCountryDisplayed) {
            test.pass("Test Succeed, can't submit blank form.");
        } else {
            test.fail("Test failed because of the alert not presented.");
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubs09(String browser, String site, String resolution, String expectedResultEmail) {
        ExtentTest test = createTestNodes(site, browser, resolution, "Scenario Test: Subscription with extra long information");

        driver.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(driver.get(), resolution);
        driver.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(driver.get());
        subscription.clickEmailSubs();

        subscription.fillEmailFieldDummy();
        subscription.fillLongFirstNameField();
        subscription.fillLongLastNameField();
        subscription.fillCountryField();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();
        subscription.validateDataEmail(expectedResultEmail);

        boolean success = subscription.validateDataEmail(expectedResultEmail);
        if (success) {
            test.pass("Subscription with extra long information was successful.");
        } else {
            test.fail("Subscription with extra long information failed.");
        }
    }

    //browser not closing after test
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Tear down method has been executed");
        if (driver != null) {
            driver.get().close();
            driver.remove();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void endTest() throws MessagingException, IOException {
        System.out.println("Email sending report has been executed");
        extent.flush();
        String reportPath = "report/Spark.html";
        SendEmailReport.sendReport(reportPath, scenarioTest);
    }
}