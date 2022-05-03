package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.context.annotation.Value;

import java.util.List;

public class MovieResult {
  public List<Movie> getMovies() {
    return movies;
  }

  public void setMovies(List<Movie> movies) {
    this.movies = movies;
  }

  @JsonProperty("cast")
  List<Movie> movies;


}
