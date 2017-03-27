package wsn;

public interface WSNAction {
    WSN wsn=null;
    public void startAction(WSN wsn,String dataSource,String data);
}