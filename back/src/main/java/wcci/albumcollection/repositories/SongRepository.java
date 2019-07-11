package wcci.albumcollection.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wcci.albumcollection.entities.Artist;
import wcci.albumcollection.entities.Song;

@Repository
public interface SongRepository extends CrudRepository<Song, Long>{

}
