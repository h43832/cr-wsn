package wsn;

import java.net.*;
import java.util.*;
import java.io.*;
import java.text.*;

public class WSNSocketServer implements Runnable {

  ResourceBundle bundle2;
  private ServerSocket ss;
  private boolean up= false,autochangeserverport;
  private Exception error= null;
  private String sep=""+(char)0,pid="";
  public String sendingTo="",heartbeatData="Hi";

  public HashMap cmdData=new HashMap();
  private boolean first=false,n_monitor=false,n_debug=false,canListenToPort=false;
  protected int port=0,sendingType=1;

  public long pingTime= 60L;
  PingThread pThread;
  

  public static final int connectionLimit=100;
  public int joinLimit=20;
  protected WSNSocketConnection cns[]=new WSNSocketConnection[connectionLimit];
  private Hashtable ht=new Hashtable();
  public SimpleDateFormat format3 = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");

  private String sx[]={"","2015-01-15","1.0.3","","",""};
  

  private String ssx[]={"infinity.dat","infinity.dat","","","","",""};
  

  private int ix[]={};
  

  private int iix[]={0};
  

  private boolean bx[]={};
  

  private boolean bbx[]={false,false};
  WSN wsn;

  public WSNSocketServer(WSN wsn,int port2) {
    this.wsn=wsn;

    bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    if(wsn.props.getProperty("socket_port_"+port2)!=null){
      setConfig(wsn.props.getProperty("socket_port_"+port2));
    } else if(wsn.props.getProperty("socket_port_default")!=null){
      setConfig(wsn.props.getProperty("socket_port_default"));
    } else setConfig("10,0,,,1,");
    port=port2;
    init();
  }

  public WSNSocketServer(WSN wsn,int port2,int connectionLimit,int hbeat,String pid) {
    this.wsn=wsn;

    bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    port=port2;
    joinLimit=connectionLimit;
    pingTime=hbeat;
    this.pid=pid;
    String conf[];
    if(wsn.props.getProperty("socket_port_"+port2)!=null){
      conf=wsn.w.csvLineToArray(wsn.props.getProperty("socket_port_"+port2));
    } else if(wsn.props.getProperty("socket_port_default")!=null){
      conf=wsn.w.csvLineToArray(wsn.props.getProperty("socket_port_default"));
    } else conf=wsn.w.csvLineToArray(port2+",10,0,,,1,,,,");
    if(conf.length>5 && conf[5].length()>0 && wsn.isNumeric(conf[5])) sendingType=Integer.parseInt(conf[5]);
    if(conf.length>6) sendingTo=conf[6];
    if(conf.length>9 && conf[9].length()>0) heartbeatData=conf[9];
    if(heartbeatData.length()==0) heartbeatData="hi";
    wsn.w.replace(heartbeatData,",",".");
    init();
  }
  void init(){
    for(int i=0;i<cns.length;i++) cns[i]=null;

      try {
	if(port>0) ss= new ServerSocket(port);
        canListenToPort=true;

        String cmd2="performmessage wsn.WSN wsn_opensocketport "+wsn.w.getGNS(6)+" "+port+" "+wsn.w.e642(wsn.w.getGNS(6)+" "+bundle2.getString("WSNSocketServer.xy.msg3") +" "+port );
        wsn.w.sendToAll(cmd2);
      } catch(IOException e) {

          canListenToPort=false;
          String cmd2="performmessage wsn.WSN wsn_msg "+wsn.w.e642(wsn.w.getGNS(6)+" "+bundle2.getString("WSNSocketServer.xy.msg4")+" "+port);
          wsn.w.sendToAll(cmd2);
        }

    if(canListenToPort) new Thread(this).start();
  }

