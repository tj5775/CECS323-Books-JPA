package csulb.cecs323.model;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "authoring_entities_type", discriminatorType = DiscriminatorType.STRING)
public class Authoring_Entities {

    @Id
    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 80)
    private String name;


    public Authoring_Entities(String name, String email) {
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

//    public String getAuthoring_entity_type() {
//        return authoring_entity_type;
//    }
//
//    public void setAuthoring_entity_type(String authoring_entity_type) {
//        this.authoring_entity_type = authoring_entity_type;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getHead_writer() {
//        return head_writer;
//    }
//
//    public void setHead_writer(String head_writer) {
//        this.head_writer = head_writer;
//    }
//
//    public int getYear_formed() {
//        return year_formed;
//    }
//
//    public void setYear_formed(int year_formed) {
//        this.year_formed = year_formed;
//    }

    @Override
    public String toString () {
        return "Authoring_Entities - email: " + this.email + ", authoring_entity_type: " +
                ", Name: " + this.name;
    }
}
