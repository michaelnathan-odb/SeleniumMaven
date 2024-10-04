package pages;

import config.BrowserConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.FormData;

public class Subscription {
    public static WebDriver driver;
    private static final FormData formData = new FormData();

    public static String emailBtnXpath = "//*[@id=\"row-subscription-items\"]/ul/li[2]";
    public static String printBtnXpath = "//*[@id=\"row-subscription-items\"]/ul/li[1]";
    public static String emailId = "email";
    public static String firstNameId = "First Name";
    public static String lastNameId = "Last Name";
    public static String dropdownXpath = "/html[1]/body[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[1]/form[1]/div[1]/div[3]/select[1]";
    public static String checkboxName = "list[]";
    public static String submitBtnXpath = "//*[@id=\"subscribe-form-email\"]/div/button[2]";

    //initiate element


    public static void click_EmailSubs(){
        WebElement email = driver.findElement(By.xpath(emailBtnXpath));
        new Actions(driver).scrollToElement(email).perform();
        driver.findElement(By.xpath(emailBtnXpath)).click();
    }

    public static void click_PrintSubs(){
        driver.findElement(By.xpath(printBtnXpath)).click();
    }

    public static void printField_all(){

    }

    public static void emailField_all(){
        driver.findElement(By.id(emailId)).sendKeys(formData.email);
        driver.findElement(By.id(firstNameId)).sendKeys(formData.firstName);
        driver.findElement(By.id(lastNameId)).sendKeys(formData.lastName);
        driver.findElement(By.xpath(dropdownXpath)).sendKeys(formData.country);
        driver.findElement(By.name(checkboxName)).click();
    }

    public static void clearPrintField(){

    }

    public static void clearEmailField(){

    }

    public static void submitForm(){
        driver.findElement(By.xpath(submitBtnXpath)).click();
    }
}