  public void setConfig(String confx){
    String conf[]=wsn.w.csvLineToArray(confx);
     if(conf.length>1 && wsn.isNumeric(conf[1])) joinLimit=Integer.parseInt(conf[1]);    
     if(conf.length>2 && conf[2].length()>0 && wsn.isNumeric(conf[2])) pingTime=Long.parseLong(conf[2]);
     if(conf.length>5 && conf[5].length()>0 && wsn.isNumeric(conf[5])) sendingType=Integer.parseInt(conf[5]);
     if(conf.length>6) sendingTo=conf[6];
     if(conf.length>9 && conf[9].length()>0) heartbeatData=conf[9];
     if(pThread!=null) pThread.interrupt();
  }
  public boolean canListenToPort(){
      return canListenToPort;
  }
  public WSNSocketConnection[] getConnections(){
    return cns;
  }
  public void setMaxConnection(int count){
    if(count>connectionLimit) count=connectionLimit;
    if(count<1) count=1;
    joinLimit=count;
}
  public void close(){
      for(int i=0;i<cns.length;i++){
          if(cns[i]!=null){
              cns[i].disconnect();
              cns[i]=null;
          }
      }
  }
  public boolean containConnection(String id){
      for(int i=0;i<cns.length;i++){
          if(cns[i]!=null && cns[i].getId().equals(id)) return true;
      }
      return false;
  }
  public int connectionCount() {
    int c=0;
    for(int i=0;i<cns.length;i++){
          if(cns[i]!=null) c++;
    }
    return c;
  }
 public int getPort(){
     return port;
 }
   public String [] getProps(){
    String rtn[]=new String[2];
    rtn[0]="socket_port_"+port;
    rtn[1]=port+","+joinLimit+","+pingTime+",,,"+sendingType+","+sendingTo+",,,";
    return rtn;
  }
  public synchronized boolean isUp(){
    return up;
  }
  /** start the server */
  public void run() {
    if(error!=null)
      return;
    up= true;
    pThread=new PingThread(this);
    pThread.start();
    try {
	while(isUp()){

	  if(connectionCount()<connectionLimit){
            addConnection(getConnection(ss.accept()));
  	    first=true;
  	   } else {
  	       if(first){

	         first=false;
  	       }
  	     }
  	 }
      } catch(IOException e) {
	error= e;

	stop(); 
      }
  }
  protected WSNSocketConnection getConnection(Socket sot){
  for(int i=0;i<cns.length;i++){
      if(cns[i]==null){
         cns[i]=new WSNSocketConnection(sot,this,wsn,pid);
         cns[i].setId(String.valueOf(i+1));
         String ip=sot.getInetAddress().toString();
         ip=wsn.w.replace(ip,"/","");
         ip=wsn.w.replace(ip,"\\","");
         cns[i].setCGNS(1, ip,pid);
         String cmd3="performmessage wsn.WSN wsn_opensocketconnection "+wsn.w.getGNS(6)+" "+port+" "+cns[i].getId()+" "+wsn.w.e642(wsn.w.getGNS(6)+":"+port+"-"+cns[i].getId()+" "+bundle2.getString("WSNSocketServer.xy.msg1") +" "+ip+").");
         wsn.w.sendToAll(cmd3);
         return cns[i];
      }
  }
  return null;
  }
  public WSNSocketConnection getConnection(String id){
    for(int i=0;i<cns.length;i++){
      if(cns[i]!=null && cns[i].getId().equals(id)){
         return cns[i];
      }
    }
  return null;
}

 

  public synchronized void stop() {
    up=false;
    for(int i=0;i<cns.length;i++){
        removeConnection(cns[i]);
    }
    try{
      ss.close();
    } catch (IOException e){
      e.printStackTrace();
    }
  }

 public synchronized void removeConnection(WSNSocketConnection c) {
    for(int i=0;i<cns.length;i++) {
        if(cns[i]!=null && c.equals(cns[i])) {
          cns[i].disconnect(); 
          cns[i]=null; 
          break;
        }
    }

  }
  /** add a connection to the system */
  public synchronized void addConnection(WSNSocketConnection c)  {
    if(c!=null && c.connect()) {
      }
  }

  public void broadcast(byte [] b){
   for(int i=0;i<cns.length;i++){
       if(cns[i]!=null){
         cns[i].put(b);      
       }
   }
  }
  

  

  

