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

    public void buyAProperty(Property _property) {
        boolean hasBought = decreaseCoins(_property.getSaleValue());
        if (hasBought) {
            _property.setOwner(this.id);
            System.out.println("\t\tComprou");
        } else {
            System.out.println("\t\tSem dindin");
        }
    }

    public boolean decreaseCoins(Integer _coins) {
        this.coins -= _coins;
        if (this.coins < 0) {
            System.out.println("\t\t\t\t player" + this.id + " faliu");
            return false;
        }
        System.out.println("\t\t\t\t player" + this.id + " coins: " + this.coins);
        return true;
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
