# Task 12: Extend Vehicle Pricing and Passes

## A. Impact Analysis

New requirements add an e-bike flat fee, time-sensitive delivery-van pricing, a visitor one-day pass, and five floors with unequal capacities. Pricing affects the vehicle pricing behavior, entry/exit fee calculation, and the vehicle factory. The facility must store one slot array per floor; a single rectangular array assumes equal floor sizes and cannot model `{2, 3, 1, 4, 2}` without wasted or invalid cells. In the new design, each floor has its own `String[]` length. A switch-based original design would need edits to the factory and central price calculation for every new kind.

## B. Evaluate the Original Design

Adding a subclass alone is insufficient if a central factory or fee switch must also be edited. The original task-level model puts rates on types, but this makes non-hourly and time-window rules awkward. It cannot keep every tested class unchanged if the same classes contain pricing policy. The extension adds a `PricingRule` strategy and `Pass` interface so the tested Task11 source remains unchanged; the facility continues to hold a jagged layout and maps active registrations to tickets.

## C. Decision and Implementation

Decision: **MODIFY by extension**. `Vehicle` delegates to a `PricingRule`; `HourlyPricingRule`, `FlatPricingRule`, and `DeliveryVanPricingRule` contain separate algorithms. `EBike` selects a flat Rs 15 rule. `DeliveryVan` doubles the car first-hour/additional-hour charge for billed hour-starts in the interval [09:00, 17:00), with a doubled Rs 400 maximum. This time-window interpretation is an explicit assumption: each started hour is classified by its start time. `OneDayPass` is a `Pass` valid only on its date and makes that day's parking fee zero. The five-floor facility uses different capacities `{2, 3, 1, 4, 2}`.

## D. Defense and Trade-offs

Zero existing Task11 classes were modified; the extension is additive in Task12. A new vehicle type needs a new model class and a pricing rule/configuration, plus one registration in the facility factory; a further refactor could replace that switch with a registry to avoid even that edit. Captured runtime results verify the e-bike flat rate, delivery van rates inside and partly outside business hours, one-day-pass waiver, and five-floor dimensions in `output.txt`. Strategy objects make pricing testable and localized, at the cost of more small classes and indirection. The strategy demo warms up and averages 50,000 calls using `System.nanoTime()`.

## Alternative Concept and Complexity Analysis

An alternative is one long conditional in `Vehicle.calculateFee` based on a type string and local time. That is initially compact but spreads type-specific logic into a central method; adding `k` cases can mean up to $O(k)$ checks and requires editing the tested base class. The strategy design uses $O(1)$ dispatch and $O(1)$ extra rule state per vehicle, with $O(1)$ fee computation for the hourly/flat rules; delivery pricing scans billed hours and is $O(h)$ for `h` hours. Both facility designs use $O(S)$ space for `S` total slots; a jagged array stores only configured slots.