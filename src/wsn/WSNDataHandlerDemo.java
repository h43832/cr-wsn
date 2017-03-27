
package wsn;

import java.awt.Color;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class WSNDataHandlerDemo implements WSNSocketDataHandler{
    public void setData(byte b[],WSNSocketDevice device){
       if(device.showCB.isSelected()){
            String temp2="";
            if(device.show16RB.isSelected()){
               temp2=device.byteToStr(b);
            } else {
            temp2=new String(b);
            }
            if(device.crnlCB.isSelected() || !device.lastIsData){
                String tmp="";
                if(device.showTimeCB.isSelected()) tmp="\r\nHandler receive: "+device.wsn.formatter3.format(new Date())+" ";
                else temp2="\r\nHandler receive: "+temp2;
                if(tmp.length()>0) device.textPaneAppend(device.jTextPane1,tmp,Color.BLACK,0);
                device.textPaneAppend(device.jTextPane1,temp2,Color.RED,0); 
            } else {
                  temp2=" Handler: "+temp2;
                  device.textPaneAppend(device.jTextPane1,temp2,Color.RED,0); 
            }

             device.lastIsData=true;
            }

       if(device.connected && device.writer!=null){
           byte[] c="Echo from data handler: ".getBytes();
           byte[] d=new byte[c.length+b.length];
           for(int i=0;i<c.length;i++) d[i]=c[i];
           for(int i=0;i<b.length;i++) d[c.length+i]=b[i];
           device.writer.send(d,1,5,false,false);
       }
    }
}