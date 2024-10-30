package utils;

import tests.ReportData;

import java.util.List;

public class AutomationReport {
    public static String createEmailBody(List<ReportData> scenarioTests) {
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<h1>Test Report</h1>");

        // Build the hierarchical report structure
        for (ReportData reportData : scenarioTests) {
            emailBody.append("<h2>Site: ").append(reportData.site).append("</h2>");
            emailBody.append("<h3>Browser: ").append(reportData.browser).append("</h3>");
            emailBody.append("<h4>Resolution: ").append(reportData.resolution).append("</h4>");
            emailBody.append("<p>Scenario: ").append(reportData.scenario).append("</p>");
            emailBody.append("<p>Status: ").append(reportData.test.getStatus()).append("</p>");
        }

        return emailBody.toString();
    }
}
