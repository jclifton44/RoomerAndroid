package com.roomra.roomerAndroid.roomerandroid;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
import android.app.Activity;
import org.apache.http.util.EntityUtils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.WindowManager;

public class ScreenLogin extends Activity
{
    static boolean requestReady = true;
    public static final String PREFS_LOGIN = "UserFile";
    static EditText email;
    private static ScreenLogin loginActivity;
    static EditText sha1;
    String mainServer = "https://qu.ax.lt:81/";
    private View mLoginFormView;
    private TextView mLoginStatusMessageView;
    private View mLoginStatusView;
    private String mPassword;
    private EditText mPasswordView;
    private String mUserName;
    private EditText mUserNameView;
    public SharedPreferencesEditor spe;


    public void attemptLogin()
            throws NoSuchAlgorithmException
    {
        this.mUserNameView.setError(null);
        this.mPasswordView.setError(null);
        this.mUserName = this.mUserNameView.getText().toString();
        this.mPassword = this.mPasswordView.getText().toString();
        EditText localEditText = null;
        if (TextUtils.isEmpty(this.mPassword))
        {
            this.mPasswordView.setError("Enter your password");
            localEditText = this.mPasswordView;
            requestReady = false;
        }

        if (requestReady && TextUtils.isEmpty(this.mUserName))
        {
            this.mUserNameView.setError("Enter your handle/email");
            localEditText = this.mUserNameView;
            requestReady = false;
        }
        localEditText.requestFocus();
        if(requestReady) {
            DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
            HttpPost localHttpPost = new HttpPost(this.mainServer + "php/authentication.php");
            try
            {
                ArrayList localArrayList = new ArrayList(3);
                localArrayList.add(new BasicNameValuePair("username", this.mUserName));
                localArrayList.add(new BasicNameValuePair("password", this.mPassword));
                localArrayList.add(new BasicNameValuePair("action", "2"));
                localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
                String str = new String(EntityUtils.toString(localDefaultHttpClient.execute(localHttpPost).getEntity(), "UTF-8"));
                Log.d("Starting second Ac", str);
                if (str.contains("200"))
                {
                    Log.d("Starting second Ac", "Yep1");
                    Intent localIntent = new Intent(this, RoomerHome.class);
                    Log.d("Starting second Ac", "Yep2");
                    startActivity(localIntent);
                    Log.d("Starting second Ac", "Yep3");
                    SharedPreferencesEditor localSharedPreferencesEditor = new SharedPreferencesEditor(getApplicationContext(), "MyPrefsFile");
                    localSharedPreferencesEditor.editClear();
                    //localSharedPreferencesEditor.putUserName(this.mUserName);
                    //localSharedPreferencesEditor.putSha1(HashSha1.SHA1(this.mPassword));
                    //localSharedPreferencesEditor.putHandle(((JsonObject)new JsonParser().parse(str)).get("handle").toString());
                    localSharedPreferencesEditor.commitChanges();
                    loginActivity.finish();
                    return;
                }
            }
            catch (IOException localIOException)
            {
            }
        }
        requestReady = true;
    }


    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        loginActivity = this;
        Log.d("Logging", "log");
        setContentView(R.layout.activity_login);
        getActionBar().hide();
        Log.d("Logging", "logw");
        Log.d("Logging", "lowwg");
        spe = new SharedPreferencesEditor(getApplicationContext(), "roomra");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.mUserName = getIntent().getStringExtra("com.example.android.authenticatordemo.extra.EMAIL");
        Log.d("Logging", "lsog");
        this.mUserNameView = ((EditText)findViewById(R.id.email));
        Log.d("Logging", "log");
        this.mUserNameView.setText(this.mUserName);
        Log.d("Logging", "logqw");
        this.mPasswordView = ((EditText)findViewById(R.id.password));
        Log.d("Logging", "lw");
        try {
            AuthenticationService as = new AuthenticationService(spe, "clifton", "anyanton");
        }catch(Exception e){

        }
    /*this.mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousInt == 2131427333) || (paramAnonymousInt == 0))
          try
          {
            ScreenLogin.this.attemptLogin();
            return true;
          }
          catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
          {
            while (true)
              localNoSuchAlgorithmException.printStackTrace();
          }
        return false;
      }
    });*/
        Log.d("Loggi222ng", "logqw");
        this.mLoginFormView = findViewById(R.id.login_form);
        Log.d("Loggi222ng", "logeeeqw");

        findViewById(R.id.login_form).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                try
                {
                    ScreenLogin.this.attemptLogin();
                    return;
                }
                catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
                {
                    localNoSuchAlgorithmException.printStackTrace();
                }
            }
        });
    }
  

 /* 
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    getMenuInflater().inflate(2131361792, paramMenu);
    return true;
  }

  
    protected void onCancelled()
    {
      ScreenLogin.this.mAuthTask = null;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
      ScreenLogin.this.startActivity(new Intent(LoginActivity.this, LoginActivity.class));
      return true;
    }

   /* protected void onPostExecute(Boolean paramBoolean)
    {
      ScreenLogin.this.mAuthTask = null;
      if (paramBoolean.booleanValue())
      {
        LoginActivity.this.finish();
        return;
      }
      LoginActivity.this.mPasswordView.setError(LoginActivity.this.getString(2131165197));
      LoginActivity.this.mPasswordView.requestFocus();
    }*/
}


