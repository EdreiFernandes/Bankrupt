package bankrupt;

import config.Behaviour;

public class Player implements Comparable<Player> {
    private Integer id;
    private Integer diceValue;
    private Behaviour behaviour;

    public Player(Integer _id) {
        this.id = _id;
        // Behaviour _behaviour
        // this.behaviour = _behaviour;
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

    public void setDiceValue(Integer diceValue) {
        this.diceValue = diceValue;
    }
}
