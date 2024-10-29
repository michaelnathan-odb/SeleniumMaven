package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserConfig {
    public static WebDriver getDriver(String browser) {
        WebDriver driver = null; // Explicitly declared variable
        if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=old");
            System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
            driver = new ChromeDriver(options);
        } else if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe").addArguments("--headless");
            System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
            driver = new FirefoxDriver(options);
        } else if ("edge".equalsIgnoreCase(browser)) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless=old");
            System.setProperty("webdriver.edge.driver", "src/main/resources/driver/msedgedriver.exe");
            driver = new EdgeDriver(options);
        }
        if (driver == null) {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        return driver;
    }
}
