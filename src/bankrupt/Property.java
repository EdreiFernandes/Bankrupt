package bankrupt;

public class Property {
    private Integer saleValue;
    private Integer rentValue;
    private Integer owner;

    public Property(Integer _saleValue, Integer _rentValue) {
        this.saleValue = _saleValue;
        this.rentValue = _rentValue;
    }

    public Boolean hasOwner() {
        if (this.owner == null) {
            return false;
        }
        return true;
    }

    public Integer getSaleValue() {
        return saleValue;
    }

    public Integer getRentValue() {
        return rentValue;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }
}
