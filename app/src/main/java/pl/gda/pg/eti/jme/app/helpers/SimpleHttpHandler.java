package pl.gda.pg.eti.jme.app.helpers;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleHttpHandler {
    private String url = null;
    InputStream is = null;
    JSONArray jArr = null;
    private final DefaultHttpClient httpClient;
    private final HttpPost httpPost;
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    HashMap<String, String> paramsMap = new HashMap<String, String>();

//    DefaultHttpClient httpClient;

    // constructor
    public SimpleHttpHandler(String url) {
        this.url = url;
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost(url);
    }

    public void addParam(String name, String value) {
        params.add(new BasicNameValuePair(name, value));
        paramsMap.put(name, value);
    }

    public String getStringFromUrl() {
        String responseString = "";
        // Making HTTP request
        try {
            // defaultHttpClient
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("SimpleHttpHandler::::", "No connection!");
            return "0";
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            responseString = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return responseString;
    }
}