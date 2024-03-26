
import org.example.controller.ClientController;
import org.example.controller.NutritionistController;
import org.example.service.client.UserRepositoryImp;
import org.example.service.nutritionist.NutritionistRepository;
import org.example.service.nutritionist.NutritionistRepositoryImp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
      ClientController clientController = new ClientController();
      clientController.createClient("erickert", "erickert@mail.austral.edu.ar");
      clientController.createClient("hlagos", "hlagos@mail.austral.edu.ar");
      clientController.createClient("tbernardez", "tbernardez@mail.austral.edu.ar");
      clientController.readClient(1L);
      clientController.readClient(2L);
      clientController.readClient(3L);
      clientController.updateClient(1L, "eballesteros", "eballesteros@mail.austral.edu.ar");
      clientController.deleteClient(1L);
      NutritionistController nutritionistController = new NutritionistController();
      nutritionistController.createNutritionist("erickert", "lol", "lal");
    }
}

