# Task 04: Demonstrate Constructors and Constructor Chaining

`ParkingTicket` owns ticket number, registration number, floor, slot, and entry time. The no-argument constructor calls the full constructor with `this(...)`; the two-argument overload also delegates to the full constructor and supplies defaults for location and time. The copy constructor calls the same full constructor, so the new object has equal field values but a different object identity. It duplicates the ticket number intentionally for this demonstration, not for issuing a real second active ticket.

In simple terms, constructors create a valid object in one go. `this(...)` avoids repeating field assignments. `this.field` names the current object's field when a parameter has a similar name.

## Stack and Heap

Local reference variables such as `fullTicket` are typically stored in a method's stack frame; the ticket objects they reference are allocated on the heap. Java passes the reference value by value when an object is passed to a method. Returning an object returns a reference to it. Actual JVM memory placement can be optimized, but this is the standard conceptual model for a viva.

## Alternative Concept and Complexity Analysis

An alternative is a public default constructor followed by setters, or a static factory method such as `ParkingTicket.create(...)`. Setters permit temporarily incomplete or inconsistent tickets unless every use validates them; factories make construction names expressive but still need a constructor internally. Constructor validation gives a valid state at creation. Creating `n` fixed-size tickets takes $O(n)$ total time and $O(n)$ heap space; constructing one is $O(1)$ time and space. The demo warms up and averages 100,000 constructions measured with `System.nanoTime()`; the sample is JVM- and machine-dependent and is not a formal benchmark.