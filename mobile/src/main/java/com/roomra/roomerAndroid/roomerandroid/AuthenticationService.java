package com.roomra.roomerAndroid.roomerandroid;

import android.content.Context;
import android.util.Log;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import android.provider.Settings.Secure;

public class AuthenticationService {
    private SharedPreferencesEditor sharedEditor;
    public Context context;
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

        this.sharedEditor = spe;
this.logout();
        logShared();
        if(false) {
            sharedEditor.editClear();
            logShared();
        }
    }
    AuthenticationService(SharedPreferencesEditor spe, String un, String password, Context c) {
        context = c;
        Log.i("LOGGING", "CONSTRUCTION_COMPLEX");
        this.sharedEditor = spe;
        logShared();
        ArrayList<BasicNameValuePair> pv = new ArrayList<BasicNameValuePair>();
        String path = "";
        AsyncConnection ac = new AsyncConnection(false, new Task(TaskType.NOP, path, pv));
        Task task;
        Task.spe = spe;
        try{

            if(un != null && password != null) {
                Log.d("GETTING TOKEN", "OWNER");
                pv.clear();
                pv.add(new BasicNameValuePair("userId", un));
                pv.add(new BasicNameValuePair("password", password));
                task = new Task(TaskType.SIGNON, "oauth2/authenticate", pv);
                ac = new AsyncConnection(false, task);
                if(!this.clientRegistered()) {
                    Log.d("Regsitering Client with Owner", "567890987654345678909876543");
                    //downloadUserInfo(1000L);
                    downloadClientIdentifier(3000L);
            } else if(checkExpired()){
                if(spe.getClientId() != "false"){
                    Log.d("GETTING TOKEN", "CLIENT");
                    pv.clear();
                    pv.add(new BasicNameValuePair("clientId", spe.getClientId()));
                    task = new Task(TaskType.REOPEN, "oauth2/authenticate", pv);
                    ac = new AsyncConnection(false, task);
                }
            } else if(spe.getRefreshToken() != "false") {
                    Log.d("GETTING TOKEN", "REFRESH");
                    pv.clear();
                    pv.add(new BasicNameValuePair("clientId", spe.getClientId()));
                    task = new Task(TaskType.REFRESHTOKENS, "oauth2/authenticate", pv);
                    ac = new AsyncConnection(true, task);
                //update with refresh
                }
            }
            ac.connect();
        } catch (Exception e) {

        } finally {
            //this.isAuth = isAuthenticated();
            logShared();

        }
    }
    public boolean downloadClientIdentifier(Long schedule) {
        ArrayList<BasicNameValuePair> pv = new ArrayList<BasicNameValuePair>();
        AsyncConnection ac;
        Task task;
        TaskBroadcastReceiver tbr = new TaskBroadcastReceiver();
        if(!checkExpired()){
            pv.add(new BasicNameValuePair("accessToken", RoomerConstants.UPDATE_ACCESS));
            pv.add(new BasicNameValuePair("user", RoomerConstants.UPDATE_USERNAME));
            task = new Task(TaskType.UPDATECLIENT, "db/user/getClient", pv);
            tbr.setAlarm(context, task, schedule, true);
            pv.add(new BasicNameValuePair("redirectUri", Secure.getString(context.getContentResolver(), Secure.ANDROID_ID)));
            pv.add(new BasicNameValuePair("type", "public"));
            pv.add(new BasicNameValuePair("profile", "native"));
            task = new Task(TaskType.REGISTERCLIENT, "oauth2/client-registration/", pv);
            //if(sharedEditor.getClientId() == "false") {
            //    tbr.setAlarm(context, task, 4000L, true);
            //}
            return true;
        }
        return false;
    }
    public boolean downloadUserInfo(Long schedule) {
        ArrayList<BasicNameValuePair> pv = new ArrayList<BasicNameValuePair>();
        AsyncConnection ac;
        Task task;
        TaskBroadcastReceiver tbr = new TaskBroadcastReceiver();
        if(!checkExpired()){
            pv.add(new BasicNameValuePair("accessToken", RoomerConstants.UPDATE_ACCESS));
            task = new Task(TaskType.GETUSERINFO, "db/getUser", pv);
            tbr.setAlarm(context, task, schedule, true);
            return true;
        }
        return false;
    }
    public boolean checkExpired() {
        Long expiry = sharedEditor.getExpiry();
        Long creation = sharedEditor.getCreationTime();
        Log.d("" + expiry, "" + creation);
        Log.d("SYSTEMTIME", String.valueOf(System.currentTimeMillis()));
        if(System.currentTimeMillis() < creation + expiry * 1000 || expiry == 0 || creation == 0) {
            return false;
        } else {
            return true;
        }
    }
    public void logout() {
        sharedEditor.putAuthorizationCode("false");
        sharedEditor.putAuthToken("false");
        sharedEditor.putClientId("false");
        sharedEditor.putRefreshToken("false");
        sharedEditor.putUserBlock("false");
        sharedEditor.putUserName("false");
        sharedEditor.putExpiry(0L);
        sharedEditor.putCreationTime(0L);

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