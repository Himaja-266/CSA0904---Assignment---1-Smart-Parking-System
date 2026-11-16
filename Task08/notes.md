# Task 08: Demonstrate Polymorphic Parking Fees

`calculateFee(int)` and `calculateFee(LocalTime, LocalTime)` are overloads: the compiler selects one from the argument types at the call site. A `Car` reference is upcast into a `Vehicle` variable and placed in a `Vehicle[]`. At runtime the abstract `ratePerHour()` call inside the base fee method dispatches to the actual object's implementation. For the trace shown in `PolymorphismDemo`, the declared type is `Vehicle`, actual type is `Car`, `Vehicle.calculateFee(int)` runs, then `Car.ratePerHour()` supplies 30.0.

The requested `FeeDemo.java` starter was absent, so a new file was created with the specified `main` behavior. `calculateFee(130)` uses integer arithmetic: `(130 + 59) / 60` is 3. The EV adds 10 kWh at Rs 12/kWh. The final line prints `true` because `equals` accepts any `Vehicle` subtype (`instanceof Vehicle`) and compares only `regNo`, not `getClass()`.

That equality is defensible for registration identity: the same plate refers to the same registered vehicle even if one reference is typed as `Car` and another as `ElectricCar`. The full system must still reject parking the same registration twice simultaneously. If vehicle subtype is part of domain identity, `getClass()` equality would be stricter, but that is not the rule demonstrated here.

## Alternative Concept and Complexity Analysis

An alternative is an `instanceof` and cast chain in one fee routine. It can require up to `k` type checks for `k` kinds, or $O(k)$ worst-case dispatch work, and adding a vehicle type edits the central chain. Virtual method dispatch is conceptually $O(1)$ and puts each rate beside its subtype. Both use $O(1)$ extra space per call; the chain may have fewer class files but grows in code size and maintenance burden as types increase. The separate demo warms up and averages 50,000 calls using `System.nanoTime()`.