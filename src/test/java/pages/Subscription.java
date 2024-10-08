package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import utils.FormData;

import java.time.Duration;

public class Subscription {
    private final WebDriver driver;

    public Subscription(WebDriver _driver){
        driver = _driver;
    }
    private static final FormData formData = new FormData();

    public static String emailBtnXpath = "//*[@id=\"row-subscription-items\"]/ul/li[2]";
    public static String scrollToEmailId = "subscription-email-signup";
    public static String printBtnXpath = "//*[@id=\"row-subscription-items\"]/ul/li[1]";
    public static String emailName = "email";
    public static String firstName = "First Name";
    public static String lastName = "Last Name";
    public static String dropdownXpath = "/html[1]/body[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[1]/form[1]/div[1]/div[3]/select[1]";
    public static String checkboxName = "list[]";
    public static String submitBtnXpath = "//*[@id=\"subscribe-form-email\"]/div/button[2]";
    public static String successStatus = "/html/body/div[2]/div[2]/div[3]/div[2]/div/div/form/div";


    public void clickEmailSubs(){
        WebElement email = driver.findElement(By.xpath(emailBtnXpath));
        WebElement scroll = driver.findElement(By.id(scrollToEmailId));
        //TODO: fix scroll by element later
//        new Actions(driver).scrollByAmount(0,511).perform();
        email.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        new Actions(driver).scrollToElement(scroll).perform();
    }

    public void clickPrintSubs(){
        this.driver.findElement(By.xpath(printBtnXpath)).click();
    }

    public void printFieldAll(){

    }

    public void emailFieldAll(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.name(emailName)).sendKeys(formData.email);
        driver.findElement(By.name(firstName)).sendKeys(formData.firstName);
        driver.findElement(By.name(lastName)).sendKeys(formData.lastName);
        driver.findElement(By.xpath(dropdownXpath)).sendKeys(formData.country);
        driver.findElement(By.name(checkboxName)).click();
    }

    public void clearPrintField(){

    }

    public void clearEmailField(){

    }

    public void submitForm(){
        driver.findElement(By.xpath(submitBtnXpath)).click();
    }

    public void validateData(){
        String expectedStatus = "Succeed!";
        String actualStatus = driver.findElement(By.xpath(successStatus)).getText();
        Assert.assertEquals(actualStatus, expectedStatus);
    }
}
