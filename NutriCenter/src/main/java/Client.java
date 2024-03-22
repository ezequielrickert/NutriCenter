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


    public void setClientName(String name) {
        this.clientName = name;
    }

    public void setClientEmail(String mail) {
        this.clientEmail = mail;
    }
}
