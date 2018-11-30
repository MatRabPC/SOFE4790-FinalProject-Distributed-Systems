import java.io.*;
import java.net.*;
import java.rmi.Naming;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLEncoder; 
import javax.swing.JOptionPane;
import com.mpatric.mp3agic.*;
import java.util.HashSet;
import java.util.Arrays;


public class FibClient {
	
	public static String SET_PATH = "";
	public static Fib f = null;
	
   public static void main(String[] args) {

      try {
        f = (Fib) Naming.lookup(Fib.SERVICENAME);
		SET_PATH = f.dirChooseReturnPath();
      } catch(Exception e) {
        System.err.println("Remote exception: "+e.getMessage());
        e.printStackTrace();
      } 
	  
	  
		System.out.println(SET_PATH);
		
		viewGUI();
	  
   }
   
   
   public static File[] getFileList(String path)
   {
	   File folder = new File(path); 
		
		FilenameFilter mp3Filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(".mp3")) {
					return true;
				} else {
					return false;
				}
			}
		};	
		
		return folder.listFiles(mp3Filter);

   }
   
   public static String[] getFileNames(File[] files)
   {
	   String [] list = new String[files.length];
	   
	   for (int i = 0; i < files.length; i++) {
		if (files[i].isFile() ) {//&& listOfFiles[i].getName().toLowerCase().endsWith(".mp3")) {
			list[i] = files[i].getName();
		  } //else if (listOfFiles[i].isDirectory()) {
			//System.out.println("Directory " + listOfFiles[i].getName());
		  //}
		}
		return list;
   }
   
   public static void viewGUI()
   {
	   JFrame mainFrame = new JFrame("");
		mainFrame.setLayout(new GridLayout(1,2));
		
				JPanel filePanel = new JPanel();
		filePanel.setLayout(new GridLayout(2, 1));
		JList list = new JList(getFileNames(getFileList(SET_PATH)));
		
		filePanel.add(list);
		filePanel.setBorder(BorderFactory.createTitledBorder(
                   BorderFactory.createEtchedBorder(), "Files"));
				   
				   
		
		JButton buttonSortAlbum = new JButton("Auto-Sort by Album");
		buttonSortAlbum.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			 sortIntoFoldersAlbum(getFileList(SET_PATH));
			 JOptionPane.showMessageDialog(null, "Sorted by album.","Message", JOptionPane.INFORMATION_MESSAGE);
			 mainFrame.revalidate();
          }});
		
		
		
		JButton buttonSortArtist = new JButton("Auto-Sort by Artist");
		buttonSortArtist.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			 sortIntoFoldersArtist(getFileList(SET_PATH));
			 JOptionPane.showMessageDialog(null, "Sorted by artist.","Message", JOptionPane.INFORMATION_MESSAGE);
          }});
		  
		  
		JButton buttonRenameFile = new JButton("Rename");
		JButton buttonManual = new JButton("Manual Tag");
		JButton buttonSetDir = new JButton("Set Directory");
		
		JPanel sidePanel = new JPanel(); 
		sidePanel.setLayout(new GridLayout(5, 1));
        
		sidePanel.add(buttonSortAlbum);
		sidePanel.add(buttonSortArtist);
		sidePanel.add(buttonRenameFile);
		sidePanel.add(buttonManual);
		sidePanel.add(buttonSetDir);
		

        //... Add a titled border to the button panel.
        sidePanel.setBorder(BorderFactory.createTitledBorder(
                   BorderFactory.createEtchedBorder(), "Sort"));
				   
		mainFrame.add(sidePanel);
		mainFrame.add(filePanel);
		mainFrame.setSize(1280,720);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mainFrame.pack();
        mainFrame.setVisible(true);
	   
   }

public static String[] listOfAlbum(File[] listOfFiles)
{
	   String [] guesses = new String[listOfFiles.length];
	   
	   for(int i = 0; i < listOfFiles.length; i++)
	   {
	   Mp3File mp3file = initiateTagger(listOfFiles[i].getAbsolutePath());
	   ID3v2 id3v2Tag = mp3file.getId3v2Tag();
	   try {
		   //System.out.println(id3v2Tag.getAlbum());
			guesses[i] = id3v2Tag.getAlbum();
	   }catch(NullPointerException e) {continue;}
	   }
	   guesses = new HashSet<String>(Arrays.asList(guesses)).toArray(new String[0]); //remove duplicates
	   return guesses;
}

