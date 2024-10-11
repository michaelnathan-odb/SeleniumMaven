package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserConfig {
    public static WebDriver getDriver(String browser) {
        WebDriver driver = null;
        if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions option = new ChromeOptions();
            option.addArguments("--headless");
            System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
            driver = new ChromeDriver(option);
        } else if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
            driver = new FirefoxDriver(options);
        } else if ("edge".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.edge.driver", "src/main/resources/driver/msedgedriver.exe");
            driver = new EdgeDriver();
        }
        if (driver == null) {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        return driver;
    }
}
