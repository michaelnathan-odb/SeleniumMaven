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
import utils.TestDataProvider;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EmailSubscriptionTest {
    private final String filePath = "report/EmailSubsReporter.html";
    //report
    static ExtentReports extent = new ExtentReports();
    static ExtentSparkReporter sparkReporter;

    private static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();
    private static final ThreadLocal<ExtentTest> threadLocalTest = new ThreadLocal<ExtentTest>();

    //TODO: Make the scenario test to Thread Local Safe
    private final List<ReportData> scenarioTest = new ArrayList<>();
    private final ConcurrentHashMap<String, ExtentTest> siteMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ExtentTest> browserMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ExtentTest> resolutionMap = new ConcurrentHashMap<>();

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() throws IOException {
        System.out.println("Setting up the report");
        extent = new ExtentReports();
        sparkReporter = new ExtentSparkReporter(filePath);
        extent.attachReporter(sparkReporter);

        TestDataProvider hahaha = new TestDataProvider();
        Object[][] data = hahaha.dataProvider();
        for (Object[] object : data) {
            String site = (String) object[1];
            String browser = (String) object[0];
            String resolution = (String) object[2];

            ExtentTest siteTest;
            if (siteMap.containsKey(site)) {
                siteTest = siteMap.get(site);
            } else {
                siteTest = extent.createTest(site);
                siteMap.put(site, siteTest);
            }

            ExtentTest browserTest;
            String browserKey = site + "_" + browser;
            if (browserMap.containsKey(browserKey)) {
                browserTest = browserMap.get(browserKey);
            } else {
                browserTest = siteTest.createNode("Browser: " + browser);
                browserMap.put(browserKey, browserTest);
            }

            ExtentTest resolutionTest;
            String resolutionKey = browserKey + "_" + resolution;
            if (resolutionMap.containsKey(resolutionKey)) {
                resolutionTest = resolutionMap.get(resolutionKey);
            } else {
                resolutionTest = browserTest.createNode("Resolution: " + resolution);
                resolutionMap.put(resolutionKey, resolutionTest);
            }
        }

        //TODO: Create test node function (hierarchy logic here)

//        test.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site + ", Scenario: " + scenario);
    }

    private ExtentTest createTestNodes(String site, String browser, String resolution, String scenario) {
        //TODO: check from resolution map if the key exists
        String resolutionKey = site + "_" + browser + "_" + resolution;
        if (resolutionMap.containsKey(resolutionKey)) {
            //TODO: if key exist get the ExtentTest from the resolutionMap
            ExtentTest resolutionTest = resolutionMap.get(resolutionKey);
            //TODO: create the node from scenario
            ExtentTest test = resolutionTest.createNode("Scenario :" + scenario);
            //TODO: set to local thread the ExtentTest from scenario
            threadLocalTest.set(test);
        }
        //TODO: return the local thread using get()
        return threadLocalTest.get();
        //Pass the test data to send email reporter class
//        ReportData reportData = new ReportData(test, browser, site, resolution, scenario);
//        scenarioTest.add(reportData);
//        threadLocalTest.set(test);
//        return test;
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
        System.out.println("This is from testSubs04: " + test.getModel());
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
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE)
            threadLocalTest.get().fail(result.getThrowable());
        else if (result.getStatus() == ITestResult.SKIP)
            threadLocalTest.get().skip(result.getThrowable());
        else
            threadLocalTest.get().pass(String.valueOf(result.isSuccess()));
        System.out.println("Tear down method has been executed");
        if (threadLocal.get() != null) {
            threadLocal.get().quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void endTest() throws MessagingException {
        System.out.println("Email sending report has been executed");
        extent.flush();
//        SendEmailReport.sendReport(filePath, scenarioTest);
    }
}