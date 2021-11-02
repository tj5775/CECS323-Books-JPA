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
/**
 *Author or Authors of a book
 */
public class Authoring_Entities {

    @Id
    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 80)
    private String name;

    @OneToMany(mappedBy = "entity_name", cascade = CascadeType.PERSIST)
    private List<Books> books;

    /**
     * A constructor with all the private varaibles in Authoring_Entities
     * @param email
     * @param name
     */
    public Authoring_Entities(String email, String name) {
        this.email = email;
        this.name = name;
    }

    /**
     * Default Constructor
     */
    public Authoring_Entities() {

    }

    /**
     * Get method for email
     * @return          email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set method for email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get method for name
     * @return          name
     */
    public String getName() {
        return name;
    }

    /**
     * Set method for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * toString method for Authoring_Entities
     * @return
     */
    @Override
    public String toString() {
        return "Authoring_Entities{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
