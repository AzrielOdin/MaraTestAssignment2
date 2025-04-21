package Client.core;

import Client.viewmodel.VinylViewModel;
import Client.views.VinylViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewHandler {
  private final ViewModelFactory viewModelFactory;
  private Stage primaryStage;

  public ViewHandler(ViewModelFactory viewModelFactory) {
    this.viewModelFactory = viewModelFactory;
  }

  public void start(Stage stage) {
    this.primaryStage = stage;
    openMainView();
  }

  public void openMainView() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/views/VinylLibrary.fxml"));
      Parent root = loader.load();

      VinylViewController controller = loader.getController();
      controller.init(viewModelFactory.getVinylViewModel());

      primaryStage.setTitle("Vinyl Library");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}