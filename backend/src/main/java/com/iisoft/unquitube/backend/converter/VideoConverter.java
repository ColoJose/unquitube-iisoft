package com.iisoft.unquitube.backend.converter;

import com.iisoft.unquitube.backend.dto.VideoDTO;
import com.iisoft.unquitube.backend.model.Video;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("VideoConverter")
public class VideoConverter {
    public List<Video> convertUsers(List<VideoDTO> videoDTOS){
        List<Video> videos = new ArrayList<>();

        videoDTOS.forEach(v -> videos.add(new Video(v)));

        return videos;
    }
}
