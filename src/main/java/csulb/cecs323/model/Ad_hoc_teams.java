package csulb.cecs323.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

@NamedNativeQuery(
        name = "ReturnAllAdHocTeams",
        query = "SELECT * " +
                "FROM   authoring_entities " +
                "WHERE authoring_entities_type = 'Ad Hoc Teams' " +
                "ORDER BY email, name ",
            resultClass = Ad_hoc_teams.class)
@NamedNativeQuery(
        name = "ReturnAllAdHocTeamMembers",
        query = "SELECT * " +
                "FROM ad_hoc_teams_member ad inner join authoring_entities au " +
                "on  ad.ad_hoc_teams_email = au.email " +
                "and ad.individual_author_email = au.email" +
                "ORDER BY individual_authors_email ",
        resultClass = Authoring_Entities.class)
@DiscriminatorValue("Ad Hoc Teams")
/**
 * A Collection of individual authors
 */
public class Ad_hoc_teams extends Authoring_Entities{
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Ad_hoc_teams_member",
            joinColumns = @JoinColumn(name = "ad_hoc_teams_email"),
            inverseJoinColumns = @JoinColumn(name = "individual_authors_email")
    )
    private List<Individual_authors> individual_authors;

    /**
     *Constructor for all private variables in Ad_hoc_teams
     * @param email
     * @param name
     */
    public Ad_hoc_teams(String email, String name) {
        super(email, name);
        this.individual_authors = new ArrayList<>();
    }

    /**
     *Default constructor
     */
    public Ad_hoc_teams() {

    }

    /**
     * A method to add individual authors
     * @param individual_author
     */
    public void add_individual_authors(Individual_authors individual_author){
        if(! this.individual_authors.contains(individual_author)){
            this.individual_authors.add(individual_author);
            individual_author.add_ad_hoc_teams(this);
        }
    }

    /**
     * A method to remove an individual author
     * @param individual_author
     */
    public void remove_individual_authors(Individual_authors individual_author){
        if(this.individual_authors.contains(individual_author)){
            this.individual_authors.remove(individual_author);
            individual_author.remove_ad_hoc_teams(this);
        }
    }

    /**
     * A get method that returns the list of individual authors
     * @return          individual_authors
     */
    public List<Individual_authors> getIndividual_authors() {
        return individual_authors;
    }

    /**
     * A set method for Ad_hoc_teams
     * @param individual_authors
     */
    public void setIndividual_authors(List<Individual_authors> individual_authors) {
        this.individual_authors = new ArrayList<Individual_authors>();
    }

//    @Override
//    public String toString() {
//        return "Ad_hoc_teams{" +
//                "individual_authors=" + individual_authors +
//                '}';
//    }
}
