import java.rmi.*;

public class TaggrServer {
   public static void main(String[] args) {
      System.setSecurityManager(new SecurityManager());
      try {
        TaggrImpl ta = new TaggrImpl();
        Naming.rebind(Taggr.SERVICENAME, ta);
        System.out.println("Published in RMI registery, ready...");
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
   }
}
