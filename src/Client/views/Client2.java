package Client.views;

import Client.core.ClientFactory;
import Client.core.ModelFactory;
import Client.core.ViewHandler;
import Client.core.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client2 extends Application {
  @Override
  public void start(Stage primaryStage) {
    ClientFactory clientFactory = new ClientFactory();
    ModelFactory modelFactory = new ModelFactory(clientFactory);
    ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);

    viewHandler.start(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
