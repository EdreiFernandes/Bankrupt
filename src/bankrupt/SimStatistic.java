package bankrupt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.Behavior;

public class SimStatistic {
    private List<Integer> listRoundsPerGame;
    private List<Behavior> listWinnerBehavior;

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

    public void winningBehaviorPerCent() {
        for (int i = 0; i < 4; i++) {
            Behavior behavior = Behavior.valueOf(i);
            Integer frequency = Collections.frequency(this.listWinnerBehavior, behavior);
            Integer perCent = frequency / 3;
            System.out.println(behavior.name() + ":\t" + perCent + "%");
        }
    }

    public Behavior mostWinnerBehavior() {
        Behavior winningBehavior = null;
        Integer winnerPerCent = 0;

        for (int i = 0; i < 4; i++) {
            Behavior behavior = Behavior.valueOf(i);
            Integer frequency = Collections.frequency(this.listWinnerBehavior, behavior);
            Integer perCent = frequency / 3;

            if (winnerPerCent < perCent) {
                winningBehavior = behavior;
                winnerPerCent = perCent;
                if (winnerPerCent > 50) {
                    break;
                }
            }
        }

        return winningBehavior;
    }
}
