package com.example.demo.services;

import com.example.demo.repositories.VideoRepository;
import com.example.demo.entities.Video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

	@Autowired
	private VideoRepository videoRepository;

	public VideoService(VideoRepository videoRepository){
		this.videoRepository = videoRepository;
	}

	public List<Video> getAllVideos(){
		return videoRepository.findAll();
	}

	public Optional<Video> getVideoById(Long id){
		return videoRepository.findById(id);
	}

	public Video createVideo(Video video){
		videoRepository.save(video);
		return video;
	}

	public void deleteVideo(Long id) {
		videoRepository.deleteById(id);
	}
}