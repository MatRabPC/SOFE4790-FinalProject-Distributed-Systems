import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.util.HashSet;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JOptionPane;
import com.mpatric.mp3agic.*;

public class FibClient {
	
	public static String SET_PATH = "C://";
	
	public static void main(String[] args) {
		viewGUI();  
   }
   
   public static File[] getFileList(String path)
   {
		/**
		* This method returns an array of mp3 Files within a directory
		* @param String path: path of directory
		* @return File[] list of files
		*/
	   
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
	   	/**
		* This method returns a String array of all
		* the names of the files from a File array
		* @param File[] files: File array of files
		* @return String[] list of file names
		*/
	   
	   String [] list = new String[files.length];
	   
	   for (int i = 0; i < files.length; i++) {
		if (files[i].isFile() ) {
			list[i] = files[i].getName();
		  } 
		}
		return list;
   }
   
   
   public static String fileChooseReturnPath()
   {
	   	/**
		* This method opens the Java FileChooser
		* and allows the user to select a file
		* of which its path will be returned
		* @param none
		* @return String absolute path to file
		*/
		
	    JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		String fpath = "";
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
				fpath = file.getAbsolutePath();
            } else 
				System.out.println("Open command cancelled by user.");
            
		return fpath;    
   }
   
   
   public static String dirChooseReturnPath()
   {
	   	/**
		* This method opens the Java FileChooser
		* and allows the user to select a directory
		* of which its path will be returned
		* @param none
		* @return String absolute path to directory
		*/
		
	    JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(null);
		String fpath = "";
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
				fpath = file.getAbsolutePath();
            } else 
                System.out.println("Open command cancelled by user.");
		return fpath;    
   }
   
   
	public static void setAllAlbum()
	{
		/**
		* This method opens a new frame that takes in
		* a String, and changes all IDV3 Album tags 
		* in the directory to that String
		* @param none
		* @return none
		*/
		
		JFrame frame = new JFrame("Taggr - Set Album");
		frame.setLayout(new GridLayout(1,2));
	
		JTextArea txtArea = new JTextArea(1, 20);
		JButton buttonSet = new JButton("Set");
	
		buttonSet.addActionListener( new ActionListener() {
        @Override
        public void actionPerformed( ActionEvent aActionEvent ) {
			String name = txtArea.getText();
			File[] files = getFileList(SET_PATH);
			String temp = "";
			new File(SET_PATH+"/"+name).mkdirs();
			for (File song : files)
			{
				temp = song.getName();
				Mp3File mp3file = initiateTagger(song.getAbsolutePath());
				ID3v2 id3v2Tag = mp3file.getId3v2Tag();
				try {
					id3v2Tag.setAlbum(name);
					mp3file.save(SET_PATH+"/"+name+"/"+temp);
					System.out.println(name);
				}catch(Exception e) {}
			}			
		
			JOptionPane.showMessageDialog(null, "Retagged Album.","Message", JOptionPane.INFORMATION_MESSAGE);
			frame.dispose(); 
          }});

		frame.add(txtArea);
		frame.add(buttonSet);
		frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
        frame.setVisible(true);
	}


   public static void setAllArtist()
	{
		/**
		* This method opens a new frame that takes in
		* a String, and changes all IDV3 Artist tags 
		* in the directory to that String
		* @param none
		* @return none
		*/
		
		JFrame frame = new JFrame("Taggr - Set Artist");
		frame.setLayout(new GridLayout(1,2));
	
		JTextArea txtArea = new JTextArea(1, 20);
		JButton buttonSet = new JButton("Set");
	
		buttonSet.addActionListener( new ActionListener() {
        @Override
        public void actionPerformed( ActionEvent aActionEvent ) {
			String name = txtArea.getText();
			File[] files = getFileList(SET_PATH);
			String temp = "";
			new File(SET_PATH+"/"+name).mkdirs();
			for (File song : files)
			{
				temp = song.getName();
				Mp3File mp3file = initiateTagger(song.getAbsolutePath());
				ID3v2 id3v2Tag = mp3file.getId3v2Tag();
				try {
					id3v2Tag.setArtist(name);
					mp3file.save(SET_PATH+"/"+name+"/"+temp);
					System.out.println(name);
				}catch(Exception e) {}
			}			
		
			JOptionPane.showMessageDialog(null, "Retagged Artist.","Message", JOptionPane.INFORMATION_MESSAGE);
			frame.dispose(); 
          }});

		frame.add(txtArea);
		frame.add(buttonSet);
		frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
        frame.setVisible(true);
	}
	
	
	public static String[] listOfAlbum(File[] listOfFiles)
	{
		/**
		* This method returns all the albums read 
		* from the IDV3 Album tags of a list of mp3 files
		* @param File[] list of files to read
		* @return String[] list of albums
		*/
		
		String [] listAlbum = new String[listOfFiles.length];
		for(int i = 0; i < listOfFiles.length; i++)
		{
			Mp3File mp3file = initiateTagger(listOfFiles[i].getAbsolutePath());
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			try {
				listAlbum[i] = id3v2Tag.getAlbum();
				}catch(NullPointerException e) {continue;}
		}
		listAlbum = new HashSet<String>(Arrays.asList(listAlbum)).toArray(new String[0]); //remove duplicates
		return listAlbum;
	}
   
   
	
	public static void sortIntoFoldersAlbum(File[] files)
	{
		/**
		* This method sorts all passed files into created  
		* directories, organized by Album
		* @param File[] list of files to read
		* @return none
		*/
		
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
				temp = id3v2Tag.getAlbum();
				song.renameTo(new File(direc+"/"+temp+"/"+song.getName()));
			}catch(NullPointerException e) {}
		}	
	}

	
	
	
	public static String[] listOfArtists(File[] listOfFiles)
	{
		/**
		* This method returns all the artists read 
		* from the IDV3 Artist tags of a list of mp3 files
		* @param File[] list of files to read
		* @return String[] list of Artists
		*/
		
		String [] listArtist = new String[listOfFiles.length];
		for(int i = 0; i < listOfFiles.length; i++)
		{
			Mp3File mp3file = initiateTagger(listOfFiles[i].getAbsolutePath());
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			try {
				listArtist[i] = id3v2Tag.getArtist();
				}catch(NullPointerException e) {continue;}
		}
		listArtist = new HashSet<String>(Arrays.asList(listArtist)).toArray(new String[0]); //remove duplicates
		return listArtist;
	}

	
	
	public static void sortIntoFoldersArtist(File[] files)
	{
		/**
		* This method sorts all passed files into created  
		* directories, organized by Artist
		* @param File[] list of files to read
		* @return none
		*/
		
		String direc = SET_PATH;
		String temp = "";
		String[] artists = listOfArtists(files);
		
		for (String artist : artists)
				if (artist != null)
					new File(direc+"/"+artist).mkdirs();
				
		for (File song : files)
		{
			Mp3File mp3file = initiateTagger(song.getAbsolutePath());
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
		   try {
			   
				temp = id3v2Tag.getArtist();
				song.renameTo(new File(direc+"/"+temp+"/"+song.getName()));
		   }catch(NullPointerException e) {}
		   }	
	}
   
   
   
	public static void renameByTags()
	{
		/**
		* This method renames all mp3 files in the   
		* directory based on Track Title and Track number, if available
		* @param none
		* @return none
		*/
		
		File[] files = getFileList(SET_PATH);
		String temp1 = "";
		String temp2 = "";
		
		for (File song : files)
		{
			Mp3File mp3file = initiateTagger(song.getAbsolutePath());
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			try {
				temp1 = id3v2Tag.getTitle();
				System.out.println(temp1);
				temp2 = (id3v2Tag.getTrack()).split("/")[0];
				System.out.println(temp2);
				if (temp1 != null && !temp1.equals("null"))
				{
					if (temp2 != null && !temp2.equals("null"))
				{
					//Files.move(source, source.resolveSibling(temp2+" " + temp1+".mp3"));
					song.renameTo(new File(SET_PATH+"/"+temp2+" " + temp1+".mp3"));
					System.out.println(SET_PATH+"/"+temp2+" " + temp1+".mp3");
				}
				else
					song.renameTo(new File(SET_PATH+"/"+temp2+" " + temp1+".mp3"));
				System.out.println(song.getAbsolutePath());
				}
				
		   }catch(Exception e) {}
		   }	
	}
	
	public static Mp3File initiateTagger(String filename)	
	{
		/**
		* This method creates an MP3File Object (to be tagged)
		* @param String path: path for file of mp3
		* @return MP3File
		*/
	   
		try	{
		Mp3File mp3file = new Mp3File(filename);
		//creates this locally, so the new file will be found in the Client folder
		return mp3file;
		}catch (Exception e) {}
		
	return null;
	}
   
   
   public static void viewGUI()
   {
	   	/**
		* This method creates the user GUI for the application
		* @param none
		* @return none
		*/
		
		JFrame mainFrame = new JFrame("Taggr");
		mainFrame.setLayout(new GridLayout(1,2));
		
		JPanel filePanel = new JPanel();
		filePanel.setLayout(new GridLayout(2, 1));
		JList list = new JList(getFileNames(getFileList(SET_PATH)));
		
		filePanel.add(list);
		filePanel.setBorder(BorderFactory.createTitledBorder(
                   BorderFactory.createEtchedBorder(), "Files"));
				   
				   
		//buttons and button actions
		
		JButton buttonSortAlbum = new JButton("Auto-Sort by Album");
		buttonSortAlbum.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			 sortIntoFoldersAlbum(getFileList(SET_PATH));
			 JOptionPane.showMessageDialog(null, "Sorted by album.","Message", JOptionPane.INFORMATION_MESSAGE);
			 mainFrame.dispose();
			 viewGUI();
        }});
		
		
		JButton buttonSortArtist = new JButton("Auto-Sort by Artist");
		buttonSortArtist.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			 sortIntoFoldersArtist(getFileList(SET_PATH));
			 JOptionPane.showMessageDialog(null, "Sorted by artist.","Message", JOptionPane.INFORMATION_MESSAGE);
        }});
		  
		  
		JButton buttonRenameFile = new JButton("Rename");
		buttonRenameFile.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			 renameByTags();
			 JOptionPane.showMessageDialog(null, "Renamed.","Message", JOptionPane.INFORMATION_MESSAGE);
			 mainFrame.dispose();
			 viewGUI();
        }});
		
		
		JButton buttonSetAllAlbum = new JButton("Set All Album");
		buttonSetAllAlbum.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			setAllAlbum();
        }});
		
		
		JButton buttonSetAllArtist = new JButton("Set All Artist");
		buttonSetAllArtist.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			setAllArtist(); 
        }});
		
		
		JButton buttonSetDir = new JButton("Set Directory");
		buttonSetDir.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			SET_PATH = dirChooseReturnPath();
			mainFrame.dispose();
			viewGUI();
        }});
		
		
		JButton buttonOpenDir = new JButton("Open Directory");
		buttonOpenDir.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			  try{
			Desktop.getDesktop().open(new File(SET_PATH));
		  }catch (Exception e) {}
			 
        }});
		  
		  
		JButton buttonAutoFillTags = new JButton("Get Tags");
		buttonAutoFillTags.addActionListener(new ActionListener(){
		  @Override
		  public void actionPerformed(ActionEvent aActionEvent){
			 findNSetTags((list.getSelectedValue()).toString());
		}});
		
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.addActionListener( new ActionListener() {
          @Override
          public void actionPerformed( ActionEvent aActionEvent ) {
			mainFrame.dispose();
			viewGUI();
        }});
		
		
		
		//add buttons to panel 
		JPanel sidePanel = new JPanel(); 
		sidePanel.setLayout(new GridLayout(9, 1));
		sidePanel.add(buttonSortAlbum);
		sidePanel.add(buttonSortArtist);
		sidePanel.add(buttonRenameFile);
		sidePanel.add(buttonSetAllAlbum);
		sidePanel.add(buttonSetAllArtist);
		sidePanel.add(buttonSetDir);
		sidePanel.add(buttonOpenDir);
		sidePanel.add(buttonRefresh);
		sidePanel.add(buttonAutoFillTags);
		

       
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
   
   
   
   
   public static void setTags(String filename){
	ArrayList<String> tags;
	ID3v2 id3v2Tag;
	
	try {
		Fib f = (Fib) Naming.lookup(Fib.SERVICENAME);
		System.out.println(filename);
		tags = f.mp3LookUp(filename);
		if(tags != null){
			Mp3File mp3file = initiateTagger(SET_PATH+"/"+filename);
			if (mp3file.hasId3v2Tag()) {
				id3v2Tag = mp3file.getId3v2Tag();
			} else {
				id3v2Tag = new ID3v24Tag();
				mp3file.setId3v2Tag(id3v2Tag);
			}
			
			id3v2Tag.setTrack(tags.get(0));
			id3v2Tag.setArtist(tags.get(1));
			id3v2Tag.setTitle(tags.get(2));
			id3v2Tag.setAlbum(tags.get(3));
			id3v2Tag.setYear(tags.get(4));
			id3v2Tag.setGenre(Integer.parseInt(tags.get(5)));
			id3v2Tag.setComment(tags.get(6));
			id3v2Tag.setLyrics(tags.get(7));
			id3v2Tag.setComposer(tags.get(8));
			id3v2Tag.setPublisher(tags.get(9));
			id3v2Tag.setOriginalArtist(tags.get(10));
			id3v2Tag.setAlbumArtist(tags.get(11));
			id3v2Tag.setCopyright(tags.get(12));
			id3v2Tag.setUrl(tags.get(13));
			id3v2Tag.setEncoder(tags.get(14));
			mp3file.save(SET_PATH+"/"+"1"+filename);
		}
    } catch(Exception e) {
        System.err.println("Remote exception: "+e.getMessage());
        e.printStackTrace();
    } 
}
   
   public static void findNSetTags(String filename){
	
	/*
	JFrame dirFrame = new JFrame("");
	dirFrame.setLayout(new GridLayout(1,2));
	JTextArea txt = new JTextArea(1, 20);
	JButton btn = new JButton("Set");

	btn.addActionListener( new ActionListener() {
	@Override
	public void actionPerformed( ActionEvent aActionEvent ) {
		String filename = txt.getText();
		setTags(filename);
	}});
	
	dirFrame.add(txt);
	dirFrame.add(btn);
	dirFrame.setLocationRelativeTo(null);
	dirFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	dirFrame.pack();
	dirFrame.setVisible(true);
	*/
	setTags(filename);
	JOptionPane.showMessageDialog(null, "Tagged.","Message", JOptionPane.INFORMATION_MESSAGE);
	
	
}

/*
   public static void downloadMusicFile(String fileName){
      try {
      //   String name = "//" + InetAddress.getLocalHost().getHostName() + "/FileServer";
         f = (Fib) Naming.lookup(Fib.SERVICENAME);
		
         byte[] filedata = f.downloadFile(fileName);
         File file = new File(fileName);
		 System.out.println(file.getName());
         BufferedOutputStream output = new
           BufferedOutputStream(new FileOutputStream(file.getName()));
         output.write(filedata,0,filedata.length);
         output.flush();
         output.close();
		 
      } catch(Exception e) {
         System.err.println("FileServer exception: "+ e.getMessage());
         e.printStackTrace();
      }
   }
   */
	
}