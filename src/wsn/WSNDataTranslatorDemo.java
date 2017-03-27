package wsn;

import java.util.*;
public class WSNDataTranslatorDemo implements WSNDataTranslator{
    public double getValue(String data){
        data=getStringData(data, 1, -1, -1);
        double value=0.0;
        try{
          value=Double.parseDouble(data);
          value=value * Math.random();
        }catch(NumberFormatException e){
            return -9999.0;
        }
        return value;
    }
    public String getStringData(String data,int fieldNo,int subStrInx1,int subStrInx2){
        int cnt=0,cnt2=0;
        byte[] b0={};
        StringTokenizer st2=new StringTokenizer(data, " ");
               int intx[]=new int[data.length()];
               if(data!=null && data.length()>0){
                 for(cnt2=0;st2.hasMoreTokens();cnt2++){
                  intx[cnt2]=Integer.decode("0x"+st2.nextToken()).intValue();  
                }
                b0=new byte[cnt2];
                for(int i=0;i<cnt2;i++){
                  b0[i]=(byte)intx[i];
                }
               }
        data=new String(b0);

        if(fieldNo>0){
        StringTokenizer st=new StringTokenizer(data, " ");
        while(st.hasMoreElements()){
            cnt++;
            if(fieldNo==cnt) {
                data=st.nextToken();
                break;
            } else st.nextToken();
        }
        }
        if(subStrInx1!=-1){

          if(subStrInx2!=-1 && subStrInx1 < subStrInx2) data=data.substring(subStrInx1,subStrInx2);
            else data=data.substring(subStrInx1);
        }
    return data;
  }
}