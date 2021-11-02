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
/**
 * A group of writers of a book
 */
public class Writing_groups extends Authoring_Entities {
    @Column(nullable = true, length = 80)
    private String head_writer;

    @Column(nullable = true)
    private int year_formed;

    /**
     * A constructor with all the private variables of Writing_groups
     * @param email
     * @param name
     * @param head_writer
     * @param year_formed
     */
    public Writing_groups(String email, String name, String head_writer, int year_formed) {
        super(email, name);
        this.head_writer = head_writer;
        this.year_formed = year_formed;
    }

    /**
     * Default Constructor
     */
    public Writing_groups() {
    }

    /**
     * Get method for head_writer
     * @return          head_writer
     */
    public String getHead_writer() {
        return head_writer;
    }

    /**
     * Set method for head_writer
     * @param head_writer
     */
    public void setHead_writer(String head_writer) {
        this.head_writer = head_writer;
    }

    /**
     * Get method for year_formed
     * @return
     */
    public int getYear_formed() {
        return year_formed;
    }

    /**
     * Set method for year_formed
     * @param year_formed
     */
    public void setYear_formed(int year_formed) {
        this.year_formed = year_formed;
    }

    /**
     * toString method for Writing groups
     * @return
     */
    @Override
    public String toString() {
        return "Writing_groups{" +
                "head_writer='" + head_writer + '\'' +
                ", year_formed=" + year_formed +
                '}';
    }
}
