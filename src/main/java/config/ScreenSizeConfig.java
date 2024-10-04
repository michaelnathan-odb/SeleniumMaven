package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;

public class ScreenSizeConfig {
    public static void setScreenSize(WebDriver driver, String deviceType) {
        if ("desktop".equalsIgnoreCase(deviceType)) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else if ("tablet".equalsIgnoreCase(deviceType)) {
            driver.manage().window().setSize(new Dimension(1024, 768));
        } else if ("mobile".equalsIgnoreCase(deviceType)) {
            driver.manage().window().setSize(new Dimension(375, 667));
        } else {
            throw new IllegalArgumentException("Device type not supported: " + deviceType);
        }
    }
}
