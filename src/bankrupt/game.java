package bankrupt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import config.GameConfig;

public class Game {
    private Integer maxPlayers;
    private Player[] players;
    private List<Property> properties;

    private Game() {
        this.maxPlayers = 4;
        this.properties = new ArrayList<>();
        this.players = new Player[this.maxPlayers];
    }

    private void initializeProperties() {
        try {
            List<String> propertiesInfo = GameConfig.reader();

            for (String propertyInfo : propertiesInfo) {
                String[] splitedInfo = propertyInfo.split("-");

                Integer saleValue = Integer.valueOf(splitedInfo[0]);
                Integer rentValue = Integer.valueOf(splitedInfo[1]);

                this.properties.add(new Property(saleValue, rentValue));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void initializePlayers() {
        for (int i = 0; i < this.maxPlayers; i++) {
            this.players[i] = new Player(i + 1);
            this.players[i].setDiceValue(rollTheDice());
        }
    }

    private void definePlayersOrder() {
        Arrays.sort(this.players);
    }

    private static Integer rollTheDice() {
        Random dice = new Random();
        return dice.nextInt(6) + 1;
    }

    public static void main(String[] args) {
        System.out.println("Bankrupt");
        Game game = new Game();

        game.initializeProperties();
        game.initializePlayers();

        game.definePlayersOrder();
    }
}