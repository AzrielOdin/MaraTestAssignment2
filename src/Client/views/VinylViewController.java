package Client.views;

import Client.viewmodel.VinylDisplay;
import Server.model.User;
import Client.viewmodel.VinylViewModel;
import Server.model.Vinyl;
import Storage.LoggerSingleton;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class VinylViewController {
  public Button markForRemovalButton;
  public Button unreserveButton;
  public Button borrowButton;
  public Button reserveButton;
  public Button returnButton;
  public Button unmarkForRemovalButton;
  public Button addVinylButton;
  public Button addUserButton;
  public Button removeButton;

  @FXML private TableView<VinylDisplay> vinylTable;
  @FXML private TextArea logTextArea;
  @FXML private TableColumn<VinylDisplay, String> titleColumn;
  @FXML private TableColumn<VinylDisplay, String> artistColumn;
  @FXML private TableColumn<VinylDisplay, Integer> releaseYearColumn;
  @FXML private TableColumn<VinylDisplay, String> stateColumn;
  @FXML private TableColumn<VinylDisplay, String> reservedByColumn;
  @FXML private TableColumn<VinylDisplay, String> borrowedByColumn;
  @FXML private TableColumn<VinylDisplay, String> markedForRemovalColumn;
  @FXML private Label statusLabel;

  private static final Logger logger = Logger.getLogger(VinylViewController.class.getName());
  private VinylViewModel viewModel;
  private User currentUser = User.adminUser;

  public void init(VinylViewModel viewModel) {
    this.viewModel = viewModel;
    vinylTable.setItems(viewModel.getVinylDisplays());
    viewModel.updateVinyls();

    statusLabel.textProperty().bind(viewModel.statusMessageProperty());

    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
    releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
    stateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
    reservedByColumn.setCellValueFactory(new PropertyValueFactory<>("reservedBy"));
    borrowedByColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedBy"));
    markedForRemovalColumn.setCellValueFactory(new PropertyValueFactory<>("markedForRemoval"));
    vinylTable.refresh(); //Force refresh on window open


    viewModel.statusMessageProperty().addListener((obs, oldMsg, newMsg) -> {
      if (newMsg != null && !newMsg.isBlank()) {
        log(newMsg);
      }
    });
  }

  public void setCurrentUser(User user) {
    this.currentUser = user;
  }

  private void updateUI() {
    Platform.runLater(() -> vinylTable.refresh());
  }

  private void log(String message) {
    Platform.runLater(() -> logTextArea.appendText(message + "\n"));
    logger.info(message);
    LoggerSingleton.getInstance().log(message);
  }

  @FXML
  public void openLogWindow() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/views/LogView.fxml"));
      Parent root = loader.load();
      Stage stage = new Stage();
      stage.setTitle("Activity Log");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void onBorrowVinylButtonPress() {
    Vinyl vinyl = getSelectedVinyl();
    if (vinyl == null) return;
    runTask(currentUser.getName() + " [" + currentUser.getId() + "] borrowed: " + vinyl.getTitle(),
        () -> viewModel.borrowVinylVM(vinyl, currentUser));
  }

  @FXML
  public void onReserveButtonPress() {
    Vinyl vinyl = getSelectedVinyl();
    if (vinyl == null) return;
    runTask(currentUser.getName() + " [" + currentUser.getId() + "] reserved: " + vinyl.getTitle(),
        () -> viewModel.reserveVinylVM(vinyl, currentUser));
  }

  @FXML
  public void onReturnButtonPress() {
    Vinyl vinyl = getSelectedVinyl();
    if (vinyl == null) return;
    runTask(currentUser.getName() + " [" + currentUser.getId() + "] returned: " + vinyl.getTitle(),
        () -> viewModel.returnVinylVM(vinyl, currentUser));
  }

  @FXML
  public void onUnreserveButtonPress() {
    Vinyl vinyl = getSelectedVinyl();
    if (vinyl == null) return;
    runTask(currentUser.getName() + " [" + currentUser.getId() + "] unreserved: " + vinyl.getTitle(),
        () -> viewModel.unreserveVinylVM(vinyl, currentUser));
  }

  @FXML
  public void onMarkForRemovalButtonPress() {
    Vinyl vinyl = getSelectedVinyl();
    if (vinyl == null) return;
    runTask(currentUser.getName() + " [" + currentUser.getId() + "] marked for removal: " + vinyl.getTitle(),
        () -> viewModel.markForRemovalVM(vinyl));
  }

  @FXML
  public void onUnmarkForRemoval() {
    Vinyl vinyl = getSelectedVinyl();
    if (vinyl == null) return;
    runTask(currentUser.getName() + " [" + currentUser.getId() + "] unmarked from removal: " + vinyl.getTitle(),
        () -> viewModel.unmarkForRemovalVM(vinyl));
  }

  @FXML
  public void onRemoveVinylButtonPress() {
    Vinyl vinyl = getSelectedVinyl();
    if (vinyl == null) return;
    runTask(currentUser.getName() + " [" + currentUser.getId() + "] removed: " + vinyl.getTitle(),
        () -> viewModel.removeVinylVM(vinyl));
  }

  @FXML
  public void openAddUserWindow() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddUserView.fxml"));
      Parent root = loader.load();
      AddUserController controller = loader.getController();
      controller.initViewModel(viewModel);
      controller.setCurrentUser(currentUser);

      Stage stage = new Stage();
      stage.setTitle("Add User");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void openAddVinylWindow() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/views/AddVinylView.fxml"));
      Parent root = loader.load();

      AddVinylController controller = loader.getController();
      controller.initViewModel(viewModel);
      controller.setCurrentUser(currentUser);

      Stage stage = new Stage();
      stage.setTitle("Add Vinyl");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private Vinyl getSelectedVinyl() {
    VinylDisplay selectedDisplay = vinylTable.getSelectionModel().getSelectedItem();
    if (selectedDisplay == null) {
      log("No vinyl selected.");
      return null;
    }

    Vinyl realVinyl = viewModel.getVinylById(selectedDisplay.getId());
    if (realVinyl == null) {
      log("Could not find matching vinyl.");
      return null;
    }
    return realVinyl;
  }

  private void runTask(String actionDescription, Runnable backgroundAction) {
    VinylDisplay selectedDisplay = vinylTable.getSelectionModel().getSelectedItem();
    Task<Void> task = new Task<>() {
      @Override
      protected Void call() {
        backgroundAction.run();
        return null;
      }

      @Override
      protected void succeeded() {
        log(actionDescription);
        viewModel.setStatusMessage(actionDescription);
        updateUI();
        if (selectedDisplay != null) {
          for (VinylDisplay item : vinylTable.getItems()) {
            if (item.getId() == selectedDisplay.getId()) {
              vinylTable.getSelectionModel().select(item);
              break;
            }
          }
        }
      }

      @Override
      protected void failed() {
        log("Failed: " + getException().getMessage());
        updateUI();
        if (selectedDisplay != null) {
          for (VinylDisplay item : vinylTable.getItems()) {
            if (item.getId() == selectedDisplay.getId()) {
              vinylTable.getSelectionModel().select(item);
              break;
            }
          }
        }
      }
    };

    Thread thread = new Thread(task);
    thread.setName("VinylActionTask");
    thread.start();
  }
}
