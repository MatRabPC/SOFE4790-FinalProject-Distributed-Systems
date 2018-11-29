import java.io.File;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;

public class FibImpl extends UnicastRemoteObject implements Fib {
   
   
   public FibImpl() throws RemoteException {
      super();
   }
   
   public String fileChooseReturnPath()
   {
	    JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		String fpath = "";
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
              //  System.out.println("Opening: " + file.getName() + ".");
				fpath = file.getAbsolutePath();
            } else {
                System.out.println("Open command cancelled by user.");
            }
		return fpath;    
   }
   
   public String dirChooseReturnPath()
   {
	    JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(null);
		String fpath = "";
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
              //  System.out.println("Opening: " + file.getName() + ".");
				fpath = file.getAbsolutePath();
            } else {
                System.out.println("Open command cancelled by user.");
            }
		return fpath;    
   }
   
   
   
   

}