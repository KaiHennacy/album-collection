package wcci.albumcollection.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Artist {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;

}
