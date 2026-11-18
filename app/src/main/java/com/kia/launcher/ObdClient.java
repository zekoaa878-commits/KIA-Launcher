package com.kia.launcher;
import android.bluetooth.*;
import java.io.*; import java.util.UUID;
public class ObdClient {
  public static final UUID SPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
  private BluetoothSocket socket; private BufferedReader in; private OutputStream out;
  public boolean connect(BluetoothDevice d) throws Exception {
    socket = d.createRfcommSocketToServiceRecord(SPP);
    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
    socket.connect();
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = socket.getOutputStream();
    send("ATZ"); Thread.sleep(1000); send("ATE0"); send("ATL0"); send("ATS0"); send("ATH0"); send("ATSP0"); send("0100");
    return true;
  }
  public synchronized String send(String cmd) throws Exception {
    out.write((cmd+"\r").getBytes()); out.flush();
    StringBuilder sb=new StringBuilder(); long end=System.currentTimeMillis()+2000;
    while(System.currentTimeMillis()<end){ if(in.ready()){ int c=in.read(); if(c==-1) break; char ch=(char)c; if(ch=='>') break; sb.append(ch);} else Thread.sleep(20); }
    return sb.toString();
  }
  public ObdData poll() throws Exception {
    ObdData d=new ObdData();
    d.rpm = parseRpm(send("010C")); d.speed = parseInt(send("010D")); d.coolant = parseInt(send("0105"))-40; d.fuel = parseInt(send("012F"))*100/255; d.load = parseInt(send("0104"))*100/255; d.throttle = parseInt(send("0111"))*100/255; d.intake = parseInt(send("010F"))-40;
    return d;
  }
  private int parseInt(String r){ try{ String[] p=r.replaceAll("[^0-9A-Fa-f ]"," ").trim().split("\\s+"); for(int i=0;i<p.length-1;i++){ if(p[i].equals("41")) return Integer.parseInt(p[i+2],16);} }catch(Exception e){} return -1; }
  private int parseRpm(String r){ try{ String[] p=r.replaceAll("[^0-9A-Fa-f ]"," ").trim().split("\\s+"); for(int i=0;i<p.length-2;i++){ if(p[i].equals("41")&&p[i+1].equals("0C")) return (Integer.parseInt(p[i+2],16)*256+Integer.parseInt(p[i+3],16))/4; } }catch(Exception e){} return -1; }
  public void close(){ try{socket.close();}catch(Exception e){} }
  public static class ObdData{ public int rpm,speed,coolant,fuel,load,throttle,intake; }
}
