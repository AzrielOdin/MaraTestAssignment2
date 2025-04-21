package Server.model;
import Server.model.states.AvailableState;
import Server.model.states.VinylState;

import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.io.Serializable;

public class Vinyl implements Serializable
{
  @Serial private static final long serialVersionUID = 1L;
  private String title;
  private String artist;
  private int releaseYear;
  private VinylState currentState;

  private static int nextId = 1;
  private int id;

  private Integer reservedBy;
  private Integer borrowedBy;
  private boolean isMarkedForRemoval;

  private transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);
  // Made transient so it won't be serialized
  private String state; //optional, descriptive text for UI purposes

  public Vinyl(String title, String artist, int releaseYear)
  {
    this.title = title;
    this.artist = artist;
    this.releaseYear = releaseYear;
    this.currentState = new AvailableState();
    this.reservedBy = null;
    this.borrowedBy = null;
    this.isMarkedForRemoval = false;
    this.id = nextId++;
    this.state = "Available";
  }

  public Vinyl() {
    // Required for XML decoding;
    // empty because beans require a no-arg constructor
  }


  void handleReserve(int userId){
    currentState.reserve(this, userId);
  }

  void handleUnreserve(Integer userId){
    currentState.unreserve(this, userId);
  }

  void handleBorrow(int userId){
    currentState.borrow(this, userId);
  }

  void handleReturn(int userId){
    currentState.returnVinyl(this, userId);
  }

    //
   // - Flagged for Removal Methods
  //

  public void markForRemoval(){
    if(!isMarkedForRemoval){
      isMarkedForRemoval = true;
      //firePropertyChange("markedForRemoval", false, true);
    }
  }
  public void unmarkForRemoval(){
    if (isMarkedForRemoval){
      isMarkedForRemoval = false;
      //firePropertyChange("markedForRemoval", true, false);
    }
  }

    //
   // - Setters and getters -
  //

  //returns the name of the current state
  public String getCurrentStateName() {
    return currentState.getClass().getSimpleName().replace("State", "");
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getArtist() {
    return artist;
  }
  public void setArtist(String artist) {
    this.artist = artist;
  }
  public int getReleaseYear() {
    return releaseYear;
  }
  public void setReleaseYear(int releaseYear) {
    this.releaseYear = releaseYear;
  }
  public Integer getReservedBy (){
    return reservedBy;
  }
  public Integer getBorrowedBy() { return borrowedBy; };
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }// needed for XML serialization
  public static void setNextId(int next)
  {
    nextId = next;
  }//XML again

  public VinylState getState() {
    return currentState;
  }
  public String getStateString() {
    return currentState.toString();
  }
  public boolean isMarkedForRemoval()
  {
    return isMarkedForRemoval;
  }

    //
   // - Setters (Used by olddddd.RemoteModel.States) -
  //

  public void setReservedBy(Integer userId){
    //Integer old = this.reservedBy;
    this.reservedBy = userId;
    //pcs.firePropertyChange("reservedBy", old, userId);
  }

  public void setBorrowedBy(Integer userId) {
    //Integer old = this.borrowedBy;
    this.borrowedBy = userId;
    //pcs.firePropertyChange("borrowedBy", old, userId);
  }

  public void setState(VinylState newState){
    //VinylState oldState = this.currentState;
    this.currentState = newState;
    //pcs.firePropertyChange("state", oldState, newState);
  }




    //
   // toString, equals and hashCode
  //

  @Override public String toString()
  {
    return "Viny: " + "title: '" + this.getTitle() + ", artist: " + this.getArtist() + '.'
        + ", releaseYear=" + this.getReleaseYear() + ", id=" + this.getId() + ", state=" + currentState + '}';
  }


  //
 // Testing/Utility Methods
//

  public static void resetCounter() {
    nextId = 1;
  }
}
