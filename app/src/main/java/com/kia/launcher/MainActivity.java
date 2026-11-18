package com.kia.launcher;
import android.os.*; import android.widget.*; import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.*; import java.util.Set;
public class MainActivity extends AppCompatActivity {
  TextView speedTv,rpmTv,tempTv,fuelTv,statusTv; Handler h=new Handler(Looper.getMainLooper()); boolean demo=true;
  protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_main);
    speedTv=findViewById(R.id.speed); rpmTv=findViewById(R.id.rpm); tempTv=findViewById(R.id.temp); fuelTv=findViewById(R.id.fuel); statusTv=findViewById(R.id.status);
    findViewById(R.id.btnObd).setOnClickListener(v->{ connectObd(); });
    findViewById(R.id.btnDemo).setOnClickListener(v->{ demo=!demo; statusTv.setText(demo?"وضع المحاكاة - DEMO":"وضع حقيقي - OBD"); });
    startDemoLoop();
  }
  void startDemoLoop(){ h.postDelayed(new Runnable(){ int s=96; public void run(){ if(demo){ s+= (int)(Math.random()*6-3); if(s<60)s=60; if(s>120)s=120; speedTv.setText(String.valueOf(s)); rpmTv.setText(String.format("%.1f",(s/38.0))); tempTv.setText("89°"); fuelTv.setText("62%"); } h.postDelayed(this,600);} },600); }
  void connectObd(){ new Thread(()->{ try{ BluetoothAdapter ad=BluetoothAdapter.getDefaultAdapter(); BluetoothDevice target=null; for(BluetoothDevice d: ad.getBondedDevices()){ if(d.getName()!=null && (d.getName().contains("OBD")||d.getName().contains("ELM")||d.getName().contains("V-LINK"))) target=d; } if(target==null){ h.post(()->statusTv.setText("لم يتم العثور على OBDII مقترن - اقترن أولا من إعدادات البلوتوث PIN 1234")); return; } ObdClient c=new ObdClient(); c.connect(target); h.post(()->statusTv.setText("متصل: "+target.getName()+" - ELM327 v1.5")); while(true){ ObdClient.ObdData d=c.poll(); h.post(()->{ if(d.speed>=0) speedTv.setText(String.valueOf(d.speed)); if(d.rpm>=0) rpmTv.setText(String.format("%.1f",d.rpm/1000.0)); if(d.coolant>-40) tempTv.setText(d.coolant+"°"); if(d.fuel>=0) fuelTv.setText(d.fuel+"%"); }); demo=false; Thread.sleep(600);} }catch(Exception e){ h.post(()->statusTv.setText("خطأ اتصال: "+e.getMessage())); } }).start(); }
}
