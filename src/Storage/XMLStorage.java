package Storage;

import Server.model.User;
import Server.model.Vinyl;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLStorage
{

  // Save a list of Vinyl objects to an XML file.
  public static void saveVinylsToXML(String filename, List<Vinyl> vinyls) {
    try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(filename))) {
      encoder.writeObject(vinyls);
      encoder.flush();
      System.out.println("Vinyls saved successfully to " + filename);
    } catch (Exception e) {
      System.err.println("Error saving vinyls: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Load a list of Vinyl objects from an XML file.
  public static List<Vinyl> loadVinylsFromXML(String filename) {
    File file = new File(filename);
    if (!file.exists()) {
      System.out.println("File " + filename + " does not exist. Starting with an empty list.");
      return new ArrayList<>();
    }
    try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(file))) {
      @SuppressWarnings("unchecked")
      List<Vinyl> vinyls = (List<Vinyl>) decoder.readObject();
      System.out.println("Vinyls loaded successfully from " + filename);
      return vinyls;
    } catch (Exception e) {
      System.err.println("Error loading vinyls: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  // Save a list of User objects to an XML file.
  public static void saveUsersToXML(String filename, List<User> users) {
    try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(filename))) {
      encoder.writeObject(users);
      encoder.flush();
      System.out.println("Users saved successfully to " + filename);
    } catch (Exception e) {
      System.err.println("Error saving users: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Load a list of User objects from an XML file.
  public static List<User> loadUsersFromXML(String filename) {
    File file = new File(filename);
    if (!file.exists()) {
      System.out.println("File " + filename + " does not exist. Starting with an empty list.");
      return new ArrayList<>();
    }
    try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(file))) {
      @SuppressWarnings("unchecked")
      List<User> users = (List<User>) decoder.readObject();
      System.out.println("Users loaded successfully from " + filename);
      return users;
    } catch (Exception e) {
      System.err.println("Error loading users: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }
}
