package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TVShow {
  private String name;
  private String imagePath;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("backdrop_path")
  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }
}
