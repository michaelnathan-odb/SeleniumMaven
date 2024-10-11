package tests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SubscribeLocators {

    WebDriver driver;

    public SubscribeLocators(WebDriver _driver){
        driver = _driver;
    }

    //email subscription form locator
    @FindBy(css = "li.email.subscription-tabs")
    public WebElement emailBtnCss;

    @FindBy(xpath = "//*[@id=\"moove_gdpr_cookie_info_bar\"]/div/div/div[2]/button[3]/i")
    public WebElement closeCookie;

    @FindBy(id = "subscription-email-signup")
    public WebElement scrollToEmailId;

    @FindBy(name = "email")
    public WebElement emailName;

    @FindBy(name = "First Name")
    public WebElement firstName;

    @FindBy(name = "Last Name")
    public WebElement lastName;

    @FindBy(xpath = "/html[1]/body[1]/div[2]/div[2]/div[3]/div[2]/div[1]/div[1]/form[1]/div[1]/div[3]/select[1]")
    public WebElement dropdownXpath;

    @FindBy(name = "list[]")
    public WebElement checkboxName;

    @FindBy(xpath = "//*[@id=\"subscribe-form-email\"]/div/button[2]")
    public WebElement submitBtnXpath;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div[3]/div[2]/div/div/form/div")
    public WebElement successStatus;

    //printed subscription form locator
    @FindBy(css = "li.print.subscription-tabs")
    public WebElement printBtnCss;

    @FindBy(name = "Name")
    public WebElement fullName;

    @FindBy(name = "Reserve1")
    public WebElement reserveName;

    @FindBy(name = "Email")
    public WebElement emailNameP;

    @FindBy(name = "Country")
    public WebElement countryName;

    @FindBy(name = "Postcode")
    public WebElement postcodeName;

    @FindBy(name = "Prefecture")
    public WebElement prefectureName;

    @FindBy(name = "CityWardDistrictVillage")
    public WebElement districtName;

    @FindBy(name = "Street")
    public WebElement streetName;

    @FindBy(name = "Town")
    public WebElement townName;

    @FindBy(name = "BlockNumber")
    public WebElement blockNumberName;

    @FindBy(name = "CareofBuildingName")
    public WebElement buildingName;

    @FindBy(xpath = "//input[@type='checkbox' and @value='男性']")
    public WebElement genderXpath;

    @FindBy(name = "HomePhone")
    public WebElement homePhoneName;

    @FindBy(name = "OfficePhone")
    public WebElement officePhoneName;

    @FindBy(name = "MobilePhone")
    public WebElement mobilePhoneName;

    @FindBy(name = "Church")
    public WebElement churchName;

    @FindBy(name = "Occupation")
    public WebElement occupationName;

    @FindBy(name = "Day")
    public WebElement dateName;

    @FindBy(name = "Month")
    public WebElement monthName;

    @FindBy(name = "yearName")
    public WebElement Year;

    @FindBy(name = "Message")
    public WebElement messageName;

    @FindBy(xpath = "/html/body/div[2]/div[2]/div[3]/div[1]/div/div/form/div[20]/div/input")
    public WebElement submitBtnXpathP;

    @FindBy(xpath = "//div[@class='wpcf7-response-output alert']")
    public WebElement successStatusP;

}
