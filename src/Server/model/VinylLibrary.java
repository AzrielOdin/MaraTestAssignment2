package Server.model;

import Server.model.states.*;
import Storage.XMLStorage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VinylLibrary implements Model, Serializable
{
  @Serial private static final long serialVersionUID = 1L;
  private List<User> users;
  private List<Vinyl> vinyls;
  private final Object lock = new Object();
  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


  public VinylLibrary() {
      this.users = XMLStorage.loadUsersFromXML("users.xml");
      this.vinyls = XMLStorage.loadVinylsFromXML("vinyls.xml");
  }


  public VinylLibrary (List<User> users, List<Vinyl> vinyls) {
    this.users = users;
    this.vinyls = vinyls;
  }


  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
  }
  private void saveToXML() {
    XMLStorage.saveVinylsToXML("vinyls.xml", vinyls);
    XMLStorage.saveUsersToXML("users.xml", users);
  }


  public void reserveVinyl(User u, Vinyl v)
  {
    synchronized (lock)
      {
        Vinyl vinyl = findVinylById(v.getId());
        vinyl.handleReserve(u.getId());
        pcs.firePropertyChange("vinylReserved", null, vinyl);
        saveToXML();
      }
  }
  public void unreserveVinyl(User u, Vinyl v){
    synchronized (lock)
    {
      Vinyl vinyl = findVinylById(v.getId());
      vinyl.handleUnreserve(u.getId());
      pcs.firePropertyChange("vinylUnreserved", null, vinyl);
      saveToXML();
    }
  }
  public void borrowVinyl(User u, Vinyl v){
    synchronized (lock)
    {
      Vinyl vinyl = findVinylById(v.getId());
      vinyl.handleBorrow(u.getId());
      pcs.firePropertyChange("vinylBorrowed", null, vinyl);
      saveToXML();
    }
  }
  public void returnVinyl(User u, Vinyl v){
   synchronized (lock)
     {
       Vinyl vinyl = findVinylById(v.getId());
       vinyl.handleReturn(u.getId());
       pcs.firePropertyChange("vinylReturned", null, vinyl);
       saveToXML();
     }
  }
  public Vinyl findVinylById(int vinylIdToFind) {
    synchronized (lock)
    {
      for (Vinyl vinyl : vinyls)
      {
        if (vinyl.getId() == vinylIdToFind)
        {
          return vinyl;
        }
      }
      return null;
    }
  }
  public void markForRemoval(Vinyl v) {
    synchronized (lock)
    {
      Vinyl vinyl = findVinylById(v.getId());
      vinyl.markForRemoval();
      pcs.firePropertyChange("vinylMarkedForRemoval", null, vinyl);
      saveToXML();
    }
  }
  public void unmarkForRemoval(Vinyl v) {
    synchronized (lock)
    {
      Vinyl vinyl = findVinylById(v.getId());
      vinyl.unmarkForRemoval();
      pcs.firePropertyChange("vinylUnmarkedForRemoval", null, vinyl);
      saveToXML();
    }
  }
  public List<Vinyl> getVinyls() {
    synchronized (lock)
    {
      return new ArrayList<>(
          vinyls); // Return a copy to avoid modification issues
    }
  }
  public void addUser(User user)
  {
    synchronized (lock)
    {
      users.add(user);
    }
    saveToXML();
  }

  public List<User> getUsers()
  {
    synchronized (lock)
    {
      return new ArrayList<>(users);
    }
  }

  @Override
  public void addVinyl(String title, String artist, int year) {
    synchronized (lock){
      Vinyl vinyl = new Vinyl(title, artist, year);
      vinyls.add(vinyl);
      pcs.firePropertyChange("vinylAdded", null, vinyl);
      saveToXML();
    }

  }


  public void removeVinyl(Vinyl vinyl) {
    {
      synchronized (lock)
      {
        if (vinyl == null || !vinyl.isMarkedForRemoval()
            || !(vinyl.getState() instanceof AvailableState))
        {
          throw new IllegalArgumentException("Vinyl is not marked for removal");
        }
        this.vinyls.remove(vinyl);
        saveToXML();
      }
    }
  }

  @Override
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    for (Vinyl vinyl : vinyls)
    {
      s.append(vinyl.toString());
      s.append("\n");
    }
    return s.toString();
  }
  //
  //testing relevant methods
  //
  public void clear()
  {
    synchronized (lock)
    {
      users.clear();
      vinyls.clear();
    }
  }
}
