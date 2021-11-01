package csulb.cecs323.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(
        name="ReturnAllWritingGroups",
        query = "SELECT * " +
                "FROM   authoring_entities " +
                "WHERE authoring_entities_type = 'Writing Groups' " +
                "ORDER BY email, name",
        resultClass = Writing_groups.class
)
@DiscriminatorValue("Writing Groups")
public class Writing_groups extends Authoring_Entities {
    @Column(nullable = true, length = 80)
    private String head_writer;

    @Column(nullable = true)
    private int year_formed;

    public Writing_groups(String email, String name, String head_writer, int year_formed) {
        super(email, name);
        this.head_writer = head_writer;
        this.year_formed = year_formed;
    }

    public Writing_groups() {
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
    public String toString() {
        return "Writing_groups{" +
                "head_writer='" + head_writer + '\'' +
                ", year_formed=" + year_formed +
                '}';
    }
}
