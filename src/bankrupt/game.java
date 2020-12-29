package bankrupt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import config.Behaviour;
import config.GameConfig;

public class Game {
    private Integer maxPlayers;
    private Player[] players;
    private List<Property> properties;
    private Integer roundCoins;
    private Integer round;
    private Integer maxRounds;
    private boolean hasEnded;

    private Game() {
        this.hasEnded = false;
        this.maxRounds = 100;// 1000;
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
            this.players[i] = new Player(i + 1, Behaviour.valueOf(i));
            this.players[i].setDiceValue(rollTheDice());
        }
    }

    private void definePlayersOrder() {
        Arrays.sort(this.players);
    }

    private void playARound() {
        Integer playerPlaying = 4;
        System.out.println("\nRound " + (this.round + 1) + " ------------------------------------");
        for (int i = 0; i < this.maxPlayers; i++) {
            Player player = this.players[i];

            if (player.getArePlaying()) {
                System.out.println("\tPlayer " + player.getId() + "\tCoins: " + player.getCoins());

                player.setDiceValue(rollTheDice());
                boolean isTurnCompleted = player.move();
                if (isTurnCompleted) {
                    System.out.println("\t- Player " + player.getId() + " took a complete turn on the board");
                    player.addCoins(this.roundCoins);
                }

                Integer boardPosition = player.getBoardPosition();
                if (boardPosition > 0) {
                    Property property = this.properties.get(boardPosition - 1);

                    if (property.hasOwner()) {
                        Player receiver = Arrays.asList(this.players).stream()
                                .filter(x -> x.getId().equals(property.getOwner())).findAny().orElse(null);

                        if (receiver.getArePlaying()) {
                            player.payRent(property, receiver);
                        } else {
                            property.setOwner(null);
                            player.buyAProperty(property);
                        }
                    } else {
                        player.buyAProperty(property);
                    }
                }
            } else {
                playerPlaying--;
            }
        }

        if (playerPlaying <= 1) {
            this.hasEnded = true;
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
        Integer maxSimulations = 1;// 300;

        while (simulation < maxSimulations) {
            // Game
            System.out.println("\n------------ BANKRUPT Simulation " + (simulation + 1) + " ------------");

            game.round = 0;
            game.initializeProperties();
            game.initializePlayers();
            game.definePlayersOrder();

            while (!game.hasEnded) {
                game.playARound();
                game.round++;
                if (game.round >= game.maxRounds) {
                    game.hasEnded = true;
                }
            }

            System.out.println(game.hasEnded);
            // if (game.hasEnded) {
            // System.out.println("GG");
            // }

            // for (Player player : game.players) {
            // System.out.println("Player " + player.getId() + " coins " +
            // player.getCoins());
            // }

            simulation++;
        }
    }
}