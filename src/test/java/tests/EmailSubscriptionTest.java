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

    private final List<ReportData> scenarioTest = new ArrayList<>();
    private final ConcurrentHashMap<String, ExtentTest> siteMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ExtentTest> browserMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ExtentTest> resolutionMap = new ConcurrentHashMap<>();

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() throws IOException {
        extent = new ExtentReports();
        sparkReporter = new ExtentSparkReporter(filePath);
        extent.attachReporter(sparkReporter);

        TestDataProvider testDataProvider = new TestDataProvider();
        Object[][] data = testDataProvider.dataProvider();
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
                resolutionMap.get(resolutionKey);
            } else {
                resolutionTest = browserTest.createNode("Resolution: " + resolution);
                resolutionMap.put(resolutionKey, resolutionTest);
            }
        }
    }

    private void createTestNodes(String site, String browser, String resolution, String scenario) {
        String resolutionKey = site + "_" + browser + "_" + resolution;
        if (resolutionMap.containsKey(resolutionKey)) {
            ExtentTest resolutionTest = resolutionMap.get(resolutionKey);
            ExtentTest test = resolutionTest.createNode("Scenario :" + scenario);
            threadLocalTest.set(test);
        }
        threadLocalTest.get();
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubsWithValidData(String browser, String site, String resolution, String expectedResultEmail) {
        createTestNodes(site, browser, resolution, "Scenario Test: Successful subscription with valid input");

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();

        subscription.fillEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillCountryField();
        subscription.clickCheckBoxField();

        subscription.submitFormEmail();
        subscription.validateSuccessMessageEmail(expectedResultEmail);
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubsWithoutFirstAndLastName(String browser, String site, String resolution, String expectedResultEmail) throws InterruptedException {
        createTestNodes(site, browser, resolution, "Scenario Test: Subscription with optional fields left blank (without first name and last name)");

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();
        subscription.fillEmailField();
        subscription.fillCountryField();
        subscription.clickCheckBoxField();
        subscription.submitFormEmail();
        subscription.isFirstNameAlertPresent();
        subscription.isLastNameAlertPresent();
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubsWithDifferentCountry(String browser, String site, String resolution, String expectedResultEmail) {
        createTestNodes(site, browser, resolution, "Scenario Test: Subscription with a different country selection");

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();
        subscription.fillEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillDifferentCountryField();
        subscription.clickCheckBoxField();
        subscription.submitFormEmail();
        subscription.validateSuccessMessageEmail(expectedResultEmail);
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubsWithInvalidEmail(String browser, String site, String resolution, String expectedResultEmail) {
        createTestNodes(site, browser, resolution, "Scenario Test: Subscription with an invalid email format");

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();
        subscription.fillInvalidEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillDifferentCountryField();
        subscription.clickCheckBoxField();
        subscription.submitFormEmail();
        subscription.isEmailAlertPresent();
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubsWithoutCheckAgreement(String browser, String site, String resolution, String expectedResultEmail) {
        createTestNodes(site, browser, resolution, "Scenario Test: Subscription without checking the agreement checkbox");

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();
        subscription.fillEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillCountryField();
        subscription.submitFormEmail();
        subscription.isCheckboxAlertPresent();
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubsWithBlankEmail(String browser, String site, String resolution, String expectedResultEmail) {
        createTestNodes(site, browser, resolution, "Scenario Test: Submission with blank email");

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.fillCountryField();
        subscription.clickCheckBoxField();
        subscription.submitFormEmail();
        subscription.isEmailAlertPresent();
    }


    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubsWithoutCountrySelection(String browser, String site, String resolution, String expectedResultEmail) {
        createTestNodes(site, browser, resolution, "Scenario Test: Submission without country selection");

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();

        subscription.fillEmailField();
        subscription.fillFirstNameField();
        subscription.fillLastNameField();
        subscription.clickCheckBoxField();
        subscription.submitFormEmail();
        subscription.isCountryAlertPresent();
    }

    //Attempt to submit without filling in any fields
    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubsWithoutFillAnyField(String browser, String site, String resolution, String expectedResultEmail) {
        createTestNodes(site, browser, resolution, "Scenario Test: Attempt to submit without filling in any fields");

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();
        subscription.submitFormEmail();
        subscription.isEmailAlertPresent();
        subscription.isFirstNameAlertPresent();
        subscription.isLastNameAlertPresent();
        subscription.isCountryAlertPresent();
        subscription.isCheckboxAlertPresent();
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubsWithExtraLongData(String browser, String site, String resolution, String expectedResultEmail) {
        createTestNodes(site, browser, resolution, "Scenario Test: Subscription with extra long information");

        threadLocal.set(BrowserConfig.getDriver(browser));
        ScreenSizeConfig.setScreenSize(threadLocal.get(), resolution);
        threadLocal.get().get(site);

        Subscription subscription = new Subscription(threadLocal.get());
        subscription.clickEmailSubs();
        subscription.fillEmailFieldDummy();
        subscription.fillLongFirstNameField();
        subscription.fillLongLastNameField();
        subscription.fillCountryField();
        subscription.clickCheckBoxField();
        subscription.submitFormEmail();
        subscription.validateSuccessMessageEmail(expectedResultEmail);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE){
            threadLocalTest.get().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP){
            threadLocalTest.get().skip(result.getThrowable());
        } else {
            threadLocalTest.get().pass("Test Passed");
        }
        if (threadLocal.get() != null) {
            threadLocal.get().quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void endTest() throws MessagingException {
        extent.flush();
        SendEmailReport.sendReport(filePath, scenarioTest);
    }
}