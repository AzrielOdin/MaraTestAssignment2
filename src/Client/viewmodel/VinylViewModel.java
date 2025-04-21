package Client.viewmodel;

import Shared.interfaces.Model;
import Server.model.User;
import Server.model.Vinyl;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class VinylViewModel {
  private final Model model;
  private final ObservableList<VinylDisplay> vinylDisplays = FXCollections.observableArrayList();
  private final StringProperty statusMessage = new SimpleStringProperty();

  public VinylViewModel(Model model) {
    this.model = model;
    refreshDisplayList();
  }

  public ObservableList<VinylDisplay> getVinylDisplays() {
    return vinylDisplays;
  }

  public StringProperty statusMessageProperty() {
    return statusMessage;
  }

  public void setStatusMessage(String message) {
    Platform.runLater(() -> statusMessage.set(message));
  }

  public void updateVinyls() {
    refreshDisplayList();
  }

  private void refreshDisplayList() {
    List<User> users = model.getUsers();
    List<Vinyl> vinyls = model.getVinyls();

    vinylDisplays.clear();
    for (Vinyl v : vinyls) {
      vinylDisplays.add(new VinylDisplay(v, users));
    }
  }

  public void addVinyl(String title, String artist, int year) {
    model.addVinyl(title, artist, year);
    updateVinyls();
    setStatusMessage("Added: " + title);
  }

  public void addUser(String username) {
    User user = new User(username);
    model.addUser(user);
    setStatusMessage("User added: " + username);
  }

  public void reserveVinylVM(Vinyl vinyl, User user) {
    model.reserveVinyl(user, vinyl);
    updateVinyls();
    setStatusMessage(user.getName() + " reserved: " + vinyl.getTitle());
  }

  public void unreserveVinylVM(Vinyl vinyl, User user) {
    model.unreserveVinyl(user, vinyl);
    updateVinyls();
    setStatusMessage(user.getName() + " unreserved: " + vinyl.getTitle());
  }

  public void borrowVinylVM(Vinyl vinyl, User user) {
    model.borrowVinyl(user, vinyl);
    updateVinyls();
    setStatusMessage(user.getName() + " borrowed: " + vinyl.getTitle());
  }

  public void returnVinylVM(Vinyl vinyl, User user) {
    model.returnVinyl(user, vinyl);
    updateVinyls();
    setStatusMessage(user.getName() + " returned: " + vinyl.getTitle());
  }

  public void markForRemovalVM(Vinyl vinyl) {
    model.markForRemoval(vinyl);
    updateVinyls();
    setStatusMessage("Marked for removal: " + vinyl.getTitle());
  }

  public void unmarkForRemovalVM(Vinyl vinyl) {
    model.unmarkForRemoval(vinyl);
    updateVinyls();
    setStatusMessage("Unmarked for removal: " + vinyl.getTitle());
  }

  public void removeVinylVM(Vinyl vinyl) {
    model.removeVinyl(vinyl);
    updateVinyls();
    setStatusMessage("Removed: " + vinyl.getTitle());
  }

  public Vinyl getVinylById(String id) {
    return model.getVinyls().stream()
        .filter(v -> v.getId().equals(id))
        .findFirst()
        .orElse(null);
  }
}
