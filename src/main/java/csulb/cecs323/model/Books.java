package csulb.cecs323.model;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames =
                {"title", "publisher_name"}),
                @UniqueConstraint(columnNames =
                {"title", "authoring_entity_name"})})

public class Books {
    @Id
    @Column(nullable = false, length = 17)
    private String ISBN;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false)
    private int year_published;

    @OneToOne
    @JoinColumn(name = "authoring_entity_name")
    private Authoring_Entities entity_name;

    @ManyToOne
    //@Column(length = 80)
    @JoinColumn(name = "publisher_name")
    private Publisher publisher_name_books;

    public Books(String ISBN, String title, int year_published, Authoring_Entities authoring_entity_name, Publisher publisher_name) {
        this.ISBN = ISBN;
        this.title = title;
        this.year_published = year_published;
        this.entity_name = authoring_entity_name;
        this.publisher_name_books = publisher_name;
    }

    public Books() {

    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear_published() {
        return year_published;
    }

    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }

    public Authoring_Entities getAuthoring_entity_name() {
        return entity_name;
    }

    public void setAuthoring_entity_name(Authoring_Entities authoring_entity_name) {
        this.entity_name = authoring_entity_name;
    }

    public Publisher getPublisher_name() {
        return publisher_name_books;
    }

    public void setPublisher_name(Publisher publisher_name) {
        this.publisher_name_books = publisher_name;
    }

    @Override
    public String toString() {
        return "Books{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", year_published=" + year_published +
                ", authoring_entity_name='" + entity_name + '\'' +
                ", publisher_name='" + publisher_name_books + '\'' +
                '}';
    }
}
