package Shared.interfaces;

import Server.model.User;
import Server.model.Vinyl;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface Model {
  List<Vinyl> getVinyls();
  List<User> getUsers();

  void addVinyl(String title, String artist, int year);
  void addUser(User user);

  void reserveVinyl(User user, Vinyl vinyl);
  void unreserveVinyl(User user, Vinyl vinyl);
  void borrowVinyl(User user, Vinyl vinyl);
  void returnVinyl(User user, Vinyl vinyl);

  void markForRemoval(Vinyl vinyl);
  void unmarkForRemoval(Vinyl vinyl);
  void removeVinyl(Vinyl vinyl);

  void addPropertyChangeListener(PropertyChangeListener listener);

}
