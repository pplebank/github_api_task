# GitHub API Task

This is a simple Java application that interacts with the GitHub API to retrieve non-fork repositories of a given user.

## Prerequisites

Before running this application, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 8 or later
- Apache Maven

## Getting Started

To run the application, follow these steps:

1. Clone the repository.
2. Navigate to the project directory:
    cd github-api-task

3. Build the project using Maven:
    mvn clean install

4. Run the application:
    mvn spring-boot:run

## Usage
To use the application, send a GET request with header “Accept: application/json" to the following endpoint:

GET /repositories?username={username}

For non-existing users, application returns 404 response.
For any different Accept header values, application return 406 response.

## Tests

There are unit tests with mocking all external calls. Also, application has basic integration tests with real calls to github.

## Technologies Used

- Java 17
- Spring Boot
- Maven