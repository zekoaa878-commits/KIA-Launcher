package com.kia.launcher;
import android.app.*; import android.content.Intent; import android.os.IBinder;
public class ObdService extends Service { public IBinder onBind(Intent i){return null;} public int onStartCommand(Intent i,int f,int id){ startForeground(1,new Notification.Builder(this).setContentTitle("KIA OBD").setContentText("متصل بـ ELM327").build()); return START_STICKY; } }
