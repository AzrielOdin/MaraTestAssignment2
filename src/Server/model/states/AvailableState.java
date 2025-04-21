package Server.model.states;
import Server.model.Vinyl;

import java.io.Serializable;

public class AvailableState implements VinylState, Serializable
{

  private static final long serialVersionUID = 1L;


  @Override
  public void borrow(Vinyl vinyl, int userId)
  {
    // Allow borrow if:
    // - Not flagged for removal
    if (vinyl.isMarkedForRemoval()) {
      throw new IllegalArgumentException("Vinyl is not available for borrowing");
    }
      vinyl.setBorrowedBy(userId);
      vinyl.setState(new BorrowedState());
    }



  public void returnVinyl(Vinyl vinyl, int userId) {
    // Do nothing (Available vinyls can't be returned)
    throw new IllegalArgumentException("Vinyl is not borrowed.");
  }

  @Override
  public void reserve(Vinyl vinyl, int userId) {
    // Can be reserved if it's not Marked for Removal
    if (!vinyl.isMarkedForRemoval()){
      vinyl.setReservedBy(userId);
      vinyl.setState(new AvailableAndReservedState());
    }
    throw new IllegalArgumentException("Vinyl is not available for reserving.");

  }

  public void unreserve(Vinyl vinyl, int userId) {
    // Do nothing
    throw new IllegalArgumentException("Vinyl can't be unreserved.");

  }


  @Override
  public String toString() {
    return "Available";
  }
}
