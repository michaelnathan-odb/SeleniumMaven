package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;

public class ScreenSizeConfig {
    public static void setScreenSize(WebDriver driver, String deviceType) {
        driver.manage().window().maximize();

        if ("mobile".equalsIgnoreCase(deviceType)) {
            driver.manage().window().setSize(new Dimension(375, 812));
        } else if ("tablet".equalsIgnoreCase(deviceType)) {
            driver.manage().window().setSize(new Dimension(1024, 768));
        } else if ("desktop".equalsIgnoreCase(deviceType)) {
            driver.manage().window().maximize();
        } else {
            throw new IllegalArgumentException("Device type not supported: " + deviceType);
        }
    }
}
