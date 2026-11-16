# Campus Smart-Parking Assignment

- Student: <Your Name>
- Register number: <RegisterNumber>
- Java: Temurin OpenJDK 25.0.4.1 verified; assignment code targets JDK 17+
- Build output: temporary folders under `$env:TEMP`

Run the following PowerShell commands from the repository root. Each task compiles independently.

| Task | Compile command | Run command | Captured result |
|---|---|---|---|
| 01 | `javac -d $env:TEMP\CSA09Task01Classes Task01/src/parking/app/Task01Analysis.java` | `java -cp $env:TEMP\CSA09Task01Classes parking.app.Task01Analysis` | Both search forms found index 9999. [output](Task01/output.txt) |
| 02 | `javac -d $env:TEMP\CSA09Task02Classes Task02/src/parking/app/*.java` | `java -cp $env:TEMP\CSA09Task02Classes parking.app.Task02Demo; java -cp $env:TEMP\CSA09Task02Classes parking.app.CastDemo` | Jagged 2/3/1 slots; 130 minutes rounds to 3 started hours. [output](Task02/output.txt) |
| 03 | `javac -d $env:TEMP\CSA09Task03Classes Task03/src/parking/service/ParkingFacility.java Task03/src/parking/app/ParkingFacilityBenchmark.java` | `java -cp $env:TEMP\CSA09Task03Classes parking.service.ParkingFacility` | Exact requested allocation/release output. [output](Task03/output.txt), [timing](Task03/timing.txt) |
| 04 | `javac -d $env:TEMP\CSA09Task04Classes Task04/src/parking/model/*.java Task04/src/parking/app/*.java` | `java -cp $env:TEMP\CSA09Task04Classes parking.app.Task04Demo` | Constructor overloads and copy constructor ran; copied ticket number matched. [output](Task04/output.txt) |
| 05 | `javac -d $env:TEMP\CSA09Task05Classes Task05/src/parking/model/*.java Task05/src/parking/service/*.java Task05/src/parking/app/*.java` | `java -cp $env:TEMP\CSA09Task05Classes parking.app.Task05Demo` | Static ticket numbers were unique in the run; occupancy returned to zero. [output](Task05/output.txt) |
| 06 | `javac -d $env:TEMP\CSA09Task06Classes Task06/src/parking/model/*.java Task06/src/parking/app/*.java` | `java -cp $env:TEMP\CSA09Task06Classes parking.app.Task06Demo` | Constructor trace: Vehicle, Car, ElectricCar. [output](Task06/output.txt) |
| 07 | `javac -d $env:TEMP\CSA09Task07Classes Task07/src/parking/model/*.java Task07/src/parking/app/*.java` | `java -cp $env:TEMP\CSA09Task07Classes parking.app.Task07Demo` | Cross-type equality was true; 130-minute car fee was Rs 90. [output](Task07/output.txt) |
| 08 | `javac -d $env:TEMP\CSA09Task08Classes Task08/src/parking/app/*.java` | `java -cp $env:TEMP\CSA09Task08Classes parking.app.FeeDemo; java -cp $env:TEMP\CSA09Task08Classes parking.app.PolymorphismDemo` | FeeDemo matched all four specified lines. [output](Task08/output.txt), [dispatch](Task08/polymorphism-output.txt) |
| 09 | `javac -d $env:TEMP\CSA09Task09Classes Task09/src/parking/service/*.java Task09/src/parking/model/*.java Task09/src/parking/app/*.java` | `java -cp $env:TEMP\CSA09Task09Classes parking.app.Task09Demo` | EV parking Rs 90 plus charging Rs 120. [output](Task09/output.txt) |
| 10 | `javac -d $env:TEMP\CSA09Task10Classes Task10/src/parking/model/*.java Task10/src/parking/service/*.java Task10/src/parking/app/*.java` | `java -cp $env:TEMP\CSA09Task10Classes parking.app.Task10Demo` | Invalid values rejected; Integer 127/128 identity pitfall demonstrated. [output](Task10/output.txt) |
| 11 | `javac -d $env:TEMP\CSA09Task11Classes Task11/src/parking/model/*.java Task11/src/parking/service/*.java Task11/src/parking/app/*.java` | `java -cp $env:TEMP\CSA09Task11Classes parking.app.Task11Demo; java -cp $env:TEMP\CSA09Task11Classes parking.app.LookupBenchmark` | All 15 test rows passed; hand total equalled Rs 72. [output](Task11/output.txt), [timings](Task11/lookup-timings.txt) |
| 12 | `javac -d $env:TEMP\CSA09Task12Classes Task12/src/parking/model/*.java Task12/src/parking/service/*.java Task12/src/parking/app/*.java` | `java -cp $env:TEMP\CSA09Task12Classes parking.app.Task12Demo` | E-bike, delivery van, one-day pass, and five-floor jagged facility ran. [output](Task12/output.txt) |

Create each temporary class folder before compilation with `New-Item -ItemType Directory -Force $env:TEMP\CSA09TaskXXClasses`.

## Report and Screenshots

See [report/COVER.md](report/COVER.md) and [report/REPORT.md](report/REPORT.md). Screenshot files are intentionally not fabricated; each task has a `screenshot-placeholder.txt` for the student to replace after taking a screenshot.