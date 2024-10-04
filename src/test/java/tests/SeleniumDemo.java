package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utils.FormData;
import java.time.Duration;

public class SeleniumDemo {
    public static void main(String[] args) {
        System.setProperty("webdriver.edge.browser","driver/msedgedriver.exe");
        WebDriver driver = new EdgeDriver(); //open the driver browser

        driver.manage().window().maximize(); //set the window screen to max

        driver.get("https://odb.org/subscription/jp"); //open the desired site

        System.out.println(driver.getTitle()); //show the page title in console

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000)); //wait for element exist

        /*Test order list
         * 1. Open the destination link of endpoints
         * 2. Scroll down to the designated subscription form (email)
         * 3. Select the email subscription form
         * 4. Get the fields element
         * 5. Fill the form with data
         * 6. Set the dropdown country
         * 7. Check the checkbox and submit the form*/
        WebElement emailSubs = driver.findElement(By.xpath("//*[@id=\"row-subscription-items\"]/ul/li[2]/a"));

        //scroll screen to designated element
        FormData formData = new FormData(); //initialize formData for dummy data
        new Actions(driver).scrollToElement(emailSubs).perform();
        emailSubs.click();

        //initialize elements from the web
        WebElement email = driver.findElement(By.name("email"));
        WebElement firstName = driver.findElement(By.name("First Name"));
        WebElement lastName = driver.findElement(By.name("Last Name"));
        WebElement dropdown =  driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[1]/form[1]/div[1]/div[3]/select[1]"));
        WebElement checkbox = driver.findElement(By.name("list[]"));
        WebElement submitBtn = driver.findElement(By.xpath("//*[@id=\"subscribe-form-email\"]/div/button[2]"));

        Select country = new Select(dropdown);
        //perform action
        email.sendKeys(formData.email);
        firstName.sendKeys(formData.firstName);
        lastName.sendKeys(formData.lastName);
        country.selectByValue(formData.country);
        checkbox.click();
        submitBtn.click();

        //give timeout for 5s
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //validate if the form is submitted


        // driver.quit(); //close the driver browser
    }
}
