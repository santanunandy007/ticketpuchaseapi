# Ticket Purchase API
This is a RESTful API built with Spring Boot 3.2.3 & Java 17 for managing ticket purchases.

## Features
- Create, read, update, and delete operations for tickets
- Error handling and validation
- Unit tests for endpoints

## Technologies Used
- Java 17
- Spring Boot 3.2.3
- Maven for dependency management
- JUnit and Mockito for testing

## Setup
**Clone the repository:**
git clone https://github.com/santanunandy007/ticketpuchaseapi.git
cd ticketapi
mvn clean install
java -jar target/ticketapi-0.0.1-SNAPSHOT.jar

## API Endpoints
/ticket/purchase:
  POST: Create a new ticket
  RequestBody : 
  {
    "from": "London",
    "to": "France",
    "user": {
        "firstName": "Santanu",
        "lastName": "Nandy",
        "email": "santanu@gmail.com"
    },
    "price": "35"
  }
  Response : Ticket Booked Successfully for user: santanu@gmail.com

/tickets/receipt/{emailId}:
  GET: Get a ticket deatails of user
  Response : 
  {
    "from": "London",
    "to": "France",
    "userName": "Santanu Nandy",
    "pricePaid": 35.0,
    "section": "B",
    "seatNumber": 1
  }
  
/tickets/users/{section}:
  GET: Get seat deatils of section
  Response : 
  {
    "B1": {
        "section": "B",
        "seatNumber": 1,
        "occupied": true,
        "occupieduseremailId": "santanu@gmail.com"
    },
    "B2": {
        "section": "B",
        "seatNumber": 2,
        "occupied": false,
        "occupieduseremailId": "Not Occupied"
    },
  }

/tickets/modify/{emailId}:
  PUT: Update a seat of user
  RequestBody : 
  {
    "section": "A",
    "seatNumber": "3"
  }
  Response : User's seat modified successfully.

/tickets/remove/{emailId}:
  DELETE: Delete a ticket for a user.
