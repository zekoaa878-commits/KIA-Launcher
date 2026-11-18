package com.kia.launcher;
import android.content.*; public class BootReceiver extends BroadcastReceiver{ public void onReceive(Context c,Intent i){ if(Intent.ACTION_BOOT_COMPLETED.equals(i.getAction())){ Intent s=new Intent(c,ObdService.class); c.startForegroundService(s);} } }
