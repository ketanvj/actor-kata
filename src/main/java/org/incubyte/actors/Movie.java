package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.context.annotation.Value;

public class Movie {

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private String title;

  public void setName(String title) {

    this.title = title;
  }

  public String getGetImagePath() {
    return getImagePath;
  }

  public void setGetImagePath(String getImagePath) {
    this.getImagePath = getImagePath;
  }
@JsonProperty("backdrop_path")
  public String getImagePath;

}
