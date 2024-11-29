package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import userInfo.FormData;

import java.time.Duration;

public class Subscription {
    public Subscription(WebDriver driver) {
        this.driver = driver;
    }

    private final WebDriver driver;
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
    public static String emailAlert = "//div[contains(@class, 'error-message') and contains(., 'Please provide a valid email address.')]";
    public static String firstNameAlert = "//div[contains(@class, 'error-message') and contains(., 'Please provide your first name.')]";
    public static String lastNameAlert = "//div[contains(@class, 'error-message') and contains(., 'Please provide your last name.')]";
    public static String countryAlert = "//div[contains(@class, 'error-message') and contains(., 'Please select a country to continue.')]";
    public static String checkboxAlert = "//div[contains(@class, 'error-message') and contains(., 'Please indicate which email(s) you would like to receive.')]";

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

    public void clickEmailSubs() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(closeCookie)));

        boolean cookiesElement = !driver.findElements(By.cssSelector(closeCookie)).isEmpty();
        if (cookiesElement) {
            WebElement cookieElement = driver.findElement(By.cssSelector(closeCookie));
            Assert.assertTrue(cookieElement.isDisplayed());
            cookieElement.click();
        }

        WebElement emailButtonElement = driver.findElement(By.cssSelector(emailBtnCss));
        Assert.assertTrue(emailButtonElement.isDisplayed());
        emailButtonElement.click();

        WebElement scroll = driver.findElement(By.id(scrollToEmailId));
        // Firefox-specific scroll to make sure the element is visible
        if (driver instanceof FirefoxDriver) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scroll);
        }
        new Actions(driver).scrollToElement(scroll).perform();
    }

    public void fillEmailField() {
        WebElement emailFieldElement = driver.findElement(By.name(emailName));
        Assert.assertTrue(emailFieldElement.isDisplayed());
        emailFieldElement.sendKeys(formData.email);
    }

    public void fillEmailFieldDummy() {
        WebElement emailFieldElement = driver.findElement(By.name(emailName));
        Assert.assertTrue(emailFieldElement.isDisplayed());
        emailFieldElement.sendKeys(formData.emailLong);
    }

    public void fillInvalidEmailField() {
        WebElement emailFieldElement = driver.findElement(By.name(emailName));
        Assert.assertTrue(emailFieldElement.isDisplayed());
        emailFieldElement.sendKeys(formData.emailInvalid);
    }

    public void fillFirstNameField() {
        WebElement firstNameElement = driver.findElement(By.name(firstName));
        Assert.assertTrue(firstNameElement.isDisplayed());
        firstNameElement.sendKeys(formData.firstName);
    }

    public void fillLongFirstNameField() {
        WebElement firstNameElement = driver.findElement(By.name(firstName));
        Assert.assertTrue(firstNameElement.isDisplayed());
        firstNameElement.sendKeys(formData.firstNameLong);
    }

    public void fillLastNameField() {
        WebElement lastNameElement = driver.findElement(By.name(lastName));
        Assert.assertTrue(lastNameElement.isDisplayed());
        lastNameElement.sendKeys(formData.lastName);
    }

    public void fillLongLastNameField() {
        WebElement lastNameElement = driver.findElement(By.name(lastName));
        Assert.assertTrue(lastNameElement.isDisplayed());
        lastNameElement.sendKeys(formData.lastNameLong);
    }

    public void fillCountryField() {
        WebElement countryFieldElement = driver.findElement(By.cssSelector(dropdownCss));
        Assert.assertTrue(countryFieldElement.isDisplayed());
        countryFieldElement.sendKeys(formData.country);
    }

    public void fillDifferentCountryField() {
        WebElement countryFieldElement = driver.findElement(By.cssSelector(dropdownCss));
        Assert.assertTrue(countryFieldElement.isDisplayed());
        countryFieldElement.sendKeys(formData.differentCountry);
    }

    public void clickCheckBoxField() {
        WebElement checkboxElement = driver.findElement(By.name(checkboxName));
        Assert.assertTrue(checkboxElement.isDisplayed());
        checkboxElement.click();
    }

    public void clickPrintSubs() {
        driver.findElement(By.xpath(closeCookie)).click();
        driver.findElement(By.cssSelector(printBtnCss)).click();
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
        new Actions(driver).scrollByAmount(0, 400).perform();
    }

    public void submitFormPrint() {
        driver.findElement(By.xpath(submitBtnXpathP)).click();
    }

    public void submitFormEmail() {
        WebElement submitButtonElement = driver.findElement(By.cssSelector(submitBtnCss));
        Assert.assertTrue(submitButtonElement.isDisplayed());
        submitButtonElement.click();
    }

    public void validateSuccessMessagePrinted(String expectedResultPrint) {
        String actualStatus = driver.findElement(By.cssSelector(successStatusP)).getText();
    }

    public void validateSuccessMessageEmail(String expectedResultEmail) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(successStatusClass)));

        WebElement successMessageElement = driver.findElement(By.cssSelector(successStatusClass));
        Assert.assertTrue(successMessageElement.isDisplayed());
        String actualStatus = successMessageElement.getText();
        Assert.assertEquals(actualStatus, expectedResultEmail, "Response result doesn't match, test failed!");
    }

    public void isEmailAlertPresent() {
        WebElement emailAlertElement = driver.findElement(By.xpath(emailAlert));
        Assert.assertTrue(emailAlertElement.isDisplayed());
    }

    public void isFirstNameAlertPresent() {
        WebElement firstNameAlertElement = driver.findElement(By.xpath(firstNameAlert));
        Assert.assertTrue(firstNameAlertElement.isDisplayed());
    }

    public void isLastNameAlertPresent() {
        WebElement lastNameAlertElement = driver.findElement(By.xpath(lastNameAlert));
        Assert.assertTrue(lastNameAlertElement.isDisplayed());
    }

    public void isCountryAlertPresent() {
        WebElement countryAlertElement = driver.findElement(By.xpath(countryAlert));
        Assert.assertTrue(countryAlertElement.isDisplayed());
    }

    public void isCheckboxAlertPresent() {
        WebElement checkboxAlertElement = driver.findElement(By.xpath(checkboxAlert));
        Assert.assertTrue(checkboxAlertElement.isDisplayed());
    }
}
