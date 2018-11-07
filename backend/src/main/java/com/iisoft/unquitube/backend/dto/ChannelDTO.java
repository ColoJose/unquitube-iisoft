package com.iisoft.unquitube.backend.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * @author aee
 *
 */
@Entity
@Table(name="channel")
public class ChannelDTO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@Column(name="playlist")
	private Set<VideoDTO> playlist;
	private HashSet<String> tags;
	
	
	public ChannelDTO() {
		this.playlist = new HashSet<VideoDTO>();
		this.tags = new HashSet<String>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<VideoDTO> getPlaylist() {
		return playlist;
	}
	public void setPlaylist(Set<VideoDTO> playlist) {
		this.playlist = playlist;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public HashSet<String> getTags() {
		return tags;
	}
	public void setTags(HashSet<String> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((playlist == null) ? 0 : playlist.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
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
		ChannelDTO other = (ChannelDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (playlist == null) {
			if (other.playlist != null)
				return false;
		} else if (!playlist.equals(other.playlist))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChannelDTO [id=" + id + ", name=" + name + ", playlist=" + playlist + ", tags=" + tags + "]";
	}
	
}
