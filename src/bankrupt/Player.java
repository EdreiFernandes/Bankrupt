package bankrupt;

import java.util.Random;

import config.Behaviour;

public class Player implements Comparable<Player> {
    private Integer id;
    private Integer diceValue;
    private Integer boardPosition;
    private Integer coins;
    private Behaviour behaviour;

    public Player(Integer _id, Behaviour _behaviour) {
        this.coins = 300;
        this.id = _id;
        this.boardPosition = 0;
        this.behaviour = _behaviour;
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
        boolean canBuy;
        canBuy = this.coins - _property.getSaleValue() > 0;

        if (canBuy) {
            boolean willBuy = false;
            switch (this.behaviour) {
                case IMPULSIVO:
                    System.out.println("\t\tIMPULSIVO");
                    willBuy = true;
                    break;
                case EXIGENTE:
                    System.out.println("\t\tEXIGENTE");
                    willBuy = _property.getRentValue() > 50;
                    break;
                case CAUTELOSO:
                    System.out.println("\t\tCAUTELOSO");
                    willBuy = this.coins - _property.getSaleValue() >= 80;
                    break;
                case ALEATORIO:
                    System.out.println("\t\tALEATORIO");
                    willBuy = new Random().nextBoolean();
                    break;
            }

            if (willBuy) {
                decreaseCoins(_property.getSaleValue());
                _property.setOwner(this.id);
                System.out.println("\t\tComprou");
            } else {
                System.out.println("\t\tNão comprou");
            }
        }
    }

    public boolean decreaseCoins(Integer _coins) {
        this.coins -= _coins;
        // if (this.coins < 0) {
        // System.out.println("\t\t\t\t player" + this.id + " faliu");
        // return false;
        // }
        // System.out.println("\t\t\t\t player" + this.id + " coins: " + this.coins);
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
