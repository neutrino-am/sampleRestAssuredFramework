# WTIA REST API Test Suite

In this test framework, I will explore https://wheretheiss.at/ website (WTIA) and give test scenarios & testcases around couple of API end points listed.

This is an API Automation Framework built using Rest Assured, Java, Maven, TestNG.

### Setup and Installation 
- Get Java 8+ and Maven 3.8.4.
- Set Environment variables JAVA_HOME & MAVEN_HOME paths
- Install an IDE like - IntelliJ Idea 
- Clone the repository from GitHub to your workspace.
- Running the test:
 Run the testng.xml file to run all the tests. Individual tests can be run at class level too.

### Libraries Used
- RestAssured - Java library for testing Restful Web services
- TestNG - Unit Testing Library
- AssertJ - Fluent Assertion Library in Java
- Lombok - To reduce boilerplate code


Note : All the libraries are open-source and don't include any license.




### Generic Test Scenarios 
- Verify response headers of each api.
- Verify response status - 200 ok ; 404 for invalid satellite id 
- Verify response on removing optional query parameters. : 200 ok
- Verify response on removing required query parameters. : 400 Bad request
- Verify /positions endpoint returns same response for same timestamp

- Exploratory testing - Trying to modify / delete data the endpoints under test are fetching can be done.
  like - PUT , POST & DELETE requests to both endpoints.
> In this Test framework, I have taken only delete method

- Correctness of data can be verified from  https://celestrak.com  and space-track.org as https://wheretheiss.at/ is fetching its data from there.
  But, in order to include it in my tests, I would need more information & reading through the documentation to understand how can I do that.
  Though I checked manually, it was fine.

- Check rate limits for each API.
  So , I can check each api is adhering to limits ; Also -a combination of endpoints under tests are adhering to it
> I tried this on Postman & got 429 Too many requests error.
As this is a corner scenario, I haven't added it to the test suite.

### Issues / Observations in WTIA
#### For satellites/[id]/positions

- It retrieves satellite position for timestamps before its launch too.
- Duplicate timestamps are being allowed

Eg : https://api.wheretheiss.at/v1/satellites/25544/positions?timestamps=1644185093,1644185093  
Will be giving two JSON Objects in the response array, instead of identifying it as duplicate.

- More than 10 timestamps are allowed, there is no check placed on it.

#### For Overall website 
- Authentication :
Though it's mentioned in documents that Authentication will be taken care in the future .
I would say , modification methods -PUT , POST & DELETE should return 403 , instead of 401
- Limited Data on how position is being calculated 
- Rate Limiting :
Rate Limiting on WTIA is quite less than the limits put on the website its consuming it data from, so commercial usage should be avoided. 
#### For Documentation : 
1. It doesn't specify explicitly the source of its data.
2. It doesn't specify units of velocity we are getting in response from few endpoints.