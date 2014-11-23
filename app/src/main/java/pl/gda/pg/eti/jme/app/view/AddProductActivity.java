package pl.gda.pg.eti.jme.app.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import pl.gda.pg.eti.jme.app.R;

public class AddProductActivity extends ActionBarActivity {

    EditText newProductNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        newProductNameEditText = (EditText)findViewById(R.id.new_product_name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addProduct(View view) {
        String newProductName = newProductNameEditText.getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", newProductName);
        setResult(RESULT_OK, returnIntent);
        finish();
    }


}
