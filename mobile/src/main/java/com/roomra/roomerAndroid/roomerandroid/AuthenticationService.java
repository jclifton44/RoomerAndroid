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

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.lang.Package;
import java.util.HashMap;
import java.io.StringWriter;
public class AuthenticationService {
    private SharedPreferencesEditor sharedEditor;
    private String accessToken = null;
    private String refreshToken = null;
    private String clientId = null;
    public Boolean isAuth = false;
    public static String requestUrl = null;
    public void logShared() {
        Log.i("LOGGING", "AUTH " + sharedEditor.getAuthToken());
        Log.i("LOGGING", "REF " + sharedEditor.getRefreshToken());
        Log.i("LOGGING", "CLIENT " + sharedEditor.getClientId());
        Log.i("LOGGING", "AUTH " + sharedEditor.getAuthToken());
        Log.i("LOGGING", "AUTH " + sharedEditor.getAuthToken());
    }
    AuthenticationService(SharedPreferencesEditor spe) {
        Log.i("LOGGING", "CONSTRUCTION_SIMPLE");
        Log.d("Testing Huge Task Build", "building...");

        this.sharedEditor = spe;
        logShared();
        if(!this.isAuth) {
            sharedEditor.editClear();
            logShared();

        }
    }
    AuthenticationService(SharedPreferencesEditor spe, String un, String password) {
        Log.i("LOGGING", "CONSTRUCTION_COMPLEX");
        this.sharedEditor = spe;
        logShared();
        ArrayList<BasicNameValuePair> pv = new ArrayList<BasicNameValuePair>();
        String path = "";
        AsyncConnection ac;
        Task task;
        try{

            if(checkExpired()){
                if(spe.getClientId() != "1KEY"){
                    Log.d("GETTING TOKEN", "CLIENT");
                    pv.clear();
                    pv.add(new BasicNameValuePair("clientId", spe.getClientId()));
                    task = new Task(TaskType.REOPEN, "oauth2/authenticate", pv);
                    ac = new AsyncConnection(true, task);
                }
            } else {
                if(spe.getRefreshToken() != "1KEY") {
                    Log.d("GETTING TOKEN", "REFRESH");
                    pv.clear();
                    pv.add(new BasicNameValuePair("clientId", spe.getClientId()));
                    task = new Task(TaskType.REFRESHTOKENS, "oauth2/authenticate", pv);
                    ac = new AsyncConnection(true, task);
                    //update with refresh
                } else if(un != null && password != null) {
                    Log.d("GETTING TOKEN", "OWNER");
                    pv.clear();
                    pv.add(new BasicNameValuePair("userId", un));
                    pv.add(new BasicNameValuePair("password", password));
                    task = new Task(TaskType.SIGNON, "oauth2/authenticate", pv);
                    ac = new AsyncConnection(true, task);

                }
            }
        } catch (Exception e) {

        } finally {
            //this.isAuth = isAuthenticated();
        }
    }
    public boolean downloadClientIdentifier() {
        ArrayList<BasicNameValuePair> pv = new ArrayList<BasicNameValuePair>();
        AsyncConnection ac;
        Task task;
        if(!checkExpired()){
            pv.add(new BasicNameValuePair("accessToken", sharedEditor.getAuthToken()));
            task = new Task(TaskType.UPDATECLIENT, "db/user/getClient", pv);
            return true;
        }
        return false;
    }
    public boolean registerClientIdentifier() {
        ArrayList<BasicNameValuePair> pv = new ArrayList<BasicNameValuePair>();
        AsyncConnection ac;
        Task task;
        if(!checkExpired()){
            pv.add(new BasicNameValuePair("accessToken", sharedEditor.getAuthToken()));
            task = new Task(TaskType.REGISTERCLIENT, "oauth2/client-registration/", pv);
            return true;
        }
        return false;
    }
    public boolean checkExpired() {
        Long expiry = sharedEditor.getExpiry();
        Long creation = sharedEditor.getCreationTime();

        if(System.currentTimeMillis() < creation + expiry * 1000 || expiry == 0 || creation == 0) {
            return false;
        } else {
            return true;
        }
    }
    public void logout() {
        sharedEditor.editClear();
    }

    public void setSharedPreferences(SharedPreferencesEditor spe) {
        this.sharedEditor = spe;
    }

}