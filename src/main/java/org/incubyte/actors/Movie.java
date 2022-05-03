package org.incubyte.actors;

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
}
