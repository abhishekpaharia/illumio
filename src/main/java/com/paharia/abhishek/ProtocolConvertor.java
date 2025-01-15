package com.paharia.abhishek;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProtocolConvertor { 
    private String fileName;
    private Map<Integer, String> protocolMap;

    public ProtocolConvertor(String fileName){
        this.fileName = fileName;
        this. protocolMap = new HashMap<>();
        readFile();
        //System.out.println(protocolMap);
    }

    private void readFile(){
        String line = "";
        String cvsSplitBy = ",";
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                try{
                    Integer.parseInt(data[0]);
                } catch (NumberFormatException e){
                    //System.out.println("Invalid protocol number: " + data[0]);
                    continue;
                }
                protocolMap.put(Integer.parseInt(data[0]), data[1]);
                //System.out.println("Decimal: " + data[0] + ", Keyword: " + data[1] + ", Protocol: " + data[2]);
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

    public String getProtocol(int protocolNumber){
        if(!protocolMap.containsKey(protocolNumber)){
            throw new IllegalArgumentException("Protocol number not found: " + protocolNumber);
        }
        return protocolMap.get(protocolNumber);
    }

    public Map<Integer, String> getProtocolMap(){
        return protocolMap;
    }
}
