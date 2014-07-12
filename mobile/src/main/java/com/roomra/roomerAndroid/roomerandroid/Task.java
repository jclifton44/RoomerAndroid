package com.roomra.roomerAndroid.roomerandroid;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import android.util.Log;
import android.content.Context;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by jeremyclifton on 7/9/14.
 */
public class Task {
    public String path;
    public ArrayList<BasicNameValuePair> postVars;
    public TaskType tt;
    public static SharedPreferencesEditor spe;
    public Task(TaskType tt, String path, ArrayList<BasicNameValuePair> postVars) {
        this.tt = tt;
        this.path = path;
        this.postVars = postVars;
    }
    public TaskType getTaskType() {
        return this.tt;
    }
    public String performTask(){
       switch (this.tt) {
           case SIGNON:
               return AsyncConnection.secureRESTCall(path, postVars);
           case LOGOUT:
               return "";
           case SIGNUP:
               return "";
           default:
               return "";
       }
    }
    public static void postExecute(String type, String result){
        TaskType resultTaskType = TaskType.valueOf(type);
        if(((JsonObject)new JsonParser().parse(result)).get("error") != null) {
            switch (resultTaskType) {
                case REFRESHTOKENS:
                case REOPEN:
                case SIGNON:
                    spe.putAuthToken(((JsonObject)new JsonParser().parse(result)).get("accessToken").toString());
                    spe.putRefreshToken(((JsonObject)new JsonParser().parse(result)).get("refreshToken").toString());
                    spe.putExpiry(new Long(((JsonObject)new JsonParser().parse(result)).get("expiresIn").toString()));
                    spe.putCreationTime(System.currentTimeMillis());
                case LOGOUT:
                    spe.editClear();
                    Log.d("GEOLOCATE", "NO RESULT");
                case SIGNUP:
                    Log.d("AGGREGATE", "NO RESULT");
                case REGISTERCLIENT:
                case UPDATECLIENT:
                    spe.putClientId(((JsonObject)new JsonParser().parse(result)).get("clientId").toString());
                    Log.d("GETCLIENT", "NO RESULT");
                default:
                    Log.d("DEFAULT", "NO RESULT");
            }
        } else {
            Log.d("ERROR: ", ((JsonObject)new JsonParser().parse(result)).get("error").toString());
        }
    }
}
