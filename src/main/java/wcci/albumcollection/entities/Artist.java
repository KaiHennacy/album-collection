package wcci.albumcollection.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Artist {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String imgUrl;
	
	@ManyToMany
	private Set<Song> songs = new HashSet<Song>();
	
	@ManyToMany
	private Set<Album> albums = new HashSet<Album>();
	
	protected Artist(){
		
	}
	
	public Artist(String name, String imgUrl) {
		this.name = name;
		this.imgUrl=imgUrl;
	}
	
	public Set<Song> getSongs(){
		return songs;
	}
	
	public Set<Album> getAlbums(){
		return albums;
	}
	
	public String getName() {
		return name;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void addSong(Song songToAdd) {
		this.songs.add(songToAdd);
	}
	
	public void addAlbum(Album albumToAdd) {
		this.albums.add(albumToAdd);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artist other = (Artist) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
