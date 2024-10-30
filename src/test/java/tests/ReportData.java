package tests;

import com.aventstack.extentreports.ExtentTest;

public class ReportData {
    public ExtentTest test;
    public String browser;
    public String site;
    public String resolution;
    public String scenario;

    public ReportData(ExtentTest test, String browser, String site, String resolution, String scenario) {
        this.test = test;
        this.browser = browser;
        this.site = site;
        this.resolution = resolution;
        this.scenario = scenario;
    }
}
