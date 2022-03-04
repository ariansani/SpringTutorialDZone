package com.example.SpringbootTutorialDzone.repository;

import java.util.Map;

import com.example.SpringbootTutorialDzone.model.Movie;

public interface RedisRepository {
    Map<Object, Object> findAllMovies();
    
    void add(Movie movie);
    
    void delete(String id);

    Movie findMovie(String id);
}
