# Task 11: Build and Test the Complete Parking System

The system is split into `parking.model`, `parking.service`, and `parking.app`. `ParkingFacility` owns a jagged slot array and a `HashMap` of active tickets keyed by registration number. Entry creates a known vehicle type, prevents duplicate active registrations, allocates the first free slot, and issues a process-wide atomic ticket number. Exit rejects invalid duration/energy before state changes, calculates the visitor charge, releases the slot, and removes the active ticket. A valid monthly pass makes the time-based fee zero for students/staff; EV energy remains separately chargeable.

Rates used because the prompt did not specify numeric parking slabs: TwoWheeler first hour Rs 10, each additional started hour Rs 8, daily cap Rs 100; Car and ElectricCar first hour Rs 30, each additional started hour Rs 20, daily cap Rs 200. Each started 24-hour segment is capped separately. EV energy costs Rs 12/kWh. Slot coordinates in service calls are zero-based; human-facing slot labels can be one-based.

## Test Table

The actuals and pass/fail values are printed by `Task11Demo` and captured in `output.txt` from a real run. It covers each vehicle category, last available slot, zero/exact-hour/daily-cap boundaries, invalid inputs, passes, duplicates, and full capacity.

## Day Scenario

Visitor TwoWheeler for 61 minutes costs Rs 10 + Rs 8 = Rs 18. A student with a valid pass parks a Car for 130 minutes and pays Rs 0 for parking. A visitor ElectricCar for one minute and 2 kWh costs Rs 30 + Rs 24 = Rs 54. The hand total is Rs 18 + Rs 0 + Rs 54 = Rs 72; the program prints the calculated total beside that explanation.

Encapsulation keeps slots and tickets private; inheritance shares Vehicle data and specializes rates; polymorphism chooses each rate implementation; abstraction hides fee calculation behind Vehicle and the pass contract. One limitation is that this educational fee calculation treats each 24-hour duration segment as a billing day, not calendar-midnight boundaries. Ticket IDs are unique within the running JVM; persistence across restarts would need a durable sequence/store.

## Alternative Concept and Complexity Analysis

For `n` active tickets, an array and an `ArrayList<ParkingTicket>` both take $O(n)$ space and $O(n)$ worst-case lookup time by registration. A `HashMap<String, ParkingTicket>` uses $O(n)$ space and average $O(1)$ lookup/exit access (worst-case collisions can degrade); it also has hash-table overhead. `LookupBenchmark` warms each structure and repeats last-element/last-key lookup 300 times for `n=1,000`, `10,000`, and `100,000`; the real average nanoseconds are saved separately in `lookup-timings.txt`. These microbenchmarks are directional only and depend on JIT, allocation, JVM, and machine load.