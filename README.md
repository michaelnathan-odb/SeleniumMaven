# Selenium Automated Subscription Form Testing

## About the Project
This project automates testing for subscription forms across multiple regional websites using Selenium WebDriver. It ensures that the forms are accessible, functional, and compliant across various devices and browsers.

**Note**: Current project process are still testing for the Email Subscription Form for `odb.org` site. Some region with different form are excluded (e.g. Singapore, US, UK, etc.)  

## Table of Contents
- [About the Project](#about-the-project)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Pre-requisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [Improvement Needs](#improvement-needs)

## Features
- Cross-browser (Chrome, Firefox, MS Edge) and cross-screen-resolution (Mobile, Tablet, Desktop) testing
- Data-driven testing with JSON configurations
- Automated email reporting
- Supports parallel testing
- ExtentReports for detailed HTML reports
- Run the test with various scenario (submitting valid and invalid data)

## Technologies Used
- **Programming Language**: Java
- **Testing Framework**: TestNG
- **Automation Tool**: Selenium WebDriver
- **Reporting Tool**: ExtentReports (Spark Reporter)
- **Data Handling**: JSON

## **Prerequisites**
Before setting up and running this project, ensure the following requirements are met:
### **1. Install Java and Maven**
- **Java Development Kit (JDK)** (Version 11 or higher, I'm using version 23) 
  - [Download](https://www.oracle.com/java/technologies/downloads/#java11?er=221886)
  - Verify Installation
    ```bash
    java -version
    ```
- Maven (Version 3.6 or higher, I'm using version 3.9.9)
  - [Download](https://maven.apache.org/install.html)
  - Verify Installation
    ```bash
    mvn -version
    ```

### **2. Add Browsers to the Environment Variable Path**
- Ensure the browser(s) you plan to use for testing (e.g., Chrome, Firefox, Edge) are installed on your system.
- Add the browser executables to your system's Environment Variable `PATH` to allow the project to locate them easily:
  - **For Windows**:
    1. Open System Properties → Advanced → Environment Variables.
    2. Under "System Variables," find `PATH` and click "Edit."
    3. Add the path to the browser executable (e.g., `C:\Program Files\Google\Chrome\Application\`).
  - **For macOS/Linux**:
    Add the browser path to your shell configuration file (e.g., `.bashrc`, `.zshrc`):
    ```bash
    export PATH=$PATH:/path/to/browser
    ```

### **3. Download and Configure WebDrivers**
- Download the appropriate WebDriver for the browser and version you intend to use:
  - [ChromeDriver](https://googlechromelabs.github.io/chrome-for-testing/) for Google Chrome
  - [GeckoDriver](https://github.com/mozilla/geckodriver/releases) for Firefox
  - [Edge WebDriver](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/) for Microsoft Edge

- **Place the WebDriver**:  
  Copy the downloaded WebDriver executables into the `src/main/resources/driver/` directory of the project.

- **If using a different operating system**:  
  Ensure you download the WebDriver compatible with your OS (e.g., `.exe` for Windows, Unix binary for macOS/Linux) and update the `BrowserConfig` class in the project if necessary.


## Setup Instructions
### Importing Project
1. Clone the repository:
```bash
git clone https://github.com/michaelnathan-odb/SeleniumMaven.git
```

2. Navigate to the project directory:
```bash
cd your-project-directory
```

### Set the .env file
This file is required to store the SMTP configuration for sending email feature. Please refer to `.env-example` file for checking what credential that you need to fill it out. Ask DevOps team for the SMTP Access.

## Usage
### Working with Data-Driven
- Create a JSON file to define the desired test coverage. Alternatively, you can use the existing testData.json file to test all sites. Below is an example of the Test Data JSON structure:
```json
{
  "testData": [
    {
      "site": "https://odb.org/subscription/au/",
      "expectedResultEmail": "[Expected result string]",
      "testCases": [
        {
          "browser": "chrome",
          "resolution": "desktop"
        }
      ]
    }
  ]
}
```

- Please update the JSON reader function in `src/test/java/utils/TestDataProvider.java` by replacing `"src/main/resources/testData2.json"` with the path to your actual test data file.
```
FileReader reader = new FileReader("src/main/resources/testData2.json");
```

### How to run it
The project uses Maven to manage dependencies. Run the following command to download and install all required dependencies:
```bash
mvn clean install
```

Run the tests using Maven:
```bash
mvn test -Dgroups="groupA,groupB"
```
- `-Dgroups="groupA,groupB"`: Run the test on specific labeled test cases. You can customize the grouping by adding the "groups" tag. Learn more about grouping [here](https://toolsqa.com/testng/groups-in-testng/). 
- Make sure to add double-quote when defining multiple argument

### Troubleshooting
If you find error when running the test, use this command to run test in debug-level logging:
```bash
mvn test -X -Dgroups="groupB"
```

## Improvement Needs
- Please update README.md file if there is any improvement are implemented
- Layout of the email body report
- Test for Printed Subscription Form
- Page Object Model for different Email Subscription Form layout