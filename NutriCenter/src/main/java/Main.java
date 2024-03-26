
import org.example.controller.CustomerController;
import org.example.controller.CustomerController;
import org.example.controller.NutritionistController;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
      CustomerController customerController = new CustomerController();
      customerController.createClient("erickert", "erickert@mail.austral.edu.ar");
      customerController.createClient("hlagos", "hlagos@mail.austral.edu.ar");
      customerController.createClient("tbernardez", "tbernardez@mail.austral.edu.ar");
      customerController.readClient(1L);
      customerController.readClient(2L);
      customerController.readClient(3L);
      customerController.updateClient(1L, "eballesteros", "eballesteros@mail.austral.edu.ar");
      customerController.deleteClient(2L);
      NutritionistController nutritionistController = new NutritionistController();
      nutritionistController.createNutritionist("erickert", "lol", "lal");
    }
}

