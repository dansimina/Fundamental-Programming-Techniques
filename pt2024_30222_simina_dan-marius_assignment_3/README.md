# PT2024_30222_Simina_Dan-Marius_Assignment_3

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
