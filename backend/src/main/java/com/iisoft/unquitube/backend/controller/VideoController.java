package com.iisoft.unquitube.backend.controller;

import com.iisoft.unquitube.backend.dto.VideoDTO;
import com.iisoft.unquitube.backend.model.Video;
import com.iisoft.unquitube.backend.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/video")
    public boolean createVideo(@RequestBody @Valid VideoDTO userDTO){
        return videoService.create(userDTO);
    }

    @GetMapping("/videos")
    public List<Video> getVideos(){
        return videoService.get();
    }

    @GetMapping("/video/{id}")
    public Video getVideo(@PathVariable("id") Integer id){
        return videoService.findById(id);
    }

    @DeleteMapping("/video/{id}")
    public Boolean deleteVideo(@PathVariable("id") Integer id){
        return videoService.delete(id);
    }
}
