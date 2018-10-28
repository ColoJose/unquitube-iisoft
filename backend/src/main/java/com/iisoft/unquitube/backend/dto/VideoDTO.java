package com.iisoft.unquitube.backend.dto;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Table(name="VIDEO")
@Entity
@XmlRootElement
public class VideoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTOR")
    private String autor;

    @Column(name = "URL")
    private String url;

    public VideoDTO(){

    }

    public VideoDTO(String name, String autor, String url){
        this.name = name;
        this.autor = autor;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
