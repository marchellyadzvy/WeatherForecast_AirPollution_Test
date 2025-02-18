Weather Forecast & Air Pollution API Automation Testing

Overview

This project is an API automation testing suite for Weather Forecast and Air Pollution data in South Jakarta using Katalon Studio. The tests validate responses from OpenWeather API and ensure that multiple sub-districts in South Jakarta are covered.

Features:

1. Automated API testing for weather forecast and air pollution data.
2. Validation of response body, JSON schema, and key attributes.
3. Rate limit handling to stay within OpenWeather's free tier.
4. Test Suite integration for running multiple test cases efficiently.
5. Automated test reports in HTML, PDF, and JUnit format.

Project Structure

WeatherForecast_AirPollution_Test/
│── Object Repository/
│   ├── Get_Weather_JakartaSelatan
│   ├── Get_AirPollution_JakartaSelatan
│── Test Cases/
│   ├── TC_Get_Weather_JakartaSelatan
│   ├── TC_Get_AirPollution_JakartaSelatan
│   ├── TC_Get_Weather_And_AirPollution_JakartaSelatan
│── Test Suites/
│   ├── TS_Weather_AirPollution
│── Scripts/
│   ├── WeatherAPIUtils.groovy
│── Reports/
│── README.md
│── .gitignore

Prerequisites

1. Katalon Studio
2. Git
3. OpenWeather API Key
4. GitHub repository: WeatherForecast_AirPollution_Test

Installation & Setup

1. Clone this repository: git clone https://github.com/marchellyadzvy/WeatherForecast_AirPollution_Test.git
2. Open Katalon Studio and load the project folder.
3. Configure API Key in GlobalVariable inside Profiles/Default.glbl.
4. Ensure the test objects in Object Repository are correctly set.

Running Tests

Run Individual Test Case:
1. Open Katalon Studio.
2. Navigate to Test Cases/.
3. Right-click on the test case (e.g., TC_Get_Weather_JakartaSelatan) and click Run.

Run All Tests Using Test Suite:
1. Open Katalon Studio.
2. Go to Test Suites/ and open TS_Weather_AirPollution.
3. Click Run to execute all test cases.

Generating Reports
1. After execution, reports will be stored in the Reports/ folder.
2. Formats available: HTML, PDF, JUnit.
3. Reports can be accessed directly from Katalon Studio’s Test Explorer > Reports section.

Contributing
Feel free to fork this repository and submit pull requests to improve test coverage or add new features.

License
This project is licensed under the XLAxiata-Lancedoft Indonesia.


