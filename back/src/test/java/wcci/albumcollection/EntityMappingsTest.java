package wcci.albumcollection;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

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
	private Album testAlbum = new Album("test-title", "testImg.com", testArtist);
	private Song testSong = new Song("test-title", "test-link.com", "00:00", testAlbum);

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

		Artist foundArtist = artistRepo.findById(testArtist.getId()).get();
		assertThat(foundArtist, is(testArtist));
	}

	@Test
	public void shouldSaveAndLoadAnSong() {

		Song foundSong = songRepo.findById(testSong.getId()).get();
		assertThat(foundSong, is(testSong));
	}

	@Test
	public void shouldSaveAndLoadAnAlbum() {

		Album foundAlbum = albumRepo.findById(testAlbum.getId()).get();
		assertThat(foundAlbum, is(testAlbum));
	}

	@Test
	public void artistShouldHaveCollectionOfAlbums() {
		testArtist = artistRepo.findById(testArtist.getId()).get();

		Set<Album> foundAlbums = testArtist.getAlbums();
		assertThat(foundAlbums, containsInAnyOrder(testAlbum));
		assertThat(foundAlbums.size(), is(1));

		Album testAlbum2 = new Album("test-title2", "testImg2.com", testArtist);
		albumRepo.save(testAlbum2);

		entityManager.flush();
		entityManager.clear();

		testArtist = artistRepo.findById(testArtist.getId()).get();
		foundAlbums = testArtist.getAlbums();

		for (Album albm : foundAlbums) {
			System.out.println(albm.getTitle());
		}

		assertThat(foundAlbums, containsInAnyOrder(testAlbum, testAlbum2));
		assertThat(foundAlbums.size(), is(2));
	}

	@Test
	public void albumShouldHaveCollectionOfSongs() {
		testAlbum = albumRepo.findById(testAlbum.getId()).get();

		Set<Song> foundSongs = testAlbum.getSongs();
		assertThat(foundSongs, containsInAnyOrder(testSong));
		assertThat(foundSongs.size(), is(1));

		Song testSong2 = new Song("test-title2", "test-link2.com", "00:00", testAlbum);
		songRepo.save(testSong2);

		entityManager.flush();
		entityManager.clear();

		testAlbum = albumRepo.findById(testAlbum.getId()).get();
		foundSongs = testAlbum.getSongs();

		assertThat(foundSongs, containsInAnyOrder(testSong, testSong2));
		assertThat(foundSongs.size(), is(2));
	}

	@Test
	public void albumShouldHaveOneArtist() {
		testAlbum = albumRepo.findById(testAlbum.getId()).get();
		Artist foundArtist = testAlbum.getArtist();

		testArtist = artistRepo.findById(testArtist.getId()).get();
		assertThat(foundArtist, is(testArtist));
	}

	@Test
	public void songShouldHaveOneAlbum() {
		testSong = songRepo.findById(testSong.getId()).get();
		Album foundAlbum = testSong.getAlbum();

		testAlbum = albumRepo.findById(testAlbum.getId()).get();
		assertThat(foundAlbum, is(testAlbum));
	}
	
	@Test
	public void canCreateArtistWithDefaultAvatar() {
		Artist defAvatarArtist = new Artist("First Last");
		artistRepo.save(defAvatarArtist);
		
		String foundImgUrl = artistRepo.findById(defAvatarArtist.getId()).get().getImgUrl();
		assertThat(foundImgUrl, is("defaultImg.png"));
	}
	
	@Test
	public void canCreateAlbumWithDefaultCoverImg() {
		testArtist = artistRepo.findById(testArtist.getId()).get();
		Album defCoverAlbum = new Album("defCover", testArtist);
		albumRepo.save(defCoverAlbum);
		
		String foundImgUrl = albumRepo.findById(defCoverAlbum.getId()).get().getImgUrl();
		assertThat(foundImgUrl, is("defaultImg.png"));		
	}
	
	@Test
	public void canCreateSongWithDefaultLinkAndDuration() {
		testAlbum = albumRepo.findById(testAlbum.getId()).get();
		Song defSong = new Song("defSong", testAlbum);
		songRepo.save(defSong);
		
		String foundLink = songRepo.findById(defSong.getId()).get().getLinkUrl();
		assertThat(foundLink, is("defaultLink.com"));	
		
		String foundDuration = songRepo.findById(defSong.getId()).get().getDuration();
		assertThat(foundDuration, is("00:00"));
	}
	
	@Test
	public void canCreateAlbumWithArtistNameParamAsString() {
		NameHandler nh = new NameHandler(artistRepo, albumRepo, songRepo);
		Album newAlbum = new Album("defCover", nh.getArtistOfName("Test-Name"));
		albumRepo.save(newAlbum);
		
		entityManager.flush();
		entityManager.clear();
		
		testArtist = artistRepo.findById(testArtist.getId()).get();
		Set<Album> foundAlbums = testArtist.getAlbums();
		assertThat(foundAlbums.size(), is(2));
		
		Artist foundArtistByAlbum = albumRepo.findById(newAlbum.getId()).get().getArtist();
		Artist foundArtistByName = artistRepo.findByName(newAlbum.getArtist().getName());
		
		assertThat(foundArtistByName, is(foundArtistByAlbum));
	}
	
	@Test
	public void canCreateSongWithAlbumTitleParamAsString() {
		NameHandler nh = new NameHandler(artistRepo, albumRepo, songRepo);
		Song newSong = new Song("defCover", nh.getAlbumOfName("test-title"));
		songRepo.save(newSong);
		
		entityManager.flush();
		entityManager.clear();
		
		testAlbum = albumRepo.findById(testAlbum.getId()).get();
		Set<Song> foundSongs = testAlbum.getSongs();
		assertThat(foundSongs.size(), is(2));
		
		Album foundAlbumBySong = songRepo.findById(newSong.getId()).get().getAlbum();
		Album foundAlbumByTitle = albumRepo.findByTitle(newSong.getAlbum().getTitle());
		
		assertThat(foundAlbumByTitle, is(foundAlbumBySong));
	}
	
	@Test
	public void canAutoCreateArtistWhenCreatingAlbum() {
		NameHandler nh = new NameHandler(artistRepo, albumRepo, songRepo);
		Album newAlbum = new Album("defCover", nh.getArtistOfName("notYetCreated"));
		albumRepo.save(newAlbum);
		
		entityManager.flush();
		entityManager.clear();
		
		Artist autoCreatedArtist = artistRepo.findByName(newAlbum.getArtist().getName());
		Set<Album> foundAlbums = autoCreatedArtist.getAlbums();
		assertThat(foundAlbums.size(), is(1));
		
		Artist foundArtistByAlbum = albumRepo.findById(newAlbum.getId()).get().getArtist();
		Artist foundArtistByName = autoCreatedArtist;
		
		assertThat(foundArtistByName, is(foundArtistByAlbum));
	}
	
	@Test
	public void canAutoCreateAlbumAndUnknownArtistWhenCreatingSong() {
		NameHandler nh = new NameHandler(artistRepo, albumRepo, songRepo);
		Song newSong = new Song("defSong", nh.getAlbumOfName("notYetCreated"));
		songRepo.save(newSong);
		
		entityManager.flush();
		entityManager.clear();
		
		Album autoCreatedAlbum = albumRepo.findByTitle(newSong.getAlbum().getTitle());
		Set<Song> foundSongs = autoCreatedAlbum.getSongs();
		assertThat(foundSongs.size(), is(1));
		
		Album foundAlbumBySong = songRepo.findById(newSong.getId()).get().getAlbum();
		Album foundAlbumByTitle = albumRepo.findByTitle(newSong.getAlbum().getTitle());
		
		assertThat(foundAlbumByTitle, is(foundAlbumBySong));
		
		Artist unknownArtist = foundAlbumBySong.getArtist();
		
		assertThat(unknownArtist.getName(), is("unknown"));
	}
	
	@Test
	public void canAutoCreateAlbumAndNewNamedArtistWhenCreatingSong() {
		NameHandler nh = new NameHandler(artistRepo, albumRepo, songRepo);
		Song newSong = new Song("defSong", nh.getAlbumAndArtistOfNames("albumNotYetCreated", "artistNotYetCreated"));
		songRepo.save(newSong);
		
		entityManager.flush();
		entityManager.clear();
		
		Album autoCreatedAlbum = albumRepo.findByTitle(newSong.getAlbum().getTitle());
		Set<Song> foundSongs = autoCreatedAlbum.getSongs();
		assertThat(foundSongs.size(), is(1));
		
		Album foundAlbumBySong = songRepo.findById(newSong.getId()).get().getAlbum();
		Album foundAlbumByTitle = albumRepo.findByTitle(newSong.getAlbum().getTitle());
		
		assertThat(foundAlbumByTitle, is(foundAlbumBySong));
		
		Artist namedArtist = foundAlbumBySong.getArtist();
		
		assertThat(namedArtist.getName(), is("artistNotYetCreated"));
	}
	
	@Test
	public void canCreateSongWithNewAlbumAndExistingArtist() {
		NameHandler nh = new NameHandler(artistRepo, albumRepo, songRepo);
		Song newSong = new Song("defSong", nh.getAlbumAndArtistOfNames("notYetCreated", "Test-Name"));
		songRepo.save(newSong);
		
		entityManager.flush();
		entityManager.clear();
		
		Album autoCreatedAlbum = albumRepo.findByTitle(newSong.getAlbum().getTitle());
		Set<Song> foundSongs = autoCreatedAlbum.getSongs();
		assertThat(foundSongs.size(), is(1));
		
		Album foundAlbumBySong = songRepo.findById(newSong.getId()).get().getAlbum();
		Album foundAlbumByTitle = albumRepo.findByTitle(newSong.getAlbum().getTitle());
		
		assertThat(foundAlbumByTitle, is(foundAlbumBySong));
		
		Artist ExistingArtist = foundAlbumBySong.getArtist();
		
		assertThat(ExistingArtist.getName(), is("Test-Name"));
	}
	
	@Test
	public void canChangeUnknownArtistToExistingArtist() {
		NameHandler nh = new NameHandler(artistRepo, albumRepo, songRepo);
		Song newSong = new Song("defSong", nh.getAlbumOfName("notYetCreated"));
		songRepo.save(newSong);
		
		entityManager.flush();
		entityManager.clear();
		
		Album autoCreatedAlbum = albumRepo.findByTitle(newSong.getAlbum().getTitle());
		
		assertThat(autoCreatedAlbum.getArtist().getName(), is("unknown"));
		
		autoCreatedAlbum.setArtist(nh.getArtistOfName("Test-Name"));
		albumRepo.save(autoCreatedAlbum);
		
		entityManager.flush();
		entityManager.clear();
		
		testArtist = artistRepo.findById(testArtist.getId()).get();
		Artist foundArtistBySong = songRepo.findById(newSong.getId()).get().getAlbum().getArtist();
		
		assertThat(testArtist, is(foundArtistBySong));
	}
	
}
