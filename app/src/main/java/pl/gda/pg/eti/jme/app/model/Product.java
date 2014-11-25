package pl.gda.pg.eti.jme.app.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int amount;
    private int localAmount;

    public Product(String name, int amount, int localAmount) {
        this.name = name;
        this.amount = amount;
        this.localAmount = localAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(int localAmount) {
        this.localAmount = localAmount;
    }

}
