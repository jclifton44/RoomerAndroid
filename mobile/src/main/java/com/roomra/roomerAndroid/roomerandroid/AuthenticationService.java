package com.roomra.roomerAndroid.roomerandroid;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;

import java.lang.Package;
import java.util.HashMap;

public class AuthenticationService {
    private SharedPreferencesEditor sharedEditor;
    private String accessToken = null;
    private String refreshToken = null;
    private String clientId = null;
    public String requestUrl = null;
    public static String URL = new String("https://localhost:8001");
    AuthenticationService(SharedPreferencesEditor spe, String un, String password) {
        try{
            this.requestUrl = SharedPreferencesEditor.https + SharedPreferencesEditor.host + SharedPreferencesEditor.port_https;
            this.sharedEditor = spe;
            Log.d("creating SPE", "SPE");
        if(spe.getAuthToken() != null) {
                Long expiry = new Long(spe.getExpiry());
                Long creationTime = new Long(spe.getCreationTime());
                Long nowTime = new Long(System.currentTimeMillis());
                if(expiry == 0 || creationTime == 0 || nowTime > (expiry + creationTime - 30000)) {
                    List<BasicNameValuePair> pv = new ArrayList(3);
                    pv.add(new BasicNameValuePair("refreshToken", spe.getRefreshToken()));

                    String response = performAuthRequest("oauth2/authenticate", pv);
                    Log.d(response, "SSL REQUEST");
                    System.out.println("SSL REQUEST " + response);
                }
                if(spe.getClientId() == null) {
                    List<BasicNameValuePair> pv = new ArrayList(3);
                    //pv.add("accessToken", "accessToken");
                    //String response = performAuthRequest("/db/user/getClient")

                    //check to see if user has a registered client
                }
            } else if(spe.getClientId() != null) {
                //use client ID to get access token
            } else {
                //use username and password to get access token
                List<BasicNameValuePair> pv = new ArrayList(3);
                pv.add(new BasicNameValuePair("userId", un));
                pv.add(new BasicNameValuePair("password", password));
                String response = performAuthRequest("/oauth2/authenticate", pv);
                Log.d(response, "this ist he response");
               // if()

            }
            //see if an Auth code exists in the spe
            // check expiration time, receiving time and current time ( is it expired? )
            // if close to EXPIRY use REFRESH TOKEN to get a new AccessToken
            // if expired check existance of Client ID
            // if exists -> use to get an Access Token (write that to the editor)
            //if doesn't exist use username and Password profided to get an accesstoken
            //if client not registered for user, register client - Save access Token as well as client to the editor

        } finally {

        }
    }
    public String performAuthRequest(String path, List<BasicNameValuePair> postVars) {
        //HashMap
        String response = "";
        try {
            URL url = new URL(requestUrl + path);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postVars);
            HttpsURLConnection request = (HttpsURLConnection) url.openConnection();

            request.setDoOutput(true);
            request.setDoInput(true);
            request.setUseCaches(false);

            request.setRequestMethod("POST");

            OutputStream post = request.getOutputStream();
            entity.writeTo(post);
            post.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response += inputLine;
            }
            post.close();
            in.close();
        } catch(Exception e) {
            System.out.println("error: %s"  + e);
            Log.d("error", "There was an error!");

        } finally {

        }
        return response;

    }
    public void setSharedPreferences(SharedPreferencesEditor spe) {
        this.sharedEditor = spe;
    }
    public String getAccessToken() {
        return this.accessToken;
    }
    public String getRefreshToken() {
        return this.refreshToken;
    }
    public String getClientId() {
        return this.clientId;
    }

    public void setAccessToken(String param) {
        this.accessToken = param;
    }
    public void setRefreshToken(String param) {
        this.refreshToken = param;
    }
    public void setClientId(String param) {
        this.clientId = param;
    }


}