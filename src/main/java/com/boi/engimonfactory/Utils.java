package com.boi.engimonfactory;
import java.io.InputStream;
import java.util.Scanner;

public class Utils {
    public static String loadResource(String fileName) throws Exception {
        String result;
        System.out.println(Utils.class.getResource("/fragmentShader.fs").getPath());

        System.out.println(fileName);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream in = classLoader.getResourceAsStream(fileName)) {
            Scanner scanner = new Scanner(in, java.nio.charset.StandardCharsets.UTF_8.name());
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
}
