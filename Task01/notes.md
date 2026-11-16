# Task 01: Analyse the Parking Problem and Identify Objects

## Problem, Inputs, and Outputs

The system accepts a vehicle registration number and type, the driver's category and pass status, a requested entry or exit action, and (at exit) the recorded entry time and exit time. An EV may also use a charging slot and report energy consumed in kWh. Facility configuration supplies the number of floors and the slot count on each floor.

On successful entry, the system should return a unique ticket and assigned floor/slot. On exit, it should release that slot and return the fee. Invalid requests should be rejected with a clear result rather than changing facility state. Task01's Java program uses synthetic records only to time registration-number searches; it does not simulate parking or calculate fees.

## Candidate Objects and Responsibilities

| Noun | Candidate class | Responsibility |
|---|---|---|
| Parking facility | `ParkingFacility` | Own floor/slot layout and coordinate entry and exit. |
| Floor | `Floor` | Represent one level and its fixed set of slots. |
| Slot | `ParkingSlot` | Track one vehicle or vacancy and whether charging is available. |
| Vehicle | `Vehicle` | Hold common registration details; parent for vehicle kinds. |
| Two-wheeler, car, electric car | `TwoWheeler`, `Car`, `ElectricCar` | Represent supported vehicle kinds and their differing rules. |
| Parking visit | `ParkingTicket` | Connect a unique ticket number, vehicle, slot, and entry time. |
| Driver category | `Student`, `Staff`, `Visitor` | Represent eligibility for a monthly pass or visitor pricing. |
| Pass | `MonthlyPass` | Represent a pass holder and the pass validity period. |
| Fee rule | `PricingRule` | Calculate time-based charges, daily maximum, and EV charging separately. |

These are candidates rather than a claim that every noun must become a class. A floor object can be omitted if the facility's jagged slot array already gives floors a clear boundary. Student and staff could instead be represented by one `User` plus a category value if they have no distinct behaviour.

## Verbs and Candidate Methods

`enter` requests parking; `allocateSlot` finds and occupies a suitable free slot; `issueTicket` creates a unique ticket and records entry time; `exit` ends a visit; `releaseSlot` makes a slot available; `calculateFee` computes the time charge; `chargingFee` bills EV energy; `hasValidPass` checks eligibility; and `validateRegistration`, `validateDuration`, and `validateSlot` reject invalid inputs.

## Business Rules and Assumptions

- A slot has at most one active vehicle, and an active registration number appears in at most one slot.
- Ticket IDs increase from a static counter and are never reused, including after a vehicle exits.
- Every started hour is charged: integer hours are `(minutes + 59) / 60`; no floating-point division or cast is used for this ceiling calculation.
- The applicable vehicle rate has a first-hour and additional-hour slab, with a daily maximum. The precise rates are to be supplied by the later fee task.
- Staff and students with a currently valid monthly pass pay no time-based parking fee. Visitors pay the full fee. EV energy charges are separate at Rs 12/kWh, including for a pass holder unless the assignment later says otherwise.
- A facility configuration such as 1x20 means one floor with 20 slots; 3x50 means three floors with 50 slots each; 5x100 means five floors with 100 slots each. Floors and slots may have different sizes when represented by a jagged array.
- Public slot labels are assumed to be one-based for people. Internal array indexes are zero-based, and conversion happens at the input/output boundary.
- Times on one visit are assumed to use the same local time basis. Duration must be non-negative. Unknown vehicle kinds, out-of-range slots, and invalid registration values are rejected before state changes.
- The input/output description is a design contract, not an implemented full system in Task01.

## Alternative Concept and Complexity Analysis

An alternative is to keep parallel arrays such as `regNo[]`, `type[]`, and `entryTime[]` instead of storing one object per vehicle. Both representations require $O(n)$ time for an unsorted registration-number search among $n$ active vehicles. Adding a new attribute to parallel arrays requires allocating and maintaining another array and keeping every index aligned; with objects, the attribute can be added to the record/class and its construction/access can be updated in one place. Both use $O(n)$ space asymptotically. Per vehicle, parallel arrays store the $k$ attribute values, while objects store the same values plus object/reference overhead; the exact byte cost depends on the JVM.

Parallel-array pseudocode:

```text
INPUT targetRegNo
FOR index FROM 0 TO regNo.length - 1
	IF regNo[index] equals targetRegNo
		RETURN index
	ENDIF
ENDFOR
RETURN NOT_FOUND
```

Object-array pseudocode:

```text
INPUT targetRegNo
FOR index FROM 0 TO vehicles.length - 1
	IF vehicles[index].regNo equals targetRegNo
		RETURN index
	ENDIF
ENDFOR
RETURN NOT_FOUND
```

`Task01Analysis` measures 1,000 worst-case searches across 10,000 synthetic records with `System.nanoTime()`. This is a small, single-run timing sample, not a controlled benchmark; JVM warm-up, garbage collection, and machine load can affect the result. Each search is $O(n)$ time and uses $O(1)$ extra space; the stored sample data is $O(n)$ space.

From the repository root in PowerShell, compile with `New-Item -ItemType Directory -Force build/Task01; javac -d build/Task01 Task01/src/parking/app/Task01Analysis.java`, then run `java -cp build/Task01 parking.app.Task01Analysis`.