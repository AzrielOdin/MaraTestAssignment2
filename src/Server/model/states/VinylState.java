package Server.model.states;

import Server.model.Vinyl;

import java.io.Serializable;

public interface VinylState extends Serializable
{
  void borrow(Vinyl vinyl, int userId);
  void returnVinyl(Vinyl vinyl, int userId);
  void reserve(Vinyl vinyl, int userId);
  void unreserve(Vinyl vinyl, int userId);

}