# 💸 Expenses API

A RESTful API for managing personal expenses, built with Java and Spring Boot. Supports expense tracking by category and place, date range filtering, total calculations, and CSV export.

---

## 🛠️ Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySQL**
- **Springdoc OpenAPI (Swagger UI)**
- **Maven**

---

## ✨ Features

- Create, read, update, and delete expenses
- Filter expenses by category or place
- Filter expenses by date range
- Calculate totals (all expenses, by category, by date range)
- Export all expenses to a CSV file
- Interactive API documentation via Swagger UI

---

## ⚙️ How to Run

### Prerequisites

- Java 17+
- MySQL running locally
- Maven

### 1. Clone the repository

```bash
git clone https://github.com/juniormaximiano/juno-expenses.git
cd juno-expenses
```

### 2. Configure the database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expenses_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Make sure the database `expenses_db` exists before running the app.

### 3. Run

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 📋 Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/expenses` | Create a new expense record |
| `GET` | `/expenses` | Retrieve all registered expenses |
| `GET` | `/expenses/sorted` | Retrieve all expenses sorted by date descending |
| `GET` | `/expenses/{id}` | Retrieve a specific expense by its identifier |
| `PUT` | `/expenses/{id}` | Update an existing expense |
| `DELETE` | `/expenses/{id}` | Delete an expense by its identifier |
| `GET` | `/expenses/filter/category?category=` | Retrieve expenses filtered by category |
| `GET` | `/expenses/filter/place?place=` | Retrieve expenses filtered by place name |
| `GET` | `/expenses/total` | Calculate the total amount of all expenses |
| `GET` | `/expenses/total/category?category=` | Calculate the total amount for a specific category |
| `GET` | `/expenses/filter/date-range` | Retrieve expenses within a date range |
| `GET` | `/expenses/total/date-range` | Calculate total expenses within a date range |
| `GET` | `/expenses/export` | Export all expenses to a CSV file |

---

## 📦 Example JSON

### Create / Update Expense — Request Body

```json
{
  "description": "Weekly groceries",
  "amount": 157.90,
  "date": "2025-06-10",
  "category": "FOOD",
  "place": "Mercado Extra"
}
```

### Expense Response

```json
{
  "id": 1,
  "description": "Weekly groceries",
  "amount": 157.90,
  "date": "2025-06-10",
  "category": "FOOD",
  "place": "Mercado Extra"
}
```

### Total Response

```json
{
  "total": 1284.50
}
```

### Total Per Category Response

```json
{
  "category": "FOOD",
  "total": 540.00
}
```

---

## 📤 CSV Export

Sending a `GET` request to `/expenses/export` returns a downloadable `.csv` file with all registered expenses.

```
GET /expenses/export
```

Response headers:
```
Content-Disposition: attachment; filename=Expenses.csv
Content-Type: text/plain
```

---

## 📂 Categories

Available values for the `category` field:

- `FOOD`
- `TRANSPORT`
- `GYM`
- `HEALTH`
- `EDUCATION`
- `ENTERTAINMENT`
- `LEISURE`
- `OTHER`