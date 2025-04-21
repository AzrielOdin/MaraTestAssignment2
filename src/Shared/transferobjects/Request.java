package Shared.transferobjects;

import Server.model.User;
import Server.model.Vinyl;

import java.io.Serializable;

public class Request implements Serializable {
  private final String type;
  private User user;
  private Vinyl vinyl;
  private String title;
  private String artist;
  private int year;

  public Request(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public String getTitle() { return title; }
  public String getArtist() { return artist; }
  public int getYear() { return year; }


  public void setVinyl(Vinyl vinyl) {
    this.vinyl = vinyl;
  }

  public Vinyl getVinyl() {
    return vinyl;
  }

  // Optional: use if you want to send title/artist/year separately (for addVinyl from fields)
  public void setVinylInfo(String title, String artist, int year) {
    this.title = title;
    this.artist = artist;
    this.year = year;
  }



}
