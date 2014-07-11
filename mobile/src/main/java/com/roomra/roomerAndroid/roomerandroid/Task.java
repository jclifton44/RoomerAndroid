package com.roomra.roomerAndroid.roomerandroid;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;

/**
 * Created by jeremyclifton on 7/9/14.
 */
public class Task {
    public String path;
    public ArrayList<BasicNameValuePair> postVars;
    public TaskType tt;
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
           case DB:
               return AsyncConnection.secureRESTCall(path, postVars);
           case GEOLOCATE:
               return "";
           case AGGREGATE:
               return "";
           case GETCLIENT:
           default:
               return "";
       }

    }
}
