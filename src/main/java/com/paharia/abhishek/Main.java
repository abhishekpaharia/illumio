package com.paharia.abhishek;

import java.net.URL;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.testProtocolConversion();
        main.testLookupTable();
        main.testFlowLog();
        main.testFlowLogTagCount();
        main.testFlowLogPortPortocolCombination();
    }

    /*
     * Test 1 : Protocol Conversion
        1. Read the protocol numbers from the file protocol-numbers-1.csv
        2. Create a map of protocol number to protocol name
     */
    public void testProtocolConversion() {
        String testStartText = """
                ----------------------------------------------------------------
                Test 1 : Protocol Conversion
                    1. Read the protocol numbers from the file protocol-numbers-1.csv
                    2. Create a map of protocol number to protocol name

                    Following is the map:
                """;
        System.out.println(testStartText);

        String protocolFile = "protocol-numbers-1.csv";
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(protocolFile);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + protocolFile);
        }
        ProtocolConvertor protocolConvertor = new ProtocolConvertor(resource.getFile());
        System.out.println(protocolConvertor.getProtocolMap());

        String testEndText = """

                Test 1 completed successfully
                ----------------------------------------------------------------
                """;
        System.out.println(testEndText);
    }

    /*
     * Test 2 : (dstport, protocol) to Tag Mapping
        1. Read the lookup table from the file lookup_table.txt
        2. create a map of (dstport, protocol) to tag
     */
    public void testLookupTable() {
        String testStartText = """
                ----------------------------------------------------------------
                Test 2 : (dstport, protocol) to Tag Mapping
                    1. Read the lookup table from the file lookup_table.txt
                    2. create a map of (dstport, protocol) to tag

                    Following is the map:
                """;
        System.out.println(testStartText);

        String lookupTable = "lookup_table.txt";
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(lookupTable);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + lookupTable);
        }
        TagMapper tagMapper = new TagMapper(resource.getFile());
        System.out.println(tagMapper.getTagMap());

        String testEndText = """

                Test 2 completed successfully
                ----------------------------------------------------------------
                """;
        System.out.println(testEndText);
    }

    /*
     * Test 3 : Flow Log Processing
        1. Read the flow logs from the file flow_logs.txt
        2. create a List of flow logs
     */
    public void testFlowLog() {
        String testStartText = """
                ----------------------------------------------------------------
                Test 3 : Flow Log Processing
                    1. Read the flow logs from the file flow_logs.txt
                    2. create a List of flow logs

                    Following is the List:
                """;
        System.out.println(testStartText);

        String flowLogFile = "flow_logs.txt";
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(flowLogFile);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + flowLogFile);
        }
        FlowLogProcessor flowLogProcessor = new FlowLogProcessor(resource.getFile());
        System.out.println(flowLogProcessor.getFlowLog());

        String testEndText = """

                Test 3 completed successfully
                ----------------------------------------------------------------
                """;
        System.out.println(testEndText);

    }

    /*
     * Test 4 : Flow Log Processing to get Tag Count
        1. Read the flow logs from the file flow_logs.txt
        2. Read the lookup table from the file lookup_table.txt
        3. Read the protocol numbers from the file protocol-numbers-1.csv
        4. create tag to tag count map
        5. write the tag count to output/tag_count.txt
     */
    public void testFlowLogTagCount() {
        String testStartText = """
                ----------------------------------------------------------------
                Test 4 : Flow Log Processing to get Tag Count
                    1. Read the flow logs from the file flow_logs.txt
                    2. Read the lookup table from the file lookup_table.txt
                    3. Read the protocol numbers from the file protocol-numbers-1.csv
                    4. create tag to tag count map
                    5. write the tag count to output/tag_count.txt

                    Following is the tag count map:
                """;
        System.out.println(testStartText);

        String flowLogFile = "flow_logs.txt";
        String protocolFile = "protocol-numbers-1.csv";
        String lookupTable = "lookup_table.txt";
        
        TagMapper tagMapper = new TagMapper(getResourcePath(lookupTable));
        ProtocolConvertor protocolConvertor = new ProtocolConvertor(getResourcePath(protocolFile));
        FlowLogProcessor flowLogProcessor = new FlowLogProcessor(getResourcePath(flowLogFile), protocolConvertor, tagMapper);

        // get tag to tag count map
        Map<String, Long> tagCount = flowLogProcessor.getTagCount();
        System.out.println(tagCount);

        System.out.println();
        System.out.println("Writing tag count to file");
        CustomCsvWriter customCsvWriter = new CustomCsvWriter();
        String outputFilePath = customCsvWriter.writeTagCount(tagCount, "tag_count.txt");
        System.out.println("Target file path: " + outputFilePath);

        String testEndText = """

                Test 4 completed successfully
                ----------------------------------------------------------------
                """;
        System.out.println(testEndText);
        
    }

    /*
     * Test 5 : Flow Log Processing to get port/protocal combination Count present in lookup table
        1. Read the flow logs from the file flow_logs.txt
        2. Read the lookup table from the file lookup_table.txt
        3. Read the protocol numbers from the file protocol-numbers-1.csv
        4. create (port, protocol) to PortPortocolAggregator map. PortPortocolAggregator contains the count of the port/protocol combination
        5. write the (port, protocol) combination count to output/port_protocol_combination_count.txt
     */
    public void testFlowLogPortPortocolCombination() {
        String testStartText = """
                ----------------------------------------------------------------
                Test 5 : Flow Log Processing to get port/protocal combination Count present in lookup table
                    1. Read the flow logs from the file flow_logs.txt
                    2. Read the lookup table from the file lookup_table.txt
                    3. Read the protocol numbers from the file protocol-numbers-1.csv
                    4. create (port, protocol) to PortPortocolAggregator map. PortPortocolAggregator contains the count of the port/protocol combination
                    5. write the (port, protocol) combination count to output/port_protocol_combination_count.txt

                    Following is the (port, protocol) to PortPortocolAggregator map. PortPortocolAggregator contains the count of the port/protocol combination:
                """;
        System.out.println(testStartText);

        String flowLogFile = "flow_logs.txt";
        String protocolFile = "protocol-numbers-1.csv";
        String lookupTable = "lookup_table.txt";
        
        TagMapper tagMapper = new TagMapper(getResourcePath(lookupTable));
        ProtocolConvertor protocolConvertor = new ProtocolConvertor(getResourcePath(protocolFile));
        FlowLogProcessor flowLogProcessor = new FlowLogProcessor(getResourcePath(flowLogFile), protocolConvertor, tagMapper);

        // get (port, protocol) to PortPortocolAggregator map. PortPortocolAggregator contains the count of the port/protocol combination
        Map<String, PortPortocolAggregator> portProtocolCombinationCount = flowLogProcessor.getPortProtocolCombinationCount();
        System.out.println(portProtocolCombinationCount);

        System.out.println();
        System.out.println("Writing (port, protocol) combination count to file");
        CustomCsvWriter customCsvWriter = new CustomCsvWriter();
        String outputFilePath = customCsvWriter.writePortProtocolCombinationCount(portProtocolCombinationCount, "port_protocol_count.txt");
        System.out.println("Target file path: " + outputFilePath);

        String testEndText = """

                Test 5 completed successfully
                ----------------------------------------------------------------
                """;
        System.out.println(testEndText);
        
    }

    private String getResourcePath(String fileName) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }
        return resource.getFile();
    }
}