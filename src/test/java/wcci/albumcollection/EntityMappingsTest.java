package wcci.albumcollection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import wcci.albumcollection.entities.Album;
import wcci.albumcollection.entities.Artist;
import wcci.albumcollection.entities.Song;
import wcci.albumcollection.repositories.AlbumRepository;
import wcci.albumcollection.repositories.ArtistRepository;
import wcci.albumcollection.repositories.SongRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EntityMappingsTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	AlbumRepository albumRepo;
	
	@Autowired
	ArtistRepository artistRepo;
	
	@Autowired
	SongRepository songRepo;
	
	private Artist testArtist = new Artist("Test-Name", "testImg.com");
	private Song testSong = new Song("test-title", "test-link.com", "00:00");
	private Album testAlbum = new Album("test-title", "testImg.com");
	
	@Before
	public void saveTestEntitiesToRepos() {
		artistRepo.save(testArtist);
		albumRepo.save(testAlbum);
		songRepo.save(testSong);
		
		entityManager.flush();
		entityManager.clear();
	}
	
	@Test
	public void shouldSaveAndLoadAnArtist() {
//		artistRepo.save(testArtist);
//		entityManager.persist(testArtist);
//		entityManager.flush();
		
		Artist foundArtist = artistRepo.findById(testArtist.getId()).get();
		assertThat(foundArtist, is(testArtist));
	}
	
	@Test
	public void shouldSaveAndLoadAnSong() {
//		songRepo.save(testSong);
//		entityManager.persist(testSong);
//		entityManager.flush();
		
		Song foundSong = songRepo.findById(testSong.getId()).get();
		assertThat(foundSong, is(testSong));
	} 
	
	@Test
	public void shouldSaveAndLoadAnAlbum() {
//		albumRepo.save(testAlbum);
//		entityManager.persist(testAlbum);
//		entityManager.flush();
		
		Album foundAlbum = albumRepo.findById(testAlbum.getId()).get();
		assertThat(foundAlbum, is(testAlbum));
	} 
	
	@Test
	public void songShouldHaveACollectionOfArtists() {
		Song songFromRepo = songRepo.findById(testSong.getId()).get();
		
		songFromRepo.addArtist(testArtist);
		songRepo.save(songFromRepo);
		entityManager.flush();
		entityManager.clear();
		
//		Song loadedSong = songRepo.findById(testSong.getId()).get();
		
		
	}
	

}
