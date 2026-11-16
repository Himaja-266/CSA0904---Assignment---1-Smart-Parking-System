# Task 02: Choose Data Types and Jagged Array Representation

## Data Types and Representation

| Data | Type | Reason |
|---|---|---|
| Floor and slot index | `int` | Array indexes and practical slot counts are whole numbers; validation checks bounds before indexing. |
| Vehicle/registration number | `String` | Registration numbers contain letters and digits, and must preserve leading zeroes. |
| Entry time | `long` | Epoch milliseconds are integral and fit a wide time range; production code can use `Instant` for richer date/time handling. |
| Duration | `long` minutes | Whole minutes and a wide range; reject negative values before fee calculation. |
| Fee | `long` rupees | This assignment's rates are whole rupees, avoiding binary floating-point rounding in money. Use integer minor units if fractional rates are introduced. |

`String[][]` is jagged: each outer element is one floor and can have a different slot count. This naturally represents floors with different capacities. A non-null string means occupied; `null` means free. The demo uses three floors with 2, 3, and 1 slots. `Arrays.deepToString` displays that layout.

The demo accepts a registration number as the first command-line argument, or uses `TN02CD2222` if none is supplied. No `Scanner` is needed for this non-interactive demonstration.

## Casting and Fee Arithmetic

For 130 minutes, `130 / 60` is integer division and produces 2, which undercharges a started third hour. The correct integer ceiling is `(130 + 59) / 60`, which produces 3. Casting `130 / 60` to `double` after the division does not restore the discarded fraction: `(double) (130 / 60)` is still `2.0`. `(double) 130 / 60` gives an approximation of 2.166..., but floating point is unnecessary and risks rounding errors in money calculations. Cast only at a boundary where a different representation is required, not to perform billing arithmetic.

## Alternative Concept and Complexity Analysis

An alternative is one flat array plus a floor-offset table. If floor `f` has a different capacity, compute `flatIndex = floorStart[f] + slot`; `floorStart` is a prefix sum of earlier floor sizes. The simpler `floor * slotsPerFloor + slot` formula works only when every floor has the same capacity. `ArrayList<String[]>` is another option when floors need to be added dynamically; its array elements still represent jagged floors.

Pseudocode for a variable-capacity flat array:

```text
INPUT floor, slot
IF floor or slot is outside its floor's bounds
    RETURN INVALID
ENDIF
flatIndex = floorStart[floor] + slot
RETURN flatSlots[flatIndex]
```

The demo scans a jagged layout with `F` floors and `S` total slots in $O(F + S)$ worst-case time (the floor traversal contributes `F`, and all slot checks contribute `S`); it uses $O(F + S)$ storage for the layout. With a flat array and prefix offsets, direct validated lookup is $O(1)$ time and $O(F + S)$ storage, while building the offset table costs $O(F)$. `Task02Demo` warms up the search and measures 10,000 repeated lookups; its average nanoseconds per operation is a local JVM sample, not a portable performance guarantee.

Compile from the repository root in PowerShell with `javac -d $env:TEMP\CSA09Task02Classes Task02/src/parking/app/*.java`, then run `java -cp $env:TEMP\CSA09Task02Classes parking.app.Task02Demo` and `java -cp $env:TEMP\CSA09Task02Classes parking.app.CastDemo`.