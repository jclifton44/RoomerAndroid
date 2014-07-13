package com.roomra.roomerAndroid.roomerandroid;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.cookie.Cookie;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.client.protocol.ClientContext;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.NameValuePair;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.http.protocol.BasicHttpContext;
import android.webkit.CookieSyncManager;
import android.webkit.CookieManager;
import java.net.CookieStore;
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

    public void connect() {
        if(isAsync) {
            this.execute(ats);
        } else {
            onPostExecute(doInBackground(ats));
        }
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
        Log.d("REST", "MAKING SECURE CONNECTION...");

        String finalResponse = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RoomerConstants.ROOMRA_URL + path);
            httppost.setEntity(new UrlEncodedFormEntity(postVars));

            DefaultHttpClient mHttpClient = new DefaultHttpClient();
            BasicHttpContext mHttpContext = new BasicHttpContext();
            BasicCookieStore mCookieStore      = new BasicCookieStore();
            mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore);

            HttpResponse response = httpclient.execute(httppost, mHttpContext);


            finalResponse = EntityUtils.toString(response.getEntity());
            //finalResponse = finalResponse.replace("\"","");
        } catch(Exception e) {
            System.out.println("error: %s"  + e);
            Log.d("error", "There was an error!");

        } finally {
        }
        return finalResponse;

    }
}
