package pl.gda.pg.eti.jme.app.business;


import java.util.ArrayList;
import java.util.List;

import pl.gda.pg.eti.jme.app.model.Product;

public class ProductsController {
    public List<Product> products;

    public ProductsController() {
        products = new ArrayList<Product>();
    }


    public List<Product> getProducts() {
        return products;
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

    public void addProductAmount(int id, int amount) {
        Product p = getProductById(id);
        int prevAmount = p.getAmount();
        p.setAmount(prevAmount + amount);
    }
}
