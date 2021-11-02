package csulb.cecs323.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedNativeQuery(
        name="ReturnAllPublishers",
        query = "SELECT * " +
                "FROM   PUBLISHER",
        resultClass = Publisher.class
)
/**
 * A company that publishes a book
 */
public class Publisher {

    @Id

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 24, unique = true)
    private String phone;

    @Column(nullable = false, length = 80, unique = true)
    private String email;

    @OneToMany(mappedBy = "publisher_name_books", cascade = CascadeType.PERSIST)
    private List<Books> books;

    /**
     * Default constructor
     */
    public Publisher(){}

    /**
     * A constructor with all the private variables from Publisher
     * @param name
     * @param phone
     * @param email
     */
    public Publisher(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
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
     * Get method for phone
     * @return          phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set method for phone
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * A method that removes the publisher
     */
    public void removePublisher(){
//        this.name = "";
//        this.phone = "";
//        this.email = "";
    }

    /**
     * toString method for Publisher
     * @return
     */
    @Override
    public String toString () {
        return "Name: " + this.name + " | Phone: " + this.phone
                + " | Email: " + this.email;
    }
}
