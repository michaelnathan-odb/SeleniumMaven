package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDataProvider {

    @DataProvider(name = "provider")
    public Object[][] dataProvider() throws IOException {
        // Parse the JSON file
        FileReader reader = new FileReader("src/main/resources/testData2.json");
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

        // Get the testData array from JSON
        JsonArray testDataArray = jsonObject.getAsJsonArray("testData");

        // Create a list to hold all the test data
        List<Object[]> data = new ArrayList<>();

        // Loop through each site
        for (int i = 0; i < testDataArray.size(); i++) {
            JsonObject siteData = testDataArray.get(i).getAsJsonObject();
            String site = siteData.get("site").getAsString();
            JsonArray testCases = siteData.getAsJsonArray("testCases");

            // Loop through the test cases for each site
            for (int j = 0; j < testCases.size(); j++) {
                JsonObject testCase = testCases.get(j).getAsJsonObject();
                String browser = testCase.get("browser").getAsString();
                String resolution = testCase.get("resolution").getAsString();

                // Add each combination of site, browser, and resolution to the data list
                data.add(new Object[]{browser, site, resolution});
            }
        }

        // Convert List<Object[]> to Object[][]
        return data.toArray(new Object[0][]);
    }
}
