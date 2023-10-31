//Student Number: 501166279
//Name: Shreya Dasoju
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

//import Song.Genre;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your library
		AudioContentStore store = new AudioContentStore();
		
		// Create my music library
		Library library = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			try{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				library.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				library.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				library.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				library.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				library.listAllPlaylists(); 
			}

			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int start = 0;
				int end =0;
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt()){
					start = scanner.nextInt();
					scanner.nextLine();
				}
				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt()){
					end = scanner.nextInt();
					scanner.nextLine();
				}
				for (int i=start; i<=end; i++){
					AudioContent content = store.getContent(i);
					if (content == null){
						System.out.println("Content Not Found in Store");
					}
					else{
						//for all the download methods, a seperate inner try..catch is required, otherwise after a content that is already downloaded is found,
						//it will not download the next few valid songs
						try{
							library.download(content);
						}
						catch(RuntimeException e){
							System.out.println(e.getMessage());
						}
					}
				}
			
			}

			else if (action.equalsIgnoreCase("DOWNLOADA"))
			{
				String artistName = ""; 
				System.out.print("Creator Name: ");
				if (scanner.hasNextLine()){
					artistName = scanner.nextLine();
				}
				ArrayList<Integer> artistDownloads = store.downloadA(artistName);
				for (int i=0; i<artistDownloads.size();i++){
					try{
						AudioContent content = store.getContent(artistDownloads.get(i)+1);
						library.download(content);
					}
					catch(RuntimeException e){
						System.out.println(e.getMessage());
					}
				}
			}

			else if (action.equalsIgnoreCase("DOWNLOADG"))
			{
				String genreType = "";
				System.out.print("Genre: ");
				if (scanner.hasNextLine()){
					genreType = scanner.nextLine();
				}
				ArrayList<Integer> genreDownloads = store.downloadG(genreType);
				for (int i=0; i<genreDownloads.size();i++){
					try{
						AudioContent content = store.getContent(genreDownloads.get(i)+1);
						library.download(content);
					}
					catch (RuntimeException e){
						System.out.println(e.getMessage());
					}
				}
			}

			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				int index = 0;

				System.out.print("Song Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
				// consume the nl character since nextInt() does not
					scanner.nextLine(); 
				}
				library.playSong(index);
			}

			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				int index = 0;

				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				library.printAudioBookTOC(index);	
			}

			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int index = 0;

				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
				}
				int chapter = 0;
				System.out.print("Chapter: ");
				if (scanner.hasNextInt())
				{
					chapter = scanner.nextInt();
					scanner.nextLine();
				}
				library.playAudioBook(index, chapter);	
			}
			
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				int index = 0;
				int season = 0;
				
				System.out.print("Podcast Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
				}
				System.out.print("Season: ");
				if (scanner.hasNextInt())
				{
					season = scanner.nextInt();
					scanner.nextLine();
				}
				library.printPodcastEpisodes(index, season);
			}

			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				int index = 0;

				System.out.print("Podcast Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				int season = 0;
				System.out.print("Season: ");
				if (scanner.hasNextInt())
				{
					season = scanner.nextInt();
					scanner.nextLine();
				}
				int episode = 0;
				System.out.print("Episode: ");
				if (scanner.hasNextInt())
				{
					episode = scanner.nextInt();
					scanner.nextLine();
				}
				library.playPodcast(index, season, episode);
			}

			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				String title = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				library.playPlaylist(title);
			}

			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				String title = "";
       			int index = 0;
        
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				System.out.print("Content Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				library.playPlaylist(title, index);
			}

			// Delete a song from the library and any play lists it belongs to
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int songNum = 0;

				System.out.print("Library Song #: ");
				if (scanner.hasNextInt())
				{
					songNum = scanner.nextInt();
					scanner.nextLine();
				}
				library.deleteSong(songNum);
			}

			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				String title = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				library.makePlaylist(title);
			}

			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String title = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
					title = scanner.nextLine();
				library.printPlaylist(title);
			}

			// Add content from library (via index) to a playlist
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				int contentIndex = 0;
				String contentType = "";
        		String playlist = "";
        		System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
					playlist = scanner.nextLine();

				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
				if (scanner.hasNextLine())
					contentType = scanner.nextLine();
				
				System.out.print("Library Content #: ");
				if (scanner.hasNextInt())
				{
					contentIndex = scanner.nextInt();
					scanner.nextLine(); // consume nl
				}
				library.addContentToPlaylist(contentType, contentIndex, playlist);
			}

			// Delete content from play list
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				int contentIndex = 0;
				String playlist = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
					playlist = scanner.nextLine();
				
				System.out.print("Playlist Content #: ");
				if (scanner.hasNextInt())
				{
					contentIndex = scanner.nextInt();
					scanner.nextLine(); // consume nl
				}
				library.delContentFromPlaylist(contentIndex, playlist);
			}

			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				library.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				library.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				library.sortSongsByLength();
			}
			else if (action.equalsIgnoreCase("SEARCH"))
			{
				String songName = "";
				System.out.print("Title: ");
				if (scanner.hasNextLine()){
					songName = scanner.nextLine();
				}
				store.getSearchAudio(songName);	//uses store object to access AudioContentStore's method
			}

			else if (action.equalsIgnoreCase("SEARCHA"))
			{
				String creator = "";
				System.out.print("Artist: ");
				if (scanner.hasNextLine()){
					creator = scanner.nextLine();
				}
				store.getSearchArtist(creator);
			}

			else if (action.equalsIgnoreCase("SEARCHG"))
			{
				String genre = "";
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				if (scanner.hasNextLine()){
					genre = scanner.nextLine();
				}
				store.getSearchGenre(genre);
			}
			
			}

			//use RuntimeException in the catch block instead of creating multiple catch blocks 
			//(RuntimeException is the superclass, so due to hierarchy, this will take care of all the newly created exception classes)
			catch(RuntimeException e){	
				System.out.println(e.getMessage());
			}
			
			System.out.print("\n>");
		}
	}
}
