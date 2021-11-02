package csulb.cecs323.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedNativeQuery(
        name="ReturnAllIndividualAuthors",
        query = "SELECT * " +
                "FROM   authoring_entities " +
                "WHERE authoring_entities_type = 'Individual Authors' " +
                "ORDER BY email, name",
        resultClass = Individual_authors.class
)
@DiscriminatorValue("Individual Authors")
/**
 * An individual person writing a book
 */
public class Individual_authors extends Authoring_Entities{

    @ManyToMany(mappedBy = "individual_authors",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Ad_hoc_teams> ad_hoc_teams;

    /**
     * Constructor for all the private variables in Individual_authors
     * @param email
     * @param name
     */
    public Individual_authors(String email, String name) {
        super(email, name);
        this.ad_hoc_teams = new ArrayList<Ad_hoc_teams>();
    }

    /**
     * Default Constructor
     */
    public Individual_authors() {
    }

    /**
     * Get method for ad_hoc_teams
     * @return
     */
    public List<Ad_hoc_teams> getAd_hoc_teams() {
        return ad_hoc_teams;
    }

    /**
     * Set method for ad_hoc_teams
     * @param ad_hoc_teams
     */
    public void setAd_hoc_teams(List<Ad_hoc_teams> ad_hoc_teams) {
        this.ad_hoc_teams = ad_hoc_teams;
    }

    /**
     * A method that adds a team to the ad_hoc_teams
     * @param team
     */
    public void add_ad_hoc_teams (Ad_hoc_teams team){
        if(! this.ad_hoc_teams.contains(team)){
            this.ad_hoc_teams.add(team);
            team.add_individual_authors(this);
        }
    }

    /**
     * A method that removes a team from othe ad_hoc_teams
     * @param team
     */
    public void remove_ad_hoc_teams (Ad_hoc_teams team){
        if(this.ad_hoc_teams.contains(team)){
            this.ad_hoc_teams.remove(team);
            team.remove_individual_authors(this);
        }
    }

//    @Override
//    public String toString() {
//        return "individual_authors{" +
//                "ad_hoc_teams=" + ad_hoc_teams +
//                '}';
//    }
}
