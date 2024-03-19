import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
public class Client {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private Long clientId;

    @Column(nullable = false, unique = false)
    private String clientName;

    @Column(nullable = false, unique = true)
    private String clientEmail;

    @Column(nullable = true, unique = false)
    private HashMap<String, Integer> goals;

    @Column(nullable = false, unique = false)
    private ArrayList<String> dietType;

    @Column(nullable = true, unique = false)
    private ArrayList<String> allergies;
}
