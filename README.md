# CoffeeTimeNewsApi

## Overview
That's a simple `HTTP API` coded only using Kotlin. This API's intended to be used for Coffee Time News Android App only. It responds to the user with an article list which is made randomly by another service (for now).

It deployed on Heroku. With a small configuration, it can use for small-size applications, with changing working way of Article data source and Article model class. JWT authentication will limit access who is using your app.

## Features
- Simple structure: Easily configurable
- Authentication: knows who is using
- Test cases: all state tested

## Design - File Structure

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
