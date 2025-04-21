package Server.model.states;
import Server.model.Vinyl;

import java.io.Serializable;

public class BorrowedState implements VinylState, Serializable
{
  private static final long serialVersionUID = 1L;


  public void borrow(Vinyl vinyl, int userId)
  {
    // Do nothing (already borrowed by someone)
    throw new IllegalArgumentException("Vinyl is already borrowed");
  }

  public void returnVinyl(Vinyl vinyl, int userId)
  {
    // Can be returned only by the borrowing user
    if (vinyl.getBorrowedBy() != userId) {
      throw new IllegalArgumentException("Vinyl is not borrowed by this user");
    }
    vinyl.setBorrowedBy(null);
    vinyl.setState(new AvailableState());
  }

  public void reserve(Vinyl vinyl, int userId)
  {
    // Can only reserve vinyls that are not flagged for removal
    // Users who have the vinyl should not be able to reserve them
    if (vinyl.isMarkedForRemoval()) {
      throw new IllegalArgumentException("Vinyl is marked for removal");
    }
    if (vinyl.getBorrowedBy() == userId){
      throw new IllegalArgumentException("User already owns it, why reserve it?");
    }
    vinyl.setReservedBy(userId);
    vinyl.setState(new BorrowedAndReservedState());
  }

  public void unreserve(Vinyl vinyl, int userId)
  {
    // Do nothing (Just borrowed, there is no reservation)
    // System.out.println("Vinyl is not reserved");
    throw new IllegalArgumentException("Vinyl is not reserved");
  }


  @Override public String toString()
  {
    return "Borrowed";
  }
}
