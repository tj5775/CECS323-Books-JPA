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

    public Publisher(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Publisher(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    public void removePublisher(){
//        this.name = "";
//        this.phone = "";
//        this.email = "";
    }
    @Override
    public String toString () {
        return "Name: " + this.name + " | Phone: " + this.phone
                + " | Email: " + this.email;
    }
}
