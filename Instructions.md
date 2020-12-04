# QuoteMedia Interview Project

Hector Solares Martinez
Reference:   [README.md](./README.md) of this project

## Description

The application is prepared to be executed in a local environment.
	
This project fulfills the next objectives:
- An endpoint that accepts any symbol (e.g., `MSFT`) and returns the most recent quote for that symbol
- An endpoint to retrieve the symbol with the highest ask for a given day
- A test suite
- Authentication
	
## Requirements

- JDK 8 or higher

- Apache Maven


## Specifications

This project was created using the following tools:
- Java 8
- Spring Boot
- H2 Database
- Junit
- Maven

## Configuration
All the available configurations can be modified in the `application.properties` file of this project.

- symbol.min.length=2  
Specified the minimum length of the symbol request

- symbol.max.length=6   
Specified the maximum length of the symbol request

- date.query.format=yyyyMMdd   
Specified the format of the day provided in the request

- api.auth.user=quotemedia   
User name to perform the request to the endpoint

- api.auth.pwd=qu0t3m3d1a  
The password to perform the request to the endpoint


## Build and start  

- Unzip the folder of the project
- From a terminal or cmd go to the folder of the project : `hector-solares/quote-service`
- Execute the command to build the project:


```sh
$ mvn clean install
```

- Finally, execute the next command to start the application:

```sh
$ mvn spring-boot:run
```

5. Ready to go!

## Testing

The project contains 9 test cases for the 2 endpoints.
- 4 Tests for the most recent quote 
- 3 Test to get the highest symbol at a specified day
- 2 Authentication

To perform the test validation execute the command

```sh
$ mvn test
```

## Assumptions
- The dataset is limited by the init.sql script
- The creation of the database and the data insertion is done by spring boot
- The application is started in port 8080

## Endpoints

### Get the most recent quote for a given symbol
 ``` 
 GET /symbols/{symbol}/quotes/latest  
{"bid": 1.23, "ask": 4.56}
```
Accepts any symbol and returns the most recent quote for that symbol.

Request type: GET

Symbol: String of the symbol (e.g., GOOG, FB)

Min length: 2

Max length: 6

Response type: JSON

Notes: Capital case and lower case are accepted

### Example

http://localhost:8080/symbols/MSFT/quotes/latest

```JSON
{
"bid": 2.51,
"ask": 2.96
}
```

http://localhost:8080/symbols/FB/quotes/latest

```JSON
{
"bid": 3.68,
"ask": 3.93
}
```

### Get the highest ask for a given day

 ``` 
 GET /symbols/{day}/highest  
{"symbol": "FB"}
```

Accepts a day and retrieve the symbol with the highest ask for the given day.

Request type: GET

day: String of the day (e.g., 20200105 , 20200102 )

Format: yyyyMMdd

Response type: JSON

### Example

http://localhost:8080/symbols/20200105/highest

```JSON
{
"symbol": "FB"
}
```

http://localhost:8080/symbols/20200103/highest

```JSON
{
"symbol": "GOOG"
}
```

### Authentication

The application performs basic auth provided by spring security, the user and password have to be sent in every request.

- Default user: **quotemedia**
- Default password: **qu0t3m3d1a**

If any request fails the authentication step the endpoint will return a **401 Unauthorized** response code.


## Additional notes

- The git files used for the project are in this zip folder.

- Every time the configuration properties file is changed, the application has to be restarted.



