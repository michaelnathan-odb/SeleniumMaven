package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class BrowserConfig {
    public static WebDriver getDriver(String browser) throws MalformedURLException, URISyntaxException {
        WebDriver driver = null; // Explicitly declared variable
        String webRemoteUrl = System.getProperty("web.remote.url","http://localhost:4444/wd/hub");
        URL url = new URI(webRemoteUrl).toURL();
        if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=old");
            driver = new RemoteWebDriver(url, options);
        } else if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-private").addArguments("--headless");
            driver = new RemoteWebDriver(url, options);
        } else if ("edge".equalsIgnoreCase(browser)) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless=old");
            driver = new RemoteWebDriver(url, options);
        }
        if (driver == null) {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        return driver;
    }
}