public static void sortIntoFoldersAlbum(File[] files)
{
	String direc = SET_PATH;
	String temp = "";
	String[] albums = listOfAlbum(files);
	
	for (String album : albums)
			if (album != null)
				new File(direc+"/"+album).mkdirs();
			
	for (File song : files)
	{
		Mp3File mp3file = initiateTagger(song.getAbsolutePath());
		ID3v2 id3v2Tag = mp3file.getId3v2Tag();
	   try {
		   //System.out.println(id3v2Tag.getAlbum());
			temp = id3v2Tag.getAlbum();
			song.renameTo(new File(direc+"/"+temp+"/"+song.getName()));
	   }catch(NullPointerException e) {}
	   }	
}

public static String[] listOfArtists(File[] listOfFiles)
{
	   String [] guesses = new String[listOfFiles.length];
	   
	   for(int i = 0; i < listOfFiles.length; i++)
	   {
	   Mp3File mp3file = initiateTagger(listOfFiles[i].getAbsolutePath());
	   ID3v2 id3v2Tag = mp3file.getId3v2Tag();
	   try {
		   //System.out.println(id3v2Tag.getAlbum());
			guesses[i] = id3v2Tag.getArtist();
	   }catch(NullPointerException e) {continue;}
	   }
	   guesses = new HashSet<String>(Arrays.asList(guesses)).toArray(new String[0]); //remove duplicates
	   return guesses;
}

public static void sortIntoFoldersArtist(File[] files)
{
	String direc = SET_PATH;
	String temp = "";
	String[] albums = listOfArtists(files);
	
	for (String album : albums)
			if (album != null)
				new File(direc+"/"+album).mkdirs();
			
	for (File song : files)
	{
		Mp3File mp3file = initiateTagger(song.getAbsolutePath());
		ID3v2 id3v2Tag = mp3file.getId3v2Tag();
	   try {
		   //System.out.println(id3v2Tag.getAlbum());
			temp = id3v2Tag.getArtist();
			song.renameTo(new File(direc+"/"+temp+"/"+song.getName()));
	   }catch(NullPointerException e) {}
	   }	
	   /*
	   try {
	   SET_PATH = f.dirChooseReturnPath();
	   }catch(Exception e) {}
	   */
}

public static Mp3File initiateTagger(String filename)	{
		 /**
	   * This method creates an MP3File Object (to be tagged)
	   * @param String path: path for file of mp3
	   * @return MP3File
	   */
	   
	try{
	Mp3File mp3file = new Mp3File(filename);//"She's So Mean.mp3");
	
	//creates this locally, so the new file will be found in the Client folder

	  return mp3file;
		
	}catch (Exception e) {}
	
	return null;
}

	
	
public static void readFileTags(String filename)	{
	
		 /**
	   * This method reads and prints all embedded tags on a file
	   * @param String path: path for file of mp3
	   * @return none
	   */
	
	try{
	Mp3File mp3file = new Mp3File(filename);//"She's So Mean.mp3");
	/*
	System.out.println("Length: " + mp3file.getLengthInSeconds() + " s");
	System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
	System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
	System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
	System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
	System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
	*/
	if (mp3file.hasId3v2Tag()) {
	  ID3v2 id3v2Tag = mp3file.getId3v2Tag();
	  System.out.println("Track: " + id3v2Tag.getTrack());
	  System.out.println("Artist: " + id3v2Tag.getArtist());
	  System.out.println("Title: " + id3v2Tag.getTitle());
	  System.out.println("Album: " + id3v2Tag.getAlbum());
	  System.out.println("Year: " + id3v2Tag.getYear());
	  System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
	  System.out.println("Comment: " + id3v2Tag.getComment());
	  System.out.println("Lyrics: " + id3v2Tag.getLyrics());
	  System.out.println("Composer: " + id3v2Tag.getComposer());
	  System.out.println("Publisher: " + id3v2Tag.getPublisher());
	  System.out.println("Original artist: " + id3v2Tag.getOriginalArtist());
	  System.out.println("Album artist: " + id3v2Tag.getAlbumArtist());
	  System.out.println("Copyright: " + id3v2Tag.getCopyright());
	  System.out.println("URL: " + id3v2Tag.getUrl());
	  System.out.println("Encoder: " + id3v2Tag.getEncoder());
	  byte[] albumImageData = id3v2Tag.getAlbumImage();
	  if (albumImageData != null) {
		System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
		System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
	  }
	}
	
	}catch (Exception e) {}
}
	
}