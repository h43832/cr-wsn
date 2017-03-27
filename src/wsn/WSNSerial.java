package wsn;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.lang.reflect.*;

import gnu.io.*;
import y.ylib.OneVar;

public class WSNSerial implements Runnable {

    int statusOne=0;
    String portName="COM17",sendingTo="";
    private String pid="";
    ResourceBundle bundle2;
    Enumeration portList;
    CommPortIdentifier portId;
    SerialPort serialPort;
    long tryCount=0,retryTime=10000L,lastDisConnectTime=0,prepareTime=0;
    int baudRate=115200,dataBits=8,parityBit=SerialPort.PARITY_NONE,stopBit=SerialPort.STOPBITS_1,sendingType=1,receiveTimeout=500;
    WSN wsn;
    boolean foundPortList=false,portInUse=false,isSleep=false,toOpen=false,retryIfFailed=true;
    OutputStream out;
    InputStream inputStream;
    SerialWriter serialWriter;
    Thread outThread,mainThread;

   public WSNSerial(WSN wsn,String portName,String pid){

        this.wsn=wsn;
        bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    	this.portName=portName;
    	this.baudRate=baudRate;
        this.pid=pid;
        String conf[];
        prepareTime=3000L;
    if(wsn.props.getProperty("serial_port_"+portName)!=null){
      conf=wsn.w.csvLineToArray(wsn.props.getProperty("serial_port_"+portName));
    } else if(wsn.props.getProperty("serial_port_default")!=null){
      conf=wsn.w.csvLineToArray(wsn.props.getProperty("sserial_port_default"));
    } else conf=wsn.w.csvLineToArray(portName+",10,0,,,1,,,,");
    if(conf.length>1 && conf[1].length()>0 && wsn.isNumeric(conf[1])) baudRate=Integer.parseInt(conf[1]);
    if(conf.length>2 && conf[2].length()>0 && wsn.isNumeric(conf[2])) dataBits=Integer.parseInt(conf[2]);
    if(conf.length>3 && conf[3].length()>0 && wsn.isNumeric(conf[3])) parityBit=Integer.parseInt(conf[3]);
    if(conf.length>4 && conf[4].length()>0 && wsn.isNumeric(conf[4])) stopBit=Integer.parseInt(conf[4]);
    if(conf.length>5 && conf[5].length()>0 && wsn.isNumeric(conf[5])) sendingType=Integer.parseInt(conf[5]);
    if(conf.length>6) sendingTo=conf[6];
    receiveTimeout=(conf.length>7 && conf[7].length()>0? wsn.w.getValueInt(conf[7]):500);
    	init();
    }

