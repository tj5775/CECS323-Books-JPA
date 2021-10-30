package csulb.cecs323.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Individual Authors")
public class Individual_authors extends Authoring_Entities{

    @ManyToMany(mappedBy = "individual_authors",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Ad_hoc_teams> ad_hoc_teams;

    public Individual_authors(String email, String name, List<Ad_hoc_teams> ad_hoc_teams) {
        super(email, name);
        this.ad_hoc_teams = new ArrayList<Ad_hoc_teams>();
    }

    public Individual_authors() {
    }

    public List<Ad_hoc_teams> getAd_hoc_teams() {
        return ad_hoc_teams;
    }

    public void setAd_hoc_teams(List<Ad_hoc_teams> ad_hoc_teams) {
        this.ad_hoc_teams = ad_hoc_teams;
    }

    public void add_ad_hoc_teams (Ad_hoc_teams team){
        if(! this.ad_hoc_teams.contains(team)){
            this.ad_hoc_teams.add(team);
            team.add_individual_authors(this);
        }
    }

    public void remove_ad_hoc_teams (Ad_hoc_teams team){
        if(this.ad_hoc_teams.contains(team)){
            this.ad_hoc_teams.remove(team);
            team.remove_individual_authors(this);
        }
    }

    @Override
    public String toString() {
        return "individual_authors{" +
                "ad_hoc_teams=" + ad_hoc_teams +
                '}';
    }
}