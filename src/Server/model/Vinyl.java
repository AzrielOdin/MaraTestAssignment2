package Server.model;

import Server.model.states.AvailableState;
import Server.model.states.VinylState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Model representing a vinyl record with state transitions and property-change notifications.
 */
public class Vinyl implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private String artist;
    private int releaseYear;
    private VinylState currentState;

    private String id;

    private Integer reservedBy;
    private Integer borrowedBy;
    private boolean isMarkedForRemoval;

    private transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Vinyl(String title, String artist, int releaseYear) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.currentState = new AvailableState();
        this.reservedBy = null;
        this.borrowedBy = null;
        this.isMarkedForRemoval = false;
        this.id = UUID.randomUUID().toString();
    }

    public Vinyl() {
        // For XML/serialization
        this.pcs = new PropertyChangeSupport(this);
        this.id = UUID.randomUUID().toString();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.pcs = new PropertyChangeSupport(this);
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    // --- State transition handlers ---
    public void handleReserve(int userId) {
        Integer old = this.reservedBy;
        currentState.reserve(this, userId);
        pcs.firePropertyChange("reservedBy", old, this.reservedBy);
    }

    public void handleUnreserve(int userId) {
        Integer old = this.reservedBy;
        currentState.unreserve(this, userId);
        pcs.firePropertyChange("reservedBy", old, this.reservedBy);
    }

    public void handleBorrow(int userId) {
        Integer oldRes = this.reservedBy;
        Integer oldBor = this.borrowedBy;
        currentState.borrow(this, userId);
        pcs.firePropertyChange("reservedBy", oldRes, this.reservedBy);
        pcs.firePropertyChange("borrowedBy", oldBor, this.borrowedBy);
    }

    public void handleReturn(int userId) {
        Integer old = this.borrowedBy;
        currentState.returnVinyl(this, userId);
        pcs.firePropertyChange("borrowedBy", old, this.borrowedBy);
    }

    // --- Removal flag methods ---
    public void markForRemoval() {
        boolean old = this.isMarkedForRemoval;
        this.isMarkedForRemoval = true;
        pcs.firePropertyChange("isMarkedForRemoval", old, true);
    }

    public void unmarkForRemoval() {
        boolean old = this.isMarkedForRemoval;
        this.isMarkedForRemoval = false;
        pcs.firePropertyChange("isMarkedForRemoval", old, false);
    }

    // --- Internal setters used by state implementations ---
    public void setReservedBy(Integer userId) {
        this.reservedBy = userId;
    }

    public void setBorrowedBy(Integer userId) {
        this.borrowedBy = userId;
    }

    public void setState(VinylState newState) {
        VinylState old = this.currentState;
        this.currentState = newState;
        pcs.firePropertyChange("currentState", old, newState);
    }

    // --- Getters and editable properties ---
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String old = this.title;
        this.title = title;
        pcs.firePropertyChange("title", old, title);
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        String old = this.artist;
        this.artist = artist;
        pcs.firePropertyChange("artist", old, artist);
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        int old = this.releaseYear;
        this.releaseYear = releaseYear;
        pcs.firePropertyChange("releaseYear", old, releaseYear);
    }

    public Integer getReservedBy() {
        return reservedBy;
    }

    public Integer getBorrowedBy() {
        return borrowedBy;
    }

    public boolean isMarkedForRemoval() {
        return isMarkedForRemoval;
    }

    public String getId() {
        return id;
    }

    public VinylState getState() {
        return currentState;
    }

    public String getStateString() {
        return currentState.toString();
    }

    public String getCurrentStateName() {
        return currentState.getClass().getSimpleName().replace("State", "");
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "Vinyl[id=%s, title='%s', artist='%s', year=%d, state=%s, reservedBy=%s, borrowedBy=%s, markedForRemoval=%s]",
                id, title, artist, releaseYear,
                getCurrentStateName(), reservedBy, borrowedBy, isMarkedForRemoval);
    }
}
