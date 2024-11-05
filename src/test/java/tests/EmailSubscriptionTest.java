package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.Subscription;
import utils.SendEmailReport;
import utils.TestDataProvider;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public class EmailSubscriptionTest {
    //report
    static ExtentReports extent = new ExtentReports();
    static ExtentSparkReporter sparkReporter;

    private static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();

    private List<ReportData> scenarioTest = new ArrayList<>();

    //TODO: Make the scenario test to Thread Local Safe

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        System.out.println("Setting up the report");
        extent = new ExtentReports();
        sparkReporter = new ExtentSparkReporter("report/EmailSubsReporter.html");
        extent.attachReporter(sparkReporter);

        //TODO: Create test node function add here
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

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();

        subscription.fillEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillCountryField();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();
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

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(threadLocal.get());
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

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();

        subscription.fillEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillCountryField2();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();

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

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(threadLocal.get());
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

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(threadLocal.get());
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

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(threadLocal.get());
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

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(threadLocal.get());
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
    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupC")
    void testEmailSubs08(String browser, String site, String resolution, String expectedResultEmail) {
        ExtentTest test = createTestNodes(site, browser, resolution, "Scenario Test: Attempt to submit without filling in any fields");

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(threadLocal.get());

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

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();

        subscription.fillEmailFieldDummy();
        subscription.fillLongFirstNameField();
        subscription.fillLongLastNameField();
        subscription.fillCountryField();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();

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
        if (threadLocal.get() != null) {
            threadLocal.get().quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void endTest() throws MessagingException {
        System.out.println("Email sending report has been executed");
        extent.flush();
        String reportPath = "report/Spark.html";
        SendEmailReport.sendReport(reportPath, scenarioTest);
    }
}