package com.iisoft.unquitube.backend.model;

import com.iisoft.unquitube.backend.dto.VideoDTO;

public class Video {
    private Integer id;
    private String name;
    private String autor;
    private String url;

    public Video(){

    }

    public Video(String name, String autor, String url){
        this.name = name;
        this.autor = autor;
        this.url = url;
    }

    public Video(VideoDTO videoDTO){
        this.id = videoDTO.getId();
        this.name = videoDTO.getName();
        this.autor = videoDTO.getAutor();
        this.url = videoDTO.getUrl();
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
