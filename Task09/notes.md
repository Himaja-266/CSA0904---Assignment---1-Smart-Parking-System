# Task 09: Apply Abstraction with Abstract Class and Interface

`Vehicle` is abstract because a generic vehicle has shared registration state but no single correct hourly rate. Its `ratePerHour()` is abstract; `calculateFee` is concrete shared behavior that uses the subtype's rate. `Chargeable` and `Payable` are interfaces because charging and paying are capabilities that can be combined with a class hierarchy. `ElectricCar` extends `Car` and implements `Chargeable`; it inherits `Payable` as well. This gives one class behavior from the superclass and independent contracts from multiple interfaces.

`Chargeable.chargingSummary` and `Payable.paymentStatus` are default methods, while `defaultRatePerKWh` and `isValidAmount` are interface static methods called through the interface name. The demo upcasts the EV to `Vehicle`, then safely uses its known concrete type for payment and charging.

## Alternative Concept and Complexity Analysis

An alternative makes `Vehicle` an interface with default methods and `Chargeable` an abstract class. A vehicle interface cannot own per-instance constructor-initialized registration state (interface fields are constants), and Java classes can implement multiple interfaces but extend only one class. Making `Chargeable` an abstract class would prevent a class already extending another base class from inheriting that capability. The chosen design keeps common state in one abstract class and lets capabilities compose.

Fee and interface calls here take $O(1)$ time and $O(1)$ additional space per call; each vehicle object uses $O(1)$ fields in addition to its string. The demo warms up and averages 50,000 abstract-dispatch calls with `System.nanoTime()`; local timings vary with JIT and hardware.