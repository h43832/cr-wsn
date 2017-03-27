package wsn;

import java.awt.Color;
import golib.ControlChartPanel;
import java.util.*;
public class WSNLineIntegraPanel extends javax.swing.JPanel {

    WSN wsn;
    WSNLineChart lineChart;
    WSNLineOptionPanel oPanel;

    WSNLineButtonPanel btnPanel;
    ControlChartPanel tPanel;

    int count1=0;
    public WSNLineIntegraPanel(WSN wsn,WSNLineChart lineChart) {
        this.wsn=wsn;
        this.lineChart=lineChart;
        oPanel=new WSNLineOptionPanel(wsn,lineChart,this);

       tPanel=new ControlChartPanel("demo",Color.gray,Color.white,Color.black,
            Color.red,Color.blue,Color.white,Color.yellow, Color.pink, Color.GREEN,
            Color.magenta,Color.orange,true,false,true,
            true,true,true,false,
            false,false,100.0,55.0,10.0,1.0,
         1,400,50,3.0f,1);

       btnPanel=new WSNLineButtonPanel(wsn,lineChart);
        initComponents();
        oPanel.setBackground(Color.gray);

        add(oPanel);
        add(tPanel);
        add(btnPanel);
    }
  void setBlink(boolean onoff){

     if(onoff){

        btnPanel.repaint();
        if(wsn!=null) oPanel.jLabel27.setText(wsn.formatter.format(new Date()));

     } else{
        btnPanel.repaint();
     }
  }
  public void setTMData(TreeMap data,TreeMap config,TreeMap status,String currentDatatId){
      tPanel.setTMData(data,config,status,currentDatatId);
  }
  public void setProperties(long p){
    oPanel.setProperties(p);
  }
  public void doLayout(){
      int w=this.getWidth();
      int h=this.getHeight();
      tPanel.setSize((int)((double)w*0.75), (int)((double)h*0.75));
      tPanel.setLocation(0, 0);
      oPanel.setSize((int)((double)w*0.75), (int)((double)h*0.25));
      oPanel.setLocation(0, (int)((double)h*0.75));

      btnPanel.setSize((int)((double)w*0.25),h);
      btnPanel.setLocation((int)((double)w*0.75), 0);
  }
  

    @SuppressWarnings("unchecked")

  private void initComponents() {

    setBackground(new java.awt.Color(255, 51, 51));
    setLayout(null);
  }

}