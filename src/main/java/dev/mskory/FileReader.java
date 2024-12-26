package dev.mskory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private FileReader() {
    }

    public static List<String> read(String path) {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.matches("\\d+")) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            App.LOGGER.warn("Can't read file by path: {}.", path);
            App.main(new String[0]);
            System.exit(0);
        }
        return lines;
    }
}
