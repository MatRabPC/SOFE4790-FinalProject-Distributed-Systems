import java.rmi.*;

public interface Fib extends Remote {
   public final static String SERVICENAME="FibService";
   public String fileChooseReturnPath() throws RemoteException;   
   public String dirChooseReturnPath() throws RemoteException;
}