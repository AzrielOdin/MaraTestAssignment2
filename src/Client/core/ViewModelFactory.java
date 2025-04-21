package Client.core;

import Client.viewmodel.VinylViewModel;
import Client.model.RemoteModel;

public class ViewModelFactory {
  private final VinylViewModel vinylViewModel;

  public ViewModelFactory(ModelFactory modelFactory) {
    RemoteModel model = modelFactory.getModel();
    vinylViewModel = new VinylViewModel(model);
  }

  public VinylViewModel getVinylViewModel() {
    return vinylViewModel;
  }
}
