package bankrupt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.Behavior;

public class SimStatistic {
    private List<Integer> listRoundsPerGame;
    private List<Behavior> listWinnerBehavior;
    private Behavior winningBehavior;

    public SimStatistic() {
        this.listRoundsPerGame = new ArrayList<>();
        this.listWinnerBehavior = new ArrayList<>();
    }

    public void addToListRoundsPerGame(Integer _roundsPerGame) {
        this.listRoundsPerGame.add(_roundsPerGame);
    }

    public void addToListWinnerBehavior(Behavior _winnerBehavior) {
        this.listWinnerBehavior.add(_winnerBehavior);
    }

    public void showWinnerBehavior() {
        System.out.println(this.listWinnerBehavior.toString());
    }

    public Integer roundsEndedInTimeOut() {
        Integer frequency = Collections.frequency(this.listRoundsPerGame, 1000);
        return frequency;
    }

    public Integer averageRoundsPerGame() {
        Integer sum = 0;
        for (Integer roundsPerGame : this.listRoundsPerGame) {
            sum += roundsPerGame;
        }

        return sum / this.listRoundsPerGame.size();
    }
}
