package pl.gda.pg.eti.jme.app.helpers;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.HashMap;

public class LoginHelper {
    static HashMap<BasicNameValuePair, Integer> users;
    static {
        users =  new HashMap<BasicNameValuePair, Integer>();
        users.put(new BasicNameValuePair("test1", "c4ca4238a0b923820dcc509a6f75849b"), 1);
        users.put(new BasicNameValuePair("test2", "c81e728d9d4c2f636f067f89cc14862c"), 2);
    };

    public static int getUserId(String login, String password) {
        BasicNameValuePair bnvp = new BasicNameValuePair(login, password);
        if (users.containsKey(bnvp)) {
            return users.get(bnvp);
        }
        return 0;
    }
}