  public void treat(String str,String cid,WSNSocketConnection c) {
          StringTokenizer st=new StringTokenizer(str,sep);
          String command=st.nextToken();
          if(command.equals("data")){

               String cmd2=st.nextToken();

                 cmd2="performmessage wsn.WSN wsn_data "+wsn.w.getGNS(6)+" 1 "+port+" "+cid+" "+wsn.w.e642(cmd2)+" "+c.getIp();

                 switch(sendingType){
                   case 0:
                     String cmdCode=wsn.w.getSsx(6, pid);
                     cmdCode=(cmdCode!=null && cmdCode.length()>0? cmdCode:"%empty%");
                     wsn.w.command(cmd2,7,7,cmdCode,"0",null,wsn.w.getGNS(1),null);
                     break;
                   case 1:
                     wsn.w.sendToAll(cmd2);
                     break;
                   case 2:
                     String id[]=wsn.getIdFromDataSrc(sendingTo);
                     for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd2, id[i]);
                     break;
                   case 3:
                     wsn.w.sendToOne(cmd2, sendingTo);
                     break;
                   case 4:
                     String id2[]=wsn.getIdFromFixId(sendingTo);
                     for(int i=0;i<id2.length;i++) wsn.w.sendToOne(cmd2, id2[i]);
                     break;
                   case 5:
                     wsn.w.sendToGroup(cmd2, sendingTo);
                     break;
                   case 6:
                     wsn.w.sendToSubGroup(sendingTo, cmd2);
                     break;
                   case 7:
                     wsn.w.sendToTop(cmd2);
                     break;
                   case 8:
                     wsn.w.sendToUpper(cmd2);
                     break;
                 }

              return;
          }
       if(command.equals("leave")) {
           removeConnection(c);

           String cmd3="performmessage wsn.WSN wsn_closesocketconnection "+wsn.w.getGNS(6)+" "+port+" "+cid+" "+wsn.w.e642(wsn.w.getGNS(6)+":"+port+"-"+cid+"("+c.getIp()+") "+bundle2.getString("WSNSocketServer.xy.msg2"));
           wsn.w.sendToAll(cmd3);

           return;
      }
  }

  public void setHVar(String n,String v){
    ht.put(n.toLowerCase(),v);
  }

  public void removeHVar(String n){
    ht.remove(n);
  }

  public String getHVar(String n){
    return (String)ht.get(n);
  }
  public Enumeration getHKeys(){
    return ht.keys();
  }
  public int getHSize(){
    return ht.size();
  }
  public void setIx(int x,int s){
    this.ix[x]=s;
  }
  public int getIx(int x){
    return ix[x];
  }
  public void setIix(int x,int s){
    this.ix[x]=s;
  }
  public int getIix(int x){
    return ix[x];
  }
  public void setBx(int x,boolean s){
    this.bx[x]=s;
  }
  public boolean getBx(int x){
    return bx[x];
  }
  public void setBbx(int x,boolean s){
    this.bx[x]=s;
  }
  public boolean getBbx(int x){
    return bbx[x];
  }
  public void setSx(int x,String s){
    this.sx[x]=s;
  }
  public String getSx(int x){
    return sx[x];
  }
  public void setSsx(int x,String s){
    this.ssx[x]=s;
  }
  public String getSsx(int x){
    return ssx[x];
  }
  public void broadcastExcept(byte[] b,String id){
    for(int i=0;i<cns.length;i++){
      if(cns[i]!=null && !cns[i].getId().equals(id)){
        cns[i].put(b);
      }
    }
  }
  public void broadcastExcept2(byte [] b,String id,String id2) {
    for(int i=0;i<cns.length;i++){
      if(cns[i]!=null && !cns[i].getId().equals(id) && !cns[i].getId().equals(id2)){
        cns[i].put(b);
      }
    }
  }
class PingThread extends Thread {
  WSNSocketServer sv;
  PingThread(WSNSocketServer s) {
    sv=s;
  }

  public void run()  {
   while(sv.isUp()) {
     try  {
	    if(sv.pingTime==0) sleep(1000L * 60L * 60L * 24L * 365L * 100L);
            else sleep(sv.pingTime * 1000L);
	    sv.broadcast(sv.heartbeatData.getBytes());
	    yield();
	  }
    catch(Exception e){

    }
   }

  }
}
}