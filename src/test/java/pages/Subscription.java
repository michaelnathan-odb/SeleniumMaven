package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    //email subscription form locator
    public static String emailBtnCss = "li.email.subscription-tabs";
    public static String closeCookie = "button.moove-gdpr-infobar-close-btn.gdpr-fbo-3";
    public static String scrollToEmailId = "subscription-email-signup";
    public static String emailName = "email";
    public static String firstName = "First Name";
    public static String lastName = "Last Name";
    public static String dropdownCss = "select.form-control.select-required";
    public static String checkboxName = "list[]";
    public static String submitBtnCss = "button.validation-btn";
    public static String successStatusClass = "div.response.text-center.alert.alert-success";
    public static String emailFocus = "//div[contains(@class, 'error-message') and contains(., 'Please provide a valid email address.')]";
    public static String firstNameFocus = "//div[contains(@class, 'error-message') and contains(., 'Please provide your first name.')]";
    public static String lastNameFocus = "//div[contains(@class, 'error-message') and contains(., 'Please provide your last name.')]";
    public static String countryFocus = "//div[contains(@class, 'error-message') and contains(., 'Please select a country to continue.')]";
    public static String checkboxFocus = "//div[contains(@class, 'error-message') and contains(., 'Please indicate which email(s) you would like to receive.')]";

    //<div class="error-message" style="display: block;"><i class="fa fa-exclamation-circle" aria-hidden="true"></i><span class="message"> Please provide a valid email address.</span></div>
    ////*[@id="container-email-3"]/div
    //<div class="error-message" style="display: block;"><i class="fa fa-exclamation-circle" aria-hidden="true"></i><span class="message"> Please provide your first name.</span></div>
    // //*[@id="container-first-name-3"]/div
    //<div class="error-message" style="display: block;"><i class="fa fa-exclamation-circle" aria-hidden="true"></i><span class="message"> Please provide your last name.</span></div>
    //<div class="error-message" style="display: block;"><i class="fa fa-exclamation-circle" aria-hidden="true"></i><span class="message"> Please select a country to continue.</span> </div>
    //<div class="error-message" style="display: block;"><i class="fa fa-exclamation-circle" aria-hidden="true"></i>
    //  		<span class="message"> Please indicate which email(s) you would like to receive. </span>
    //  	</div>

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
    public static String successStatusP = "div.wpcf7-response-output.alert";
    //<div class="wpcf7-response-output alert" style="display: block;">Thank you for your message. It has been sent.</div>

    public void clickEmailSubs(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        if (!(driver instanceof FirefoxDriver) && !driver.findElements(By.cssSelector(closeCookie)).isEmpty()){
            driver.findElement(By.cssSelector(closeCookie)).click();
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

    public void fillEmailField(){
        driver.findElement(By.name(emailName)).sendKeys(formData.email);
    }

    public void fillInvalidEmailField(){
        driver.findElement(By.name(emailName)).sendKeys(formData.emailDummy);
    }

    public void fillFistNameField(){
        driver.findElement(By.name(firstName)).sendKeys(formData.firstName);
    }

    public void fillLastNameField(){
        driver.findElement(By.name(lastName)).sendKeys(formData.lastName);
    }

    public void fillCountryField(){
        driver.findElement(By.cssSelector(dropdownCss)).sendKeys(formData.country);
    }

    public void clickCheckBoxField(){
        driver.findElement(By.name(checkboxName)).click();
    }

    //Successful subscription with valid input
    public void emailFieldFill(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.name(emailName)).sendKeys(formData.email);
        driver.findElement(By.name(firstName)).sendKeys(formData.firstName);
        driver.findElement(By.name(lastName)).sendKeys(formData.lastName);
        driver.findElement(By.cssSelector(dropdownCss)).sendKeys(formData.country);
        driver.findElement(By.name(checkboxName)).click();
    }

    //Subscription with optional fields left blank (e.g., first name and last name)
    public void emailRed01(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.name(emailName)).sendKeys(formData.email);
        driver.findElement(By.cssSelector(dropdownCss)).sendKeys(formData.country);
        driver.findElement(By.name(checkboxName)).click();
    }

    //Subscription with a different country selection
    public void emailRed02(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.name(emailName)).sendKeys(formData.email);
        driver.findElement(By.name(firstName)).sendKeys(formData.firstName);
        driver.findElement(By.name(lastName)).sendKeys(formData.lastName);
        driver.findElement(By.cssSelector(dropdownCss)).sendKeys(formData.country01);
        driver.findElement(By.name(checkboxName)).click();
    }

    //Subscription with an invalid email format
    public void emailRed03(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.name(emailName)).sendKeys(formData.emailInvalid);
        driver.findElement(By.name(firstName)).sendKeys(formData.firstName);
        driver.findElement(By.name(lastName)).sendKeys(formData.lastName);
        driver.findElement(By.cssSelector(dropdownCss)).sendKeys(formData.country);
        driver.findElement(By.name(checkboxName)).click();
    }

    //Subscription without checking the agreement checkbox (groupF)
    public void emailRed04(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.name(emailName)).sendKeys(formData.emailInvalid);
        driver.findElement(By.name(firstName)).sendKeys(formData.firstName);
        driver.findElement(By.name(lastName)).sendKeys(formData.lastName);
        driver.findElement(By.cssSelector(dropdownCss)).sendKeys(formData.country);
    }

    //Submission with blank email
    public void emailRed05(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.name(firstName)).sendKeys(formData.firstName);
        driver.findElement(By.name(lastName)).sendKeys(formData.lastName);
        driver.findElement(By.cssSelector(dropdownCss)).sendKeys(formData.country);
        driver.findElement(By.name(checkboxName)).click();
    }

    //Submission with an invalid country selection
    public void emailRed06(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.name(emailName)).sendKeys(formData.email);
        driver.findElement(By.name(firstName)).sendKeys(formData.firstName);
        driver.findElement(By.name(lastName)).sendKeys(formData.lastName);
        driver.findElement(By.name(checkboxName)).click();
    }

    //Subscription with extra long names or email
    public void emailRed07(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.name(emailName)).sendKeys(formData.emailDummy);
        driver.findElement(By.name(firstName)).sendKeys(formData.firstNameDummy);
        driver.findElement(By.name(lastName)).sendKeys(formData.lastNameDummy);
        driver.findElement(By.cssSelector(dropdownCss)).sendKeys(formData.country);
        driver.findElement(By.name(checkboxName)).click();
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

    public void submitFormPrint(){
        driver.findElement(By.xpath(submitBtnXpathP)).click();
    }

    public void submitFormEmail(){
        driver.findElement(By.cssSelector(submitBtnCss)).click();
    }

    public void validateDataPrint(String expectedResultPrint){
        String actualStatus = driver.findElement(By.cssSelector(successStatusP)).getText();
        softAssert.assertEquals(actualStatus,expectedResultPrint, "Response result doesn't met, test failed!");
        softAssert.assertAll();
    }

    public boolean validateDataEmail(String expectedResultEmail){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(successStatusClass)));
        String actualStatus = driver.findElement(By.cssSelector(successStatusClass)).getText();
        softAssert.assertEquals(actualStatus,expectedResultEmail, "Response result doesn't met, test failed!");
        softAssert.assertAll();
        return true;
    }

    public boolean isEmailAlertPresent() {
        return driver.findElement(By.xpath(emailFocus)).isDisplayed();
    }

    public boolean isFirstNameAlertPresent() {
        try {
            return driver.findElement(By.xpath(firstNameFocus)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isLastnameAlertPresent() {
        try {
            return driver.findElement(By.xpath(lastNameFocus)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isCountryAlertPresent() {
        try {
            return driver.findElement(By.xpath(countryFocus)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isCheckboxAlertPresent() {
        try {
            return driver.findElement(By.xpath(checkboxFocus)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
