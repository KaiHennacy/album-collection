package wcci.albumcollection.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wcci.albumcollection.entities.Artist;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long>{

}
