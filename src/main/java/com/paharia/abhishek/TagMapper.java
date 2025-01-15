package com.paharia.abhishek;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TagMapper {

    private String fileName;
    private Map<String, String> tagMap;

    public  TagMapper(String lookupFile){
        this.fileName = lookupFile;
        this.tagMap = new HashMap<>();
        readLookupTable();
        //System.out.println(tagMap);
    }
        
    private void readLookupTable() {
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
                    //System.out.println("Invalid lookup table row " + data[0]);
                    continue;
                }
                String key = data[0]+"#"+data[1].toLowerCase();
                tagMap.put(key, data[2].trim());
                //System.out.println("Dstport: " + data[0] + ", Protocol: " + data[1] + ", Tag: " + data[2]);
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

    public String getTag(String dstport, String protocol){
        String key = dstport+"#"+protocol.toLowerCase();
        return tagMap.getOrDefault(key, "untagged");
    }

    public Map<String, String> getTagMap(){
        return tagMap;
    }  
}
