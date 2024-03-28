
import org.example.controller.CustomerController;
import org.example.controller.NutritionistController;
import org.example.controller.StoreController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main {
    public static void main(String[] args) {
      EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
      EntityManager entityManager = entityManagerFactory.createEntityManager();;
      CustomerController customerController = new CustomerController(entityManager);
      customerController.createClient("erickert", "erickert@mail.austral.edu.ar");
      customerController.createClient("hlagos", "hlagos@mail.austral.edu.ar");
      customerController.createClient("tbernardez", "tbernardez@mail.austral.edu.ar");
      customerController.readClient(1L);
      customerController.readClient(2L);
      customerController.readClient(3L);
      customerController.updateClient(1L, "eballesteros", "eballesteros@mail.austral.edu.ar");
      customerController.deleteClient(2L);
      NutritionistController nutritionistController = new NutritionistController(entityManager);
      nutritionistController.createNutritionist("yael", "yael@test.com", "UA 4 year diploma");
      nutritionistController.createNutritionist("paola", "paola@test.com", "UBA 4 year diploma");
      nutritionistController.updateNutritionist(2L, "silvina", "silvina@test.com", "UBA 4 year diploma");
      nutritionistController.deleteNutritionist(1L);
      StoreController storeController = new StoreController(entityManager);
      storeController.createStore("New Garden", "newgarden@gmail.com", "1122223333");
      storeController.createStore("Green Food", "greenfood@gmail.com", "1133334444");
      storeController.createStore("Green Life", "greenlife@gmail.com", "1144445555");
      storeController.readStore(1L);
      storeController.readStore(2L);
      storeController.readStore(3L);
      storeController.deleteStore(3L);
      storeController.updateStore(2L, "Green Life", "greenlife@gmail.com", "1133334444");
    }
}

