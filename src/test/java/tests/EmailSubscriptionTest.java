package tests;

import config.BrowserConfig;
import config.ScreenSizeConfig;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.Subscription;

public class EmailSubscriptionTest {
    public static WebDriver driver;

    // DataProvider to provide browser, site, and resolution combinations
    @DataProvider(name = "provider")
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

    /* Dummy test
    @Test(dataProvider = "provider")
    void dummyTest(String browser, String site, String resolution){
        driver = BrowserConfig.getDriver(browser);
        ScreenSizeConfig.setScreenSize(driver, resolution);
        driver.get(site);

        String siteTitle = driver.getTitle();
        System.out.println("Site Title :" + siteTitle);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.quit();
    }
    */

    @Test(dataProvider = "provider")
    void testEmailSubs(String browser, String site, String resolution) throws InterruptedException {
        driver = BrowserConfig.getDriver(browser);
        ScreenSizeConfig.setScreenSize(driver, resolution);
        driver.get(site);

        Thread.sleep(3000);
        Subscription subscription = new Subscription(driver);
        subscription.clickEmailSubs();
        subscription.emailFieldAll();
        subscription.submitFormEmail();
        Thread.sleep(5000);
        subscription.validateDataEmail();
    }

    @AfterMethod
    public static void tearDown(){
        driver.quit();
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
