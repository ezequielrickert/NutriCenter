import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        // Create a configuration instance
        Configuration configuration = new Configuration();

        // Load the hibernate configuration file
        configuration.configure("hibernate.cfg.xml");

        // Create a session factory
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Create a new session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Create a new Client
        Client client = new Client();
        client.setClientName("John Doe");
        client.setClientEmail("john.doe@example.com");

        // Save the Client
        session.save(client);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }
}