package wsn;

public abstract class WSNApplication extends javax.swing.JFrame {
  public abstract void init(WSN wsn);
  public abstract void setBlink(boolean onoff);

  public abstract void setStatus(String nodeId,String dataSrc[],int statusCode);
  public abstract void setData(long time,String nodeId, String dataSrc,String data);
  public abstract boolean runInBackground();

  public abstract void onExit(int type);
}