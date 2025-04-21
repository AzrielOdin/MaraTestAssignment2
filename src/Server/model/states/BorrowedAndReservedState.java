package Server.model.states;
import Server.model.Vinyl;

import java.awt.*;
import java.io.Serializable;

public class BorrowedAndReservedState implements VinylState, Serializable
{

  private static final long serialVersionUID = 1L;


  @Override
    public void borrow(Vinyl vinyl, int userId) {
      // Do nothing (already borrowed)
      throw new IllegalArgumentException("Vinyl is already borrowed");
    }

    @Override
    public void returnVinyl(Vinyl vinyl, int userId) {
      // Can only be returned by the following user
      if (vinyl.getBorrowedBy() != userId) {
        throw new IllegalArgumentException("Vinyl is not borrowed by this user");
      }
      vinyl.setBorrowedBy(null);
      vinyl.setState(new AvailableAndReservedState());
    }

    @Override
    public void reserve(Vinyl vinyl, int userId) {
      // Do nothing (already reserved);
      throw new IllegalArgumentException("Vinyl is already reserved");
    }

    public void unreserve(Vinyl vinyl, int userId) {
      // Only by the reserving user
      if (vinyl.getReservedBy() != userId) {
        throw new IllegalArgumentException("Vinyl was not reserved by this user");
      }
      vinyl.setReservedBy(null);
      vinyl.setState(new BorrowedState());
    }

  @Override
  public String toString() {
    return "Borrowed and Reserved";}
  }

