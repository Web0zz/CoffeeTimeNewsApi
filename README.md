# CoffeeTimeNewsApi

## Overview :bookmark_tabs:
That's a simple `REST API` coded using Kotlin. This API's intended to be used for Coffee Time News Android App only. It responds to the user with an article list which is made randomly by another service (for now).

This API is currently deployed on `https://coffeetimenews.herokuapp.com/` . You can try it.

It deployed on Heroku. With a small configuration, it can be used for small-size applications, with changing working way of Article data source and Article model class. JWT authentication will limit access who is using your app.

## Features :fire:
- Simple structure: Easily configurable
- Authentication: knows who is using
- Test cases: all state tested,

## Build with :paperclip: 
- Ktor: is an application framework for building microservices, web applications, and more. so easy to use give a chance 
- Kodein-DB: is a Kotlin/Multiplatform embedded NoSQL database. If you can use collections, it's easy for you

## How to Build :hammer_and_wrench:
First of all, you need the last version of JetBrains IntelliJ Idea to build and run the server application.
- Import project in IntelliJ Idea.
- Build the project.
- Set environment variables for the :application:run configuration as following
```
SECRET_KEY=YOUR_RANDOM_KEY
API_URL=mockaroo_model_api              
TEST_STATE=false
```
> Mockaroo api makes easier to create fake data if you want how to use and create api url: [Mockaroo](https://www.mockaroo.com/apis)
> If you want to creat database to only testing test_state should be false
- Run command `./gradlew :application:run`.
- Type http://localhost:8080 in your browser and API will be live. You can use service like [Postman](https://www.postman.com)

## Design - File Structure :card_file_box:

```
├── Application.kt            # Main application server will start here
├── ConfigUtil.kt             # A class stores application properties
│ 
├── config                    # JWT Authentication configuration
│   └── ...
├── di                        # Dependency injection 
│   └── ...
├── features                  # Features of api
│   ├── article
│   │   └── ...
│   └── auth
│       └── ...
├── model                     # Data classes to use at request and response
│   ├── exception
│   │   └── ...
│   ├── request
│   │   └── ...
│   └── response
│       └── ...
└── plugins                   # Ktor plugins
        └── ...
```
# How to use :electric_plug: 
You can use [/http]() file to test API calls in IntelliJ Idea after starting the API.

## Authentication :inbox_tray:
### Register
```
POST http://localhost:8080/auth/register
Content-Type: application/json

{
    "username": "admin123",
    "password": "qwerty1234"
}
```
### Login
```
POST http://localhost:8080/auth/login
Content-Type: application/json

{
    "username": "admin123",
    "password": "qwerty1234"
}
```

## Article 	:outbox_tray:
```
GET http://localhost:8080/article/{CATEGORY}
Content-Type: application/json
Authorization: Bearer YOUR_AUTH_TOKEN
```
