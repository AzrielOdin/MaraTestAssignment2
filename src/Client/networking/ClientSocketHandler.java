package Client.networking;

import Client.model.RemoteModel;
import Shared.transferobjects.Request;
import Shared.transferobjects.Response;
import Server.model.User;
import Server.model.Vinyl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSocketHandler implements RemoteModel {
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  public ClientSocketHandler() {
    try {
      Socket socket = new Socket("localhost", 19988);
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream(socket.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Response sendRequest(Request request) {
    try {
      out.writeObject(request);
      out.flush();
      return (Response) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      return new Response("error", e.getMessage());
    }
  }

  @Override
  public List<Vinyl> getVinyls() {
    Response res = sendRequest(new Request("getVinyls"));
    return res.getPayload() != null ? (List<Vinyl>) res.getPayload() : new ArrayList<>();
  }

  @Override
  public List<User> getUsers() {
    Response res = sendRequest(new Request("getUsers"));
    return res.getPayload() != null ? (List<User>) res.getPayload() : new ArrayList<>();
  }

  @Override
  public void addUser(User user) {
    Request req = new Request("addUser");
    req.setUser(user);
    sendRequest(req);
  }

  @Override
  public void addVinyl(String title, String artist, int year) {
    Request req = new Request("addVinyl");
    req.setVinylInfo(title, artist, year);
    sendRequest(req);
  }

  @Override
  public void reserveVinyl(User user, Vinyl vinyl) {
    Request req = new Request("reserveVinyl");
    req.setUser(user);
    req.setVinyl(vinyl);
    sendRequest(req);
  }

  @Override
  public void unreserveVinyl(User user, Vinyl vinyl) {
    Request req = new Request("unreserveVinyl");
    req.setUser(user);
    req.setVinyl(vinyl);
    sendRequest(req);
  }

  @Override
  public void borrowVinyl(User user, Vinyl vinyl) {
    Request req = new Request("borrowVinyl");
    req.setUser(user);
    req.setVinyl(vinyl);
    sendRequest(req);
  }

  @Override
  public void returnVinyl(User user, Vinyl vinyl) {
    Request req = new Request("returnVinyl");
    req.setUser(user);
    req.setVinyl(vinyl);
    sendRequest(req);
  }

  @Override
  public void markForRemoval(Vinyl vinyl) {
    Request req = new Request("markForRemoval");
    req.setVinyl(vinyl);
    sendRequest(req);
  }

  @Override
  public void unmarkForRemoval(Vinyl vinyl) {
    Request req = new Request("unmarkForRemoval");
    req.setVinyl(vinyl);
    sendRequest(req);
  }

  @Override
  public void removeVinyl(Vinyl vinyl) {
    Request req = new Request("removeVinyl");
    req.setVinyl(vinyl);
    sendRequest(req);
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
  }
}
