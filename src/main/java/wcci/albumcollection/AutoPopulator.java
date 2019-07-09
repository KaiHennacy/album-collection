package wcci.albumcollection;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import wcci.albumcollection.entities.Album;
import wcci.albumcollection.entities.Artist;
import wcci.albumcollection.entities.Song;
import wcci.albumcollection.repositories.AlbumRepository;
import wcci.albumcollection.repositories.ArtistRepository;
import wcci.albumcollection.repositories.SongRepository;

public class AutoPopulator {

	private static final Random RANDOM = new Random(System.nanoTime());
	private static final Lorem LOREM = LoremIpsum.getInstance();

	public AutoPopulator() {

	}

	public void linkSongsAndArtists(ArtistRepository artistRepo, SongRepository songRepo) {
		Set<Artist> artists = new HashSet<Artist>();
		artistRepo.findAll().iterator().forEachRemaining(artists::add);
//		Set<Song> songs = new HashSet<Song>();
//		songRepo.findAll().iterator().forEachRemaining(songs::add);

		Iterable<Song> songs = songRepo.findAll();

		for (Song song : songs) {
			Set<Artist> artistsSubset = getRandomSubsetOfArtists(artists, 1 + RANDOM.nextInt());
			linkSongWithArtists(artistsSubset, song, artistRepo);
		}
	}

	public void linkSongWithArtists(Set<Artist> artists, Song song, ArtistRepository artistRepo) {
		for (Artist artist : artists) {
			Artist upToDateArtist = artistRepo.findById(artist.getId()).get();
			upToDateArtist.addSong(song);
			artistRepo.save(upToDateArtist);
		}
	}

	public Set<Artist> getRandomSubsetOfArtists(Set<Artist> superSet, int subSetSize) {
		if (subSetSize >= superSet.size()) {
			return superSet;
		} else {
			List<Artist> list = new LinkedList<Artist>(superSet);
			Set<Artist> randomSubSet = new HashSet<Artist>();
			int counter = 0;
			while (!list.isEmpty() && counter < subSetSize) {
				int index = RANDOM.nextInt(list.size());
				randomSubSet.add(list.remove(index));
				counter++;
			}
			return randomSubSet;
		}
	}

	public Set<Song> getRandomSubsetOfSongs(Set<Song> superSet, int subSetSize) {
		if (subSetSize >= superSet.size()) {
			return superSet;
		} else {
			List<Song> list = new LinkedList<Song>(superSet);
			Set<Song> randomSubSet = new HashSet<Song>();
			int counter = 0;
			while (!list.isEmpty() && counter < subSetSize) {
				int index = RANDOM.nextInt(list.size());
				randomSubSet.add(list.remove(index));
				counter++;
			}
			return randomSubSet;
		}
	}

	public void createRandomAlbumsInDatabase(AlbumRepository albumRepo, int albumCount) {
		for (int i = 0; i < albumCount; i++) {
			Album album = generateRandomAlbum();
			albumRepo.save(album);
		}
	}

	public void createRandomArtistsInDatabase(ArtistRepository artistRepo, int artistCount) {
		for (int i = 0; i < artistCount; i++) {
			Artist artist = generateRandomArtist();
			artistRepo.save(artist);
		}
	}

	public void createRandomSongsInDatabase(SongRepository songRepo, int songCount) {
		for (int i = 0; i < songCount; i++) {
			Song song = generateRandomSong();
			songRepo.save(song);
		}
	}

	public Song generateRandomSong() {
		String title = LOREM.getTitle(1 + RANDOM.nextInt(4));
		String linkUrl = LOREM.getUrl();
		String duration = generateRandomDuration();
		Song song = new Song(title, linkUrl, duration);
		return song;
	}

	public Album generateRandomAlbum() {
		String title = LOREM.getTitle(1 + RANDOM.nextInt(6));
		String imgUrl = LOREM.getUrl();
		Album album = new Album(title, imgUrl);
		return album;
	}

	public Artist generateRandomArtist() {
		String name = LOREM.getName();
		String imgUrl = LOREM.getUrl();
		Artist artist = new Artist(name, imgUrl);
		return artist;
	}

	public String generateRandomDuration() {
		int minutes = RANDOM.nextInt(10);
		int seconds = RANDOM.nextInt(60);
		if (minutes == 0 && seconds == 0) {
			minutes = 1;
			seconds = 5;
		}
		String duration;
		if (seconds < 10) {
			duration = minutes + ":0" + seconds;
		} else {
			duration = minutes + ":" + seconds;
		}

		return duration;
	}

}
