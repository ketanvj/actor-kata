package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TvResult {

  public List<TVShow> getTvShows() {
    return tvShows;
  }

  public void setTvShows(List<TVShow> tvShows) {
    this.tvShows = tvShows;
  }

  @JsonProperty("cast")
  List<TVShow> tvShows;


}
