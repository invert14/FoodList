package pl.gda.pg.eti.jme.app.business;


import java.util.ArrayList;
import java.util.List;

import pl.gda.pg.eti.jme.app.model.Product;

public class ProductsController {
    private List<Product> products;

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private List<Product> productsToBeAdded;
    private List<Product> productsToBeDeleted;

    public ProductsController() {
        products = new ArrayList<Product>();
        productsToBeAdded = new ArrayList<Product>();
        productsToBeDeleted = new ArrayList<Product>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProductToBeAdded(Product product) {
        productsToBeAdded.add(product);
    }

    public void addProductsThatShouldBeAdded() {
        products.addAll(productsToBeAdded);
        productsToBeAdded.clear();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void clearAndAddProducts(List<Product> products) {
        this.products.clear();
        for (Product p : products) {
            addProduct(p);
        }
    }

    public void addProductAmount(String name, int amount) {
        Product p = getProductByName(name);
        int prevAmount = p.getAmount();
        int newAmount = prevAmount + amount;
        int prevLocalAmount = p.getLocalAmount();
        int newLocalAmount = prevLocalAmount + amount;

        if (newAmount < 0) {
            newAmount = 0;
            newLocalAmount = prevLocalAmount - prevAmount;
        }
        p.setAmount(newAmount);
        p.setLocalAmount(newLocalAmount);
    }

    public void clear() {
        products.clear();
    }

    public Product getProductByName(String name) {
        for (Product p : products) {
            if (p.getName().equals(name))
                return p;
        }
        return null;
    }

    public int getLocalAmountByName(String name) {
        Product p = getProductByName(name);
        if (p != null)
            return p.getLocalAmount();
        return 0;
    }

    public List<Product> getProductsToBeDeleted() {
        return productsToBeDeleted;
    }

    public void deleteProduct(String name) {
        Product p = getProductByName(name);
        if (products.contains(p)) {
            products.remove(p);
            productsToBeDeleted.add(p);

        }
    }

    public void clearProductsToBeDeleted() {
        productsToBeDeleted.clear();
    }
}
