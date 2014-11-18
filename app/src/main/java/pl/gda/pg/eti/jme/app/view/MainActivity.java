package pl.gda.pg.eti.jme.app.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import pl.gda.pg.eti.jme.app.helpers.SimpleHttpHandler;
import pl.gda.pg.eti.jme.app.R;
import pl.gda.pg.eti.jme.app.business.ProductsController;
import pl.gda.pg.eti.jme.app.model.Product;
import pl.gda.pg.eti.jme.app.model.dummy.DummyModel;
import pl.gda.pg.eti.jme.app.view.adapters.ListItemAdapter;

public class MainActivity extends ActionBarActivity {

    private static final String FILE_NAME = "FoodList_";
    public static final String SERVER_URL = "http://192.168.0.101:5000";
    public static final String PRODUCTS_URL = SERVER_URL + "/products";
    public static final String USER_ID_URL = SERVER_URL + "/user";

    private int userId;

    ListView listView;
    ProductsController productsController;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int size = productsController.getProducts().size();
        int[] ids = new int[size];
        String[] names = new String[size];
        int[] amounts = new int[size];

        int i = 0;
        for (Product product : productsController.getProducts()) {
            ids[i] = product.getId();
            names[i] = product.getName();
            amounts[i] = product.getAmount();
            i++;
        }

        outState.putIntArray("ids", ids);
        outState.putStringArray("names", names);
        outState.putIntArray("amounts", amounts);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);

        Intent intent = getIntent();
        userId = intent.getIntExtra(LoginActivity.USER_ID_MESSAGE, 1);

        productsController = new ProductsController();

        if (savedInstanceState != null) {
            productsController.getProducts().clear();
            int[] ids = savedInstanceState.getIntArray("ids");
            String[] names = savedInstanceState.getStringArray("names");
            int[] amounts = savedInstanceState.getIntArray("amounts");
            for (int i=0; i != ids.length; i++) {
                //FIXME: user ID
                productsController.getProducts().add(new Product(ids[i], names[i], amounts[i], 1));
            }
        }


        updateListView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String FILE_DIR = getApplicationContext().getFilesDir().getPath().toString() + "/"
                + FILE_NAME + String.valueOf(userId);
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        File file;

        try {

            file = new File(FILE_DIR);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            os = new ObjectOutputStream(fos);
            os.writeObject(productsController.getProducts());

            System.out.println("Done serializing");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        String FILE_DIR = getApplicationContext().getFilesDir().getPath().toString() + "/"
                + FILE_NAME + String.valueOf(userId);
        ArrayList<Product> products = new ArrayList<Product>();
        FileInputStream fis;
        ObjectInputStream is = null;
        File file;

        try {

            file = new File(FILE_DIR);

            // if file doesnt exists, then create it
            if (file.exists()) {
                fis = new FileInputStream(file);
                is = new ObjectInputStream(fis);
                products = (ArrayList<Product>) is.readObject();
            }

            System.out.println("Done deserializing");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productsController.products = products;
        updateListView();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            new JSONParseTask().execute();
            return true;
        } else if (id == R.id.sample_data) {
            productsController.clearAndAddProducts(DummyModel.getProducts());
            updateListView();
            return true;
        } else if (id == R.id.update_server) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateListView() {
        ListItemAdapter adapter = new ListItemAdapter(
                getApplicationContext(), R.layout.list_item, productsController.getProducts()) {

            @Override
            public void onClick(View view, Product product, int amount) {
                add_amount(product, amount);
            }
        };
        listView.setAdapter(adapter);
    }

    public void add_amount(Product product, int amount) {
        String text = product.getName() + " -->  " + String.valueOf(amount);
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        productsController.addProductAmount(product.getId(), amount);
        updateListView();
    }

    private class JSONParseTask extends AsyncTask<String, String, JSONArray> {

        ArrayList<Product> products;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            products = new ArrayList<Product>();
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            SimpleHttpHandler shh = new SimpleHttpHandler(PRODUCTS_URL);
            shh.addParam("user_id", String.valueOf(userId));
            JSONArray json = shh.getJSONFromUrl();
            return json;
        }
        @Override
        protected void onPostExecute(JSONArray json_products) {
            try {
                // Getting JSON Array from URL
                for(int i = 0; i < json_products.length(); i++){
                    JSONObject c = json_products.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    int id = c.getInt("id");
                    String name = c.getString("name");
                    int amount = c.getInt("amount");

                    //FIXME: user id
                    Product product = new Product(id, name, amount, 1);

                    products.add(product);
                }
                productsController.clearAndAddProducts(products);
                updateListView();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
