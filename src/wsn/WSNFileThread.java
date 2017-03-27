package wsn;

import java.util.*;
import java.io.*;
public class WSNFileThread extends Thread {
  WSN wsn;
  Vector waitV=new Vector();
  boolean isSleep=false;
  long waitTime=1000L*60L*60L*24L,waitTime2=1L;
  public WSNFileThread(WSN wsn){
    this.wsn=wsn;
  }
  public void run(){
      while(true){
        while(waitV.size()>0){

          DataClass dataClass=(DataClass)waitV.get(0);
            if(dataClass.time==0){
              wsn.saveLog(dataClass.data);
            } else {
             String fDate=wsn.formatter2.format(new Date()).substring(0,8);
            if(!fDate.equals(wsn.dataDate)) wsn.makeDataDir();
           String fname=dataClass.dataSrc.replace(':','_')+"_"+fDate+".txt";
           String data=wsn.formatter3.format(new Date(dataClass.time))+" "+(wsn.show16RB.isSelected()?  dataClass.data:wsn.getStringData(dataClass.data, -1, -1,-1))+"\r\n";
               try{
                 FileOutputStream out;
                     out = new FileOutputStream (wsn.dataDir+File.separator+fname,true);
	             out.write(data.getBytes());
      	             out.close();
               } catch (IOException e){
                   e.printStackTrace();
               }
            }
          waitV.remove(0);
          }
          try{
              isSleep=true;
              sleep(waitTime);
          }catch(InterruptedException e){
             isSleep=false;
          }
      }

    }
 public void setData(long time,String nodeId,String dataSrc,String data){

    DataClass dataClass=new DataClass(time,dataSrc,data);
    waitV.add(dataClass);
    if(isSleep) this.interrupt();
}
 public class DataClass{
   long time; String dataSrc, data;
   public DataClass(long time,String dataSrc,String data){
     this.time=time; this.dataSrc=dataSrc;this.data=data;
   }
 }
}