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
        Task[] t = new Task[1000];

        this.sharedEditor = spe;
        logShared();
        this.isAuth = isAuthenticated();
        if(!this.isAuth) {
            sharedEditor.editClear();
            logShared();

        }
    }
    AuthenticationService(SharedPreferencesEditor spe, String un, String password) {
        Log.i("LOGGING", "CONSTRUCTION_COMPLEX");
        this.sharedEditor = spe;
        logShared();

        try{
            Log.d("creating SPE", "SPE");
            Log.d(spe.getAuthToken().length() + "", "THE AUTH TOKEN");
            Log.d(spe.getAuthToken(), "THE AUTH TOKEN");
            Log.d(spe.getAuthToken().length() + "", "THE AUTH TOKEN");

            Log.d(spe.getClientId() + "", "THE AUTH TOKEN");


            if(spe.getAuthToken() != "1KEY"){
            //if(false) {
                Long expiry = new Long(spe.getExpiry());
                Long creationTime = new Long(spe.getCreationTime());
                Long nowTime = new Long(System.currentTimeMillis());
                this.isAuth = true;
                if(nowTime < (expiry + creationTime)) {
                    ArrayList<BasicNameValuePair> pv = new ArrayList(3);
                    pv.add(new BasicNameValuePair("refreshToken", spe.getRefreshToken()));

                    AsyncConnection asyncConnection = new AsyncConnection(false, new Task(TaskType.DB, "login", pv));
                    String response = asyncConnection.connect();
                    Log.d("RESONSE", response);
                    pv.add(new BasicNameValuePair("refreshToken", spe.getRefreshToken()));
                    String error = ((JsonObject)new JsonParser().parse(response)).get("error").toString();
                    if(error != null) {
                        Log.d("----","error");
                    } else {
                        String token = ((JsonObject)new JsonParser().parse(response)).get("accessToken").toString();
                        String refresh = ((JsonObject)new JsonParser().parse(response)).get("refreshToken").toString();
                        spe.putAuthToken(token);
                        spe.putRefreshToken(refresh);
                        Log.d(token,refresh);
                        this.isAuth = true;
                    }

                }
                if(spe.getClientId() == "1KEY") {
                    if(spe.getAuthToken() != "1KEY"){
                        //check client existence
                        Log.d("WOW", "MAKING CLIENT");
                        ArrayList<BasicNameValuePair> pv = new ArrayList(3);
                        pv.add(new BasicNameValuePair("accessToken", spe.getAuthToken()));

                        String response = AsyncConnection.secureRESTCall("db/user/getClient/", pv);
                        Log.d(response, "RESPONSPSONPOSNS");
                        JsonObject JSONClientCreate = (JsonObject)new JsonParser().parse(response);
                        if(JSONClientCreate.get("error") != null) {
                            //Create a client
                            pv = new ArrayList(4);
                            pv.add(new BasicNameValuePair("clientType", "public"));
                            pv.add(new BasicNameValuePair("clientType", "native"));
                            pv.add(new BasicNameValuePair("clientType", "https://roomra.com/frontPage"));
                            pv.add(new BasicNameValuePair("accessToken", spe.getAuthToken()));
                            String clientResponse = AsyncConnection.secureRESTCall("oauth2/client-registration/", pv);
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

                String ownerResponse = AsyncConnection.secureRESTCall("login/", pv);
                Log.d("THIS>>", ownerResponse);
                JsonObject JSONOwnerResponse = (JsonObject) new JsonParser().parse(ownerResponse);
                String errorResponse = "";
                String accessTokenResponse = "";
                String refreshTokenResponse = "";
                if (JSONOwnerResponse.get("error") != null) {
                    errorResponse = JSONOwnerResponse.get("error").toString();
                    Log.d("ERROR", errorResponse);
                } else if (JSONOwnerResponse.get("accessToken") != null) {
                    if (JSONOwnerResponse.get("refreshToken") != null) {
                        refreshTokenResponse = JSONOwnerResponse.get("refreshToken").toString();
                    }
                    accessTokenResponse = JSONOwnerResponse.get("accessToken").toString();
                    Log.d("AUTHENTICATED", accessTokenResponse);
                    spe.putAuthToken(accessTokenResponse);
                    spe.putRefreshToken(refreshTokenResponse);
                    spe.putExpiry(new Long(Integer.parseInt(JSONOwnerResponse.get("expiresIn").toString()) * 1000));
                    spe.putCreationTime(new Long(System.currentTimeMillis()));
                    this.isAuth = true;
                }
                ArrayList<BasicNameValuePair> clientPv = new ArrayList(3);
                clientPv.add(new BasicNameValuePair("accessToken", spe.getAuthToken()));
                String response = AsyncConnection.secureRESTCall("db/user/getClient", pv);
                Log.d(response," RESPONSPNSONPSONSPONSPONSPONSPONSPON");
                JSONOwnerResponse = (JsonObject) new JsonParser().parse(response);
                if (JSONOwnerResponse.get("error") != null) {
                    clientPv = new ArrayList(4);
                    clientPv.add(new BasicNameValuePair("clientType", "public"));
                    clientPv.add(new BasicNameValuePair("clientType", "native"));
                    clientPv.add(new BasicNameValuePair("clientType", "https://roomra.com/frontPage"));
                    clientPv.add(new BasicNameValuePair("accessToken", spe.getAuthToken()));
                    String clientResponse = AsyncConnection.secureRESTCall("oauth2/client-registration", pv);
                    JsonObject JSONClientResponse = (JsonObject) new JsonParser().parse(clientResponse);
                    String clientIdResults = ((JsonObject) new JsonParser().parse(clientResponse)).get("clientId").toString();
                    if (JSONClientResponse.get("error") == null) {
                        //Authentication Failed (spe entries should be null
                        Log.d("client created for User", "No error");
                        spe.putClientId(clientId);
                    } else {
                        Log.d("Error", "client not found");
                    }
                } else if (JSONOwnerResponse.get("client") != null) {
                    spe.putClientId(JSONOwnerResponse.get("client").toString());
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
            //this.isAuth = isAuthenticated();
        }
    }

    public boolean isAuthenticated() {
        Log.i("LOGGING", "IS_AUTH");
logShared();
        String authToken = this.sharedEditor.getAuthToken();
        Log.d("AUTHENTICATING...", authToken);
        ArrayList<BasicNameValuePair> nvp = new ArrayList<BasicNameValuePair>();
        nvp.add(new BasicNameValuePair("accessToken", authToken));
        String response = AsyncConnection.secureRESTCall("checkToken", nvp);
        Log.d("IS AUTHENTICATED", response);
        JsonObject JSON_response = (JsonObject) new JsonParser().parse(response);
        if(JSON_response.get("error") != null) {
            Log.d("AUTHENTICATION","NOT_AUTHENTICATED" );
            String errorResponse = JSON_response.get("error").toString();
            return false;
        } else if(JSON_response.get("authenticated") != null) {
            String authenticationResponse = JSON_response.get("authenticated").toString();
            if(authenticationResponse == "true") {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public void logout() {
        sharedEditor.editClear();
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