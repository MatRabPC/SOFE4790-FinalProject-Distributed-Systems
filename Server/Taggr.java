import java.rmi.*;
import java.util.ArrayList;

public interface Taggr extends Remote {
   public final static String SERVICENAME="TaggrService";
   public ArrayList<String> mp3LookUp(String name) throws RemoteException;   
}