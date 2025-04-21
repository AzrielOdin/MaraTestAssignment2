package Client.core;

import Client.model.RemoteModel;
import Client.networking.ClientSocketHandler;

public class ModelFactory {
  private RemoteModel remoteModel;

  public ModelFactory(ClientFactory clientFactory) {
    remoteModel = new ClientSocketHandler();
  }

  public RemoteModel getModel() {
    return remoteModel;
  }
}

