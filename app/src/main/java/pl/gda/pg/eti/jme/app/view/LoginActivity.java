package pl.gda.pg.eti.jme.app.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pl.gda.pg.eti.jme.app.R;
import pl.gda.pg.eti.jme.app.helpers.SimpleEncryptor;
import pl.gda.pg.eti.jme.app.helpers.SimpleHttpHandler;

public class LoginActivity extends ActionBarActivity {

    public final static String USER_ID_MESSAGE = "pl.gda.pg.eti.jme.app.USER_ID_MESSAGE";

    private EditText loginEditText;
    private EditText passwordEditText;

    private String login;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = (EditText) findViewById(R.id.login);
        passwordEditText = (EditText) findViewById(R.id.password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    public void getUserId(View view) {
        login = loginEditText.getText().toString();
        password = SimpleEncryptor.md5(passwordEditText.getText().toString());
        new GetUserIdTask().execute();
    }

    private class GetUserIdTask extends AsyncTask<String, String, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            SimpleHttpHandler shh = new SimpleHttpHandler(MainActivity.USER_ID_URL);
            shh.addParam("login", login);
            shh.addParam("password", password);
            String httpResponse = shh.getStringFromUrl();
            httpResponse = httpResponse.replace("\n", "").replace("\r", "");
            Integer id;
            try {
                id = new Integer(httpResponse);
            } catch (NumberFormatException e) {
                return 0;
            }
            return id;
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id != 0) {
                Intent intent = new Intent(LoginActivity.this.getBaseContext(), MainActivity.class);
                intent.putExtra(USER_ID_MESSAGE, id);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Wrong credentials!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
