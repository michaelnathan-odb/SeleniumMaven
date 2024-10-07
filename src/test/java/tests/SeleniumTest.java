    package tests;

    import com.aventstack.extentreports.ExtentReports;
    import com.aventstack.extentreports.ExtentTest;
    import com.aventstack.extentreports.reporter.ExtentSparkReporter;
    import config.BrowserConfig;
    import config.ScreenSizeConfig;
    import org.openqa.selenium.WebDriver;
    import org.testng.annotations.*;
    import pages.Subscription;
    import java.time.Duration;

    public class SeleniumTest {
        public static WebDriver driver;

        public static ExtentTest test;
        static ExtentReports extent = new ExtentReports();

        // DataProvider to provide browser, site, and resolution combinations
        @DataProvider(name = "browserSiteResolutionData")
        public Object[][] dataProvider() {
            return new Object[][]{
                    {"chrome", "https://odb.org/subscription/jp/", "mobile"},
                    {"chrome", "https://odb.org/subscription/jp/", "tablet"},
                    {"chrome", "https://odb.org/subscription/jp/", "desktop"},
                    {"edge", "https://odb.org/subscription/jp/", "mobile"},
                    {"edge", "https://odb.org/subscription/jp/", "tablet"},
                    {"edge", "https://odb.org/subscription/jp/", "desktop"}
            };
        }

        @BeforeMethod
        public static void Setup(String browser,String site,String resolution){
            driver = BrowserConfig.getDriver(browser);
            ScreenSizeConfig.setScreenSize(driver, resolution);
            driver.get(site);
    //        ExtentSparkReporter spark = new ExtentSparkReporter("target/testng.html");
    //        extent.attachReporter(spark);
        }

        @Test(dataProvider = "browserSiteResolutionData")
        void dummyTest(String browser, String site, String resolution){
            String siteTitle = driver.getTitle();
            System.out.println("Site Title :" + siteTitle);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.quit();
        }

//        @Test
//        void testEmailSubs() throws InterruptedException {
//            Thread.sleep(5000);
//            test = extent.createTest("Email Subs","This test fill out the Email subscription and verify the data");
//            Subscription subscription = new Subscription(driver);
//            subscription.clickEmailSubs();
//            subscription.emailFieldAll();
//            subscription.submitFormEmail();
//            Thread.sleep(10000);
//            subscription.validateDataEmail();
//        }
//
//        @Test
//        void testPrintSubs() throws InterruptedException {
//            Thread.sleep(5000);
//            test = extent.createTest("Printed Subs","This test fill out the Print subscription and verify the data");
//            //TODO: Translate the form
//            Subscription subscription = new Subscription(driver);
//            subscription.clickPrintSubs();
//            subscription.printFieldAll();
//            subscription.submitFormPrint();
//            Thread.sleep(10000);
//            subscription.validateDataPrint();
//        }

        @AfterMethod
        public static void tearDown(){
            driver.quit();
        }

        @AfterSuite
        public static void CleanUp(){
            extent.flush();
        }
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
