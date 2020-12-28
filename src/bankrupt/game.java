package bankrupt;

import java.util.Arrays;
import java.util.Random;

public class Game {
    private Integer maxPlayers;
    private Player[] players;

    private Game() {
        this.maxPlayers = 4;
        this.players = new Player[this.maxPlayers];
    }

    private static Integer rollTheDice() {
        Random dice = new Random();
        return dice.nextInt(6) + 1;
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

    public static void main(String[] args) {
        System.out.println("Bankrupt");
        Game game = new Game();

        game.initializePlayers();

        game.definePlayersOrder();
    }
}