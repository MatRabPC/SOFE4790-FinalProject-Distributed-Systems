import java.io.*; 
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.mpatric.mp3agic.*;

public class TaggrImpl extends UnicastRemoteObject implements Taggr {
   
	public TaggrImpl() throws RemoteException {
      super();
	}
   
      
	public ArrayList<String> mp3LookUp(String name){
		try{
			
		/**
		* This method is invoked by the server to look up 
		* a file by name and return its tagging info is available
		* @param String name of the file to find
		* @return ArrayList of tags
		*/
			ArrayList<String> title = new ArrayList<>();
			ArrayList<String> tags = new ArrayList<>();
			Scanner read = new Scanner (new File("songDB.txt"));
			read.useDelimiter(",");
			while(read.hasNext()){
				title.add(read.next());
			}System.out.println(name);
			boolean found = false;
			for(String s: title){
				
				if(name.equals(s)){
					found = true;
					System.out.println("F");
					
					break;
				}
			}
			if(found == true){
				//Insert tag finder shenanigans here
				try{
					Mp3File mp3file = new Mp3File("DB/"+name);//"She's So Mean.mp3");
					
					if (mp3file.hasId3v2Tag()) {
						ID3v2 id3v2Tag = mp3file.getId3v2Tag();
						System.out.println(id3v2Tag.getTitle());
						
						if(id3v2Tag.getTrack() != null){
							tags.add(id3v2Tag.getTrack());
							System.out.println(id3v2Tag.getTrack());
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
   
   
      /*
   public byte[] downloadFile(String fileName){
      try {
         File file = new File(fileName);
         byte buffer[] = new byte[(int)file.length()];
         BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
         input.read(buffer,0,buffer.length);
         input.close();
         return(buffer);
      } catch(Exception e){
         System.out.println("FileImpl: "+e.getMessage());
         e.printStackTrace();
         return(null);
      }
   }
   */
   

}