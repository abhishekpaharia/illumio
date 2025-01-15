package com.paharia.abhishek;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class CustomCsvWriter {

    public String writeTagCount(Map<String, Long> tagCount, String path) {
        FileWriter txtWriter = null;
        try {
            URI classesUri = this.getClass().getClassLoader().getResource("").toURI();
            Path classesPath = Paths.get(classesUri);
            Path srcPath = classesPath.getParent().getParent().resolve("src").resolve("main").resolve("output").resolve("tag_count.txt");
            //System.out.println("Target file path: " + srcPath);

            // Ensure the parent directories exist
            File file = srcPath.toFile();
            file.getParentFile().mkdirs();

            txtWriter = new FileWriter(file);
            txtWriter.append("Tag");
            txtWriter.append(",");
            txtWriter.append("Count");
            txtWriter.append("\n");
            for (Map.Entry<String, Long> entry : tagCount.entrySet()) {
                txtWriter.append(entry.getKey());
                txtWriter.append(",");
                txtWriter.append(entry.getValue().toString());
                txtWriter.append("\n");
            }
            txtWriter.flush();
            return srcPath.toString();
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error writing tag count to file", e);
        } finally {
            if(txtWriter != null){
                try {
                    txtWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String writePortProtocolCombinationCount(Map<String, PortPortocolAggregator> portProtocolCombinationCount, String path) {
        FileWriter txtWriter = null;
        try {
            URI classesUri = this.getClass().getClassLoader().getResource("").toURI();
            Path classesPath = Paths.get(classesUri);
            Path srcPath = classesPath.getParent().getParent().resolve("src").resolve("main").resolve("output").resolve("port_protocol_combination_count.txt");
            //System.out.println("Target file path: " + srcPath);

            // Ensure the parent directories exist
            File file = srcPath.toFile();
            file.getParentFile().mkdirs();

            txtWriter = new FileWriter(file);
            txtWriter.append("Port");
            txtWriter.append(",");
            txtWriter.append("Protocol");
            txtWriter.append(",");
            txtWriter.append("Count");
            txtWriter.append("\n");
            for (Map.Entry<String, PortPortocolAggregator> entry : portProtocolCombinationCount.entrySet()) {
                txtWriter.append(entry.getValue().getPort());
                txtWriter.append(",");
                txtWriter.append(entry.getValue().getProtocol());
                txtWriter.append(",");
                txtWriter.append(entry.getValue().getCount().toString());
                txtWriter.append("\n");
            }
            txtWriter.flush();
            return srcPath.toString();
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error writing port protocol combination count to file", e);
        } finally {
            if(txtWriter != null){
                try {
                    txtWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

