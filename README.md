# Selenium Automated Subscription Form Testing

## About the Project
This project automates testing for subscription forms across multiple regional websites using Selenium Docker. It ensures that the forms are accessible, functional, and compliant across various devices and browsers.

**Note**: Current project process are still testing for the Email Subscription Form for `odb.org` site. Some region with different form are excluded (e.g. Singapore, US, UK, etc.)  

## Table of Contents
- [About the Project](#about-the-project)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Pre-requisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [Modification](#modification)
- [Improvement Needs](#improvement-needs)

## Features
- Cross-browser testing (Chrome, Firefox, Edge)
- Cross-screen-resolution testing (Mobile, Tablet, Desktop)
- Data-driven testing using JSON configurations 
- Automated email reporting 
- Parallel test execution support 
- Detailed HTML reports using ExtentReports 
- Validation scenarios for submitting valid and invalid form data

## Technologies Used
- **Programming Language**: Java
- **Testing Framework**: TestNG
- **Automation Tool**: Selenium Docker
- **Reporting Tool**: ExtentReports (Spark Reporter)
- **Data Handling**: JSON

## **Prerequisites**
Before setting up and running this project, ensure the following requirements are met:
### **1. Install Java and Maven**
- **Java Development Kit (JDK)** (Version 11 or higher, I'm using version 23) 
  - [Download](https://www.oracle.com/java/technologies/downloads/#java11?er=221886)
  - Verify Installation
    ```bash
    java --version
    ```
- Maven (Version 3.6 or higher, I'm using version 3.9.9)
  - [Download](https://maven.apache.org/install.html)
  - Verify Installation
    ```bash
    mvn --version
    ```
### **2. Install Docker**
- Install Docker on your system:
  - [Download Docker](https://www.docker.com/get-started)
  - Verify the installation:
    ```bash
    docker --version
    ```

## Setup Instructions
### **1. Clone the Repository**
```bash
git clone https://github.com/michaelnathan-odb/SeleniumMaven.git
cd SeleniumMaven
```

### **2. Configure the `.env` File**
- The `.env` file is required to store SMTP credentials for the email reporting feature.
- Refer to the `.env-example` file for guidance on the required fields.
- Contact the DevOps team to obtain SMTP access details.

### **3. Run Selenium Docker Containers**
- Start the Selenium Grid and browser nodes:
  ```bash
  docker-compose -f docker-compose.yml up -d
  ```
- Verify the Selenium Grid setup at: `http://localhost:4444`.

## Usage
### Working with Data-Driven
- Create a JSON file to define the desired test coverage. Alternatively, you can use the existing testData.json file to test all sites. Below is an example of the Test Data JSON structure:
```json
{
  "testData": [
    {
      "site": "https://odb.org/subscription/au/",
      "expectedResultEmail": "Expected result string",
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
FileReader reader = new FileReader("src/main/resources/testData.json");
```

### How to run it
The project uses Maven to manage dependencies. Run the following command to download and install all required dependencies:

Run the tests using Maven:
```bash
mvn clean test -Dtest="<test-classes>" -Dgroups="<groups-name>"
```
- `-Dgroups="<group-name>"`: Run the test on specific labeled test cases. You can customize the grouping by adding the "groups" tag. Learn more about grouping [here](https://toolsqa.com/testng/groups-in-testng/).
- Make sure to add double-quote when defining multiple argument.

### Troubleshooting
1. If you find error when running the test, use this command to run test in debug-level logging:
```bash
mvn test -X <test-arguments>
```
2. **View Reports**:  
   Open the following files in a browser to review test results:
  - `target/surefire-reports/emailable-report.html`
  - `target/surefire-reports/index.html`

## Modification
To add new test cases (e.g., for the Printed Subscription Form or other forms with different layouts), follow these steps using the Page Object Model (POM) pattern:

1. **Create a Page Class**:
  - Define locators for the DOM elements and implement methods for page interactions.

2. **Create a Test Class**:
  - Develop a new test class (e.g., `PrintedSubscriptionTest`).
  - Follow the structure of existing test classes, ensuring to update DataProvider-related code.

3. **Add a JSON File**:
  - Store test data (e.g., sites, browsers, resolutions) in a new JSON file.

4. **Update TestDataProvider**:
  - Implement a new method in the `TestDataProvider` class to read the JSON file for the new test class.

5. **Run the Test**:
  - Execute the test using the `-Dtest` parameter in the terminal:
    ```bash
    mvn clean test -Dtest="<TestClassName>"
    ```

## Improvement Needs
- Please update README.md file if there is any improvement are implemented
- Test for Printed Subscription Form
- Develop Page Object Models (POM) for other Email Subscription Form layouts.