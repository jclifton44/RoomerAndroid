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

import com.google.common.collect.Multimap;
import com.google.common.collect.ArrayListMultimap;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * Created by jeremyclifton on 7/9/14.
 */
public class AsyncConnection extends AsyncTask<Task, Integer, ArrayList<NameValuePair>> {
    private boolean isAsync;
    private Task[] ats;
    public AsyncConnection(boolean isAsync, Task... ats) {
        this.isAsync = isAsync;
        this.ats = ats;
    }

    public String connect() {
        if(isAsync) {
            this.execute(ats);
            return "FINISHED";
        }
        return "NOT ASYNC";

    }
    @Override
    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected ArrayList<NameValuePair> doInBackground(Task... tasks) {
        //Call Progress update to do updates and interface w/ UI
        int i = 0;
        ArrayList<NameValuePair> al = new ArrayList<NameValuePair>();
        StringBuilder sb = new StringBuilder();
        sb.append("{'taskGroup': [");
        for (i=0; i < tasks.length; i++){
            String taskResult = tasks[i].performTask();
            sb.append("{'taskType':" + ((Task)tasks[i]).getTaskType() + ", 'taskResult': ");
            sb.append(taskResult);
            al.add(new BasicNameValuePair((((Task)tasks[i]).getTaskType()).name(), taskResult));
            sb.append("}");
            if((i + 1) < tasks.length) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        String retval = sb.toString();
        Log.d("Creating JSON of task group...", retval);
        return al;
    }
    @Override
    protected void onPostExecute(ArrayList<NameValuePair> result) {
        for(int i = 0; i < result.size(); i++){
           // Multimap mm = result.get(1)
           Task.postExecute(result.get(i).getName(), result.get(i).getValue());
           Log.d(result.get(i).getName(), result.get(i).getValue());
        }
    }
    public static String secureRESTCall(String path, List<BasicNameValuePair> postVars) {
           String finalResponse = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RoomerConstants.ROOMRA_URL + path);
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
