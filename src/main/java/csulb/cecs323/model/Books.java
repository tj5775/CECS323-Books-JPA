package csulb.cecs323.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames =
                {"title", "publisher_name"}),
                @UniqueConstraint(columnNames =
                {"title", "authoring_entity_name"})})
@NamedNativeQuery(
        name="ReturnAllBooks",
        query = "SELECT * " +
                "FROM   BOOKS",
        resultClass = Books.class
)
/**
 * A collection of papers that has information written in it
 */
public class Books {
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ISBN", referencedColumnName = "ISBN", nullable = false)
//    private List<Publisher> publisher;

    @Id
    @Column(nullable = false, length = 17)
    private String ISBN;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false)
    private int year_published;

    @ManyToOne
    @JoinColumn(name = "authoring_entity_name")
    private Authoring_Entities entity_name;

    @ManyToOne
    //@Column(length = 80)
    @JoinColumn(name = "publisher_name")
    private Publisher publisher_name_books;

    /**
     * A constructor that has all the private variables of Books
     * @param ISBN
     * @param title
     * @param year_published
     * @param authoring_entity_name
     * @param publisher_name
     */
    public Books(String ISBN, String title, int year_published, Authoring_Entities authoring_entity_name, Publisher publisher_name) {
        this.ISBN = ISBN;
        this.title = title;
        this.year_published = year_published;
        this.entity_name = authoring_entity_name;
        this.publisher_name_books = publisher_name;
    }

    /**
     * Default Constructor
     */
    public Books() {

    }

    /**
     * Get method for ISBN
     * @return          ISBN
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Set method for ISBN
     * @param           ISBN
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Get Method for title
     * @return          title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set Method for title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get method for year_published
     * @return          year_published
     */
    public int getYear_published() {
        return year_published;
    }

    /**
     * Set method for year_published
     * @param year_published
     */
    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }

    /**
     * Get method for entity_name
     * @return
     */
    public Authoring_Entities getAuthoring_entity_name() {
        return entity_name;
    }

    /**
     * Set method for authoring_entity_name
     * @param authoring_entity_name
     */
    public void setAuthoring_entity_name(Authoring_Entities authoring_entity_name) {
        this.entity_name = authoring_entity_name;
    }

    /**
     * Get method for publisher_name_books
     * @return          publisher_name_books
     */
    public Publisher getPublisher_name() {
        return publisher_name_books;
    }

    /**
     * Set method for publisher_name_books
     * @param publisher_name
     */
    public void setPublisher_name(Publisher publisher_name) {
        this.publisher_name_books = publisher_name;
    }

    /**
     * toString method for Books
     * @return
     */
    @Override
    public String toString() {
        return "Books{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", year_published=" + year_published +
                ", entity_name=" + entity_name +
                ", publisher_name_books=" + publisher_name_books +
                '}';
    }
}
