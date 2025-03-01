# PT2024_30222_Simina_Dan-Marius_Assignment_2 

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
