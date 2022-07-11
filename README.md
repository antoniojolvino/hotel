# Briefing

* This project is a test for ALTEN Ca.
* It was built using Spring Boot
* Spring web was used as implementation to REST Controllers
* Lombok was used to simplify pojos
* Mapstruct was used as a mapper to transform objects between models and DTO's
* PostgreSQL was used as Database
* Docker was used as container to run PostgreSQL
* Spring Data JPA was used as JPA implementation
* Some tests were built, using Junit Jupiter

# How To Run

### Requirements

* Docker
* Maven
* Java 11+
* Postman o Insomnia REST

### Preparing Database

* In Console/Prompt, navigate to directory "**/docker**" where docker-compose.yml file is located
* run the following command:

``` 
$ docker-compose up 
```

* In other Console/prompt window navigate to root directory in the project, where pom.xml file is located
* run the following command:

``` 
$ mvn spring-boot:run 
```

* The application will start
* Import the collection (located in the directory "**/collections**") in your Postman or Insomnia REST
* Now you can test all endpoints
---
# API Documentation

## Endpoints

### Booking

| HTTP Method | URL                                                           | Description                  |
|-------------|---------------------------------------------------------------|------------------------------|
| GET         | http://localhost:8080/hotel/bookings/{id}                     | Find booking by ID           |
| GET         | http://localhost:8080/hotel/bookings?roomNumber={room-number} | Find bookings by room number |
| POST        | http://localhost:8080/hotel/bookings                          | Create a new booking         |
| PUT         | http://localhost:8080/hotel/bookings/{id}                     | Update a booking by its id   |
| DELETE      | http://localhost:8080/hotel/bookings/{id}                     | Delete a booking by its id   |

#### Booking Request Example
```
{
	"booking-id": 1,
	"start-date": "2022-07-12",
	"end-date": "2022-07-14",
	"room": {
		"room-number": 1
	},
	"customer": {
		"identification-document": "A123"
	}
}
```

#### Booking Response Example
```
{
	"booking-id": 1,
	"start-date": "2022-07-12",
	"end-date": "2022-07-14",
	"room": {
		"room-number": 1
	},
	"customer": {
		"name": "John Doe",
		"email": "john.doe@gmail.com",
		"identification-document": "A123"
	}
}
```
---

### Customer

| HTTP Method | URL                                        | Description           |
|-------------|--------------------------------------------|-----------------------|
| GET         | http://localhost:8080/hotel/customers      | Find all customers    |
| GET         | http://localhost:8080/hotel/customers/{id} | Find a customer by ID |
| POST        | http://localhost:8080/hotel/customers      | Create a new customer |
#### Customer Request Example
```
{
	"identification-document": "A123",
	"name": "Antonio Jolvino",
	"email": "antonio.feitosa@gmail.com" 
}
```

#### Customer Response Example
```
[
	{
		"name": "John Doe",
		"email": "john.doe@gmail.com",
		"identification-document": "A123"
	},
	{
		"name": "Jane Doe",
		"email": "jane.doe@gmail.com",
		"identification-document": "B123"
	}
]
```
---

### Room

| HTTP Method | URL                               | Description       |
|-------------|-----------------------------------|-------------------|
| GET         | http://localhost:8080/hotel/rooms | Find all rooms    |
| POST        | http://localhost:8080/hotel/rooms | Create a new room |
#### Room Request Example
```
{
	"room-number":1
}
```

#### Room Response Example
```
[
	{
		"room-number": 1
	}
]
```
---
# Challenge Description

Post-Covid scenario:
People are now free to travel everywhere but because of the pandemic, a lot of hotels went bankrupt. Some former famous
travel places are left with only one hotel.
You’ve been given the responsibility to develop a booking API for the very last hotel in Cancun.

The requirements are:

* API will be maintained by the hotel’s IT department.
* As it’s the very last hotel, the quality of service must be 99.99 to 100% => no downtime
* For the purpose of the test, we assume the hotel has only one room available
* To give a chance to everyone to book the room, the stay can’t be longer than 3 days and can’t be reserved more than 30
  days in advance.
* All reservations start at least the next day of booking,
* To simplify the use case, a “DAY’ in the hotel room starts from 00:00 to 23:59:59.
* Every end-user can check the room availability, place a reservation, cancel it or modify it.
* To simplify the API is insecure.

Instructions :

* Pas de limite de temps (très bien fait il faut au moins 3 à 4 soirées)
* Le minimum requis est un README et du code.
* Tous les shortcuts pour gagner du temps sont autorisés dans la mesure où c’est documenté. Tout shortcut non expliqué
  doit etre consideré comme une erreur. On pourrait accepter un rendu avec 3 lignes de code si elles ont du sens et que
  tout le raisonnement et les problèmatiques à prendre en compte sont decrites.
