package pl.gda.pg.eti.jme.app.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pl.gda.pg.eti.jme.app.R;
import pl.gda.pg.eti.jme.app.helpers.LoginHelper;
import pl.gda.pg.eti.jme.app.helpers.ResponseStringHelper;
import pl.gda.pg.eti.jme.app.helpers.SimpleEncryptor;
import pl.gda.pg.eti.jme.app.helpers.SimpleHttpHandler;

public class LoginActivity extends ActionBarActivity {

    public final static String USER_ID_MESSAGE = "pl.gda.pg.eti.jme.app.USER_ID_MESSAGE";
    public final static String DEVICE_ID_MESSAGE = "pl.gda.pg.eti.jme.app.DEVICE_ID_MESSAGE";

    private EditText loginEditText;
    private EditText passwordEditText;
    private EditText deviceIdEditText;

    private String login;
    private String password;
    private int deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = (EditText) findViewById(R.id.login);
        passwordEditText = (EditText) findViewById(R.id.password);
        deviceIdEditText = (EditText) findViewById(R.id.deviceId);
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

    public void logIn(View view) {
        login = loginEditText.getText().toString();
        password = SimpleEncryptor.md5(passwordEditText.getText().toString());
        deviceId = getDeviceId();
        new GetUserIdAndLogIn().execute();
    }

    private int getDeviceId() {
        int id = 0;
        String deviceIdString = deviceIdEditText.getText().toString();
        try {
            id = Integer.parseInt(deviceIdString);
        } catch (NumberFormatException nfe) {
            Log.e("Login - Int parsing", nfe.toString());
        }
        return id;
    }

    private class GetUserIdAndLogIn extends AsyncTask<String, String, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            SimpleHttpHandler shh = new SimpleHttpHandler(MainActivity.USER_ID_URL);
            shh.addParam("login", login);
            shh.addParam("password", password);
            String httpResponse = shh.getStringFromUrl();
            return ResponseStringHelper.getIntFromResponseString(httpResponse);
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id != 0)
                startMainActivityLogged(id);
            else {
                id = LoginHelper.getUserId(login, password);
                if (id != 0)
                    startMainActivityLogged(id);
                else
                    Toast.makeText(getApplicationContext(), "Wrong credentials!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startMainActivityLogged(Integer id) {
        Intent intent = new Intent(this.getBaseContext(), MainActivity.class);
        intent.putExtra(USER_ID_MESSAGE, id);
        intent.putExtra(DEVICE_ID_MESSAGE, deviceId);
        startActivity(intent);
    }
}
