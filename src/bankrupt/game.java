package bankrupt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import config.Behavior;
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
            this.players[i] = new Player(i + 1, Behavior.valueOf(i));
            this.players[i].setDiceValue(rollTheDice());
        }
    }

    private void definePlayersOrder() {
        Arrays.sort(this.players);
    }

    private void playARound() {
        Integer playerPlaying = 4;
        for (int i = 0; i < this.maxPlayers; i++) {
            Player player = this.players[i];

            if (player.getArePlaying()) {
                player.setDiceValue(rollTheDice());
                boolean isTurnCompleted = player.move();
                if (isTurnCompleted) {
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

    private Player defineWinner() {
        Player winner = this.players[0];

        for (int i = 0; i < this.maxPlayers; i++) {
            Player player = this.players[i];
            if (player.getArePlaying() && winner.getCoins() < player.getCoins()) {
                winner = player;
            }
        }
        return winner;
    }

    public static void main(String[] args) {
        Game game = new Game();
        SimStatistic statistic = new SimStatistic();

        Integer simulation = 0;
        Integer maxSimulations = 300;

        while (simulation < maxSimulations) {
            game.round = 0;
            game.hasEnded = false;
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

            statistic.addToListRoundsPerGame(game.round);
            statistic.addToListWinnerBehavior(game.defineWinner().getBehaviour());
            simulation++;
        }

        System.out.println("Quantas partidas terminam por time out (1000 rodadas)?");
        System.out.println(statistic.roundsEndedInTimeOut());

        System.out.println("Quantos turnos (rodadas), em média, demora uma partida?");
        System.out.println(statistic.averageRoundsPerGame());

        System.out.println("Qual a porcentagem de vitórias por comportamento dos jogadores?");
        statistic.winningBehaviorPerCent();

        System.out.println("Qual o comportamento que mais vence?");
        System.out.println(statistic.mostWinnerBehavior().name());
    }
}