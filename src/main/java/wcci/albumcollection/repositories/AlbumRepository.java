package wcci.albumcollection.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import wcci.albumcollection.entities.Album;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long>{

}
