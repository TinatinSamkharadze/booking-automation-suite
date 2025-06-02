Booking Automation Suite
# 🧪 Project N2: End-to-End UI Test Automation for Booking.com

## 🎯 Objective
Create a clean, maintainable, and scalable UI automation framework using **Playwright + Java + TestNG** to test core workflows on Booking.com.

The framework supports:
- ✅ Data-driven tests via SQL
- ✅ Backend edge case simulation using network mocking
- ✅ UI responsiveness validation
- ✅ Cross-browser testing

---

## 🔧 Tech Stack

| Tool      | Purpose                                |
|-----------|----------------------------------------|
| Java      | Core language                          |
| Playwright| Headless browser automation            |
| TestNG    | Test execution framework               |
| Allure    | Reporting with steps, screenshots, etc |
| SQL/JDBC  | External test data source              |
| MyBatis   | SQL mapping to POJOs                   |
| Chrome, WebKit | Multi-browser testing             |

---

## 📁 Repository Name: `booking-automation-suite`

---


## 🧩 Architecture Overview

- `pages/`: Contains only **Playwright selectors** per page.
- `steps/`: Implements all **UI actions and assertions** using fluent interfaces.
- `tests/`: Calls **step methods** for readability and reusability.

**Benefit**: Locator changes do not affect logic, and step logic is reusable.

---

## ✅ Test Suites

### 1️⃣ `CoreFunctionalityTests.java`
Tests core Booking.com workflows:
| Test | Description |
|------|-------------|
| `searchTest` | Fills search field and validates hotel results |
| `dateSelectionTest` | Uses date picker to select check-in/check-out dates |
| `filterApplicationTest` | Applies filters like "Free Breakfast", price, stars |
| `sortByReviewScoreTest` | Sorts and validates top-rated properties |
| `propertyDetailsConsistencyTest` | Checks match between listing and details page |

---

### 2️⃣ `UIResponsivenessTests.java`

Validates layout across multiple viewports:

| Viewport          | Checks                                      |
|-------------------|---------------------------------------------|
| Desktop (1920x1080)| Grid layout, full nav, no hamburger menu   |
| Tablet (768x1024) | Stacked footer, visible search bar          |
| Mobile (375x667)  | Hamburger menu, sticky header               |

---

### 3️⃣ `DataDrivenTests.java`

UI tests driven by SQL data using TestNG `@DataProvider`.

- **SQL Table**: `booking_cases` with destination, dates, guests
- **Tool**: JDBC / MyBatis
- **Behavior**: One test run per row in DB

### 4️⃣ `BookingMockTests.java`

Mocks Booking.com APIs using Playwright’s network interception (`page.route()`).

| Mock Scenario           | Purpose                                      |
|-------------------------|----------------------------------------------|
| Empty hotel list        | Test "No Results" UI                         |
| Slow response (delay)   | Validate loaders and spinner visibility      |
| 500 error               | Ensure fallback messaging or retry UI        |



