package com.roomra.roomerAndroid.roomerandroid;
import android.content.BroadcastReceiver;
import android.os.PowerManager;
import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;




/**
 * Created by jeremyclifton on 7/12/14.
 */
public class TaskBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock battery = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, RoomerConstants.LOCK_TAG);
        battery.acquire();    //Acquiring Semaphore for sleep/wake

        //do shit

        battery.release();    //Releasing Semaphore for sleep/wake
    }
    public void setAlarm(Context context, String taskObject){
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TaskBroadcastReceiver.class);
        intent.putExtra("", Boolean.FALSE);

        PendingIntent pendingTask = PendingIntent.getBroadcast(context, 0, intent, 0);

    }
    public void cancelAlarm(Context context){

    }
    public void setOneTimeAlarm(Context context){

    }

}
