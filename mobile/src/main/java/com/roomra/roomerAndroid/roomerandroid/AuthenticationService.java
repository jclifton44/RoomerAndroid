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
        if(sharedEditor.getAuthToken() != null) {
            Log.i("LOGGING", "AUTH " + sharedEditor.getAuthToken());
        }
        if(sharedEditor.getRefreshToken() != null) {
            Log.i("LOGGING", "REF " + sharedEditor.getRefreshToken());
        }
        if(sharedEditor.getClientId() != null) {
            Log.i("LOGGING", "CLIENT " + sharedEditor.getClientId());
        }
        if(sharedEditor.getAuthToken() != null) {
            Log.i("LOGGING", "AUTH " + sharedEditor.getAuthToken());
        }
        if(sharedEditor.getAuthToken() != null) {
            Log.i("LOGGING", "AUTH " + sharedEditor.getAuthToken());
        }

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
        //logShared();
        ArrayList<BasicNameValuePair> pv = new ArrayList<BasicNameValuePair>();
        String path = "";
        AsyncConnection ac = new AsyncConnection(false, new Task(TaskType.NOP, path, pv));
        Task task;
        Task.spe = spe;
        try{

            if(checkExpired()){
                if(spe.getClientId() != "false"){
                    Log.d("GETTING TOKEN", "CLIENT");
                    pv.clear();
                    pv.add(new BasicNameValuePair("clientId", spe.getClientId()));
                    task = new Task(TaskType.REOPEN, "oauth2/authenticate", pv);
                    ac = new AsyncConnection(false, task);
                }
            } else {
                if(spe.getRefreshToken() != "false") {
                    if(!this.clientRegistered()){
                        downloadClientIdentifier();
                    }
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
                    ac = new AsyncConnection(false, task);

                }
            }
            ac.connect();
        } catch (Exception e) {

        } finally {
            //this.isAuth = isAuthenticated();
            logShared();

        }
    }
    public boolean downloadClientIdentifier() {
        ArrayList<BasicNameValuePair> pv = new ArrayList<BasicNameValuePair>();
        AsyncConnection ac;
        Task task;
        if(!checkExpired()){
            pv.add(new BasicNameValuePair("accessToken", sharedEditor.getAuthToken()));
            task = new Task(TaskType.UPDATECLIENT, "db/user/getClient", pv);
            ac = new AsyncConnection(false, task);
            ac.connect();
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
            ac = new AsyncConnection(true, task);
            ac.connect();
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
    public boolean clientRegistered() {
        return sharedEditor.getClientId() != "false";
    }
    public boolean accessGranted() {
        return !this.checkExpired() && sharedEditor.getAuthToken() != "false";
    }
    public void setSharedPreferences(SharedPreferencesEditor spe) {
        this.sharedEditor = spe;
    }

}