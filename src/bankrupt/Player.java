package bankrupt;

import config.Behaviour;

public class Player implements Comparable<Player> {
    private Integer id;
    private Integer diceValue;
    private Integer boardPosition;
    private Integer coins;
    private Behaviour behaviour;

    public Player(Integer _id) {
        this.coins = 300;
        this.id = _id;
        this.boardPosition = 0;
        // Behaviour _behaviour
        // this.behaviour = _behaviour;
    }

    public boolean move() {
        this.boardPosition += diceValue;

        if (this.boardPosition > 20) {
            this.boardPosition -= 21;
            return true;
        }
        return false;
    }

    public void addCoins(Integer _coins) {
        this.coins += _coins;
    }

    public void decreaseCoins(Integer _coins) {
        this.coins -= _coins;
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
