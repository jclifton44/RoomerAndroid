package com.roomra.roomerAndroid.roomerandroid;
import android.content.BroadcastReceiver;
import android.os.PowerManager;
import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.util.Log;





/**
 * Created by jeremyclifton on 7/12/14.
 */
public class TaskBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock battery = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, RoomerConstants.LOCK_TAG);
        battery.acquire();    //Acquiring Semaphore for sleep/wake

        //do shit
        Log.d("doing this...", intent.getExtras().getString("task"));
        battery.release();    //Releasing Semaphore for sleep/wake
    }
    public void setAlarm(Context context, Task task, Long intervalTime, Boolean oneTime){
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TaskBroadcastReceiver.class);
        intent.putExtra("task", task.serialize());
        PendingIntent pendingTask = PendingIntent.getBroadcast(context, 0, intent, 0);
        if(oneTime) {
            alarm.set(AlarmManager.RTC_WAKEUP, RoomerConstants.START_TIME, pendingTask);
        } else {
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, RoomerConstants.START_TIME, intervalTime, pendingTask);
        }
    }

}
