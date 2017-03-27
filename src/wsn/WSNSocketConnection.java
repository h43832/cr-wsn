package wsn;

import java.io.*;
import java.net.*;
import infinity.common.*;
import java.util.*;

public final class WSNSocketConnection implements Runnable {

  InputStream is = null;
  OutputStream os=null;
  private Socket s;
  private WSNSocketServer sv;
  private Thread reader2;
  public SkWriter skWriter;
  private Thread writer2;
  private Exception error= null;
  private boolean connected= false;
  private String sep=""+(char)0,pid="";
  boolean treatIsBusy=false;
  Vector cmdV=new Vector();
  

  private String cgns[]={"null","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"}; 
  WSN wsn;

  WSNSocketConnection(Socket so, WSNSocketServer sve,WSN wsn,String pid)  {
    sv=sve;
    s= so;

    this.wsn=wsn;
    this.pid=pid;
    reader2= null;

  }  

  boolean connect() {
    try {

        is=s.getInputStream();
        os=s.getOutputStream();
	connected= true;
	(reader2=new Thread(this)).start();
        skWriter=new SkWriter(wsn,sv);
        (writer2=new Thread(skWriter)).start();
      }
    catch(IOException e) {
	error= e;

        sv.removeConnection(this);
	return false;
      }
    return true;
  }

  public void run() {
    try {
      int count=0;
      byte[] buffer=new byte[1024];
      while(connected){
      while ((count = is.read(buffer)) > 0){
        cmdV.add("data"+sep+wsn.byteToStr(buffer,count));
        if(!treatIsBusy){
          treatIsBusy=true;
          while(cmdV.size()>0){
	    sv.treat((String)cmdV.get(0),this.getId(),this); 
            cmdV.remove(0);
	  }
          treatIsBusy=false;
        }
      }
      }
    }catch(IOException e) {

	if(!connected) return;
	error= e;
        sv.treat("leave",getId(),this); 
      }
  }
  /** put a message on the net 
    * @return succes, disconnects on failure */
  public final synchronized boolean put(byte b[]){

    if(error!=null || !connected)
      return false;

    try  {

        os.write(b);

    }
    catch(IOException e){
	error= e;
        sv.treat("leave", this.getId(),this); 
        return false;
      }
    return true;
  }
  public int getStatus(){
      int rtn=0;
      if(skWriter.type!=1) rtn=wsn.w.addOneVar(rtn,0);
      if(connected) rtn=wsn.w.addOneVar(rtn,1);
      return rtn;
  }
  /** close the socket, stop the reader */
  public void disconnect(){
    connected= false;

    if(Thread.currentThread()!=reader2 && reader2!=null && reader2.isAlive())

	connected=false;
    try  {
        is.close();
        os.close();
	s.close();
    } catch(IOException e) {
	error= e;
      }
  }
  public void stopContinueSend(){
      skWriter.stopContinueSend();
  }
  public String getFixedId(){ return cgns[12]; }
  public void setFixedId(String id,String pid){
    if(pid.equals(this.pid)) cgns[12]=id;
  }
  public void setId(String id){
    cgns[0]=id;
  }
  public String getId(){return cgns[0];}
  public String getIp(){return cgns[1];}

  public String [] getCGNS(){
    return cgns;
  }
  public void setCGNS(int i,String s,String pid){
    if(pid.equals(this.pid)) this.cgns[i]=s;
  }
  public String getCGNS(int i){
    return cgns[i];
  }
  public boolean isAlive(){
    return reader2.isAlive();
  }
  public void setNName(String nk,String privateid){
    cgns[11]=nk;
  }
  public String getNName(){
    return cgns[11];
  }
  public void sendCmd(String cmdMode,String command,boolean needSysChkSum,String chksumType,int timeMode,long cycleTime){
          byte b0[]=null;
          if(cmdMode.equalsIgnoreCase("hex")) {
            int cnt=0;
            StringTokenizer st=new StringTokenizer(command, " ");
            int intx[]=new int[command.length()];
            if(command!=null && command.length()>0){
              for(cnt=0;st.hasMoreTokens();cnt++){
                intx[cnt]=Integer.decode("0x"+st.nextToken()).intValue();  
              }
              b0=new byte[cnt];
                   for(int i=0;i<cnt;i++){
                     b0[i]=(byte)intx[i];
                   }
            }
          } else b0=command.getBytes();
         b0=wsn.addChksum(b0,needSysChkSum,chksumType);
      skWriter.send(b0, timeMode, cycleTime);
  }
  public class SkWriter implements Runnable  {
        WSNSocketServer wsnSServer;
        WSN wsn;

        boolean isSleep=false;
        String cmdMode="",command="";
        boolean cont=true;

        byte [] toSend=null;
        int type=1;
        long interval=10000L;
        public SkWriter (WSN wsn,WSNSocketServer wsnSServer) {

            this.wsn=wsn;
            this.wsnSServer=wsnSServer;
        }
    public void run(){
         while(true){
          try{
            isSleep=true;
            if(type==1) Thread.sleep(1000L * 60L * 60L * 24L * 365L);
            else Thread.sleep(interval);
            isSleep=false;
          } catch(InterruptedException e){
              isSleep=false;
          }
          if(toSend!=null) put(toSend);
          if(type==1) toSend=null;
         }
        }
   public boolean isSleep(){
       return isSleep;
   }
   public void stopContinueSend(){
        toSend=null;
        type=1;
        if(isSleep()) writer2.interrupt();
    }
   public void send(byte[] b,int type,long interval){
     switch(type){
         case 1:
           put(b);
           break;
         case 2:
           this.type=type;
           this.toSend=b;
           this.interval=interval;
           if(isSleep()) writer2.interrupt();
           break;
        }
    }
  }
}