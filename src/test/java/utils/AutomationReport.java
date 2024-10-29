package utils;

import com.aventstack.extentreports.ExtentTest;

import java.util.List;

public class AutomationReport {
    //EmailBodyGenerator
    //generateEmailBody
    static String createEmailBody(List<ExtentTest> scenarioTests) {
        //TODO: Import scenario test to generate the list of test scenario into the email
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<h1>Test Report</h1>");
        for (ExtentTest test : scenarioTests) {
            emailBody.append("<p>").append(test.getModel().getName()).append(": ");
            emailBody.append(test.getStatus()).append("</p>");
        }
        return emailBody.toString();
    }
}
