package Storage;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoggerSingleton {
  private static LoggerSingleton instance;
  private final List<LogEntry> entries;

  private LoggerSingleton() {
    entries = new ArrayList<>();
  }

  public static LoggerSingleton getInstance() {
    if (instance == null) {
      instance = new LoggerSingleton();
    }
    return instance;
  }

  public void log(String message) {
    LogEntry entry = new LogEntry(LocalDateTime.now().toString(), message);
    entries.add(entry);
    saveToXML(); // auto-save each time
  }

  public List<LogEntry> getEntries() {
    return entries;
  }

  private void saveToXML() {
    try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream("log.xml"))) {
      encoder.writeObject(entries);
    } catch (Exception e) {
      System.err.println("Failed to write log file: " + e.getMessage());
    }
  }

  public static class LogEntry implements Serializable {
    private String timestamp;
    private String message;

    public LogEntry() {}

    public LogEntry(String timestamp, String message) {
      this.timestamp = timestamp;
      this.message = message;
    }

    public String getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
}
