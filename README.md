# 📦 Inventory Management API

A scalable **Spring Boot REST API** for managing products, categories, and inventory with:

* 🔐 JWT Authentication
* 🛡️ Role-Based Access Control (RBAC)
* ⚡ Redis Caching for performance optimization

---

## 🚀 Features

* 🔐 Secure Authentication (JWT)
* 🛡️ Role-Based Authorization (ADMIN, MANAGER, USER)
* 📦 Product Management
* 🗂️ Category Management
* 📊 Inventory Tracking & Logs
* ⚡ Redis Caching for high-volume endpoints
* 🔎 Search & Filtering
* 📉 Low Stock Alerts
* ✅ Method-level security using `@PreAuthorize`

---

## 🧑‍💻 Tech Stack

* **Backend:** Spring Boot
* **Security:** Spring Security + JWT
* **Caching:** Redis
* **API Docs:** Swagger / OpenAPI

---

## 🔐 Authentication

### Login

```http
POST /auth/login
```

#### Request

```json
{
  "username": "admin",
  "password": "password"
}
```

#### Response

```json
{
  "token": "jwt-token",
  "username": "admin",
  "role": "ADMIN"
}
```

👉 Use token in headers:

```http
Authorization: Bearer <token>
```

---

## 🛡️ Role-Based Access Control (RBAC)

| Feature / Endpoint Type | ADMIN | MANAGER | USER |
| ----------------------- | ----- | ------- | ---- |
| Products - Read         | ✅     | ✅       | ✅    |
| Products - Create       | ✅     | ❌       | ❌    |
| Products - Update       | ✅     | ❌       | ❌    |
| Products - Delete       | ✅     | ❌       | ❌    |
| Low Stock Products      | ✅     | ✅       | ❌    |
| Categories              | ✅     | ❌       | ❌    |
| Inventory               | ✅     | ✅       | ❌    |
| User Registration       | ✅     | ✅       | ✅    |

---

## ⚡ Redis Caching

Caching is applied to frequently accessed and large datasets:

* `/products`
* `/products/search`
* `/products/category/{categoryId}`

### Benefits:

* 🚀 Faster response time
* 📉 Reduced database load
* ⚡ Improved scalability

---

## 📌 Key API Endpoints

### 📦 Products

| Method | Endpoint                          | Access         |
| ------ | --------------------------------- | -------------- |
| GET    | `/products`                       | All            |
| GET    | `/products/{id}`                  | All            |
| GET    | `/products/sku/{sku}`             | All            |
| GET    | `/products/search?name=`          | All            |
| GET    | `/products/category/{categoryId}` | All            |
| GET    | `/products/low-stock`             | ADMIN, MANAGER |
| POST   | `/products`                       | ADMIN          |
| PATCH  | `/products/{id}`                  | ADMIN          |
| DELETE | `/products/{id}`                  | ADMIN          |

---

### 🗂️ Categories

| Method | Endpoint           | Access |
| ------ | ------------------ | ------ |
| GET    | `/categories`      | All    |
| GET    | `/categories/{id}` | All    |
| POST   | `/categories`      | ADMIN  |
| PUT    | `/categories/{id}` | ADMIN  |
| DELETE | `/categories/{id}` | ADMIN  |

---

### 📊 Inventory

| Method | Endpoint                              | Access         |
| ------ | ------------------------------------- | -------------- |
| POST   | `/inventory/update`                   | ADMIN, MANAGER |
| GET    | `/inventory/logs/recent`              | ADMIN, MANAGER |
| GET    | `/inventory/logs/product/{productId}` | ADMIN, MANAGER |

---

### 👤 Users

| Method | Endpoint          | Access |
| ------ | ----------------- | ------ |
| POST   | `/users/register` | Public |

---

## 📘 API Documentation

Swagger UI:

```bash
http://localhost:8080/swagger-ui/index.html
```

OpenAPI Spec:

```bash
http://localhost:8080/v3/api-docs
```

---

## 🛠️ Running the Project

```bash
# Clone repository
git clone https://github.com/YugTank/InventoryManagement

# Navigate to project
cd inventory-management

# Run application
./mvnw spring-boot:run
```

---

## 📈 Future Improvements

* Pagination & Sorting
* Rate Limiting
* Dockerization
* API Versioning
* Unit & Integration Testing

---

