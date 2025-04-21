package Client.views;

import Storage.LoggerSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LogViewController {

  @FXML private TableView<LoggerSingleton.LogEntry> logTable;
  @FXML private TableColumn<LoggerSingleton.LogEntry, String> timeColumn;
  @FXML private TableColumn<LoggerSingleton.LogEntry, String> messageColumn;

  @FXML
  public void initialize() {
    timeColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

    logTable.getItems().setAll(LoggerSingleton.getInstance().getEntries());
  }
}
