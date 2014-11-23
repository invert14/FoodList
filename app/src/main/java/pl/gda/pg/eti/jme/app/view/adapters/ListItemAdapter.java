package pl.gda.pg.eti.jme.app.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.gda.pg.eti.jme.app.R;
import pl.gda.pg.eti.jme.app.model.Product;

public abstract class ListItemAdapter extends ArrayAdapter<Product> {
    private LayoutInflater mInflater;
    List<Product> products;

    public ListItemAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.products = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = mInflater.inflate(R.layout.list_item, parent, false);
        }

        TextView productName = (TextView) view.findViewById(R.id.product_name);
        TextView productAmount = (TextView) view.findViewById(R.id.product_amount);
        TextView productLocalAmount = (TextView) view.findViewById(R.id.product_localamount);

        final Product product = products.get(position);
        productName.setText(product.getName());
        productAmount.setText(String.valueOf(product.getAmount()));
        productLocalAmount.setText(String.valueOf(product.getLocalAmount()));

        final EditText editText = (EditText) view.findViewById(R.id.modify_amount);



        Button addButton = (Button) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int amount = GetModifyAmount(editText);
                if (amount > 0) {
                    ListItemAdapter.this.onModifyAmountClick(view, product, amount);
                }
            }
        });

        Button removeButton = (Button) view.findViewById(R.id.remove_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int amount = GetModifyAmount(editText);
                if (amount > 0) {
                    ListItemAdapter.this.onModifyAmountClick(view, product, -amount);
                }
            }
        });

        Button deleteProductButton = (Button) view.findViewById(R.id.deleteProduct_button);
        deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItemAdapter.this.onDeleteClick(view, product);
            }
        });

        return view;
    }

    private int GetModifyAmount(EditText editText) {
        int probAmount = 0;
        String amountString = editText.getText().toString();
        try {
            probAmount = Integer.parseInt(amountString);
        } catch (NumberFormatException nfe) {
            Log.e("ListItemAdapter - Int parsing", nfe.toString());
        }
        return probAmount;
    }

    abstract public void onModifyAmountClick(View view, Product product, int amount);
    abstract public void onDeleteClick(View view, Product product);
}
