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
        this.maxRounds = 10;// 1000;
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

            // comprar
            Integer boardPosition = player.getBoardPosition();
            if (boardPosition > 0) { // se esta no livre
                Property property = this.properties.get(boardPosition - 1);

                System.out.println("------> " + property.hasOwner());

                if (property.hasOwner()) {
                    payRent(player, property);
                } else {
                    player.buyAProperty(property);
                }
            }

            System.out.printf(" rolls " + player.getDiceValue() + " and go to " + boardPosition + "\n");
        }
    }

    private void payRent(Player tenant, Property property) {
        Player receiver = Arrays.asList(this.players).stream().filter(x -> x.getId().equals(property.getOwner()))
                .findAny().orElse(null);
        Integer rentValue = property.getRentValue();

        tenant.decreaseCoins(rentValue);
        receiver.addCoins(rentValue);
        System.out.println("\t\tPlayer " + tenant.getId() + " paying " + rentValue + " to Player " + receiver.getId());
    }

    private static Integer rollTheDice() {
        Random dice = new Random();
        return dice.nextInt(6) + 1;
    }

    public static void main(String[] args) {
        Game game = new Game();

        // Simulations
        Integer simulation = 0;
        Integer maxSimulations = 1;// 300;

        while (simulation < maxSimulations) {
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