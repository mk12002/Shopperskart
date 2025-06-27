# Shopperskart: A Scalable Microservices-based E-Commerce Backend

## 🛒 Overview
Shopperskart is a production-ready, containerized e-commerce backend system built using microservices architecture. It integrates robust DevOps tools, distributed systems design, and secure service communication using Docker, Spring Boot, Kafka, Keycloak, MongoDB, PostgreSQL, Eureka, and more.

---

## 📐 Architecture

```plaintext
                            +--------------------------+
                            |     Grafana Dashboard    |
                            +------------+-------------+
                                         |
                              +----------v----------+
                              |   Prometheus Server  |
                              +----------+----------+
                                         |
+-----------+       +---------v---------+         +--------------+
| Zipkin UI |<----->|  Zipkin Tracing   |<------->| Spring Cloud |
+-----------+       +---------+---------+         |   Gateway    |
                                          +--------^-------------+
                                                   |
                                                   v
                                         +---------------------+
                                         |  Eureka Discovery   |
                                         |      Server         |
                                         +---------+-----------+
                                                   |
                                                   v
            +-------------------+      +------------------+     +------------------+
            |  Product Service  |<---->| Inventory Service |<--->|  Order Service   |
            +-------------------+      +------------------+     +------------------+
                      |                         ^                       |
                      v                         |                       v
               MongoDB (Atlas)         PostgreSQL (inventory)   PostgreSQL (order)

            +-----------------------+
            | Notification Service  |
            +----------+------------+
                       |
                       v
                  Kafka Broker <--> Zookeeper

              +-------------------+
              |   Keycloak Auth   |
              +---------+---------+
                        |
                        v
                 MySQL (Keycloak)
```

## 🧱 Microservices

### ✅ Product Service
- **Responsibilities**:
  - Add, update, delete, and fetch product information.
  - Supports querying by attributes like category and price.
- **Technology**: Spring Boot + MongoDB Atlas
- **Endpoints**:
  - `GET /products`
  - `POST /products`
  - `PUT /products/{id}`
  - `DELETE /products/{id}`

---

### ✅ Inventory Service
- **Responsibilities**:
  - Manage product stock.
  - Check product availability and update quantities.
- **Technology**: Spring Boot + PostgreSQL
- **Endpoints**:
  - `GET /inventory/check`
  - `POST /inventory/update`

---

### ✅ Order Service
- **Responsibilities**:
  - Process and store user orders.
  - Emit Kafka events upon successful order creation.
- **Technology**: Spring Boot + PostgreSQL + Kafka
- **Endpoints**:
  - `POST /orders`
  - `GET /orders/history`

---

### ✅ Notification Service
- **Responsibilities**:
  - Listen to Kafka topics for order events.
  - Send email/SMS notifications (extendable).
- **Technology**: Spring Boot + Kafka
- **Kafka Topic**: `order-events`

---

## 🛠 Infrastructure Components

### 🚪 API Gateway
- **Framework**: Spring Cloud Gateway
- **Responsibilities**:
  - Centralized routing to microservices.
  - Pre/post filters and load balancing.
- **Accessible at**: `http://localhost:8181`

---

### 🌐 Eureka Discovery Server
- **Framework**: Spring Cloud Netflix Eureka
- **Responsibilities**:
  - Service registry and discovery.
  - Enables dynamic communication between services.
- **Accessible at**: `http://localhost:8761`

---

### 🔒 Keycloak (Auth Server)
- **Responsibilities**:
  - Authentication and authorization using OAuth2.
  - Issues JWT tokens for secured microservice access.
- **Database**: MySQL (persistent storage)
- **Accessible at**: `http://localhost:8080`

---

### 📩 Kafka + Zookeeper
- **Kafka**: Message broker for inter-service events.
- **Zookeeper**: Coordination service for Kafka.
- **Used for**:
  - Order events → Notification Service

---

### 🔍 Zipkin
- **Purpose**: Distributed tracing
- **Collects**:
  - Latency data
  - Service-to-service request chains
- **Accessible at**: `http://localhost:9411`

---

### 📊 Prometheus + Grafana
- **Prometheus**: Scrapes metrics from services.
- **Grafana**: Dashboard for monitoring data.
- **Uses**:
  - Spring Boot Actuator endpoints
- **Accessible at**:
  - Prometheus: `http://localhost:9090`
  - Grafana: `http://localhost:3000`

---

## 🔁 Communication Flow 

1. The **client** interacts with the system by sending HTTP requests to the **API Gateway** (`localhost:8181`).
2. The **API Gateway**, built using Spring Cloud Gateway, uses route definitions to forward the request to the appropriate microservice (e.g., Product, Inventory, or Order).
3. Each microservice is registered with the **Eureka Discovery Server** (`localhost:8761`), allowing the Gateway and other services to resolve their network locations dynamically.
4. Upon receiving the request, the target microservice performs its logic:
   - **Product Service** fetches product data from **MongoDB Atlas**.
   - **Inventory Service** checks or updates stock using **PostgreSQL**.
   - **Order Service** processes orders and updates order status in **PostgreSQL**.
5. After an order is placed, the **Order Service** emits an event to **Kafka**.
6. The **Notification Service**, a Kafka consumer, listens for this event and processes it (e.g., sends email or logs).
7. **Keycloak** (`localhost:8080`) handles authentication and issues tokens. The API Gateway verifies tokens before forwarding requests.
8. All service calls are traced using **Zipkin** (`localhost:9411`) to analyze request paths and latency.
9. **Prometheus** (`localhost:9090`) scrapes metrics from all services using **Spring Boot Actuator**.
10. **Grafana** (`localhost:3000`) displays visual dashboards of real-time system metrics collected by Prometheus.

---

## 🧪 Technology Stack

| Layer                  | Technologies                                 |
|------------------------|----------------------------------------------|
| Microservices          | Spring Boot, Spring Web                      |
| Service Discovery      | Spring Cloud Netflix Eureka                  |
| API Gateway            | Spring Cloud Gateway                         |
| Messaging              | Apache Kafka + Zookeeper                     |
| Authentication         | Keycloak + MySQL                             |
| Product DB             | MongoDB Atlas                                |
| Inventory/Order DB     | PostgreSQL                                   |
| Tracing                | Zipkin                                       |
| Monitoring             | Prometheus, Grafana                          |
| Metrics Collection     | Spring Boot Actuator, Micrometer             |
| Containerization       | Docker, Docker Compose                       |

---

## ✨ Features

### 🎯 Core Features
- **Product Management**: Add, update, delete, and view product details via Product Service.
- **Inventory Tracking**: Check availability and adjust stock using Inventory Service.
- **Order Processing**: Place and manage orders through the Order Service.
- **Notifications**: Receive Kafka-driven notifications post-order placement.

### 🧩 Infrastructure Features
- **Centralized Routing**: All client traffic is routed via Spring Cloud Gateway.
- **Service Registration & Discovery**: Dynamic service resolution using Eureka.
- **Asynchronous Communication**: Kafka enables event-based architecture.
- **Secure Auth System**: OAuth2 and JWT token-based authentication with Keycloak.
- **Distributed Tracing**: Full request visibility using Zipkin.
- **Real-Time Monitoring**: Prometheus scrapes service metrics for Grafana dashboards.
- **Polyglot Persistence**: MongoDB for flexible product data, PostgreSQL for transactional data.

### 🧑‍💻 Developer-Friendly
- Fully containerized setup with Docker Compose.
- Modular architecture allows easy integration of new services.
- Actuator and Micrometer pre-configured for observability.
- Clean, loosely coupled codebase following best practices.
