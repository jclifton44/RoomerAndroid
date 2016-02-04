package com.roomra.roomerAndroid.roomerandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
@SuppressLint("NewApi")
public class ScreenRegister extends Activity
{
    private static final String[] DUMMY_CREDENTIALS = { "foo@example.com:hello", "bar@example.com:world" };
    public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
    private static ScreenRegister sr;
    public Context context;
    //private UserLoginTask mAuthTask = null;
    private String mHandle;
    private EditText mHandleView;
    private View mLoginFormView;
    private TextView mLoginStatusMessageView;
    private View mLoginStatusView;
    private String mPassword;
    private EditText mPasswordView;
    private String mUserName;
    private EditText mUserNameView;
    private Camera mCamera;
    private CameraPreview mPreview;
    public Toast toast;


    public void attemptLogin()
            throws NoSuchAlgorithmException
    {


        this.mUserNameView.setError(null);
        this.mPasswordView.setError(null);
        this.mUserName = this.mUserNameView.getText().toString();
        this.mPassword = this.mPasswordView.getText().toString();
        this.mHandle = this.mHandleView.getText().toString();
        EditText localEditText = null;
        int j;
        if (TextUtils.isEmpty(this.mPassword))
        {
            this.mPasswordView.setError("Enter a password");
            localEditText = this.mPasswordView;
            j = 1;
            if (!TextUtils.isEmpty(this.mUserName))

                this.mUserNameView.setError("You didn't enter a username");
            localEditText = this.mUserNameView;
            j = 1;
        }


        localEditText.requestFocus();
        int i = this.mPassword.length();
        j = 0;
        localEditText = null;

        this.mPasswordView.setError("Enter a password");
        localEditText = this.mPasswordView;
        j = 1;
        if (!this.mUserName.contains("@"))
        {
            this.mUserNameView.setError("Enter username");
            localEditText = this.mUserNameView;
            j = 1;
        }

        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
        HttpPost localHttpPost = new HttpPost(RoomerHome.mainServer + "php/authentication.php");
        try
        {
            Log.d("inregister", "");
            ArrayList localArrayList = new ArrayList(5);
            localArrayList.add(new BasicNameValuePair("email", this.mUserName));
            localArrayList.add(new BasicNameValuePair("sha1", this.mPassword));
            localArrayList.add(new BasicNameValuePair("handle", this.mHandle));
            localArrayList.add(new BasicNameValuePair("action", "1"));
            localArrayList.add(new BasicNameValuePair("webdesk", "0"));
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
                localSharedPreferencesEditor.putUserName(this.mUserName);
                localSharedPreferencesEditor.putSha1(this.mPassword);
                localSharedPreferencesEditor.commitChanges();
                sr.finish();

            }
        }
        catch (ClientProtocolException localClientProtocolException)
        {
        }
        catch (IOException localIOException)
        {
        }
    }
    protected void onStop(){
        super.onStop();
        Log.d("Stopping", "NOW");
        mCamera.release();
        mCamera = null;

    }


    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        sr = this;
        this.context = getApplicationContext();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_register);
        getActionBar().hide();
        this.mUserNameView = ((EditText)findViewById(R.id.email));
        this.mPasswordView = ((EditText)findViewById(R.id.password));
        this.mHandleView = ((EditText)findViewById(R.id.handle));
        this.mLoginFormView = findViewById(R.id.login);
        mCamera = Camera.open();


        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                try
                {
                    ScreenRegister.this.attemptLogin();
                    return;
                }
                catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
                {
                    localNoSuchAlgorithmException.printStackTrace();
                }
            }
        });
        this.mUserNameView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                Log.d("wow", "text");
            }
        });
    }


/* public class UserLoginTask extends AsyncTask<Void, Void, Boolean>
  {
    public UserLoginTask()
    {
    }

    protected Boolean doInBackground(Void[] paramArrayOfVoid)
    {
      while (true)
      {
        String[] arrayOfString1;
        int j;
        try
        {
          Thread.sleep(2000L);
          arrayOfString1 = ScreenRegister.DUMMY_CREDENTIALS;
          int i = arrayOfString1.length;
          j = 0;
          if (j >= i)
            return Boolean.valueOf(true);
        }
        catch (InterruptedException localInterruptedException)
        {
          return Boolean.valueOf(false);
        }
        String[] arrayOfString2 = arrayOfString1[j].split(":");
        if (arrayOfString2[0].equals(ScreenRegister.this.mUserName))
          return Boolean.valueOf(arrayOfString2[1].equals(ScreenRegister.this.mPassword));
        j++;
      }
    }

    protected void onCancelled()
    {
      ScreenRegister.this.mAuthTask = null;
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      ScreenRegister.this.mAuthTask = null;
      if (paramBoolean.booleanValue())
      {
        ScreenRegister.this.finish();
        return;
      }
      ScreenRegister.this.mPasswordView.setError(ScreenRegister.this.getString(2131165197));
      ScreenRegister.this.mPasswordView.requestFocus();
    }
  }*/
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.RegisterActivity
 * JD-Core Version:    0.6.2
 */