   public WSNSerial(WSN wsn,String portName,int baudRate,int dataBits,String parityBit,String stopBit,String pid){

        this.wsn=wsn;
        bundle2 = java.util.ResourceBundle.getBundle("wsn/Bundle"); 
    	this.portName=portName;
    	this.baudRate=baudRate;
        this.dataBits=dataBits;
        this.pid=pid;
        prepareTime=500L;
        if(parityBit.equalsIgnoreCase("NONE") || parityBit.equalsIgnoreCase("N"))  this.parityBit=SerialPort.PARITY_NONE;
        else if(parityBit.equalsIgnoreCase("EVEN") || parityBit.equalsIgnoreCase("E"))  this.parityBit=SerialPort.PARITY_EVEN;
        else if(parityBit.equalsIgnoreCase("ODD") || parityBit.equalsIgnoreCase("O"))  this.parityBit=SerialPort.PARITY_ODD;
        else if(parityBit.equalsIgnoreCase("MARK") || parityBit.equalsIgnoreCase("M"))  this.parityBit=SerialPort.PARITY_MARK;
        else if(parityBit.equalsIgnoreCase("SPACE") || parityBit.equalsIgnoreCase("S"))  this.parityBit=SerialPort.PARITY_SPACE;
        if(stopBit.equals("1")) this.stopBit=SerialPort.STOPBITS_1;
        else if(stopBit.equals("1.5")) this.stopBit=SerialPort.STOPBITS_1_5;
        else if(stopBit.equals("2")) this.stopBit=SerialPort.STOPBITS_2;
        String conf[];
    if(wsn.props.getProperty("serial_port_"+portName)!=null){
      conf=wsn.w.csvLineToArray(wsn.props.getProperty("serial_port_"+portName));
    } else if(wsn.props.getProperty("serial_port_default")!=null){
      conf=wsn.w.csvLineToArray(wsn.props.getProperty("sserial_port_default"));
    } else conf=wsn.w.csvLineToArray(portName+",10,0,,,1,,,,");
    if(conf.length>5 && conf[5].length()>0 && wsn.isNumeric(conf[5])) sendingType=Integer.parseInt(conf[5]);
    if(conf.length>6) sendingTo=conf[6];
    receiveTimeout=(conf.length>7 && conf[7].length()>0? wsn.w.getValueInt(conf[7]):500);
    	init();
    }
  public void init(){
    if(mainThread==null){
      toOpen=true;
      mainThread=new Thread(this);
      mainThread.start();
    } else reOpen();
  }
  public void run(){
       try{
        Thread.sleep(prepareTime);
       }catch(InterruptedException e){}
    while(true){
      if(toOpen){
        portList = CommPortIdentifier.getPortIdentifiers();
        

        if(wsn.w.getHVar("a_monitor")!=null && wsn.w.getHVar("a_monitor").equalsIgnoreCase("true"))
          System.out.println("in 0,before portlist,baudRate="+baudRate+",DATABITS_8="+SerialPort.DATABITS_8+
                            ",STOPBITS_1="+SerialPort.STOPBITS_1+",PARITY_NONE="+SerialPort.PARITY_NONE);
        String ownerName="WSNApp";
        foundPortList=false;
        portInUse=false;
        while (portList.hasMoreElements()) {
          portId = (CommPortIdentifier) portList.nextElement();
          if(wsn.w.getHVar("a_monitor")!=null && wsn.w.getHVar("a_monitor").equalsIgnoreCase("true")) System.out.println("in 1,portid.name="+portId.getName()+",portName="+portName+",serialPort=null?"+(serialPort==null));
          if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
          if(wsn.w.getHVar("a_monitor")!=null && wsn.w.getHVar("a_monitor").equalsIgnoreCase("true")) System.out.println("in 2,portid.name="+portId.getName()+",found="+foundPortList+",serialPort=null?"+(serialPort==null));

          if (portId.getName().equals(portName) && !foundPortList) {
            foundPortList=true;
            try {
              if(!portId.isCurrentlyOwned()){
                serialPort = (SerialPort) portId.open(ownerName, 2000);

	        if(wsn.w.getHVar("a_monitor")!=null && wsn.w.getHVar("a_monitor").equalsIgnoreCase("true")) System.out.println("3.serialPort=null?"+(serialPort==null));
              } else if(!portId.getCurrentOwner().equals(ownerName)){
                  serialPort.close();
                  serialPort = (SerialPort) portId.open(ownerName, 2000);
	          if(wsn.w.getHVar("a_monitor")!=null && wsn.w.getHVar("a_monitor").equalsIgnoreCase("true"))  System.out.println("4.serialPort=null?"+(serialPort==null));
                } else {
	            if(wsn.w.getHVar("a_monitor")!=null && wsn.w.getHVar("a_monitor").equalsIgnoreCase("true")) System.out.println("5.serialPort=null?"+(serialPort==null));
                  }
            } catch (PortInUseException e) {

                portInUse=true;

              }
           try {
             if(serialPort!=null) {
               serialPort.setSerialPortParams(baudRate,dataBits,stopBit,parityBit);
               if(receiveTimeout==0) serialPort.disableReceiveTimeout(); else serialPort.enableReceiveTimeout(receiveTimeout);
             } 
           } catch (UnsupportedCommOperationException e) {
	        	System.out.println("Error: ERR005 "+e.getMessage());
                    	e.printStackTrace();
            }
           try {
             if(serialPort!=null){
	     if(wsn.w.getHVar("a_monitor")!=null && wsn.w.getHVar("a_monitor").equalsIgnoreCase("true")) System.out.println("6.serialPort=null?"+(serialPort==null));

                serialPort.notifyOnBreakInterrupt(true); 
                serialPort.notifyOnCarrierDetect(true); 
                serialPort.notifyOnCTS(true); 
                serialPort.notifyOnDSR(true); 
                serialPort.notifyOnFramingError(true); 
                serialPort.notifyOnOutputEmpty(true); 
                serialPort.notifyOnOverrunError(true); 
                serialPort.notifyOnParityError(true); 
                serialPort.notifyOnRingIndicator(true); 

                serialPort.setDTR(true);

                serialPort.setRTS(true);

                inputStream = serialPort.getInputStream();

              try{
                serialPort.addEventListener(new SerialReader(wsn));
               } catch(TooManyListenersException e){e.printStackTrace();}
               serialPort.notifyOnDataAvailable(true);
               out = serialPort.getOutputStream();
               serialWriter=new SerialWriter(wsn,this);
               outThread=new Thread(serialWriter);
               outThread.start();
             }
           } catch (IOException e) {
          	System.out.println("Error: ERR004 "+e.getMessage());
                e.printStackTrace();
              }
            }
          }
        }
    if(!foundPortList) {
      String cmd2="performmessage wsn.WSN wsn_msg "+wsn.w.e642(wsn.w.getGNS(6)+" "+bundle2.getString("WSNSerial.xy.msg2") +" "+portName+" (port not found)");
      if(tryCount<1) wsn.w.sendToAll(cmd2);
   } 
    else if(portInUse) {
      String cmd2="performmessage wsn.WSN wsn_msg "+wsn.w.e642(wsn.w.getGNS(6)+" "+bundle2.getString("WSNSerial.xy.msg3") +" "+portName+" (port used by another application).");
      if(tryCount<1) wsn.w.sendToAll(cmd2);
   }  else {
        String cmd2="performmessage wsn.WSN wsn_openserial "+wsn.w.getGNS(6)+" "+portName+" "+wsn.w.e642(wsn.w.getGNS(6)+":"+portName+" "+bundle2.getString("WSNSerial.xy.msg4"));
        wsn.w.sendToAll(cmd2);
        wsn.serialPorts.put(portName, this);
        tryCount=0;
        statusOne=wsn.w.addOneVar(statusOne, 1);
    }
        toOpen=false;
        tryCount++;
        }
       isSleep=true;
       try{
          if(!wsn.w.checkOneVar(statusOne, 1) && (retryIfFailed || wsn.props.getProperty("run_my_ap_only").equalsIgnoreCase("Y"))) {
             toOpen=true; 
             Thread.sleep(retryTime);
          }  else Thread.sleep(365L * 24L * 60L * 60L * 1000L);
       }catch(InterruptedException e){}
      }
  }
  private void reOpen(){
      toOpen=true;
      if(isSleep) mainThread.interrupt();
  }
  public boolean foundPort(){
      return foundPortList;
  }
  public int getBaudrate(){
      return baudRate;
  }
  public int getDataBits(){
      return dataBits;
  }

  public String getParityBit(){
      String rtn="";
      if(parityBit==SerialPort.PARITY_EVEN) rtn="EVEN";
      else if(parityBit==SerialPort.PARITY_ODD) rtn="ODD";
      else if(parityBit==SerialPort.PARITY_NONE) rtn="NONE";
      else if(parityBit==SerialPort.PARITY_MARK) rtn="MARK";
      else if(parityBit==SerialPort.PARITY_SPACE) rtn="SPACE";
      return rtn;
  }
  public String [] getProps(){
    String rtn[]=new String[2];
    rtn[0]="serial_port_"+portName;
    rtn[1]=portName+","+baudRate+","+dataBits+","+getParityBit()+","+getStopBit()+","+sendingType+","+sendingTo+","+receiveTimeout+",,";
    return rtn;
  }

  public String getStopBit(){
      String rtn="";
      if(stopBit==SerialPort.STOPBITS_1) rtn="1";
      else if(parityBit==SerialPort.STOPBITS_1_5) rtn="1.5";
      else if(parityBit==SerialPort.STOPBITS_2) rtn="2";
      return rtn;
  }
  

  

  public void closePort() {

        new Thread(){
        @Override
        public void run(){
            try{
            if(out!=null) out.close();
            if(inputStream!=null) inputStream.close();
            if(serialPort!=null) {

                serialPort.close();
                statusOne=OneVar.remove(statusOne, 1);

            }
            }catch (IOException ex) {ex.printStackTrace();}
        }
        }.start();
    }

  

    public void sendCmd(String cmdMode,String command,boolean needSysChkSum,String chksumType,int timeMode,long cycleTime){
          byte b0[]=null;
          if(cmdMode.equalsIgnoreCase("hex")) {
            b0=wsn.strToByte(command);
          } else b0=command.getBytes();
          b0=wsn.addChksum(b0,needSysChkSum,chksumType);

      if(serialWriter!=null) serialWriter.send(b0, timeMode, cycleTime);
  }

  public void setConfig(String confx){
    String conf[]=wsn.w.csvLineToArray(confx);
     if(conf.length>5 && conf[5].length()>0 && wsn.isNumeric(conf[5])) sendingType=Integer.parseInt(conf[5]);
     sendingTo=conf[6];
  }
 public void stopContinueSend(){
     serialWriter.stopContinueSend();
 }

  public String getPortName(){
      return portName;
  }
    public final synchronized boolean put(byte b[]){
    try  {

        out.write(b);

    }
    catch(IOException e){
	e.printStackTrace();
        return false;
      }
    return true;
  }
  public int getStatus(){

      if(serialWriter.type!=1) statusOne=wsn.w.addOneVar(statusOne,0);
      return statusOne;
  }
  public class SerialWriter implements Runnable  {
        WSNSerial wsnSerial;
        WSN wsn;

        boolean isSleep=false,needSysChkSum=false;
        int type=1;
        long interval=1000000L;
        String cmdMode="",command="";
        boolean cont=true;
        byte[] toSend;
        public SerialWriter (WSN wsn,WSNSerial wsnSerial) {

            this.wsn=wsn;
            this.wsnSerial=wsnSerial;
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

   public void send(byte[] b,int type,long interval){
       switch(type){
           case 1:
               put(b);
               break;
           case 2:
             this.type=type;
             this.toSend=b;
             this.interval=interval;
             if(isSleep()) outThread.interrupt();
             break;
       }
    }
  public void stopContinueSend(){
      toSend=null;
      type=1;
      if(isSleep()) outThread.interrupt();
  }
  public void setCont(boolean c){
      cont=c;
  }
  }
  

      /**
     * Handles the input coming from the serial port. A new line character
     * is treated as the end of a block in this example. 
     */
    public class SerialReader implements SerialPortEventListener {

        WSN wsn;

        int byteLength=1024;
        private byte[] buffer = new byte[byteLength];
       public SerialReader (WSN wsn){

            this.wsn=wsn;

        }
        @Override
      public void serialEvent(SerialPortEvent event) {
          int data;

        switch(event.getEventType()) {
        case SerialPortEvent.BI:

        case SerialPortEvent.OE:

        case SerialPortEvent.FE:

        case SerialPortEvent.PE:

        case SerialPortEvent.CD:

        case SerialPortEvent.CTS:

        case SerialPortEvent.DSR:

        case SerialPortEvent.RI:

        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:

            break;
        case SerialPortEvent.DATA_AVAILABLE:

            try {
                int len = 0;

                long time1=System.currentTimeMillis();
                while ( ( data = inputStream.read()) > -1 &&  (System.currentTimeMillis()-time1)< 300L) {

                    if ( data == '\n' || len>byteLength-1 ) {

                        break;
                    }
                    buffer[len++] = (byte) data;
                }

                String str=wsn.byteToStr(buffer,len);

                 String cmd2="performmessage wsn.WSN wsn_data "+wsn.w.getGNS(6)+" 2 "+portName+" 1 "+wsn.w.e642(str);
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

            }
            catch ( IOException e )  {
                String cmd2="performmessage wsn.WSN wsn_closeserial "+wsn.w.getGNS(6)+" "+portName+" "+wsn.w.e642(wsn.w.getGNS(6)+":"+portName+" "+bundle2.getString("WSNSerial.xy.msg1"));
                wsn.w.sendToAll(cmd2);
                wsn.closeSerialPort(portName);

                System.out.println("serialport reader exception catched.");
            } catch(ArrayIndexOutOfBoundsException e){

                System.out.println("ArrayIndexOutOfBoundsException in portName "+portName+" in wsn.WSNSerial$SerialReader.serialEvent(),msg="+e.getMessage()+"\r\nbuffer data="+wsn.byteToStr(buffer,byteLength));
                buffer=new byte[1024];
            }
            break;
          }
        }

    }
    static void listPorts() {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() ) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
        }        
    }

    static public String[] getPorts() {
      String rtn[];
      Vector v=new Vector();
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() ) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            if(portIdentifier.getPortType()==CommPortIdentifier.PORT_SERIAL || portIdentifier.getPortType()==CommPortIdentifier.PORT_RS485) v.add(portIdentifier.getName());
        }
        Object o[]=v.toArray();
        rtn=new String[o.length];
        for(int i=0;i<o.length;i++) rtn[i]=(String)o[i];
        return rtn;
    }
    static String getPortTypeName ( int portType )  {
        switch ( portType )  {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }
}