package csulb.cecs323.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Books {
    @Id
    @Column(nullable = false, length = 17)
    private String ISBN;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false)
    private int year_published;

    @Column(length = 30)
    private String authoring_entity_name;

    @Column(length = 80)
    private String publisher_name;

    public Books(String ISBN, String title, int year_published, String authoring_entity_name, String publisher_name) {
        this.ISBN = ISBN;
        this.title = title;
        this.year_published = year_published;
        this.authoring_entity_name = authoring_entity_name;
        this.publisher_name = publisher_name;
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

    public String getAuthoring_entity_name() {
        return authoring_entity_name;
    }

    public void setAuthoring_entity_name(String authoring_entity_name) {
        this.authoring_entity_name = authoring_entity_name;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    @Override
    public String toString() {
        return "Books{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", year_published=" + year_published +
                ", authoring_entity_name='" + authoring_entity_name + '\'' +
                ", publisher_name='" + publisher_name + '\'' +
                '}';
    }
}
