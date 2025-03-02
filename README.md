# 🚀 Assignment Solutions

This repository contains solutions for multiple **Object-Oriented Programming (OOP)** assignments in **Java**. 

## 📂 Folder Structure

- `assignment1/` - **Polynomial Calculator**  
- `assignment2/` - **Queue Management Simulation**  
- `assignment3/` - **Orders Management System**  

---

## 📜 Assignment Details

### 🧮 Assignment 1: Polynomial Calculator (`assignment1/`)
This assignment involves implementing a **polynomial calculator** with a **graphical user interface**.

#### ✅ Implemented Features
- Polynomial operations: Addition, Subtraction, Multiplication, Division, Derivation, Integration.
- Graphical User Interface (GUI) using **Swing**.
- Polynomials stored as **Maps** for efficient retrieval.
- Regex-based input validation to extract coefficients correctly.
- **JUnit tests** for verifying operations.

#### ⚠️ Constraints
- Followed **Object-Oriented Design principles**.
- Used **Java naming conventions**.
- Implemented **MVC pattern** for better separation of concerns.

---

### 🔄 Assignment 2: Queue Management Simulation (`assignment2/`)
This assignment implements a **multithreaded simulation of a queue-based system**, optimizing waiting time.

#### ✅ Implemented Features
- Simulates **N clients arriving at Q queues**, being served, and exiting.
- Two queue allocation strategies:
  - **Shortest Queue Strategy** (assigns to the queue with the fewest tasks).
  - **Shortest Time Strategy** (assigns to the queue with the least waiting time).
- Real-time queue updates displayed in a **graphical interface**.
- Statistics tracking: Average waiting time, service time, peak hours.

#### ⚠️ Constraints
- Used **Multithreading** for queue processing.
- Implemented **Thread-safe structures** (`BlockingQueue`, `AtomicInteger`).
- Ensured **synchronized execution** for client allocation.

---

### 🏢 Assignment 3: Orders Management System (`assignment3/`)
This assignment develops a **warehouse order management system** using **layered architecture**.

#### ✅ Implemented Features
- **4-layered architecture**:
  - **Model** (Clients, Products, Orders, Bills).
  - **Business Logic** (Validation & processing).
  - **Data Access Layer** (Database interactions).
  - **GUI Layer** (Swing-based user interface).
- **CRUD operations**:
  - Add/Edit/Delete **Clients & Products**.
  - Create & process **Orders**.
  - Generate **Bills** for completed orders.
- **Reflection-based table generation**:
  - Extracts object properties dynamically.
  - Populates tables automatically.
- **Relational Database Integration**:
  - **MySQL database** with `Client`, `Product`, `Order`, `Bill` tables.
  - Stores all operations securely.

#### ⚠️ Constraints
- Followed **Layered Architecture Design**.
- Used **Reflection** for dynamic DB queries.
- Ensured **Data validation** (e.g., age verification, email format, stock availability).
- **Immutable Bills** using **Java Records**.

