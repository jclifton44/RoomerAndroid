package com.roomra.roomerAndroid.roomerandroid;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.io.PrintWriter;
import org.apache.http.NameValuePair;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * Created by jeremyclifton on 7/9/14.
 */
public class AsyncConnection extends AsyncTask<Task, Integer, String> {
    private boolean isAsync;
    private AsyncTask at;
    public AsyncConnection(boolean isAsync, AsyncTask at){
        this.isAsync = isAsync;
        this.at = at;
    }

    public String connect() {
        if(isAsync) {

        } else {

        }
        return "";
    }
    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected String doInBackground(Task... tasks) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("{[");
        for (i=0; i < tasks.length; i++){
            sb.append(tasks[i].performTask();
            if((i + 1) < tasks.length) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }
    @Override
    protected void onPostExecute(String result) {

    }
    public String secureRESTCall(String path, List<BasicNameValuePair> postVars) {
           String finalResponse = "";
        try {
            StringBuilder qString = new StringBuilder();
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RoomerConstants.ROOMRA_URL);
            httppost.setEntity(new UrlEncodedFormEntity(postVars));

            HttpResponse response = httpclient.execute(httppost);
            finalResponse = EntityUtils.toString(response.getEntity());

        } catch(Exception e) {
            System.out.println("error: %s"  + e);
            Log.d("error", "There was an error!");

        } finally {
        }
        return finalResponse;

    }
}
