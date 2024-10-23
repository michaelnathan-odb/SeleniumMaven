package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.BrowserConfig;
import config.ScreenSizeConfig;
import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.Subscription;
import utils.TestDataProvider;
import java.util.HashMap;
import java.util.Map;

public class EmailSubscriptionTest {
    // public static WebDriver driver;

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

        scenarioTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site + ", Scenario: " + scenario);
    }

    @BeforeSuite
    public void setupSuite(){
        extent = new ExtentReports();
        sparkReporter = new ExtentSparkReporter("target/Spark01.html");
        extent.attachReporter(sparkReporter);
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubs01(String browser, String site, String resolution, String expectedResultEmail){
        try{
            String scenarioName = "Scenario Test: Successful subscription with valid input";
            createTestNodes(site, browser, resolution, scenarioName);

            driver.set(BrowserConfig.getDriver(browser));
            ScreenSizeConfig.setScreenSize(driver.get(), resolution);
            driver.get().get(site);

            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver.get());
            subscription.clickEmailSubs();
            subscription.emailGreen01();
            subscription.submitFormEmail();
            Thread.sleep(8000);
            subscription.validateDataEmail(expectedResultEmail);

            scenarioTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            scenarioTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                driver.get().quit();
                driver.remove();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs02(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            String scenarioName = "Scenario Test: Subscription with optional fields left blank (without first name and last name)";
            createTestNodes(site, browser, resolution, scenarioName);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver.set(BrowserConfig.getDriver(browser));
            ScreenSizeConfig.setScreenSize(driver.get(), resolution);
            driver.get().get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver.get());

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailGreen02();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            scenarioTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            scenarioTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.get().quit();
                driver.remove();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs03(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            String scenarioName = "Scenario Test: Subscription with a different country selection";
            createTestNodes(site, browser, resolution, scenarioName);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver.set(BrowserConfig.getDriver(browser));
            ScreenSizeConfig.setScreenSize(driver.get(), resolution);
            driver.get().get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver.get());

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailGreen03();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            scenarioTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            scenarioTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.get().quit();
                driver.remove();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs04(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            String scenarioName = "Scenario Test: Subscription with an invalid email format";
            createTestNodes(site, browser, resolution, scenarioName);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
             driver.set(BrowserConfig.getDriver(browser));
            ScreenSizeConfig.setScreenSize(driver.get(), resolution);
            driver.get().get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver.get());

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailRed01();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            scenarioTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            scenarioTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.get().quit();
                driver.remove();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs05(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            String scenarioName = "Scenario Test: Subscription without checking the agreement checkbox";
            createTestNodes(site, browser, resolution, scenarioName);
            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
             driver.set(BrowserConfig.getDriver(browser));
            ScreenSizeConfig.setScreenSize(driver.get(), resolution);
            driver.get().get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver.get());

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailRed02();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            scenarioTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            scenarioTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.get().quit();
                driver.remove();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs06(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            String scenarioName = "Scenario Test: Submission with blank email";
            createTestNodes(site, browser, resolution, scenarioName);
            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
             driver.set(BrowserConfig.getDriver(browser));
            ScreenSizeConfig.setScreenSize(driver.get(), resolution);
            driver.get().get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver.get());

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailRed03();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            scenarioTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            scenarioTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.get().quit();
                driver.remove();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs07(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            String scenarioName = "Scenario Test: Submission without country selection";
            createTestNodes(site, browser, resolution, scenarioName);
            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver.set(BrowserConfig.getDriver(browser));
            ScreenSizeConfig.setScreenSize(driver.get(), resolution);
            driver.get().get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver.get());

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailRed04();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            scenarioTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            scenarioTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.get().quit();
                driver.remove();
            }
        }
    }

    //Attempt to submit without filling in any fields (groupJ)
    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs08(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            String scenarioName = "Scenario Test: Attempt to submit without filling in any fields";
            createTestNodes(site, browser, resolution, scenarioName);
            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
             driver.set(BrowserConfig.getDriver(browser));
            ScreenSizeConfig.setScreenSize(driver.get(), resolution);
            driver.get().get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver.get());

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            scenarioTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            scenarioTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.get().quit();
                driver.remove();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs09(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            String scenarioName = "Scenario Test: Subscription with extra long information";
            createTestNodes(site, browser, resolution, scenarioName);
            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver.set(BrowserConfig.getDriver(browser));
            ScreenSizeConfig.setScreenSize(driver.get(), resolution);
            driver.get().get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver.get());

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();
            subscription.emailRed05();

            //Then: User enter the information (email, first and last name, country)
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            scenarioTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            scenarioTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.get().quit();
                driver.remove();
            }
        }
    }

    @AfterMethod
    public void removeDriver(){
        driver.get().quit();
        driver.remove();
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
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            browserTest = browserTest.createNode("Browser: " + browser + ", Resolution: " + resolution);
            browserTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

             driver.set(BrowserConfig.getDriver(browser));
            ScreenSizeConfig.setScreenSize(driver.get(), resolution);
            driver.get().get(site);

            String siteTitle = driver.getTitle();
            System.out.println("Site Title: " + siteTitle);
            browserTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e){
            browserTest.log(Status.FAIL, "Test Failed");
            e.printStackTrace();
        } finally {
            if (driver != null){
                driver.quit();
            }
        }
    }
*/