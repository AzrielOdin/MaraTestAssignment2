package Client.core;

import Client.model.RemoteModel;
import Client.networking.ClientSocketHandler;

public class ClientFactory {
  private static RemoteModel client;

  public RemoteModel getClient() {
    if (client == null) {
      client = new ClientSocketHandler();
    }
    return client;
  }
}
