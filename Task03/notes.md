# Task 03: Implement Parking Slot Allocation and Release

The requested starter `ParkingFacility.java` was not present in the workspace, so this is a new implementation of the specified API and `main` behavior. The pseudocode below follows a clean BEGIN/END, INPUT/OUTPUT, IF/ELSE/ENDIF, FOR/ENDFOR structure suitable for the stated SIMATS-style format.

## Slot Allocation Pseudocode

```text
BEGIN Allocate
    INPUT vehicleNo
    IF vehicleNo is null or blank
        RETURN INVALID_INPUT
    ENDIF
    FOR each floor
        FOR each slot
            IF slot contains vehicleNo
                RETURN NO_ALLOCATION
            ENDIF
        ENDFOR
    ENDFOR
    FOR floor FROM first floor TO last floor
        FOR slot FROM first slot TO last slot on floor
            IF slot is occupied
                CONTINUE
            ELSE
                store vehicleNo in slot
                OUTPUT floor and slot
                RETURN floor, slot
            ENDIF
        ENDFOR
    ENDFOR
    OUTPUT NO_SLOT_FREE
    RETURN null
END
```

The `continue` skips occupied spaces. Returning after the first free space ends both scans immediately. If all spaces are occupied, the method returns `null`. Duplicate active registrations are rejected by returning `null` without changing the array.

## Release and Fee Pseudocode

```text
BEGIN Release
    INPUT floor, slot
    IF floor is outside facility bounds
        RETURN false
    ENDIF
    IF slot is outside this floor's bounds
        RETURN false
    ENDIF
    IF slot is already free
        RETURN false
    ENDIF
    clear slot
    RETURN true
END
```

```text
BEGIN CalculateFee
    INPUT minutes, firstHourRate, additionalHourRate, dailyMaximum
    IF minutes is negative
        RETURN INVALID_DURATION
    ENDIF
    hours = (minutes + 59) / 60 using integer arithmetic
    IF hours equals 0
        RETURN 0
    ENDIF
    fee = firstHourRate + (hours - 1) * additionalHourRate
    FOR each started parking day
        fee = minimum(fee for that day, dailyMaximum)
    ENDFOR
    RETURN fee
END
```

For fee slabs, `break` can stop a day-by-day billing loop once all duration is consumed; `continue` skips a day with no billable time; `return` rejects a negative duration or returns the final capped fee. Fee calculation is specified here for the design and implemented in a later task.

## Validation and Complexity

The captured main output must be exactly the six lines specified in the assignment. `release` checks both dimensions before indexing, so negative and out-of-range floor/slot values return `false`; an already-free slot also returns `false`.

For `F` floors and `S` total slots, duplicate detection and first-free allocation are each $O(S)$ worst-case time. Release is $O(1)$. The facility stores $O(S)$ slot references. A separate benchmark warms up the JVM and averages repeated allocate/release pairs. Its measured value is recorded separately in `timing.txt` so the required `output.txt` remains byte-for-byte the expected console transcript.

## Alternative Concept and Complexity Analysis

An alternative keeps a free-count for each floor and a stack of free slot coordinates per floor. Allocation can pop from a non-empty floor stack and release can push the coordinate back, giving $O(1)$ average work for each operation when a suitable floor is directly selected. For first-floor-first policy, finding the next non-empty floor can cost $O(F)$, or a secondary queue/bitset can help. The alternative uses $O(S + F)$ extra bookkeeping beyond the actual occupancy model; the direct scan uses only $O(S)$ storage and has simpler correctness reasoning. Under the required exact six-line stdout, timing is emitted by a separate repeated benchmark and not mixed into `output.txt`.