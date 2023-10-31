//Student Number: 501166279
//Name: Shreya Dasoju

import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.format.SignStyle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore extends Library	//extend Library class to access the new exception classes
{		
		
		private ArrayList<AudioContent> contents = new ArrayList<AudioContent>();	//contents arraylist holds all audiocontent objects

		private HashMap <String, Integer> searchAudio = new HashMap<String, Integer>();	//map for SEARCH

		private HashMap <String, ArrayList<Integer>> searchArtist = new HashMap<String, ArrayList<Integer>>();	//map for SEARCHA

		private Map <Song.Genre, ArrayList<Integer>> searchGenre = new HashMap<Song.Genre, ArrayList<Integer>>();	//map for SEARCHG
		

		public AudioContentStore()
		{
			try{
				//creates contents arrayList/"Audio Store"
				createStore("store.txt");

				//SEARCH MAP
				for (int i=0; i<contents.size();i++){
					searchAudio.put(contents.get(i).getTitle(), i);	//add a set with the title as key, and index as value, to the searchAudio map
				}

				//SEARCHA MAP
				for (int i =0; i<contents.size();i++){	//loop through all objects in contents arraylist
					String dataType = contents.get(i).getType();	//store the datatype to check if it's a song or audiobook
					if (dataType.equalsIgnoreCase(Song.TYPENAME)){
						Song song = (Song)contents.get(i);	//copy of object
						String creator = song.getArtist();	
						if (!searchArtist.containsKey(creator)){	//check if the artist's name is already added to the keys for the map
							ArrayList<Integer> indexCount = searchArtist.get(creator);	//if value not found, it'll return null
							if (indexCount == null){
								indexCount = new ArrayList<Integer>();
								indexCount.add(i);
								searchArtist.put(creator, indexCount);
							}
						}
						else if (searchArtist.containsKey(creator)){
							ArrayList<Integer>existingA = searchArtist.get(creator);
							existingA.add(i);
						}
					}
					
					else if (dataType.equalsIgnoreCase(AudioBook.TYPENAME)){
						AudioBook tempB = (AudioBook)contents.get(i);
						String creator = tempB.getAuthor();
						if (!searchArtist.containsKey(creator)){
							ArrayList<Integer>indexCount = searchArtist.get(creator);
							if (indexCount == null){
								indexCount = new ArrayList<Integer>();
								indexCount.add(i);
								searchArtist.put(creator,indexCount);
							}
						}
						else if (searchArtist.containsKey(creator)){
							ArrayList<Integer>existingA = searchArtist.get(creator);
							existingA.add(i);
						}
						
					}
					
					
				}

				//SEARCHG MAP
				for (int i=0; i<contents.size(); i++){	//loops through all objects of contents arraylist
					String genreMatch = contents.get(i).getType();	//store the type of the object (song or audiobook). For the sake of this method, you only need to handle songs
					if (genreMatch.equalsIgnoreCase(Song.TYPENAME)){
						Song tempS = (Song)contents.get(i);
						Song.Genre targetG = tempS.getGenre();
						if (!searchGenre.containsKey(targetG)){	//if the key with the genre isn't found in the map's keyset, then proceed 
							ArrayList<Integer> indexCount = searchGenre.get(targetG);
							if (indexCount == null){
								indexCount = new ArrayList<Integer>();
								indexCount.add(i);
								searchGenre.put(targetG, indexCount);
							}
						}
						else if (searchGenre.containsKey(targetG)){	//if key already exists, add to existing arrayList
							ArrayList<Integer>existingG = searchGenre.get(targetG);
							existingG.add(i);
						}
						
					}
				}
				

			}
			
			//catch block for in case the program cannot find the file that the method "createStore" is called with
			catch(IOException e){
				System.out.println("Invalid File");
				System.exit(1);
			}
			
		}
		
		//the method below is to actually create the store by reading the info from the store.txt file, creating objects, and adding them to the contents arraylist
		private ArrayList<AudioContent> createStore(String filename) throws IOException{
			if (!filename.equals("store.txt")){
				throw new IOException();
			}
			//declare variables needed to store info from the file
			String title ="";
			int year =0;
			String id = "";
			String type = "";
			int length=0;
			String artist="";
			String composer="";
			String author ="";
			String narrator="";
			Song.Genre genre=Song.Genre.CLASSICAL;	//initial declaration as a placeholder until method reads textfile's info
			String lyrics="";
			ArrayList<String> chapterTitles = new ArrayList<String>();
			ArrayList<String> chaptersContent = new ArrayList<String>();

			File storeFile = new File(filename); //reads file

			Scanner storeReader = new Scanner(storeFile); //scanner to read through the file

			while (storeReader.hasNextLine()){	//makes sure you read until file is complete
				String songType = storeReader.nextLine();
				if (songType.equals(Song.TYPENAME)){
					type=Song.TYPENAME;
					id=storeReader.nextLine();
					title=storeReader.nextLine();
					year=Integer.parseInt(storeReader.nextLine());
					length=Integer.parseInt(storeReader.nextLine());
					artist=storeReader.nextLine();
					composer=storeReader.nextLine();
					String tempG=storeReader.nextLine();
					if (tempG.equals("ROCK")){
						genre=Song.Genre.ROCK;
					}
					else if (tempG.equals("POP")){
						genre=Song.Genre.POP;
					}
					else if (tempG.equals("JAZZ")){
						genre=Song.Genre.JAZZ;
					}
					else if (tempG.equals("HIPHOP")){
						genre=Song.Genre.HIPHOP;
					}
					else if (tempG.equals("RAP")){
						genre=Song.Genre.RAP;
					}
					else if (tempG.equals("CLASSICAL")){
						genre=Song.Genre.CLASSICAL;
					}
					int numLyrics = Integer.parseInt(storeReader.nextLine());	//nextLine gives you the number of lines of lyrics, so use that value to call nextLine that many times
					for (int i=0; i<numLyrics; i++){
						lyrics+=storeReader.nextLine();
					}
					contents.add(new Song(title, year, id, type, lyrics, length, artist, composer, genre, lyrics));	//once all data is stored, create Song object by calling Song's constructor with those values
					System.out.println("Loading "+ Song.TYPENAME);
					lyrics="";	//reset lyrics 
				}
				else if (songType.equals(AudioBook.TYPENAME)){
					//same concept as for Songs
					type=AudioBook.TYPENAME;
					id=storeReader.nextLine();
					title=storeReader.nextLine();
					year=Integer.parseInt(storeReader.nextLine());
					length=Integer.parseInt(storeReader.nextLine());
					author=storeReader.nextLine();
					narrator = storeReader.nextLine();
					int numTitles = Integer.parseInt(storeReader.nextLine());
					for (int i=0; i<numTitles;i++){
						chapterTitles.add(storeReader.nextLine());
					}
					while (storeReader.hasNextInt()){
						String chapterC = "";
						int numChapters = Integer.parseInt(storeReader.nextLine());
						for (int i=0; i<numChapters;i++){
							chapterC+=storeReader.nextLine();
						}
						chaptersContent.add(chapterC);
					}
						
					contents.add(new AudioBook(title, year, id, type, "", length, author, narrator, chapterTitles, chaptersContent));
					System.out.println("Loading "+AudioBook.TYPENAME);
					chapterTitles.clear();	//clear the arrayLists
					chaptersContent.clear();
	
				}	
			}
			storeReader.close();	//close scanner
			return contents;	//return contents (used for maps in constructor method)
		}


			public AudioContent getContent(int index){
				if (index < 1 || index > contents.size()){
					return null;
				}
				return contents.get(index-1);
			}
		
			public void listAll()
			{
				for (int i = 0; i < contents.size(); i++){
					int index = i + 1;
					System.out.print(index + ". ");
					contents.get(i).printInfo();
					System.out.println();
				}
			}
			
			//get method for SEARCH
			public void getSearchAudio(String audioTitle){
				if (searchAudio.keySet().contains(audioTitle)){	//checks if audioTitle input exists in the map's keyset
					for (String key : searchAudio.keySet()){	//loop through each song stored in map's keyset
						if (key.equalsIgnoreCase(audioTitle)){
							int value = searchAudio.get(key);
							if (contents.get(value).getType().equalsIgnoreCase(Song.TYPENAME)){
								Song tempS = (Song)contents.get(value);	//create object of class Song in order to be able to call printInfo method on the content
								System.out.print((value + 1)+". ");
								tempS.printInfo();
								System.out.println();
								
							}
							else if (contents.get(value).getType().equalsIgnoreCase(AudioBook.TYPENAME)){
								AudioBook tempB = (AudioBook)contents.get(value);
								System.out.print((value+1)+". ");
								tempB.printInfo();
								System.out.println();
							}
							
						}
					}
				}
				else{	// if given title isn't found in map, throw exception (from exception class in Library)
					Library.ItemNotFoundException obj1 = new ItemNotFoundException("No matches for "+audioTitle);	//create an object of the exception class in Library to throw the exception in this class
					throw obj1;
				}
			}
			
			//get method for SEARCHA
			public void getSearchArtist (String creatorName){
				if (searchArtist.keySet().contains(creatorName)){
					for (String key : searchArtist.keySet()){
						if (key.equalsIgnoreCase(creatorName)){
							int songNum = searchArtist.get(key).size();	//store the arraylist size so you know how many times to loop through it
							ArrayList<Integer> tempIndex = searchArtist.get(key);	//store the integer arraylist corresponding to the artist
							for (int i=0; i<songNum; i++){
								int num = tempIndex.get(i);
								
								if (contents.get(num).getType().equalsIgnoreCase(Song.TYPENAME)){
									//same concepts as in getSearchAudio
									Song song = (Song)contents.get(num);
									System.out.print((num+1)+". ");
									song.printInfo();
									System.out.println();
								}
								else if (contents.get(num).getType().equalsIgnoreCase(AudioBook.TYPENAME)){
									AudioBook book = (AudioBook)contents.get(num);
									System.out.print((num+1)+". ");
									book.printInfo();
									System.out.println();
								}
							}
						}
					}
				}
				else{
					Library.ItemNotFoundException obj2 = new ItemNotFoundException("No matches for "+creatorName);
					throw obj2;
				}
			}


			//get method for SEARCHG
			public void getSearchGenre(String genre){
				//same concept as in getSearchArtist
				Song.Genre tempG = Song.Genre.POP;
				if (genre.equalsIgnoreCase("POP")){
					tempG = Song.Genre.POP;
				}
				else if (genre.equalsIgnoreCase("CLASSICAL")){
					tempG = Song.Genre.CLASSICAL;
				}
				else if (genre.equalsIgnoreCase("ROCK")){
					tempG = Song.Genre.ROCK;
				}
				else if (genre.equalsIgnoreCase("RAP")){
					tempG = Song.Genre.RAP;
				}
				else if (genre.equalsIgnoreCase("JAZZ")){
					tempG = Song.Genre.JAZZ;
				}
				else if (genre.equalsIgnoreCase("HIPHOP")){
					tempG = Song.Genre.HIPHOP;
				}
				if (searchGenre.keySet().contains(tempG)){
					for (Song.Genre key : searchGenre.keySet()){
						if (key == tempG){
							//int genreNum = searchGenre.get(key).size();
							ArrayList<Integer> indices = searchGenre.get(key);
							for (int i=0; i<indices.size(); i++){
								int x = indices.get(i);
								//Song song = (Song)contents.get(x);
								System.out.print((x+1)+". ");
								contents.get(indices.get(i)).printInfo();
								System.out.println();
							}
						}
					}
				}
				else{
					Library.ItemNotFoundException obj3 = new ItemNotFoundException("No matches for "+genre);
					throw obj3;
				}
			}

			//method for DOWNLOADA
			public ArrayList<Integer> downloadA (String creatorName){
				if (searchArtist.keySet().contains(creatorName)){
					return searchArtist.get(creatorName);
				}
				else{
					ArrayList<Integer> empty = new ArrayList<>();
					//print statement instead of exception because if exception was thrown here, return line would be unreachable, and no error message is required for this as per the assignment's expected output
					System.out.println("No artist found with the name "+creatorName);	
					return empty;
						
				}
			}
			
			//method for DOWNLOADG
			public ArrayList<Integer> downloadG (String genre){
				Song.Genre tempG = null;	//in case a valid genre is not entered

				if (genre.equalsIgnoreCase("POP")){
					tempG = Song.Genre.POP;
				}
				else if (genre.equalsIgnoreCase("CLASSICAL")){
					tempG = Song.Genre.CLASSICAL;
				}
				else if (genre.equalsIgnoreCase("ROCK")){
					tempG = Song.Genre.ROCK;
				}
				else if (genre.equalsIgnoreCase("RAP")){
					tempG = Song.Genre.RAP;
				}
				else if (genre.equalsIgnoreCase("JAZZ")){
					tempG = Song.Genre.JAZZ;
				}
				else if (genre.equalsIgnoreCase("HIPHOP")){
					tempG = Song.Genre.HIPHOP;
				}
				if (searchGenre.keySet().contains(tempG)){
					return searchGenre.get(tempG);
				}
				else{
					ArrayList<Integer> empty = new ArrayList<>();
					System.out.println("No genre found with name "+genre);
					return empty; 
				}

			}


		
}

