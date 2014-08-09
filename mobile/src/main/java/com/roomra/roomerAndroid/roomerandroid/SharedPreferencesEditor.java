package com.roomra.roomerAndroid.roomerandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import java.lang.String;

public class SharedPreferencesEditor
{
    public Editor ed;
    private SharedPreferences pref;
    public String preferenceName = "false";

    /*
    Most of these are singletons. We can add a transformation layer as needed, though :)
     */
    public SharedPreferencesEditor(Context paramContext, String paramString)
    {
        this.pref = paramContext.getSharedPreferences(paramString, 0);
        ed = getEditor();

    }

    public void setupListListener(){

    }
    public SharedPreferences getPref() {
        return this.pref;
    }
    public void setPref(SharedPreferences pref) {
        this.pref = pref;
    }
    public void commitChanges()
    {
        this.ed.commit();
    }

    public void editClear()
    {
        this.ed.clear(); commitChanges();
    }

    public Editor getEditor()
    {
        return this.pref.edit();
    }

    public String getUserBlock()
    {
        return this.pref.getString("key_block", this.preferenceName);
    }

    public String getHandle()
    {
        return this.pref.getString("key_handle", this.preferenceName);
    }

    public String getSha1()
    {
        return this.pref.getString("key_password", this.preferenceName);
    }

    public String getUserName()
    {
        return this.pref.getString("key_email", this.preferenceName);
    }

    public String getAuthToken() { return this.pref.getString("key_auth", this.preferenceName); }

    public String getClientId() { return this.pref.getString("key_client", this.preferenceName); }

    public String getRefreshToken() { return this.pref.getString("key_refresh", this.preferenceName); }

    public String getAuthorizationCode() { return this.pref.getString("key_code", this.preferenceName); }

    public Long getExpiry() { return this.pref.getLong("key_expiry", 0);}

    public Long getCreationTime() { return this.pref.getLong("key_time", 0);}

    public String getMarkBuffer() { return this.pref.getString("key_mark_buffer", this.preferenceName);}


    public void putUserBlock(String paramString) {
        this.ed.putString("key_block", paramString);
        this.ed.commit();
    }

    public void putHandle(String paramString) {
        this.ed.putString("key_handle", paramString);
        this.ed.commit();
    }

    public void putSha1(String paramString) {
        this.ed.putString("key_password", paramString);
        this.ed.commit();

    }

    public void putUserName(String paramString) {
        this.ed.putString("key_email", paramString);
        this.ed.commit();

    }

    public void putAuthToken(String paramString) {
        this.ed.putString("key_auth", paramString);
        this.ed.commit();

    }

    public void putClientId(String paramString) {
        this.ed.putString("key_client", paramString);
        this.ed.commit();

    }

    public void putRefreshToken(String paramString) {
        this.ed.putString("key_refresh", paramString);
        this.ed.commit();

    }

    public void putAuthorizationCode(String paramString) {
        this.ed.putString("key_code", paramString);
        this.ed.commit();
    }

    public void putExpiry(Long paramInt) {
        this.ed.putLong("key_expiry", paramInt);
        this.ed.commit();
    }
    public void putCreationTime(Long paramInt) {
        this.ed.putLong("key_time", paramInt);
        this.ed.commit();
    }

    public void putMarkBuffer(String paramString) {
        this.ed.putString("key_mark_buffer", paramString);
        this.ed.commit();
    }
}

/* Location:           /Users/jeremyclifton/Downloads/dex2jar-0.0.9.15/classes_dex2jar.jar
 * Qualified Name:     com.example.roomer.SharedPreferencesEditor
 * JD-Core Version:    0.6.2
 */