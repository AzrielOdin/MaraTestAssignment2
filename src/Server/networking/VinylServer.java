package Server.networking;

import Server.model.Model;
import Server.model.VinylLibrary;

import java.net.ServerSocket;
import java.net.Socket;

public class VinylServer {
  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(19988);
      System.out.println("Server started on port 19988");

      Model model = new VinylLibrary();

      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Client connected: " + socket.getInetAddress());

        Thread t = new Thread(new ClientHandler(socket, model));
        t.start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
