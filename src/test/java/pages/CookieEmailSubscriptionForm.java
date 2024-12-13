package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import userInfo.FormData;

import java.time.Duration;

public class CookieEmailSubscriptionForm {
    public CookieEmailSubscriptionForm(WebDriver driver) {
        this.driver = driver;
    }

    private final WebDriver driver;
    private static final FormData formData = new FormData();

    //email subscription form locator
    public static String emailBtnCss = "li.email.subscription-tabs";
    public static String closeCookie = "button.moove-gdpr-infobar-close-btn.gdpr-fbo-3";
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


    public void clickEmailSubs() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
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

    public void submitFormEmail() {
        WebElement submitButtonElement = driver.findElement(By.cssSelector(submitBtnCss));
        Assert.assertTrue(submitButtonElement.isDisplayed());
        submitButtonElement.click();
    }

    public void validateSuccessMessageEmail(String expectedResultEmail) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
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
