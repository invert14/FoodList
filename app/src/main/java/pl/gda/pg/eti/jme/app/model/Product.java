package pl.gda.pg.eti.jme.app.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int amount;
    private int localAmount;

    public Product(int id, String name, int amount, int localAmount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.localAmount = localAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
