import java.rmi.*;
import java.util.ArrayList;

public interface Fib extends Remote {
   public final static String SERVICENAME="FibService";
   public String fileChooseReturnPath() throws RemoteException;   
   public String dirChooseReturnPath() throws RemoteException;
   public ArrayList<String> mp3LookUp(String name) throws RemoteException;
}