# Harel Travel E2E Test Automation

## Project Purpose

This project provides automated end-to-end (E2E) tests for the **Harel Travel Insurance** flow.  
It simulates user interactions in the browser to verify that key functionalities (such as date selection and pricing) work as expected.

## Features

- Full journey test of the travel insurance wizard
- Cross-browser testing (Chrome, Firefox, Edge)
- Failure handling with screenshot and page source capture
- Allure reporting integration
- Extensible and maintainable code structure

## Technologies Used

- Java 22
- Maven
- TestNG
- Selenium WebDriver
- WebDriverManager
- Allure
- SLF4J (logging)

## Project Structure

src
└── test
├── java
│ ├── com.core # Base classes and WebDriver factory
│ ├── com.pages # Page object classes
│ └── com.tests # Test classes
└── resources
└── testng.xml # TestNG suite configuration

## How to Run Tests

### Prerequisites

- Java 22 installed and `JAVA_HOME` set
- Maven installed and added to the system `PATH`
- Chrome, Firefox, or Edge browser installed
- (Optional) Allure CLI installed for viewing test reports

### Clone the Repository

git clone https://github.com/AllaKh/Harel_travel_e2e.git
cd Harel_travel_e2e

## Running Tests

### On Windows
mvn clean test

### On Linux
mvn clean test

The project uses a temporary user data directory for Chrome to avoid session conflicts.
Ensure no parallel Chrome sessions are using the same user profile.

### Running Tests with a Specific Browser
To run tests in a specific browser, use the -Dbrowser parameter:
mvn clean test -Dbrowser=firefox

Supported values: chrome, firefox, edge
Default browser: chrome

### Viewing the Allure Report

After test execution, you can view the Allure report locally with:
allure serve target/allure-results
