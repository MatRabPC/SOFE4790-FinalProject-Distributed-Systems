import java.io.File;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.util.Scanner;
import com.mpatric.mp3agic.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
   
   public ArrayList<String> mp3LookUp(String name){
		try{
			ArrayList<String> title = new ArrayList<>();
			ArrayList<String> tags = new ArrayList<>();
			Scanner read = new Scanner (new File("Songs.txt"));
			read.useDelimiter(",");
			while(read.hasNext()){
				title.add(read.next());
			}
			boolean found = false;
			for(String s: title){
				if(name.equals(s)){
					found = true;
					break;
				}
			}
			if(found == true){
				//Insert tag finder shenanigans here
				try{
					Mp3File mp3file = new Mp3File(name);//"She's So Mean.mp3");
					
					if (mp3file.hasId3v2Tag()) {
						ID3v2 id3v2Tag = mp3file.getId3v2Tag();
						
						if(id3v2Tag.getTrack() != null){
							tags.add(id3v2Tag.getTrack());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getArtist() != null){
							tags.add(id3v2Tag.getArtist());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getTitle() != null){
							tags.add(id3v2Tag.getTitle());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getAlbum() != null){
							tags.add(id3v2Tag.getAlbum());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getYear() != null){
							tags.add(id3v2Tag.getYear());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getGenre() != 0){
							tags.add(Integer.toString(id3v2Tag.getGenre()));
						}else{
							tags.add("0");
						}
						
						if(id3v2Tag.getGenreDescription() != null){
							tags.add(id3v2Tag.getGenreDescription());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getComment() != null){
							tags.add(id3v2Tag.getComment());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getLyrics() != null){
							tags.add(id3v2Tag.getLyrics());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getComposer() != null){
							tags.add(id3v2Tag.getComposer());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getPublisher() != null){
							tags.add(id3v2Tag.getPublisher());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getOriginalArtist() != null){
							tags.add(id3v2Tag.getOriginalArtist());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getAlbumArtist() != null){
							tags.add(id3v2Tag.getAlbumArtist());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getCopyright() != null){
							tags.add(id3v2Tag.getCopyright());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getUrl() != null){
							tags.add(id3v2Tag.getUrl());
						}else{
							tags.add("null");
						}
						
						if(id3v2Tag.getEncoder() != null){
							tags.add(id3v2Tag.getEncoder());
						}else{
							tags.add("null");
						}

					}
					
				}catch (Exception e) {
					System.out.println("error");
				}
				return tags;
			}else{
				return null;
			}
		}catch(FileNotFoundException e){
			
		}
		return null;
	}
}