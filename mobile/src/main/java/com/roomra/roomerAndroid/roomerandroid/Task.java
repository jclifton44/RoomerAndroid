package com.roomra.roomerAndroid.roomerandroid;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by jeremyclifton on 7/9/14.
 */
public class Task {
    public String path;
    public ArrayList<BasicNameValuePair> postVars;
    public TaskType tt;
    public static SharedPreferencesEditor spe;
    public static Gson gson = new Gson();
    public Task(TaskType tt, String path, ArrayList<BasicNameValuePair> postVars) {
        this.tt = tt;
        this.path = path;
        this.postVars = postVars;
    }
    public TaskType getTaskType() {
        return this.tt;
    }
    public String performTask(){
       String retval = "";
       switch (this.tt) {
           case REFRESHTOKENS:
           case REOPEN:
           case SIGNON:
           case LOGOUT:
           case SIGNUP:
           case UPDATECLIENT:
           case CHECKTOKEN:
           case GETUSERINFO:
           case GETMARKS:
               retval = AsyncConnection.secureRESTCall(path, postVars);
                break;
           case REGISTERCLIENT:
                if(spe.getClientId() == "false") {
                    retval = AsyncConnection.secureRESTCall(path, postVars);
                }
               retval = AsyncConnection.secureRESTCall(path,postVars);
               break;
           default:
               return "";
       }
           return retval;
    }
    public static void postExecute(String type, String result){
        Log.d("POST HANDLE", type);
        Log.d("POST RESULT", result);
        JsonObject jsonObject = null;
        TaskType resultTaskType = TaskType.valueOf(type);
        if(result != "" && result != null) {
            jsonObject = (JsonObject)new JsonParser().parse(result);
        }
        if(jsonObject != null && jsonObject.get("error") == null) {
            switch (resultTaskType) {
                case REFRESHTOKENS:
                case REOPEN:
                case SIGNON:
                    spe.putAuthToken(((JsonObject)new JsonParser().parse(result)).get("accessToken").getAsString());
                    spe.putRefreshToken(((JsonObject) new JsonParser().parse(result)).get("refreshToken").getAsString());
                    spe.putExpiry(new Long(((JsonObject) new JsonParser().parse(result)).get("expiresIn").getAsString()));
                    spe.putCreationTime(System.currentTimeMillis());
                    break;
                case LOGOUT:
                    spe.editClear();
                    Log.d("GEOLOCATE", "NO RESULT");
                    break;
                case SIGNUP:
                    Log.d("AGGREGATE", "NO RESULT");
                    break;
                case REGISTERCLIENT:
                case UPDATECLIENT:
                    spe.putClientId(((JsonObject)new JsonParser().parse(result)).get("clientId").getAsString());
                    Log.d("GETCLIENT", "SAVING CLIENT");
                    break;
                case CHECKTOKEN:
                    break;
                case GETUSERINFO:
                    spe.putUserBlock(result);
                    spe.putUserName(((JsonObject)new JsonParser().parse(result)).get("username").getAsString());
                    Log.d("USER", "SAVING");
                case GETMARKS:
                    spe.putMarkBuffer(result);

                    break;

                default:
                    Log.d("DEFAULT", "NO RESULT");
            }
        } else {
            if(jsonObject != null && jsonObject.get("error") != null) {
                Log.d("ERROR: ", ((JsonObject)new JsonParser().parse(result)).get("error").getAsString());
            }
        }
    }
    public void reloadTaskData() {
        int i = 0;
        Log.d("RELOAD", "NAME");

        for(i=0; i < postVars.size(); i++) {
            if (postVars.get(i).getValue().equals(RoomerConstants.UPDATE_ACCESS)) {
                postVars.set(i, new BasicNameValuePair(postVars.get(i).getName(), spe.getAuthToken()));
            } else if (postVars.get(i).getValue().equals(RoomerConstants.UPDATE_REFRESH)) {
                postVars.set(i, new BasicNameValuePair(postVars.get(i).getName(), spe.getRefreshToken()));
            } else if (postVars.get(i).getValue().equals(RoomerConstants.UPDATE_USERNAME)) {
                postVars.set(i, new BasicNameValuePair(postVars.get(i).getName(), spe.getUserName()));
            }
        }

    }
    public String serialize() {
        return gson.toJson(this);

    }
    public static Task deserialize(String s) {
        return gson.fromJson(s, Task.class);
    }
}
