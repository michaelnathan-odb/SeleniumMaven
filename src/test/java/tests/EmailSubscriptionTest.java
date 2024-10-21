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
        sparkReporter = new ExtentSparkReporter("target/Spark01.html");
        extent.attachReporter(sparkReporter);
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupA")
    void testEmailSubs01(String browser, String site, String resolution, String expectedResultEmail){
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
            subscription.emailGreen01();
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

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupB")
    void testEmailSubs02(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            if (browserTest == null || !browserTest.getModel().getName().equals("Browser: " + browser)){
                browserTest = siteTest.createNode("Browser: " + browser);
            }
            resolutionTest = browserTest.createNode( "Resolution: " + resolution);

            resolutionTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver);

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailGreen02();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            resolutionTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            resolutionTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.quit();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupC")
    void testEmailSubs03(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            if (browserTest == null || !browserTest.getModel().getName().equals("Browser: " + browser)){
                browserTest = siteTest.createNode("Browser: " + browser);
            }
            resolutionTest = browserTest.createNode( "Resolution: " + resolution);

            resolutionTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver);

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailGreen03();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            resolutionTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            resolutionTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.quit();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupE")
    void testEmailSubs05(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            if (browserTest == null || !browserTest.getModel().getName().equals("Browser: " + browser)){
                browserTest = siteTest.createNode("Browser: " + browser);
            }
            resolutionTest = browserTest.createNode( "Resolution: " + resolution);

            resolutionTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver);

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailRed01();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            resolutionTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            resolutionTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.quit();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupF")
    void testEmailSubs06(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            if (browserTest == null || !browserTest.getModel().getName().equals("Browser: " + browser)){
                browserTest = siteTest.createNode("Browser: " + browser);
            }
            resolutionTest = browserTest.createNode( "Resolution: " + resolution);

            resolutionTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver);

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailRed02();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            resolutionTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            resolutionTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.quit();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupG")
    void testEmailSubs07(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            if (browserTest == null || !browserTest.getModel().getName().equals("Browser: " + browser)){
                browserTest = siteTest.createNode("Browser: " + browser);
            }
            resolutionTest = browserTest.createNode( "Resolution: " + resolution);

            resolutionTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver);

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailRed03();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            resolutionTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            resolutionTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.quit();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupI")
    void testEmailSubs08(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            if (browserTest == null || !browserTest.getModel().getName().equals("Browser: " + browser)){
                browserTest = siteTest.createNode("Browser: " + browser);
            }
            resolutionTest = browserTest.createNode( "Resolution: " + resolution);

            resolutionTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver);

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.emailRed04();
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            resolutionTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            resolutionTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.quit();
            }
        }
    }

    //Attempt to submit without filling in any fields (groupJ)
    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupJ")
    void testEmailSubs09(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            if (browserTest == null || !browserTest.getModel().getName().equals("Browser: " + browser)){
                browserTest = siteTest.createNode("Browser: " + browser);
            }
            resolutionTest = browserTest.createNode( "Resolution: " + resolution);

            resolutionTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver);

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();

            //Then: User enter the information (email, first and last name, country)
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            resolutionTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            resolutionTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
                driver.quit();
            }
        }
    }

    @Test(dataProvider = "provider", dataProviderClass = TestDataProvider.class, groups = "groupK")
    void testEmailSubs10(String browser, String site, String resolution, String expectedResultEmail){
        //scenario: User want to subscribe daily devotional by email
        try{
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            if (browserTest == null || !browserTest.getModel().getName().equals("Browser: " + browser)){
                browserTest = siteTest.createNode("Browser: " + browser);
            }
            resolutionTest = browserTest.createNode( "Resolution: " + resolution);

            resolutionTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

            //Given: Setting up the site, browser and screen resolution, navigated to subscription form
            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);

            //Then: User is at the subscription page
            System.out.println("Tests run in: " + browser + ", " + site + ", " + resolution);
            Thread.sleep(3000);
            Subscription subscription = new Subscription(driver);

            //When: User click the email subscription form
            //And: User close the site's cookies
            subscription.clickEmailSubs();
            subscription.emailRed05();

            //Then: User enter the information (email, first and last name, country)
            subscription.submitFormEmail();
            Thread.sleep(8000);

            //Then: Success validation message is showing
            subscription.validateDataEmail(expectedResultEmail);
            resolutionTest.log(Status.PASS, "Test Succeed");
        } catch (Exception e) {
            resolutionTest.log(Status.FAIL, "Test failed because of " + e);
            e.printStackTrace();
        } finally {
            if (driver != null){
                //close the browser
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
            if (siteTest == null || !siteTest.getModel().getName().equals(site)){
                siteTest = extent.createTest(site);
            }
            browserTest = browserTest.createNode("Browser: " + browser + ", Resolution: " + resolution);
            browserTest.log(Status.INFO, "Browser: " + browser + ", Resolution: " + resolution + ", Site: " + site);

            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);

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