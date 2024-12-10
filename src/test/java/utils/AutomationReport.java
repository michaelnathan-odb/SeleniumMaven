package utils;

import com.aventstack.extentreports.Status;
import tests.ReportData;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class AutomationReport {
    public static String createEmailBody(List<ReportData> scenarioTests) {
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<h1>Test Report</h1>");

        //Create map to group results by site and browser
        Map<String, Map<String, TestResultSummary>> reportStructure = new ConcurrentHashMap<>();

        Set<String> allBrowsers = new TreeSet<>(); // To keep track of all unique browsers

        for (ReportData reportData : scenarioTests) {
            String site = reportData.site;
            String browser = reportData.browser;

            allBrowsers.add(browser);

            //Ensure the site exists in the map
            reportStructure.putIfAbsent(site, new ConcurrentHashMap<>());

            //Ensure the browser exists under the site
            Map<String, TestResultSummary> browserMap = reportStructure.get(site);
            browserMap.putIfAbsent(browser, new TestResultSummary());

            //Update the test result summary for the browser
            TestResultSummary summary = browserMap.get(browser);
            System.out.println(site + "--" + browser + "--" + reportData.test.getStatus());
            if (reportData.test.getStatus() == Status.PASS) {
                summary.incrementSuccess();
            } else {
                summary.incrementFailed();
            }
        }
//        for (Map.Entry<String, Map<String, TestResultSummary>> siteEntry : reportStructure.entrySet()) {
//            String site = siteEntry.getKey();
//            Map<String, TestResultSummary> browserMap = siteEntry.getValue();
//            for (Map.Entry<String, TestResultSummary> browserEntry : browserMap.entrySet()) {
//                String browser = browserEntry.getKey();
//                TestResultSummary result = browserEntry.getValue();
//                System.out.println(site + "--" + browser + "==" + result.successCount + "+++" + result.failedCount);
//            }
//        }

        //TODO: Generate table header
        emailBody.append("<table border='1'>");
        emailBody.append("<thead><tr><th>Site</th>");
        for (String browser : allBrowsers) {
            emailBody.append("<th>").append(browser).append("</th>");
        }
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
                    emailBody.append("<td>")
                            .append(summary.getSuccessCount())
                            .append("/")
                            .append(summary.getFailedCount())
                            .append("</td>");
                } else {
                    emailBody.append("<td>0/0</td>"); // Default for missing browsers
                }
            }
            emailBody.append("</tr>");
        }
        emailBody.append("</tbody></table>");

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

