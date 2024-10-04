package config;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserConfig {
    public static WebDriver getDriver(String browser) {
        WebDriver driver = null; // Explicitly declared variable
        if ("chrome".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Michael Nathan\\IdeaProjects\\SeleniumMaven\\src\\main\\resources\\driver\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if ("firefox".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.gecko.driver", "C:\\Users\\Michael Nathan\\IdeaProjects\\SeleniumMaven\\src\\main\\resources\\driver\\geckodriver.exe");
            driver = new FirefoxDriver();
        } else if ("edge".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.edge.driver", "C:\\Users\\Michael Nathan\\IdeaProjects\\SeleniumMaven\\src\\main\\resources\\driver\\msedgedriver.exe");
            driver = new EdgeDriver();
        }
        if (driver == null) {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        return driver;
    }
}
