package wsn;

import java.awt.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

public class WSNLineButtonPanel extends javax.swing.JPanel {
    WSN wsn;
    WSNLineChart lineChart;
    Color bk=Color.LIGHT_GRAY;
    ResourceBundle bundle2=java.util.ResourceBundle.getBundle("wsn/Bundle");
  Toolkit toolkit;
  String startImg[]={wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.startimg0")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.startimg1")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.startimg2"))},
    stopImg[]={wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.stopimg0")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.stopimg1")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.stopimg2"))},
    runningImg[]={wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.runningimg0")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.runningimg1")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.runningimg2"))},
    alarmImg[]={wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.alarmimg0")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.alarmimg1")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.alarmimg2")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.alarmimg3")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.alarmimg4"))},
    realtimeImg[]={wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.realtimeimg0")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.realtimeimg1")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.realtimeimg2")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.realtimeimg3")),wsn.fileSeparator(bundle2.getString("WSNButtonPanel.xy.realtimeimg4"))};
  Image startImage[]=new Image[startImg.length],stopImage[]=new Image[stopImg.length],runningImage[]=new Image[runningImg.length],
          alarmImage[]=new Image[alarmImg.length],realtimeImage[]=new Image[realtimeImg.length];
    Image alarmImg2,realtimeImg2,runningImg2,startImg2,stopImg2;

    public WSNLineButtonPanel(WSN wsn,WSNLineChart lineChart) {
        this.wsn=wsn;
        this.lineChart=lineChart;
        toolkit = Toolkit.getDefaultToolkit(); 
        for(int i=0;i<startImg.length;i++) startImage[i]=toolkit.createImage(startImg[i]);
        for(int i=0;i<stopImg.length;i++) stopImage[i]=toolkit.createImage(stopImg[i]);
        for(int i=0;i<runningImg.length;i++) runningImage[i]=toolkit.createImage(runningImg[i]);
        for(int i=0;i<alarmImg.length;i++) alarmImage[i]=toolkit.createImage(alarmImg[i]);
        for(int i=0;i<realtimeImg.length;i++) realtimeImage[i]=toolkit.createImage(realtimeImg[i]);
        alarmImg2=alarmImage[0];
        realtimeImg2=realtimeImage[0];
        runningImg2=runningImage[0];
        startImg2=startImage[0];
        stopImg2=stopImage[0];

        initComponents();
    }

