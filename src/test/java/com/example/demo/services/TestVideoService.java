package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.entities.Video;
import com.example.demo.repositories.VideoRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

// TDD is a robust way of designing software components (“units”) 
// interactively so that their behaviour is specified through unit tests.
public class TestVideoService {
    private VideoService videoService;
    @Mock private VideoRepository videoRepository;
    @Rule public MockitoRule mRule = MockitoJUnit.rule();

    @Before
    public void setUp(){
        this.videoService = new VideoService(videoRepository);
    }

    @Test
    public void getAllVideos_ReturnsAllListOfVideos() throws Exception {
        // AAA Pattern can describe into 3 parts.
        // Arrange
        List<Video> expectedObject = new ArrayList<Video>();
        when(this.videoRepository.findAll()).thenReturn(expectedObject);
        // Act
        List<Video> actualObject = this.videoService.getAllVideos();
        // Assert
        assertEquals(expectedObject, actualObject, "is the same");
        verify(this.videoRepository, times(1)).findAll();
    }

    @Test
    public void testFindByIdNoData() throws Exception {
        when(this.videoRepository.findById(1L)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), this.videoService.getVideoById(1L), "tested");
    }

    @Test
    public void testFindByIdHasData() throws Exception {
        Video expectedObject = new Video();
        when(this.videoRepository.findById(1L)).thenReturn(Optional.of(expectedObject));
        assertEquals(expectedObject, this.videoService.getVideoById(1L).get(), "tested");
    }

}