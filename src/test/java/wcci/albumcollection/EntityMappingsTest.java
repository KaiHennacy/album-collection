package wcci.albumcollection;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import wcci.albumcollection.entities.Artist;
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
	
	@Test
	public void shouldSaveAndLoadAnArtist() {
		Artist testArtist = new Artist("Test Name", "testImg.com");
		artistRepo.save(testArtist);
		entityManager.persist("Test Name");
		entityManager.flush();
		
		Artist foundArtist = artistRepo.findById(testArtist.getId()).get();
		// assertThat(foundArtist, is(testArtist));
		
		
		
		
	}
	
	

}
