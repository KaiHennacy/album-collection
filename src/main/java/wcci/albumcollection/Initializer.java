package wcci.albumcollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import wcci.albumcollection.entities.Album;
import wcci.albumcollection.entities.Artist;
import wcci.albumcollection.entities.Song;
import wcci.albumcollection.repositories.AlbumRepository;
import wcci.albumcollection.repositories.ArtistRepository;
import wcci.albumcollection.repositories.SongRepository;

@Component
public class Initializer implements CommandLineRunner {

	@Autowired
	AlbumRepository albumRepo;

	@Autowired
	ArtistRepository artistRepo;

	@Autowired
	SongRepository songRepo;

	private AutoPopulator populator = new AutoPopulator();

	@Override
	public void run(String... args) throws Exception {
		System.out.println("RUNNING INITIALIZER");

//		Artist testArtist = new Artist("Test-Name", "testImg.com");
//		Song testSong = new Song("test-title", "test-link.com", "00:00");
//		Album testAlbum = new Album("test-title", "testImg.com");
//		
//		albumRepo.save(testAlbum);
//		songRepo.save(testSong);
//		artistRepo.save(testArtist);
//
//		Song songFromRepo = songRepo.findById(testSong.getId()).get();
//		Song song2 = new Song("Poop and Pee", "link", "duration");
//		songRepo.save(song2);
//
//		Artist artistFromRepo = artistRepo.findById(testArtist.getId()).get();
//
//		artistFromRepo.addSong(songFromRepo);
//
//		artistFromRepo.addSong(song2);
//		artistRepo.save(artistFromRepo);

//		Song song = populator.generateRandomSong();
//		System.out.println(song);
//
//		Album album = populator.generateRandomAlbum();
//		System.out.println(album);
//
//		Artist artist = populator.generateRandomArtist();
//		System.out.println(artist);

		populator.createRandomSongsInDatabase(songRepo, 10);
		populator.createRandomAlbumsInDatabase(albumRepo, 2);
		populator.createRandomArtistsInDatabase(artistRepo, 3);
		
		populator.linkSongsAndArtists(artistRepo, songRepo);

	}

}