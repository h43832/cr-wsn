package wsn;

import java.util.*;
import golib.*;
import infinity.client.*;

public class WSNActionProcessor extends Thread{
  Vector waitV=new Vector();
  long longWaitTime=31536000000L;
  boolean isSleep=false;

  WSN wsn;
  public WSNActionProcessor(WSN wsn){

      this.wsn=wsn;
  }
  public void run(){
    while(true){
        while(waitV.size()>0){
            DataClass actionData=(DataClass)waitV.get(0);
            String id[]=wsn.getIdFromDataSrc(actionData.conf.conf[22]);
            if(wsn.w.checkOneVar(actionData.conf.conf[1],54)){
                String cmd=actionData.conf.conf[23];
                String chksumType=actionData.conf.conf[165];
		if(cmd.trim().length()>0){
                 String id2=wsn.getId2(actionData.conf.conf[22]);
                 String id3=wsn.getId3(actionData.conf.conf[22]);
                 if(id.length>0 && id[0].length()>0){
                 if(wsn.getId1(actionData.conf.conf[4]).equals("0")){
                     cmd="performcommand wsn.WSN cmd all all "+wsn.w.checkOneVar(actionData.conf.conf[1],55)+" "+wsn.w.checkOneVar(actionData.conf.conf[1],46)+" "+wsn.w.checkOneVar(actionData.conf.conf[1],45)+" "+actionData.conf.conf[31]+" "+wsn.w.e642(cmd)+" "+wsn.w.e642(chksumType)+" 0 0 0 0"; 

                     for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } else if(id2.equals("0")){
                     cmd="performcommand wsn.WSN cmd all all "+wsn.w.checkOneVar(actionData.conf.conf[1],55)+" "+wsn.w.checkOneVar(actionData.conf.conf[1],46)+" "+wsn.w.checkOneVar(actionData.conf.conf[1],45)+" "+actionData.conf.conf[31]+" "+wsn.w.e642(cmd)+" "+wsn.w.e642(chksumType)+" 0 0 0 0"; 

                     for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } else if(id3!=null && (id3.equals("0") || (id3.length()==0))){
                     cmd="performcommand wsn.WSN cmd "+id2+" all "+wsn.w.checkOneVar(actionData.conf.conf[1],55)+" "+wsn.w.checkOneVar(actionData.conf.conf[1],46)+" "+wsn.w.checkOneVar(actionData.conf.conf[1],45)+" "+actionData.conf.conf[31]+" "+wsn.w.e642(cmd)+" "+wsn.w.e642(chksumType)+" 0 0 0 0"; 

                     for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } else if(id3!=null && id3.length()>0){
   		    cmd="performcommand wsn.WSN cmd "+id2+" "+id3+" "+wsn.w.checkOneVar(actionData.conf.conf[1],55)+" "+wsn.w.checkOneVar(actionData.conf.conf[1],46)+" "+wsn.w.checkOneVar(actionData.conf.conf[1],45)+" "+actionData.conf.conf[31]+" "+wsn.w.e642(cmd)+" "+wsn.w.e642(chksumType)+" 0 0 0 0";  

                    for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } 
            }
            }

           }
            if(wsn.w.checkOneVar(actionData.conf.conf[1],56)){
                String cmd="";

                 String id2=wsn.getId2(actionData.conf.conf[24]);
                 String id3=wsn.getId3(actionData.conf.conf[24]);
                 if(id.length>0 && id[0].length()>0){
                 if(wsn.getId1(actionData.conf.conf[22]).equals("0")){
                       cmd="performcommand wsn.WSN closocketport all null null null null null null null null 0 0 0 0 ? ? ? 0";

                       for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                       cmd="performcommand wsn.WSN closeserialport all null null null null null null null null 0 0 0 0 ? ? ? 0";

                       for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } else if(id2.equals("0")){
                       cmd="performcommand wsn.WSN closocketport all null null null null null null null null 0 0 0 0 ? ? ? 0";

                       for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                       cmd="performcommand wsn.WSN closeserialport all null null null null null null null null 0 0 0 0 ? ? ? 0";

                       for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } else if(id3!=null && (id3.equals("0") || id3.length()==0)){
                       if(id2.indexOf("COM")==0) cmd="performcommand wsn.WSN closeserialport "+id2+" null null null null null null null null 0 0 0 0 ? ? ? 0";
                       else if(id3.length()<1 || id3.equals("0"))  cmd="performcommand wsn.WSN closesocketport "+id2+" null null null null null null null null 0 0 0 0 ? ? ? 0";

                       for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } else if(id3!=null && id3.length()>0){
                     if(id2.indexOf("COM")==0) cmd="performcommand wsn.WSN closeserialport "+id2+" null null null null null null null null 0 0 0 0 ? ? ? 0";
                      else if(id3.length()<1 || id3.equals("0"))  cmd="performcommand wsn.WSN closesocketport "+id2+" null null null null null null null null 0 0 0 0 ? ? ? 0";
                      else  cmd="performcommand wsn.WSN closesocketport "+id2+" "+id3+" null null null null null null null null 0 0 0 0 ? ? ? 0";

                     for(int i=0;i<id.length;i++) wsn.w.sendToOne(cmd,id[i]);
                } 
            }

           }
           if(wsn.w.checkOneVar(actionData.conf.conf[1],52)){
                String cla=actionData.conf.conf[28];
                if(((WSNAction)wsn.jClasses.get(cla))==null){
                    wsn.loadClass(cla,2);

                }
                if(((WSNAction)wsn.jClasses.get(cla))!=null){
                    ((WSNAction)wsn.jClasses.get(cla)).startAction(wsn, actionData.conf.conf[4], actionData.stat.stat[15]);
                }

           }
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

 public void setAction(Config conf,Status stat,int type){
   DataClass dataClass=new DataClass(conf,stat,type); 
   waitV.addElement(dataClass);
   if(isSleep) interrupt();
 }
 public class DataClass{
   Config conf;Status stat;int type;
   public DataClass(Config conf,Status stat,int type){
     this.conf=conf; this.stat=stat;this.type=type;
   }
 }
}