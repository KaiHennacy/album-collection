package wcci.albumcollection.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Album {

	@Id
	@GeneratedValue
	private Long id;

	private String title;
	private String imgUrl;

	 @ManyToMany(mappedBy = "albums")
	 private Set<Artist> artists = new HashSet<Artist>();

	 @ManyToMany(mappedBy = "albums")
	 private Set<Song> songs = new HashSet<Song>();

	protected Album() {

	}

	public Album(String title, String imgUrl) {
		this.title = title;
		this.imgUrl = imgUrl;
	}

	public Set<Artist> getArtists() {
		return artists;
	}

	public Set<Song> getSongs() {
		return songs;
	}

	public String getTitle() {
		return title;
	}

	public Long getId() {
		return id;
	}

	public String getImgUrl() {
		return imgUrl;
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
		Album other = (Album) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
