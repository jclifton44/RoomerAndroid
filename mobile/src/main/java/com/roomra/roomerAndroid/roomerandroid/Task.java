package com.roomra.roomerAndroid.roomerandroid;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;

/**
 * Created by jeremyclifton on 7/9/14.
 */
public class Task {
    public enum TaskType{
        DB,
        GEOLOCATE,
        AGGREGATE
    }
    public Task(TaskType tt, String path, ArrayList<BasicNameValuePair> postVars) {

    }
}
