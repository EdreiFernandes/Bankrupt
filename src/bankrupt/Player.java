package bankrupt;

import config.Behaviour;

public class Player implements Comparable<Player> {
    private Integer id;
    private Integer diceValue;
    private Integer boardPosition;
    private Behaviour behaviour;

    public Player(Integer _id) {
        this.id = _id;
        this.boardPosition = 0;
        // Behaviour _behaviour
        // this.behaviour = _behaviour;
    }

    public void move() {
        this.boardPosition += diceValue;
        if (boardPosition >= 20) {
            boardPosition -= 20;
        }
    }

    public int compareTo(Player comparePlayer) {
        int compareDiceValue = ((Player) comparePlayer).getDiceValue();
        return compareDiceValue - this.diceValue;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDiceValue() {
        return diceValue;
    }

    public Integer getBoardPosition() {
        return boardPosition;
    }

    public void setDiceValue(Integer diceValue) {
        this.diceValue = diceValue;
    }
}
