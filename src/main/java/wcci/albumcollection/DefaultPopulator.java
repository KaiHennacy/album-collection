package wcci.albumcollection;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import wcci.albumcollection.entities.Album;
import wcci.albumcollection.entities.Artist;
import wcci.albumcollection.entities.Song;
import wcci.albumcollection.repositories.AlbumRepository;
import wcci.albumcollection.repositories.ArtistRepository;
import wcci.albumcollection.repositories.SongRepository;

@Component
public class DefaultPopulator implements CommandLineRunner {

	private static final Random RANDOM = new Random(System.nanoTime());
	private static final Lorem LOREM = LoremIpsum.getInstance();

	private static ArrayList<Long> albumIds = new ArrayList<Long>();
	private static ArrayList<Long> artistIds = new ArrayList<Long>();
	private static ArrayList<Long> songIds = new ArrayList<Long>();

	@Autowired
	AlbumRepository albumRepo;

	@Autowired
	ArtistRepository artistRepo;

	@Autowired
	SongRepository songRepo;

	protected DefaultPopulator()
		{
		}

	@Override
	public void run(String... args) throws Exception
		{
		}

	public void linkAllAlbumsToRandomArtists()
		{
		Iterable<Album> albums = albumRepo.findAll();
		for (Album album : albums)
			{
			linkAlbumToRandomArtists(album);
			}
		}

	public void linkAlbumToRandomArtists(Album album)
		{
		int numberOfArtists = 1;
		if (RANDOM.nextInt(10) > 8)
			{
			numberOfArtists++;
			if (RANDOM.nextInt(10) > 7)
				{
				numberOfArtists++;
				if (RANDOM.nextInt(10) > 6)
					{
					numberOfArtists++;
					}
				}
			}
		for (int i = 0; i < numberOfArtists; i++)
			{
			Artist artist = getRandomArtistFromDatabase();
			artist.addAlbum(album);
			artistRepo.save(artist);
			}
		}

	public void linkAllAlbumsToRandomSongs()
		{
		Iterable<Album> albums = albumRepo.findAll();
		for (Album album : albums)
			{
			linkAlbumToRandomSongs(album);
			}
		}

	public void linkAlbumToRandomSongs(Album album)
		{
		int numberOfSongs = 1 + RANDOM.nextInt(albumIds.size()) + (songIds.size() / (1 + albumIds.size()));
		for (int i = 0; i < numberOfSongs; i++)
			{
			Song song = getRandomSongFromDatabase();
			song.addAlbum(album);
			songRepo.save(song);
			}
		}

	public void linkAllSongsToRandomArtists()
		{
		Iterable<Song> songs = songRepo.findAll();
		for (Song song : songs)
			{
			linkSongToRandomArtists(song);
			}
		}

	public void linkSongToRandomArtists(Song song)
		{
		int numberOfArtists = 1;
		if (RANDOM.nextInt(10) > 6)
			{
			numberOfArtists++;
			if (RANDOM.nextInt(10) > 5)
				{
				numberOfArtists++;
				if (RANDOM.nextInt(10) > 4)
					{
					numberOfArtists++;
					}
				}
			}
		for (int i = 0; i < numberOfArtists; i++)
			{
			Artist artist = getRandomArtistFromDatabase();
			artist.addSong(song);
			artistRepo.save(artist);
			}
		}

	public Song getRandomSongFromDatabase()
		{
		int index = RANDOM.nextInt(songIds.size());
		Long id = songIds.get(index);
		Song selectedSong = songRepo.findById(id).get();
		return selectedSong;
		}

	public Artist getRandomArtistFromDatabase()
		{
		int index = RANDOM.nextInt(artistIds.size());
		Long id = artistIds.get(index);
		Artist selectedArtist = artistRepo.findById(id).get();
		return selectedArtist;
		}

	public Album getRandomAlbumFromDatabase()
		{
		int index = RANDOM.nextInt(albumIds.size());
		Long id = albumIds.get(index);
		Album selectedAlbum = albumRepo.findById(id).get();
		return selectedAlbum;
		}

	public void createRandomAlbumsInDatabase(int albumCount)
		{
		for (int i = 0; i < albumCount; i++)
			{
			Album album = generateRandomAlbum();
			albumRepo.save(album);
			albumIds.add(album.getId());
			}
		}

	public void createRandomArtistsInDatabase(int artistCount)
		{
		for (int i = 0; i < artistCount; i++)
			{
			Artist artist = generateRandomArtist();
			artistRepo.save(artist);
			artistIds.add(artist.getId());
			}
		}

	public void createRandomSongsInDatabase(int songCount)
		{
		for (int i = 0; i < songCount; i++)
			{
			Song song = generateRandomSong();
			songRepo.save(song);
			songIds.add(song.getId());
			}
		}

	public Song generateRandomSong()
		{
		String title = LOREM.getTitle(1 + RANDOM.nextInt(4));
		String linkUrl = LOREM.getUrl();
		String duration = generateRandomDuration();
		Song song = new Song(title, linkUrl, duration);
		return song;
		}

	public Album generateRandomAlbum()
		{
		String title = LOREM.getTitle(1 + RANDOM.nextInt(6));
		String imgUrl = LOREM.getUrl();
		Album album = new Album(title, imgUrl);
		return album;
		}

	public Artist generateRandomArtist()
		{
		String name = LOREM.getName();
		String imgUrl = LOREM.getUrl();
		Artist artist = new Artist(name, imgUrl);
		return artist;
		}

	public String generateRandomDuration()
		{
		int minutes = RANDOM.nextInt(10);
		int seconds = RANDOM.nextInt(60);
		if (minutes == 0 && seconds == 0)
			{
			minutes = 1;
			seconds = 5;
			}
		String duration;
		if (seconds < 10)
			{
			duration = minutes + ":0" + seconds;
			}
		else
			{
			duration = minutes + ":" + seconds;
			}

		return duration;
		}

}
