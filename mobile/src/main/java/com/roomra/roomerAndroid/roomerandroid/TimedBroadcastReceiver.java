package com.roomra.roomerAndroid.roomerandroid;
import android.content.BroadcastReceiver;
import android.os.PowerManager;
import android.content.Context;
import android.content.Intent;



/**
 * Created by jeremyclifton on 7/12/14.
 */
public class TimedBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock battery = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, RoomerConstants.LOCK_TAG);
        battery.acquire();    //Acquiring Semaphore for sleep/wake

        //do shit

        battery.release();    //Releasing Semaphore for sleep/wake
    }
    public void setAlarm(Context context){

    }
    public void cancelAlarm(Context context){

    }
    public void setOneTimeAlarm(Context context){

    }

}