protected void paintComponent(Graphics g) { 
    int w=getWidth();
    int h=getHeight();
    int r=(int)(((double)w)*0.24);
    if((int)(((double)h)/12.0)<r) r=(int)(((double)h)/12.0);
    int d=2*r;
   Insets insets = getInsets(); 
    g.setColor(bk); 

    if(wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],0)){
        if(wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],2)) {
            if(lineChart.onoff) alarmImg2=alarmImage[4];
            else alarmImg2=alarmImage[0];
        }
        else alarmImg2=alarmImage[1];
    } else alarmImg2=alarmImage[0];

    if(wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],0)){
        if(wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],5)) {
          if(lineChart.onoff) realtimeImg2=realtimeImage[4];
          else realtimeImg2=realtimeImage[0];
        }
        else realtimeImg2=realtimeImage[1];
    } else realtimeImg2=realtimeImage[0];

    if(wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],0)){
        if(lineChart.onoff) runningImg2=runningImage[2];
        else runningImg2=runningImage[1];
    } else runningImg2=runningImage[0];
    if(wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],3)){
        startImg2=startImage[1];
    } else if(wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],0)) startImg2=startImage[0];
       else startImg2=startImage[2];
    if(wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],4)){
        stopImg2=stopImage[1];
    } else if(wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],0)) stopImg2=stopImage[2];
       else stopImg2=stopImage[0];

    g.fillRect(0,0, insets.left + getWidth(), insets.top + getHeight()); 
    g.drawImage(alarmImg2,(int)(((double)w)*0.25) - r, (int)(((double)h)*0.02), d, d, this);
    g.drawImage(realtimeImg2,(int)(((double)w)*0.75) - r, (int)(((double)h)*0.02), d, d, this);

    if(wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],0)) g.drawImage(runningImg2,(int)(((double)w)*0.1), (int)(((double)h)*0.20), (int)(((double)w)*0.8), (int)(((double)h)*0.12), this);
    g.drawImage(startImg2,(int)(((double)w)*0.1), (int)(((double)h)*0.34), (int)(((double)w)*0.8),  (int)(((double)h)*0.13), this);
    g.drawImage(stopImg2,(int)(((double)w)*0.1), (int)(((double)h)*0.49), (int)(((double)w)*0.8),  (int)(((double)h)*0.13), this);

    g.setColor(Color.blue);

} 

    @SuppressWarnings("unchecked")

  private void initComponents() {

    addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(java.awt.event.MouseEvent evt) {
        formMousePressed(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        formMouseReleased(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 300, Short.MAX_VALUE)
    );
  }

private void formMousePressed(java.awt.event.MouseEvent evt) {
  int x=evt.getX();
  int y=evt.getY();
  int w=getWidth();
  int h=getHeight();

  if(x>=(int)(((double)w)*0.1) && x<= (int)(((double)w)*0.1)+ (int)(((double)w)*0.8) &&
     y>=(int)(((double)h)*0.34) && y<=(int)(((double)h)*0.34)+ (int)(((double)h)*0.13) && !wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],0)) {
     lineChart.currentStatus.longValue[1]=wsn.w.addOneVar(lineChart.currentStatus.longValue[1],3);
     lineChart.currentStatus.longValue[1]=wsn.w.removeOneVar(lineChart.currentStatus.longValue[1],4);
     lineChart.status.put(lineChart.currentDataSrcId, lineChart.currentStatus);

  }

  if(x>=(int)(((double)w)*0.1) && x<= (int)(((double)w)*0.1)+ (int)(((double)w)*0.8) &&
     y>=(int)(((double)h)*0.49) && y<=(int)(((double)h)*0.49)+ (int)(((double)h)*0.13) && wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],0)) {
     lineChart.currentStatus.longValue[1]=wsn.w.removeOneVar(lineChart.currentStatus.longValue[1],3);
     lineChart.currentStatus.longValue[1]=wsn.w.addOneVar(lineChart.currentStatus.longValue[1],4);
     lineChart.status.put(lineChart.currentDataSrcId, lineChart.currentStatus);

  }
  repaint();
}

private void formMouseReleased(java.awt.event.MouseEvent evt) {
  int x=evt.getX();
  int y=evt.getY();
  int w=getWidth();
  int h=getHeight();

  if(x>=(int)(((double)w)*0.1) && x<= (int)(((double)w)*0.1)+ (int)(((double)w)*0.8) &&
     y>=(int)(((double)h)*0.34) && y<=(int)(((double)h)*0.34)+ (int)(((double)h)*0.13) && wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],3)) {

    toStart();
  }
  if(x>=(int)(((double)w)*0.1) && x<= (int)(((double)w)*0.1)+ (int)(((double)w)*0.8) &&
     y>=(int)(((double)h)*0.49) && y<=(int)(((double)h)*0.49)+ (int)(((double)h)*0.13) && wsn.w.checkOneVar(lineChart.currentStatus.longValue[1],4)){

     toStop();
  }
     lineChart.currentStatus.longValue[1]=wsn.w.removeOneVar(lineChart.currentStatus.longValue[1],3);
     lineChart.currentStatus.longValue[1]=wsn.w.removeOneVar(lineChart.currentStatus.longValue[1],4);
     lineChart.status.put(lineChart.currentDataSrcId, lineChart.currentStatus);
 repaint();
}
void toStart(){
     lineChart.toStart(lineChart.currentDataSrcId);
     repaint();
}

void toStop(){
    lineChart.toStop(lineChart.currentDataSrcId);
    repaint();
}

}