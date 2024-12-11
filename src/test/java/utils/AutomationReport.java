package utils;

import com.aventstack.extentreports.Status;
import tests.ReportData;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class AutomationReport {
    public static String createEmailBody(ConcurrentHashMap<String, ReportData> scenarioTests) {
        StringBuilder emailBody = new StringBuilder();

        // Inline CSS styles for table
        String styles = "<head><style>"
                + "table { width: 100%; border-collapse: collapse; margin: 20px 0; font-size: 16px; font-family: Arial, sans-serif; }"
                + "th, td { border: 1px solid black; text-align: center; padding: 8px; }"
                + "th { background-color: #f4f4f4; font-weight: bold; }"
                + "tr:nth-child(even) { background-color: #f9f9f9; }" // Alternate row coloring for better readability
                + "td { border: 1px solid black; }"
                + "</style></head>";

        emailBody.append("<h1>Test Report</h1>");
        emailBody.append(styles);

        //TODO: Add a description for the test summary

        //Create map to group results by site and browser
        Map<String, Map<String, TestResultSummary>> reportStructure = new ConcurrentHashMap<>();
        Set<String> allBrowsers = new TreeSet<>(); // To keep track of all unique browsers

        for (ReportData reportData : scenarioTests.values()) {
            String site = reportData.site;
            String browser = reportData.browser;
            String scenario = reportData.scenario;

            allBrowsers.add(browser);

            //Ensure the site exists in the map
            reportStructure.putIfAbsent(site, new ConcurrentHashMap<>());

            //Ensure the browser exists under the site
            Map<String, TestResultSummary> browserMap = reportStructure.get(site);
            browserMap.putIfAbsent(browser, new TestResultSummary());

            //Update the test result summary for the browser
            TestResultSummary summary = browserMap.get(browser);
            if (reportData.test.getStatus() == Status.PASS) {
                summary.incrementSuccess();
            } else {
                summary.incrementFailed();
            }
        }

        // Generate the table header
        emailBody.append("<table>");
        emailBody.append("<thead><tr><th rowspan='2'>Site</th>");
        for (String browser : allBrowsers) {
            emailBody.append("<th colspan='2'>").append(browser).append("</th>");
        }
        emailBody.append("</tr><tr>");
        emailBody.append("<th>Success</th><th>Failed</th>".repeat(allBrowsers.size()));
        emailBody.append("</tr></thead>");

        // Generate the table body
        emailBody.append("<tbody>");
        for (Map.Entry<String, Map<String, TestResultSummary>> siteEntry : reportStructure.entrySet()) {
            String site = siteEntry.getKey();
            Map<String, TestResultSummary> browserMap = siteEntry.getValue();

            emailBody.append("<tr><td>").append(site).append("</td>");
            for (String browser : allBrowsers) {
                TestResultSummary summary = browserMap.get(browser);
                if (summary != null) {
                    emailBody.append("<td style='color: green; font-weight: bold;'>")
                            .append(summary.getSuccessCount())
                            .append("</td>");
                    emailBody.append("<td style='color: red; font-weight: bold;'>")
                            .append(summary.getFailedCount())
                            .append("</td>");
                } else {
                    emailBody.append("<td>0</td><td>0</td>"); // Default for missing browsers
                }
            }
            emailBody.append("</tr>");
        }
        emailBody.append("</tbody></table>");

        //TODO: Add scenario list


        return emailBody.toString();
    }

    //Helper class to summarize test results
    private static class TestResultSummary {
        private int successCount = 0;
        private int failedCount = 0;

        public void incrementSuccess() {
            successCount++;
        }

        public void incrementFailed() {
            failedCount++;
        }

        public int getSuccessCount() {
            return successCount;
        }

        public int getFailedCount() {
            return failedCount;
        }
    }
}

