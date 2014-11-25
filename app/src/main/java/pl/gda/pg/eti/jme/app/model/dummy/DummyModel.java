package pl.gda.pg.eti.jme.app.model.dummy;

import java.util.ArrayList;
import java.util.List;

import pl.gda.pg.eti.jme.app.model.Product;

public class DummyModel {
    public static List<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<Product>();

        products.add(new Product("bread", 5, 0));
        products.add(new Product("fish", 2, 0));
        products.add(new Product("wine bottle", 10, 0));

        return products;
    }
}
