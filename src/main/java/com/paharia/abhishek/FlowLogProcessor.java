package com.paharia.abhishek;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlowLogProcessor {
    private String flowLogFile;
    private List<List<String>> flowLog;
    private TagMapper tagMapper;
    private ProtocolConvertor protocolConvertor;

    public FlowLogProcessor(String flowLogFile) {
        this.flowLogFile = flowLogFile;
        this.flowLog = new ArrayList<>();
        readFlowLogFile();
        //System.out.println(flowLog);
    }

    public FlowLogProcessor(String flowLogFile, ProtocolConvertor protocolConvertor, TagMapper tagMapper) {
        this(flowLogFile);
        this.protocolConvertor = protocolConvertor;
        this.tagMapper = tagMapper;
    }

    private void readFlowLogFile() {
        String line = "";
        String splitBy = " ";
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(flowLogFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                try{
                    int version = Integer.parseInt(data[0]);
                    if (version != 2) {
                        throw new IllegalArgumentException("Invalid version number: " + version);
                    }
                } catch (NumberFormatException e){
                    //System.out.println("Invalid lookup table row " + data[0]);
                    continue;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                flowLog.add(List.of(data));
                //System.out.println("Flow log: " + Arrays.toString(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<String, Long> getTagCount() {
        return flowLog.stream().map(row -> {
            String dstport = row.get(6);
            String protocol = row.get(7);
            protocol = protocolConvertor.getProtocol(Integer.parseInt(protocol));
            //System.out.println("Dstport: " + dstport + ", Protocol: " + protocol);
            String tag = tagMapper.getTag(dstport, protocol.toLowerCase());
            return tag;
        }).collect(Collectors.groupingBy(tag -> tag, () -> new HashMap<>(), Collectors.summingLong(tag -> 1)));
    }

    public Map<String, PortPortocolAggregator> getPortProtocolCombinationCount(){
        return flowLog.stream().map(row -> {
            String dstport = row.get(6);
            String protocol = row.get(7);
            protocol = protocolConvertor.getProtocol(Integer.parseInt(protocol)).toLowerCase();
            String tag = tagMapper.getTag(dstport, protocol);
            if(tag.equals("untagged")){
                Optional<PortPortocolAggregator> empty = Optional.empty();
                return empty;
            }
            String key = dstport + "#" + protocol;
            return Optional.of(new PortPortocolAggregator(key, dstport, protocol, 1L));
        }).filter(agg -> agg.isPresent())
        .map(agg -> agg.get())
        .collect(Collectors.groupingBy(agg -> agg.getKey(), ()-> new HashMap<>(), 
            Collectors.reducing(new PortPortocolAggregator(null, null, null, 0L), (agg1, agg2) -> new PortPortocolAggregator(agg2.getKey(), agg2.getPort(), agg2.getProtocol(), agg1.getCount() + agg2.getCount()))));
    }

    public List<List<String>> getFlowLog() {
        return flowLog;
    }

}

class PortPortocolAggregator {
    private String key;
    private String port;
    private String protocol;
    private Long count;

    public PortPortocolAggregator(String key, String port, String protocol, Long count) {
        this.key = key;
        this.port = port;
        this.protocol = protocol;
        this.count = count;
    }

    public String getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public Long getCount() {
        return count;
    }
    
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "PortPortocolAggregator [key=" + key + ", port=" + port + ", protocol=" + protocol + ", count=" + count
                + "]";
    }

    
}
