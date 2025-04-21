package Client.viewmodel;

import Server.model.User;
import Server.model.Vinyl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.UUID;

public class VinylDisplay {
  private final String title;
  private final String artist;
  private final String releaseYear;
  private final String status;
  private final String borrowedBy;
  private final String reservedBy;
  private final String markedForRemoval;
  private final String id;
  private StringProperty state;

  public VinylDisplay(Vinyl vinyl, List<User> users) {
    this.title = vinyl.getTitle();
    this.artist = vinyl.getArtist();
    this.releaseYear = String.valueOf(vinyl.getReleaseYear());
    this.status = vinyl.getStateString();

    this.state = new SimpleStringProperty(vinyl.getCurrentStateName());

    this.borrowedBy = resolveUserName(users, vinyl.getBorrowedBy(), "Not borrowed");
    this.reservedBy = resolveUserName(users, vinyl.getReservedBy(), "Not reserved");

    this.markedForRemoval = vinyl.isMarkedForRemoval() ? "Yes" : "No";
    this.id = vinyl.getId();
  }
  public void updateState(String newState) {
    state.set(newState);
  }

  public StringProperty stateProperty() {
    return state;
  }

  public String getState() {
    return state.get();
  }


  public String getTitle() {
    return title;
  }

  public String getArtist() {
    return artist;
  }

  public String getReleaseYear() {
    return releaseYear;
  }

  public String getStatus() {
    return status;
  }

  public String getBorrowedBy() {
    return borrowedBy;
  }

  public String getReservedBy() {
    return reservedBy;
  }

  public String getMarkedForRemoval() {
    return markedForRemoval;
  }

  public String getId() {
    return id;
  }



  private String resolveUserName(List<User> users, Integer userId, String defaultMessage) {
    if (userId == null) return defaultMessage;

    for (User user : users) {
      if (user.getId() == userId) {
        return user.getName();
      }
    }

    return "Unknown user (ID: " + userId + ")";
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getTitle()).append(" - ").append(getArtist())
        .append(" (").append(getReleaseYear()).append(") [").append(getStatus()).append("]");

    if (!"Not borrowed".equals(borrowedBy)) {
      sb.append(" | Borrowed by: ").append(borrowedBy);
    }

    if (!"Not reserved".equals(reservedBy)) {
      sb.append(" | Reserved by: ").append(reservedBy);
    }

    return sb.toString();
  }

}

