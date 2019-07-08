package wcci.albumcollection.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Song {

	@Id
	@GeneratedValue
	private Long id;

	private String title;
	private String linkUrl;
	private String duration;

	// @ManyToMany(mappedBy = "songs")
	// private Set<Artist> artists = new HashSet<Artist>();

	// @ManyToMany
	// private Set<Album> albums = new HashSet<Album>();

	protected Song() {

	}
//
//	public Song(String title, String linkUrl, String duration, Set<Artist> artists, Set<Album> albums) {
//		this.title = title;
//		this.linkUrl = linkUrl;
//		this.duration = duration;
//		this.artists = artists;
//		this.albums = albums;
//	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLinkUrl() {
		return linkUrl;
	}
	
	public String getDuration() {
		return duration;
	}
	
//	public Set<Artist> getArtists(){
//		return artists;
//	}
//	
//	public Set<Album> getAlbums(){
//		return albums;
//	}

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
		Song other = (Song) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
