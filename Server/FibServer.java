import java.rmi.*;

public class FibServer {
   public static void main(String[] args) {
      System.setSecurityManager(new SecurityManager());
      try {
        FibImpl fi = new FibImpl();
        Naming.rebind(Fib.SERVICENAME, fi);
        System.out.println("Published in RMI registery, ready...");
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
   }
}
