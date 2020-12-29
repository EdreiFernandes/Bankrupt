package bankrupt;

import java.util.Random;

import config.Behavior;

public class Player implements Comparable<Player> {
    private Integer id;
    private Integer diceValue;
    private Integer boardPosition;
    private Integer coins;
    private Behavior behaviour;
    private boolean arePlaying;

    public Player(Integer _id, Behavior _behaviour) {
        this.coins = 300;
        this.id = _id;
        this.boardPosition = 0;
        this.behaviour = _behaviour;
        this.arePlaying = true;
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

    public Integer getCoins() {
        return coins;
    }

    public void payRent(Property _property, Player _receiver) {
        if (this.id == _receiver.getId()) {
            return;
        }

        Integer rentValue = _property.getRentValue();
        if (this.coins - rentValue < 0) {
            rentValue = this.coins;
            this.arePlaying = false;
        }

        decreaseCoins(rentValue);
        _receiver.addCoins(rentValue);
    }

    public void buyAProperty(Property _property) {
        boolean canBuy;
        canBuy = this.coins - _property.getSaleValue() > 0;

        if (canBuy) {
            boolean willBuy = false;
            switch (this.behaviour) {
                case IMPULSIVO:
                    willBuy = true;
                    break;
                case EXIGENTE:
                    willBuy = _property.getRentValue() > 50;
                    break;
                case CAUTELOSO:
                    willBuy = this.coins - _property.getSaleValue() >= 80;
                    break;
                case ALEATORIO:
                    willBuy = new Random().nextBoolean();
                    break;
            }

            if (willBuy) {
                decreaseCoins(_property.getSaleValue());
                _property.setOwner(this.id);
            }
        }
    }

    private void decreaseCoins(Integer _coins) {
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

    public boolean getArePlaying() {
        return arePlaying;
    }

    public void setDiceValue(Integer diceValue) {
        this.diceValue = diceValue;
    }
}
