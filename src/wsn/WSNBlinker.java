package wsn;

import java.awt.*;
import java.util.*;
import infinity.client.*;
public class WSNBlinker extends Thread {
	WSN wsn=null;

	boolean onoff=true,apEverStartup=false;
        int chkCount=0;
        int waitCount=5;
	long nextTime=0,nowTime=0,sleepTime=500;
	public WSNBlinker(WSN wsn){

		this.wsn=wsn;
	}
	public void run(){
          nextTime=System.currentTimeMillis();
	  while(true){
              nextTime=nextTime+sleepTime;
		try{

                    if(wsn.props.getProperty("ap_start_max_wait_sec")!=null && wsn.props.getProperty("ap_start_max_wait_sec").length()>0) {
                      waitCount=Integer.parseInt(wsn.props.getProperty("ap_start_max_wait_sec"));
                      waitCount=waitCount * 2;
                    }
                    if(!wsn.isVisible() && wsn.props.getProperty("run_my_ap_only")!=null && wsn.props.getProperty("run_my_ap_only").equalsIgnoreCase("Y") && wsn.myAps.size()>0){
                        boolean foundActiveAp=false;
                        for(Iterator it = wsn.myAps.values().iterator(); it.hasNext(); ){
						  Object o=it.next();
                          if(((WSNApplication)o).isVisible() || ((WSNApplication)o).runInBackground()){foundActiveAp=true; apEverStartup=true; break;}
                        }
                      if(!foundActiveAp){
                        if(apEverStartup){wsn.w.ap.onExit(105); System.exit(0);}
                        else if(chkCount>waitCount) {wsn.w.ap.onExit(106); System.exit(0);}
                       }
                      chkCount++;
                      if(chkCount>9999) chkCount=9;
                    }

                    for(Iterator it=wsn.myAps.values().iterator();it.hasNext();)  ((WSNApplication)it.next()).setBlink(onoff);
                    for(Enumeration en=wsn.lineCharts.elements();en.hasMoreElements();) ((WSNApplication)en.nextElement()).setBlink(onoff);
                    for(Enumeration en=wsn.eventHandlers.elements();en.hasMoreElements();) ((WSNApplication)en.nextElement()).setBlink(onoff);

                    onoff=!onoff;
                     nowTime=System.currentTimeMillis();

                     while(nowTime>=nextTime){
                       nextTime=nextTime+sleepTime;
                       onoff=!onoff;
                     }
                     Thread.sleep(nextTime-nowTime);
		} catch (Exception e){
                     e.printStackTrace();
		  }
	  }
	}

}