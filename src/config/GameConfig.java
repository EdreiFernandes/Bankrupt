package config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GameConfig {
    private static String filePath = "./src/config/gameConfig.txt";

    private GameConfig() {
    }

    public static List<String> reader() throws Exception {
        List<String> propertiesInfo = new ArrayList<>();

        FileReader arquive = new FileReader(filePath);
        BufferedReader reader = new BufferedReader(arquive);

        String line = reader.readLine();

        while (line != null) {
            line = line.replaceAll("( )+", "-");
            propertiesInfo.add(line);
            line = reader.readLine();
        }

        arquive.close();

        return propertiesInfo;
    }
}
