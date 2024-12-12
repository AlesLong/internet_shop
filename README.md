Project Overview
This project is an online shop application developed using Spring Boot, JPA, MySQL, and Liquibase for managing e-commerce functionalities.
It supports CRUD operations for goods and orders, with clear separation of services and controllers for scalability and maintenance.

Features:
Database: MySQL with schema versioning via Liquibase.
API Testing: Postman collections for testing API endpoints.
Transactional Management: Ensures data consistency using Spring @Transactional annotations.

Endpoints:

Goods Management:

GET /api/goods: Retrieve all goods.
GET /api/goods/{id}: Fetch a good by ID.
POST /api/goods: Add a new good.
PUT /api/goods/{id}: Update a good.

Order Management:

POST /api/orders: Create a new order.
PUT /api/orders/{orderId}: Update an order.
PUT /api/orders/{id}/pay: Mark an order as paid.
DELETE /api/orders/{orderId}/items/{itemId}: Remove an item from an order.
DELETE /api/orders/{id}: Delete an order.
GET /api/orders: List all orders.
GET /api/orders/{orderId}: Retrieve an order by ID.
JSON Example for Creating or Updating an Order

Development Details:

Database Design:

Good table stores product details (name, quantity).
Order and OrderItem tables handle order data and relationships.
Liquibase: Manages database migrations with changelog files for schema evolution.

Testing:

Comprehensive unit tests for controllers and services.
MockMVC for API testing.

Tools and Technologies:

Spring Boot for backend framework.
Liquibase for database migration.
Postman for testing API endpoints.
JUnit/Mockito for unit tests.
MySQL for relational database storage.
