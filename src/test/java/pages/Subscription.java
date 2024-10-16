package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;
import userInfo.FormData;

import java.time.Duration;

public class Subscription {
    private final WebDriver driver;

    SoftAssert softAssert = new SoftAssert();

    public Subscription(WebDriver _driver){
        driver = _driver;
    }

    private static final FormData formData = new FormData();
    //TODO: Create separate locator file

    //email subscription form locator
//    public static String emailBtnXpath = "//*[@id=\"row-subscription-items\"]/ul/li[2]";
    public static String emailBtnCss = "li.email.subscription-tabs";
    public static String closeCookie = "//*[@id=\"moove_gdpr_cookie_info_bar\"]/div/div/div[2]/button[3]/i";
    public static String scrollToEmailId = "subscription-email-signup";
    public static String emailName = "email";
    public static String firstName = "First Name";
    public static String lastName = "Last Name";
    public static String dropdownXpath = "/html[1]/body[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[1]/form[1]/div[1]/div[3]/select[1]";
    public static String checkboxName = "list[]";
    public static String submitBtnXpath = "//*[@id=\"subscribe-form-email\"]/div/button[2]";
    public static String successStatus = "/html/body/div[2]/div[2]/div[3]/div[2]/div/div/form/div";
    public static String successStatusClass = "";
    //<form method="post" class="form-validate" action="https://traditional-odb.org/wp-admin/admin-post.php" id="subscribe-form-email"><div class="response text-center alert alert-success">成功！你將於24-48小時內收到確認郵件。</div></form>
    //<div class="wpcf7-response-output alert" style="display: block;">Thank you for your message. It has been sent.</div>

    //printed subscription form locator
    public static String printBtnCss = "li.print.subscription-tabs";
    public static String fullName = "Name";
    public static String reserveName = "Reserve1";
    public static String emailNameP = "Email";
    public static String countryName = "Country";
    public static String postcodeName = "Postcode";
    public static String prefectureName = "Prefecture";
    public static String districtName = "CityWardDistrictVillage";
    public static String streetName = "Street";
    public static String townName = "Town";
    public static String blockNumberName = "BlockNumber";
    public static String buildingName = "CareofBuildingName";
    public static String genderXpath = "//input[@type='checkbox' and @value='男性']";
    public static String homePhoneName = "HomePhone";
    public static String officePhoneName = "OfficePhone";
    public static String mobilePhoneName = "MobilePhone";
    public static String churchName = "Church";
    public static String occupationName = "Occupation";
    public static String dateName = "Day";
    public static String monthName = "Month";
    public static String yearName = "Year";
    public static String messageName = "Message";
    public static String submitBtnXpathP = "/html/body/div[2]/div[2]/div[3]/div[1]/div/div/form/div[20]/div/input";
    public static String successStatusP = "//div[@class='wpcf7-response-output alert']";


    public void clickEmailSubs(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        if (!driver.findElements(By.xpath(closeCookie)).isEmpty()){
            driver.findElement(By.xpath(closeCookie)).click();
        }

        driver.findElement(By.cssSelector(emailBtnCss)).click();
        WebElement scroll = driver.findElement(By.id(scrollToEmailId));
        // Firefox-specific scroll to make sure the element is visible
        if (driver instanceof FirefoxDriver) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scroll);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        new Actions(driver).scrollToElement(scroll).perform();
    }

    public void clickPrintSubs(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.xpath(closeCookie)).click();
        driver.findElement(By.cssSelector(printBtnCss)).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public void printFieldAll() throws InterruptedException {
        driver.findElement(By.name(fullName)).sendKeys(formData.firstName + " " + formData.lastName);
        driver.findElement(By.name(reserveName)).sendKeys(formData.firstName + " " + formData.lastName);
        driver.findElement(By.name(emailNameP)).sendKeys(formData.email);
        driver.findElement(By.name(countryName)).sendKeys(formData.country);
        driver.findElement(By.name(postcodeName)).sendKeys(formData.postCode);
        driver.findElement(By.name(prefectureName)).sendKeys(formData.prefecture);
        driver.findElement(By.name(districtName)).sendKeys(formData.district);
        driver.findElement(By.name(streetName)).sendKeys(formData.street);
        driver.findElement(By.name(townName)).sendKeys(formData.town);
        driver.findElement(By.name(blockNumberName)).sendKeys(formData.blockNumber);
        driver.findElement(By.name(buildingName)).sendKeys(formData.buildingName);
        WebElement scroll = driver.findElement(By.name(buildingName));

        // Firefox-specific scroll to make sure the element is visible
        if (driver instanceof FirefoxDriver) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scroll);
        }

        new Actions(driver).scrollToElement(scroll).perform();
        Thread.sleep(1000);
        driver.findElement(By.xpath(genderXpath)).click();
        driver.findElement(By.name(homePhoneName)).sendKeys(formData.homePhone);
        driver.findElement(By.name(officePhoneName)).sendKeys(formData.officePhone);
        driver.findElement(By.name(mobilePhoneName)).sendKeys(formData.mobilePhone);
        driver.findElement(By.name(churchName)).sendKeys(formData.church);
        driver.findElement(By.name(occupationName)).sendKeys(formData.occupation);
        driver.findElement(By.name(dateName)).sendKeys(formData.date);
        driver.findElement(By.name(monthName)).sendKeys(formData.month);
        driver.findElement(By.name(yearName)).sendKeys(formData.year);
        driver.findElement(By.name(messageName)).sendKeys(formData.message);
        new Actions(driver).scrollByAmount(0,400).perform();
    }

    public void emailFieldAll(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.name(emailName)).sendKeys(formData.email);
        driver.findElement(By.name(firstName)).sendKeys(formData.firstName);
        driver.findElement(By.name(lastName)).sendKeys(formData.lastName);
        driver.findElement(By.xpath(dropdownXpath)).sendKeys(formData.country);
        driver.findElement(By.name(checkboxName)).click();
    }

    public void submitFormPrint(){
        driver.findElement(By.xpath(submitBtnXpathP)).click();
    }

    public void submitFormEmail(){
        driver.findElement(By.xpath(submitBtnXpath)).click();
    }

    public void validateDataPrint(){
        String expectedStatus = "There was an error trying to send your message. Please try again later."; //change the success message
        String actualStatus = driver.findElement(By.xpath(successStatusP)).getText();
        try {
            softAssert.assertEquals(actualStatus,expectedStatus);
            System.out.println("Test Passed: Print subscription form validated successfully.");
        } catch (AssertionError e){
            System.out.println("Test Failed: Print subscription form validated failed.");
            throw e;
        }
        softAssert.assertAll();
    }

    public void validateDataEmail(){
        String expectedStatus = "Success! You are signed up. Please wait 24 to 48 hours to receive confirmation email.";
        String actualStatus = driver.findElement(By.xpath(successStatus)).getText();
        softAssert.assertEquals(actualStatus,expectedStatus);
        softAssert.assertAll();
    }

    //assert for dummy test
    public String getExpectedString(String site){
        return switch (site) {
            case "https://odb.org/subscription/jp/" -> "Japan | Our Daily Bread";
            case "https://odb.org/subscription/id/" -> "Indonesia | Our Daily Bread";
            case "https://traditional-odb.org/subscription/id/" -> "Indonesia | 靈命日糧繁體中文網站";
            case "https://traditional-odb.org/subscription/my/" -> "Malaysia | 靈命日糧繁體中文網站";
            default -> "Default Title";
        };
    }
}
