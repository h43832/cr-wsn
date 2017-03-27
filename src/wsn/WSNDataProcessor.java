package wsn;

import java.util.*;
public class WSNDataProcessor extends Thread{
  WSN wsn;
  Vector waitV=new Vector();
  long longWaitTime=31536000000L;
  boolean isSleep=false; 

  public WSNDataProcessor(WSN wsn){
    this.wsn=wsn;
  }
  public void run(){
    while(true){
        while(waitV.size()>0){

          DataClass dataClass=(DataClass)waitV.get(0);
          wsn.processData(dataClass.originalId,dataClass.stringx);
          waitV.remove(0);
        }
        try{
            isSleep=true;
            Thread.sleep(longWaitTime);
            isSleep=false;
        }catch(InterruptedException e){
            isSleep=false;
        }
    }
  }
  public void setData(String originalId,String stringx[]){

    DataClass dataClass=new DataClass(originalId,stringx);
    waitV.add(dataClass);
    if(isSleep) this.interrupt();
}
   public class DataClass{
   String originalId,stringx[];
   public DataClass(String originalId,String stringx[]){
     this.originalId=originalId;this.stringx=stringx;
   }
 }
}