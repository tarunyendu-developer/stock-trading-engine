# Stock Trading Order Engine

## Project Overview

The Stock Trading Order Engine is an advanced backend trading system built using Spring Boot.

The application simulates a simplified stock exchange where users can place BUY and SELL orders for stocks. The engine automatically matches compatible orders based on:

- Price Priority
- FIFO (First In First Out)
- Partial Matching

The system executes trades automatically and maintains order books, trade history, and market depth.

---

# Features

## User Management
- Register User
- Get User By ID
- Get All Users
- Delete User

## Stock Management
- Create Stock
- Get All Stocks

## Order Management
- Place BUY Order
- Place SELL Order
- Cancel Order
- Order History

## Matching Engine
- Automatic Order Matching
- FIFO Logic
- Price Priority Matching
- Partial Order Matching
- Remaining Quantity Tracking
- Order Status Updates

## Trade Management
- Trade Execution
- Recent Trades API
- Trade History

## Market Depth
- Top 5 BUY Orders
- Top 5 SELL Orders
- Order Book API

## Additional Features
- DTO Architecture
- Global Exception Handling
- Validation
- Swagger Documentation
- Logging using SLF4J
- Transaction Management
- Database Indexing
- Concurrency Safety using Pessimistic Locking

---

# Technologies Used

- Java 17
- Spring Boot 3
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok
- Swagger OpenAPI
- SLF4J Logging

---

# Project Architecture

```text
Controller Layer
↓
Service Layer
↓
Repository Layer
↓
MySQL Database
```

---

# Entity Relationships

## User
- One user can place multiple orders

## Stock
- One stock can have multiple orders

## Order
- Connected with User
- Connected with Stock
- Stores:
    - BUY / SELL
    - Price
    - Quantity
    - Remaining Quantity
    - Status

## Trade
- Stores executed trades
- Connected with:
    - BUY Order
    - SELL Order

---

# Order Status

```text
OPEN
PARTIAL
FILLED
CANCELLED
```

---

# Order Types

```text
BUY
SELL
```

---

# Matching Engine Logic

## BUY Order Matching
BUY orders match with:
- SELL orders
- Same stock
- SELL price <= BUY price

## SELL Order Matching
SELL orders match with:
- BUY orders
- Same stock
- BUY price >= SELL price

## FIFO Logic
If multiple orders have same price:
- older order executes first

## Partial Matching
If quantities differ:
- remaining quantity is updated
- order status becomes PARTIAL

---

# Database Tables

```text
users
stocks
orders
trades
```

---

# API Endpoints

## User APIs

### Register User
```http
POST /users
```

### Get User By ID
```http
GET /users/{id}
```

### Get All Users
```http
GET /users
```

### Delete User
```http
DELETE /users/{id}
```

---

## Stock APIs

### Create Stock
```http
POST /stocks
```

### Get All Stocks
```http
GET /stocks
```

---

## Order APIs

### Place BUY Order
```http
POST /orders/buy
```

### Place SELL Order
```http
POST /orders/sell
```

### Cancel Order
```http
DELETE /orders/{orderId}
```

### Get User Orders
```http
GET /orders/user/{userId}
```

### Get Order Book
```http
GET /orders/orderbook/{stockId}
```

---

## Trade APIs

### Get Recent Trades
```http
GET /trades/stock/{stockId}
```

---

# Swagger Documentation

After running the application:

```text
http://localhost:8080/swagger-ui.html
```

---

# How to Run Project

## Step 1
Clone repository:

```bash
git clone <your-github-repo-url>
```

---

## Step 2
Open project in IntelliJ IDEA

---

## Step 3
Create MySQL database:

```sql
CREATE DATABASE trading_engine;
```

---

## Step 4
Configure application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/trading_engine
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## Step 5
Run project

Run:

```text
StockTradingEngineApplication
```

---

# Sample BUY Order Request

```json
{
  "userId": 1,
  "stockId": 1,
  "price": 500,
  "quantity": 10
}
```

---

# Sample SELL Order Request

```json
{
  "userId": 1,
  "stockId": 1,
  "price": 300,
  "quantity": 5
}
```

---

# Sample API Response

```json
{
  "success": true,
  "message": "BUY Order Placed Successfully",
  "data": {
    "orderId": 1,
    "userName": "Tarun",
    "stockSymbol": "AAPL",
    "orderType": "BUY",
    "price": 500,
    "quantity": 10,
    "remainingQuantity": 5,
    "status": "PARTIAL"
  }
}
```

---

# Concurrency Handling

Implemented:
- Pessimistic Locking
- Transaction Management

This prevents:
- duplicate matching
- race conditions
- incorrect quantity updates

---

# Logging

Implemented using:
- SLF4J
- Logback

Logs track:
- order placement
- matching execution
- trade execution
- cancellations

---

# Validation

Implemented using:
- Jakarta Validation

Examples:
- invalid quantity prevention
- invalid price prevention
- null validation

---

# Author

Tarun Yendu  
Full Stack Java Developer