package com.roomra.roomerAndroid.roomerandroid;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


//import com.example.roomer.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RoomerHome extends Activity
{
    public static final String PREFS_LOGIN = "MyPrefsFile";
    public static String mainServer = "https://qu.ax.lt:81/";
    //static LocationService ls;
    static RoomerHome rh;
    static EditText sha1;
    protected static RoomerHome thisActivity;
    //static UserNode un;
    static EditText username;
    static String class_sha1;
    static String class_username;
    static SharedPreferencesEditor spe;
    public static LocationService locService;
    public AuthenticationService au;

    public void attemptLogin()
    {
        Log.d("RoomerHome", "Attempting Login");
        //DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
        AuthenticationService home = new AuthenticationService(spe, null, null);
        //RoomerDeveloper rnd = new RoomerDeveloper();
        //rnd.testTaskBuilder();
        if(home.accessGranted()) {
            Log.d("Starting second Ac", "Yep1");
            Intent localIntent = new Intent(RoomerHome.this, RoomerFrontPage.class);
            localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Log.d("Starting second Ac", "Yep2");
            startActivity(localIntent);
        }
        Log.d("loging", "logggg");
    }

    protected void onCreate(Bundle paramBundle)
    {
        Log.d("ANDROI S:DLKFJS:LKDFJS:LDKJF", "S:DLKFJS:LKDFJS");
        super.onCreate(paramBundle);
        thisActivity = this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferencesEditor localSharedPreferencesEditor = new SharedPreferencesEditor(getApplicationContext(), "UserFile");
        spe = localSharedPreferencesEditor;
        attemptLogin();

        rh = this;
        setContentView(R.layout.activity_roomer_home);
        getActionBar().hide();

        TextView localTextView1 = (TextView)findViewById(R.id.textView1);
        TextView localTextView2 = (TextView)findViewById(R.id.textView2);
        Log.d("asdf", "asdf");
        localTextView1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                Intent localIntent = new Intent(RoomerHome.this, ScreenRegister.class);
                RoomerHome.this.startActivity(localIntent);
                Log.d("Starting second Ac", "Yep");
                //RoomerHome.thisActivity.finish();
            }
        });
        localTextView2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                Intent localIntent = new Intent(RoomerHome.this, ScreenLogin.class);
                RoomerHome.this.startActivity(localIntent);
                Log.d("Starting second Ac", "Yep");
                //RoomerHome.thisActivity.finish();
            }
        });
    }

    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void onPause()
    {
        super.onPause();
        Log.d("In Pause", "PAUSE");
    }

    protected void onRestart()
    {
        super.onRestart();
    }

    public void onResume()
    {
        super.onResume();
        Log.d("In Resume", "RESUME");
    }

    public void onStart()
    {
        super.onStart();
        Log.d("In Start", "START");
    }

    protected void onStop()
    {
        super.onStop();
        Log.d("In Stop", "STOP");
    }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.RoomerHomeLogin
 * JD-Core Version:    0.6.2
 */