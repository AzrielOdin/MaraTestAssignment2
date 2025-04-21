package Client.views;

import Client.viewmodel.VinylViewModel;
import Server.model.User;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class AddUserController {

  @FXML private TextField usernameField;
  @FXML private Button addUserButton;
  @FXML private Label statusLabel;

  private VinylViewModel viewModel;
  private User currentUser;

  public void initViewModel(VinylViewModel viewModel) {
    this.viewModel = viewModel;
  }


  public void setCurrentUser(User user) {
    this.currentUser = user;
    // Optional: restrict access in future
    // setButtonAccess(false);
  }

  @FXML
  public void addUser() {
    String username = usernameField.getText().trim();

    if (username.isEmpty()) {
      statusLabel.setText("Username is required!");
      return;
    }

    Task<Void> addUserTask = new Task<>() {
      @Override
      protected Void call() {
        viewModel.addUser(username);
        return null;
      }

      @Override
      protected void succeeded() {
        statusLabel.setText("User added successfully!");
        usernameField.clear();
      }

      @Override
      protected void failed() {
        statusLabel.setText("Failed to add user.");
      }
    };

    Thread thread = new Thread(addUserTask);
    thread.setName("AddUserThread");
    thread.setDaemon(true); // Fine for background UI task
    thread.start();
  }

  // Optional: you could call this from setCurrentUser in the future
  public void setButtonAccess(boolean isEnabled) {
    addUserButton.setDisable(!isEnabled);
  }
}
