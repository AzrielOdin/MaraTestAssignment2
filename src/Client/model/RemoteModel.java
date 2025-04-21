package Client.model;

import Shared.interfaces.Model;

/**
 * RemoteModel is the client-side abstraction of the Model.
 * It represents a model that communicates with the server over sockets.
 *
 * It extends the shared Model interface so it can be used by the ViewModel.
 * You can also add client-only methods here if needed in the future.
 */
public interface RemoteModel extends Model {
  // You can add extra client-only methods here if needed later.

  void addVinyl(String title, String artist, int year); // your custom shortcut
}
