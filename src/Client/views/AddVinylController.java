package Client.views;

import Client.viewmodel.VinylViewModel;
import Server.model.User;
import Server.model.Vinyl;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddVinylController {

  @FXML private TextField titleField;
  @FXML private TextField artistField;
  @FXML private TextField releaseYearField;
  @FXML private Button addVinylButton;
  @FXML private Label statusLabel;

  private VinylViewModel viewModel;
  private Vinyl currentVinyl;
  private User currentUser;

  public void initViewModel(VinylViewModel viewModel) {
    this.viewModel = viewModel;
  }


  public void setCurrentVinyl(Vinyl vinyl) {
    this.currentVinyl = vinyl;
  }
  @FXML
  public void addVinyl() {
    String title = titleField.getText().trim();
    String artist = artistField.getText().trim();
    String releaseYearText = releaseYearField.getText().trim();

    if (title.isEmpty() || artist.isEmpty() || releaseYearText.isEmpty()) {
      statusLabel.setText("All fields are required!");
      return;
    }

    int releaseYear;

    try {
      releaseYear = Integer.parseInt(releaseYearText);
    } catch (NumberFormatException e) {
      statusLabel.setText("Release year must be a number.");
      return;
    }

    Task<Void> addVinylTask = new Task<>() {
      @Override
      protected Void call() {
        viewModel.addVinyl(title,artist,releaseYear);
        return null;
      }

      @Override
      protected void succeeded() {
        statusLabel.setText("Vinyl added successfully!");
        titleField.clear();
        artistField.clear();
        releaseYearField.clear();
      }

      @Override
      protected void failed() {
        statusLabel.setText("Failed to add vinyl.");
      }
    };

    Thread thread = new Thread(addVinylTask);
    thread.setName("AddVinylThread");
    thread.setDaemon(true); // Set the thread as a daemon thread, so it closes if the application exits
    thread.start();
  }

  //future proofing if client shouldn't be able to add vinyls
  public void setButtonAccess(boolean isEnabled) {
    addVinylButton.setDisable(!isEnabled);
  }

  public void setCurrentUser(User currentUser)
  {
    this.currentUser = currentUser;
  }
}
