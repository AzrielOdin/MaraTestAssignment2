package Server.networking;



import Server.model.Model;
import Shared.transferobjects.Request;
import Shared.transferobjects.Response;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
  private final Socket socket;
  private final Model model;
  private ObjectOutputStream out;
  private ObjectInputStream in;

  public ClientHandler(Socket socket, Model model) {
    this.socket = socket;
    this.model = model;

    try {
      this.out = new ObjectOutputStream(socket.getOutputStream());
      this.in = new ObjectInputStream(socket.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      while (true) {
        Request request = (Request) in.readObject();
        Response response = handleRequest(request);
        out.writeObject(response);
        out.flush();
      }
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Client disconnected: " + e.getMessage());
    }
  }

  private Response handleRequest(Request request) {
    try {
      String type = request.getType();

      switch (type) {
        case "getVinyls":
          return new Response("ok", model.getVinyls());
        case "getUsers":
          return new Response("ok", model.getUsers());
        case "addUser":
          model.addUser(request.getUser());
          return new Response("ok", null);
        case "addVinyl":
          model.addVinyl(request.getTitle(), request.getArtist(), request.getYear());
          return new Response("ok", null);
        case "reserveVinyl":
          model.reserveVinyl(request.getUser(), request.getVinyl());
          return new Response("ok", null);
        case "unreserveVinyl":
          model.unreserveVinyl(request.getUser(), request.getVinyl());
          return new Response("ok", null);
        case "borrowVinyl":
          model.borrowVinyl(request.getUser(), request.getVinyl());
          return new Response("ok", null);
        case "returnVinyl":
          model.returnVinyl(request.getUser(), request.getVinyl());
          return new Response("ok", null);
        case "markForRemoval":
          model.markForRemoval(request.getVinyl());
          return new Response("ok", null);
        case "unmarkForRemoval":
          model.unmarkForRemoval(request.getVinyl());
          return new Response("ok", null);
        case "removeVinyl":
          model.removeVinyl(request.getVinyl());
          return new Response("ok", null);
        default:
          return new Response("error", "Unknown request: " + type);
      }
    } catch (Exception e) {
      return new Response("error", e.getMessage());
    }
  }
}
