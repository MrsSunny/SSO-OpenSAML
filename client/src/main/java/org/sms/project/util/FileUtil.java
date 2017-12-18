package org.sms.project.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtil {

    public static String getText(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line + "\r\n");
            }
            String fromFile = sb.toString();
            return fromFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeText(String path, String data) {
        try {
            Files.write(Paths.get(path), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
