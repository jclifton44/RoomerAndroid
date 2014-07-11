package com.roomra.roomerAndroid.roomerandroid;

import java.security.NoSuchAlgorithmException;
//import org.apache.http.util.EntityUtils;
import android.app.Activity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
           // AuthenticationService as = new AuthenticationService(spe, "clifton", "anyanton");

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

        findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                try
                {
                    ScreenLogin.this.loginActivity.attemptLogin(ScreenLogin.this);
                    return;
                }
                catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
                {
                    localNoSuchAlgorithmException.printStackTrace();
                }
            }
        });
    }

    public void attemptLogin(ScreenLogin screenLogin)
            throws NoSuchAlgorithmException
    {
        screenLogin.mUserNameView.setError(null);
        screenLogin.mPasswordView.setError(null);
        screenLogin.mUserName = screenLogin.mUserNameView.getText().toString();
        screenLogin.mPassword = screenLogin.mPasswordView.getText().toString();
        EditText localEditText = null;
        if (TextUtils.isEmpty(screenLogin.mPassword))
        {
            screenLogin.mPasswordView.setError("Enter your password");
            localEditText = screenLogin.mPasswordView;
            requestReady = false;
        }

        if (requestReady && TextUtils.isEmpty(screenLogin.mUserName))
        {
            screenLogin.mUserNameView.setError("Enter your handle/email");
            localEditText = screenLogin.mUserNameView;
            requestReady = false;
        }
        if(requestReady) {
            AuthenticationService au =  new AuthenticationService(screenLogin.spe, screenLogin.mUserName, screenLogin.mPassword);
            //AuthenticationService au = new AuthenticationService(screenLogin.spe);
                if(au.isAuth) {
                    Log.d("Starting second Ac", "Yep1");
                    Intent localIntent = new Intent(screenLogin, RoomerFrontPage.class);
                    localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Log.d("Starting second Ac", "Yep2");
                    screenLogin.startActivity(localIntent);
                    Log.d("Starting second Ac", "Yep3");
                    SharedPreferencesEditor localSharedPreferencesEditor = new SharedPreferencesEditor(getApplicationContext().getApplicationContext(), "MyPrefsFile");
                    localSharedPreferencesEditor.editClear();
                    //localSharedPreferencesEditor.putUserName(this.mUserName);
                    //localSharedPreferencesEditor.putSha1(HashSha1.SHA1(this.mPassword));
                    //localSharedPreferencesEditor.putHandle(((JsonObject)new JsonParser().parse(str)).get("handle").toString());
                    localSharedPreferencesEditor.commitChanges();
                    finish();
                }
            return;
            } else {
                localEditText.requestFocus();
            }
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


