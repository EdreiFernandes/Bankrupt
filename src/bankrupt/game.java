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
    private Integer roundCoins;
    private Integer round;
    private Integer maxRounds;

    private Game() {
        this.maxRounds = 1000;
        this.maxPlayers = 4;
        this.roundCoins = 100;
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

    private void playARound() {
        System.out.println("\nRound " + (this.round + 1) + " ------------------------------------");
        for (int i = 0; i < this.maxPlayers; i++) {
            Player player = this.players[i];
            System.out.printf("\tPlayer " + player.getId() + " at " + player.getBoardPosition());

            player.setDiceValue(rollTheDice());
            boolean isRoundCompleted = player.move();
            if (isRoundCompleted) {
                player.addCoins(this.roundCoins);
            }

            System.out.printf(" rolls " + player.getDiceValue() + " and go to " + player.getBoardPosition() + "\n");
        }
    }

    private static Integer rollTheDice() {
        Random dice = new Random();
        return dice.nextInt(6) + 1;
    }

    public static void main(String[] args) {
        Game game = new Game();

        // Simulations
        Integer simulation = 0;
        while (simulation < 300) {
            // Game
            System.out.println("\n------------ BANKRUPT Simulation " + (simulation + 1) + " ------------");

            game.round = 0;
            game.initializeProperties();
            game.initializePlayers();
            game.definePlayersOrder();

            while (game.round < game.maxRounds) {
                game.playARound();
                game.round++;
            }
            simulation++;
        }
    }
}