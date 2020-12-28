package config;

import java.io.BufferedReader;
import java.io.FileReader;

public class GameConfig {
    String filePath = "./src/config/gameConfig.txt";

    public void reader() {
        try {
            FileReader arquive = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(arquive);

            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }

            arquive.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
