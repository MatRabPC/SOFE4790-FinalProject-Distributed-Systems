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
/*

Application name	Tagger
API key				aabe9493c7ae7cc922ebe242f84ba4bb
Shared secret		e14bf94f5c8a3d8e924b46e7b89e6a85
Registered to		samvvsam

*/

//play music files
//analyse bpm
	//find songs that match bpm
//file name to tag

public class FibClient {
	
	public static String SET_PATH = "";
	
   public static void main(String[] args) {

      try {
        Fib f = (Fib) Naming.lookup(Fib.SERVICENAME);
				
		
        SET_PATH = f.dirChooseReturnPath();
		System.out.println(SET_PATH);
		
		viewGUI();
		//GUIForTagger(s);
		/*
		
		File folder = new File(SET_PATH);
		
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
		
		
		
		File[] listOfFiles = folder.listFiles(mp3Filter);

		for (int i = 0; i < listOfFiles.length; i++) {
		if (listOfFiles[i].isFile() ) {//&& listOfFiles[i].getName().toLowerCase().endsWith(".mp3")) {
			System.out.println(listOfFiles[i].getName());
		  } //else if (listOfFiles[i].isDirectory()) {
			//System.out.println("Directory " + listOfFiles[i].getName());
		  //}
		}
		
		
		String[] guesses = guessAlbum(listOfFiles);
		
		guesses = new HashSet<String>(Arrays.asList(guesses)).toArray(new String[0]);
		
		//for (String guess : guesses)
			//if (guess != null)
				//System.out.println(guess);
			
		//GUIForAlbumGuesses(guesses);
		
		sortIntoFolders(f.dirChooseReturnPath(), guesses, folder.listFiles(mp3Filter));
		
		
		//searchThis("Rob+Thomas");
	//	GUIForTagger(s);
		//readFileTags(s);
		*/
		
        //System.out.println("The factorial of "+ x + " is: "+fn);
      } catch(Exception e) {
        System.err.println("Remote exception: "+e.getMessage());
        e.printStackTrace();
      }
	  
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
   

   
   public static void GUIForAlbumGuesses(String[] guesses)
   {
	   
	   JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JList list = new JList((guesses));

        frame.add(new JScrollPane(list));

        frame.setSize(300, 200);
        frame.setVisible(true);
	   
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
		
		JPanel sidePanel = new JPanel(); 
		sidePanel.setLayout(new GridLayout(3, 1));
        
		sidePanel.add(buttonSortAlbum);
		sidePanel.add(buttonSortArtist);
		sidePanel.add(buttonRenameFile);
		

        //... Add a titled border to the button panel.
        sidePanel.setBorder(BorderFactory.createTitledBorder(
                   BorderFactory.createEtchedBorder(), "Sort"));
				   
		

		
				   
				   
		mainFrame.add(sidePanel);
		mainFrame.add(filePanel);
		mainFrame.setSize(1280,720);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
        mainFrame.setVisible(true);
	   
   }
   
   public static void GUIForTagger(String path)  {
	   /**
	   * This method builds the main window for the Client UID
	   * @param String path: path for file of mp3
	   * @return none
	   */
	   
	   Mp3File mp3file = initiateTagger(path);
	   ID3v2 id3v2Tag = mp3file.getId3v2Tag();
	   
	  
	   
		JFrame mainFrame = new JFrame("");
		mainFrame.setLayout(new GridLayout(8,2));
		
		
		JTextField textTitle = new JTextField(20);
		JButton buttonTitle = new JButton("Set Title");
		
		buttonTitle.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
				id3v2Tag.setTitle(textTitle.getText());
				try{
				mp3file.save("New.mp3");
				showProgress("Title change to " + textTitle.getText());
				}
					catch (Exception e) {}
          }});
		
		
		
		JTextField textArtist = new JTextField(20);
		JButton buttonArtist = new JButton("Set Artist");
		buttonArtist.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
				id3v2Tag.setArtist(textArtist.getText());
				try{
				mp3file.save("New.mp3");
				showProgress("Artist change to " + textArtist.getText());
				}
					catch (Exception e) {}
          }});
		  
		
		
		JTextField textAlbum = new JTextField(20);
		JButton buttonAlbum = new JButton("Set Album");
		buttonAlbum.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
				id3v2Tag.setAlbum(textAlbum.getText());
				try{
				mp3file.save("New.mp3");
				showProgress("Album change to " + textAlbum.getText());
				}
					catch (Exception e) {}
          }});
		
		
		
		JTextField textYear = new JTextField(20);
		JButton buttonYear = new JButton("Set Year");
		buttonYear.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			  //TODO try/catch IntegerparseInt check
				id3v2Tag.setYear(textYear.getText());
				try{
				mp3file.save("New.mp3");
				showProgress("Year change to " + textYear.getText());
				}
					catch (Exception e) {}
          }});
		
		
		
		JTextField textGenre = new JTextField(20);
		JButton buttonGenre = new JButton("Set Genre");
		buttonGenre.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
				//id3v2Tag.setGenre(textGenre.getText());
				try{
				mp3file.save("New.mp3");
				//showProgress("Artist change to " + textArtist.getText());
				}
					catch (Exception e) {}
          }});
		
		
		
		JTextField textTrack = new JTextField(20);
		JButton buttonTrack = new JButton("Set Track");
		buttonTrack.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
				//TODO try/catch IntegerparseInt check
				id3v2Tag.setTrack(textTrack.getText());
				try{
				mp3file.save("New.mp3");
				showProgress("Track number change to " + textTrack.getText());
				}
					catch (Exception e) {}
          }});
		  
		  
		  
		  JTextField textLook = new JTextField(20);
		JButton buttonLook = new JButton("Look Up DASJ");
		buttonLook.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			  System.out.println("WAIT A MIN");
				searchThis((textLook.getText()).replaceAll(" ","+"));
          }});
		  
		 JButton buttonPrintToCMD = new JButton("Read (OLD) MP3 File");
		buttonPrintToCMD.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
				readFileTags(path);
          }});
		  

		  
	
		mainFrame.add(textTitle);
		mainFrame.add(buttonTitle);		
		mainFrame.add(textArtist);
		mainFrame.add(buttonArtist);		
		mainFrame.add(textAlbum);
		mainFrame.add(buttonAlbum);		
		mainFrame.add(textYear);
		mainFrame.add(buttonYear);		
		mainFrame.add(textGenre);
		mainFrame.add(buttonGenre);
		mainFrame.add(textTrack);
		mainFrame.add(buttonTrack);
		mainFrame.add(textLook);
		mainFrame.add(buttonLook);
		mainFrame.add(buttonPrintToCMD);
		
        mainFrame.setSize(1280,720);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
        mainFrame.setVisible(true);
		
		
   }
   
  
public static void showProgress(String message)
{
	JOptionPane.showMessageDialog(null, message+" completed.","Message", JOptionPane.INFORMATION_MESSAGE);
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
}
	

public static void searchThis(String name)
   {
	   		 /**
	   * This method is broken but looks up database returns XML tags crazy smhfsf
	   * @param String name: artist name with spaces replaced because url
	   * @return none
	   */
		String urlToRead = "http://www.musicbrainz.org/ws/2/recording/?query=artist:"+name;
		
		
				URL url;
				HttpURLConnection conn;
				BufferedReader rd;
				String line;
				String result = "";
				try {
					url = new URL(urlToRead);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					while ((line = rd.readLine()) != null) {
						//if (line.contains("<title>"))
						//{
							result += line;
							
							//break;
						//}
					}
					rd.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			System.out.println(result);
   }
   
}

