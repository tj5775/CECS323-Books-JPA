package csulb.cecs323.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authoring_Entities {
    @Id
    @Column(nullable = false, length = 30)
    private String email;

    @Column(length = 31)
    private String authoring_entity_type;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(length = 80)
    private String head_writer;

    private int year_formed;

    public Authoring_Entities(String email, String authoring_entity_type, String name, String head_writer, int year_formed) {
        this.email = email;
        this.authoring_entity_type = authoring_entity_type;
        this.name = name;
        this.head_writer = head_writer;
        this.year_formed = year_formed;
    }

    public Authoring_Entities() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthoring_entity_type() {
        return authoring_entity_type;
    }

    public void setAuthoring_entity_type(String authoring_entity_type) {
        this.authoring_entity_type = authoring_entity_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_writer() {
        return head_writer;
    }

    public void setHead_writer(String head_writer) {
        this.head_writer = head_writer;
    }

    public int getYear_formed() {
        return year_formed;
    }

    public void setYear_formed(int year_formed) {
        this.year_formed = year_formed;
    }

    @Override
    public String toString () {
        return "Authoring_Entities - email: " + this.email + ", authoring_entity_type: " +
                this.authoring_entity_type + ", Name: " + this.name
                + " head_writer: " + this.head_writer + " year_formed: " + this.year_formed;
    }
}
