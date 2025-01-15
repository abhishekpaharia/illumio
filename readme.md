# Illumio Assessment

## Assumptions

- the program only supports default log format, not custom and the only version that is supported is 2.
- Count of matches for each port/protocol combination operation calculates all Dstport/Protocol Combination of flow log data that matches in lookup table and their count.
- protocol are case insentive for doing matches in lookup table e.g TCP == tcp
- Tag are case sensitive therefore sv_P2 != sv_p2. This assumption is based on sample output of tag counts. if tag is case
  insensitive then sv_P2 should be either sv_p2 or SV_P2 in tag counts table.

## How to run

- minimum requirment
  - Java 19
  - Maven

### Steps to run

1. Clone the the repo
2. Open the terminal at illumio folder
3. Compile and Run
   ```bash
   mvn compile exec:java -Dexec.mainClass="com.paharia.abhishek.Main"
   ```

## Tests

There are 5 tests. Please check test methods description for more details. When your run Main Class, all test cases will executed.
Please read terminal console. It prints description of test and result of particular test

- testProtocolConversion()
- testLookupTable()
- testFlowLog()
- testFlowLogTagCount()
- testFlowLogPortPortocolCombination()

## Input files

There are 3 files. All 3 are in resorces folder.

- flow_logs.txt : it contains flow log data
- lookup_table.txt : It is look up table
- protocol-numbers-1.csv : This file contains mapping for protocol number to protocol keyword. It is downloaded from http://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml

## output files

output files are in "output" folder

- tag_count.txt : It contains tag counts
- port_protocol_combination_count.txt : It contains Port/Protocol Combination Counts. All Dstport/Protocol Combination of flow log that matches in lookup table and their count is presnet in this file.
