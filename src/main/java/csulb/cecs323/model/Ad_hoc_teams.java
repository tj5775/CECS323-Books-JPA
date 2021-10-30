package csulb.cecs323.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Ad Hoc Teams")
public class Ad_hoc_teams extends Authoring_Entities{
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Ad_hoc_teams_member",
            joinColumns = @JoinColumn(name = "ad_hoc_teams_email"),
            inverseJoinColumns = @JoinColumn(name = "individual_authors_email")
    )
    private List<Individual_authors> individual_authors;

    public Ad_hoc_teams(String email, String name, List<Authoring_Entities> individual_authors) {
        super(email, name);
        this.individual_authors = new ArrayList<Individual_authors>();
    }

    public Ad_hoc_teams() {

    }
    public void add_individual_authors(Individual_authors individual_author){
        if(! this.individual_authors.contains(individual_author)){
            this.individual_authors.add(individual_author);
            individual_author.add_ad_hoc_teams(this);
        }
    }

    public void remove_individual_authors(Individual_authors individual_author){
        if(this.individual_authors.contains(individual_author)){
            this.individual_authors.remove(individual_author);
            individual_author.remove_ad_hoc_teams(this);
        }
    }

    public List<Individual_authors> getIndividual_authors() {
        return individual_authors;
    }

    public void setIndividual_authors(List<Individual_authors> individual_authors) {
        this.individual_authors = new ArrayList<Individual_authors>();
    }

    @Override
    public String toString() {
        return "Ad_hoc_teams{" +
                "individual_authors=" + individual_authors +
                '}';
    }
}