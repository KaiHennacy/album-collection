package wcci.albumcollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

//	@Autowired
//	private DefaultPopulator populator;

	@Override
	public void run(String... args) throws Exception
		{
		System.out.println("RUNNING INITIALIZER");
//		this.makeArtists("Frank Zappa", "Dolly Parton", "James Brown");
//		populateDatabaseWithEntities();
		}

//	private void makeArtists(String... names) {
//		for(String name : names) {
//			Artist newArtist = new Artist(name);
//			artistRepo.save(newArtist);
//		}
//	}
//	private void makeAlbums(String... titles) {
//		for(String title : titles) {
//			Album 
//			artistRepo.save(newArtist);
//		}
//	}
	
	

//	private void populateDatabaseWithEntities()
//		{
//		System.out.println("Populating database with entities...");
//		int numberOfArtistsToCreate = 10;
//		int numberOfAlbumsToCreate = 20;
//		int numberOfSongsToCreate = 100;
//		long timeBeforeProcess = System.nanoTime();
//		populator.createRandomArtistsInDatabase(numberOfArtistsToCreate);
//		populator.createRandomAlbumsInDatabase(numberOfAlbumsToCreate);
//		populator.createRandomSongsInDatabase(numberOfSongsToCreate);
//		System.out.println("Randomly Linking songs and artists...");
//		populator.linkAllSongsToRandomArtists();
//		System.out.println("Randomly Linking songs and albums...");
//		populator.linkAllAlbumsToRandomSongs();
//		System.out.println("Randomly Linking albums and albums...");
//		populator.linkAllAlbumsToRandomArtists();
//		long timePostProcess = System.nanoTime();
//		long timeDifference = timePostProcess - timeBeforeProcess;
//		double convertedTime = 1.0 * timeDifference / 1000000000.0;
//		System.out.println("Complete!");
//		System.out.println("The process took " + convertedTime + " seconds.");
//		}
}
