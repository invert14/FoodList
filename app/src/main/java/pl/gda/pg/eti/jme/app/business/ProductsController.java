package pl.gda.pg.eti.jme.app.business;


import java.util.ArrayList;
import java.util.List;

import pl.gda.pg.eti.jme.app.model.Product;

public class ProductsController {
    public List<Product> products;
    public List<Product> productsToBeAdded;

    public ProductsController() {
        products = new ArrayList<Product>();
        productsToBeAdded = new ArrayList<Product>();
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

    public Product getProductById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void addProductAmount(String name, int amount) {
        Product p = getProductByName(name);
        int prevAmount = p.getAmount();
        p.setAmount(prevAmount + amount);
        int prevLocalAmount = p.getLocalAmount();
        p.setLocalAmount(prevLocalAmount + amount);
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

    public void deleteProduct(String name) {
        Product p = getProductByName(name);
        if (products.contains(p)) {
            products.remove(p);
        }
    }
}
