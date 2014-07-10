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
    public static String requestUrl = null;
    public static String URL = new String("https://localhost:8001");
    AuthenticationService(SharedPreferencesEditor spe, String un, String password) {
        try{
            this.requestUrl = "https://www.roomra.com/";
            this.sharedEditor = spe;
            Log.d("creating SPE", "SPE");
            Log.d(spe.getAuthToken().length() + "", "THE AUTH TOKEN");
            Log.d(spe.getAuthToken(), "THE AUTH TOKEN");
            Log.d(spe.getAuthToken().length() + "", "THE AUTH TOKEN");

            Log.d(spe.getAuthToken().length() + "", "THE AUTH TOKEN");


            if(spe.getAuthToken().length() > 8){

                Long expiry = new Long(spe.getExpiry());
                Long creationTime = new Long(spe.getCreationTime());
                Long nowTime = new Long(System.currentTimeMillis());
                if(nowTime > (expiry + creationTime - 30000)) {
                    List<BasicNameValuePair> pv = new ArrayList(3);
                    pv.add(new BasicNameValuePair("refreshToken", spe.getRefreshToken()));

                    String response = AsyncConnection.secureRESTCall("oauth2/authenticate", pv);
                    String error = ((JsonObject)new JsonParser().parse(response)).get("error").toString();
                    if(error != null) {
                        Log.d("----","error");
                    } else {
                        String token = ((JsonObject)new JsonParser().parse(response)).get("accessToken").toString();
                        String refresh = ((JsonObject)new JsonParser().parse(response)).get("refreshToken").toString();
                        spe.putAuthToken(token);
                        spe.putRefreshToken(refresh);
                        Log.d(token,refresh);

                    }
                    Log.d(response, "SSL REQUEST");
                    System.out.println("SSL REQUEST " + response);
                }
                if(spe.getClientId() == null || spe.getClientId() == "") {
                    if(spe.getAuthToken() != null){
                        //check client existence
                        List<BasicNameValuePair> pv = new ArrayList(3);
                        pv.add(new BasicNameValuePair("accessToken", spe.getAuthToken()));
                        String response = AsyncConnection.secureRESTCall("db/user/getClient", pv);
                        String error = ((JsonObject)new JsonParser().parse(response)).get("error").toString();
                        if(error == null || error == "") {
                            //Create a client
                            pv = new ArrayList(4);
                            pv.add(new BasicNameValuePair("clientType", "public"));
                            pv.add(new BasicNameValuePair("clientType", "native"));
                            pv.add(new BasicNameValuePair("clientType", "https://roomra.com/frontPage"));
                            pv.add(new BasicNameValuePair("accessToken", spe.getAuthToken()));
                            String clientResponse = AsyncConnection.secureRESTCall("oauth2/client-registration", pv);
                            String clientErrorResults = ((JsonObject) new JsonParser().parse(clientResponse)).get("error").toString();
                            String clientIdResults = ((JsonObject) new JsonParser().parse(clientResponse)).get("clientId").toString();
                            if(clientErrorResults == "" || clientErrorResults == null) {
                                    //Authentication Failed (spe entries should be null
                            } else {
                                   Log.d("client created for User", "No error");
                                   spe.putClientId(clientId);
                            }
                        } else {
                            String clientId = ((JsonObject)new JsonParser().parse(response)).get("clientId").toString();
                            spe.putClientId(clientId);
                            Log.d("CLIENT ID",clientId);
                        }
                    }
                    //FUTURE: get LOW-PRIVELEDGE ACCESS client ID
                }
            } else {
                Log.d("AUTH", "No AuthToken. Making new1");
                List<BasicNameValuePair> pv = new ArrayList(3);
                Log.d("AUTH", "No AuthToken. + " + un);
                Log.d("AUTH", "No AuthToken. + " + password);

                pv.add(new BasicNameValuePair("userId", un));
                Log.d("AUTH", "No AuthToken. Making new3");

                pv.add(new BasicNameValuePair("password", password));
                Log.d("AUTH", "No AuthToken. Making new4");

                String ownerResponse = AsyncConnection.secureRESTCall("login", pv);
                Log.d(ownerResponse, "THIS>>");
                String ownerError = ((JsonObject) new JsonParser().parse(ownerResponse)).get("error").toString();
                String ownerAuthToken = ((JsonObject) new JsonParser().parse(ownerResponse)).get("accessToken").toString();
                if(ownerError == null || ownerError == "") {

                } else {
                    spe.putAuthToken(ownerAuthToken);
                }


        }
        } catch (Exception e) {
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