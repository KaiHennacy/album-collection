package wcci.albumcollection.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Song {

	@Id
	@GeneratedValue
	private Long id;

	private String title;
	private String linkUrl;
	private String duration;

//	@ManyToMany(mappedBy = "songs", fetch = FetchType.EAGER)
//	private Set<Artist> artists = new HashSet<Artist>();

	@ManyToOne
//	(fetch = FetchType.EAGER)
	private Album album;

	protected Song()
		{

		}

	public Song(String title, String linkUrl, String duration, Album album)
		{
		this.title = title;
		this.linkUrl = linkUrl;
		this.duration = duration;
		this.album = album;
		}

	public String toString()
		{
		return title + " - " + duration + " - " + linkUrl;
		}

	public String getTitle()
		{
		return title;
		}

	public String getLinkUrl()
		{
		return linkUrl;
		}

	public String getDuration()
		{
		return duration;
		}

	public Long getId()
		{
		return id;
		}

//	public Set<Artist> getArtists()
//		{
//		return artists;
//		}

	public Album getAlbum()
		{
		return album;
		}

	@Override
	public int hashCode()
		{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
		}

	@Override
	public boolean equals(Object obj)
		{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Song other = (Song) obj;
		if (id == null)
			{
			if (other.id != null)
				return false;
			}
		else if (!id.equals(other.id))
			return false;
		return true;
		}

//	public void addArtist(Artist artist)
//		{
//		artists.add(artist);
//		artist.getSongs().add(this);
//		}

//	public void addAlbum(Album album)
//		{
//		albums.add(album);
//		album.getSongs().add(this);
//		}

}
