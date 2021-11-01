package csulb.cecs323.model;

import javax.persistence.*;
import java.util.List;


@Entity
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "Authoring_entities_count",
            query = "SELECT COUNT(*) " +
                    "FROM   authoring_entities " +
                    "WHERE email = ?"),
    @NamedNativeQuery(
            name = "Authoring_entities_all",
            query = "SELECT * " +
                    "FROM   authoring_entities " +
                    "ORDER BY name",
    resultClass=Authoring_Entities.class)

})

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "authoring_entities_type", discriminatorType = DiscriminatorType.STRING)
public class Authoring_Entities {

    @Id
    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 80)
    private String name;

    @OneToMany(mappedBy = "entity_name", cascade = CascadeType.PERSIST)
    private List<Books> books;

    public Authoring_Entities(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public Authoring_Entities() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Authoring_Entities{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
