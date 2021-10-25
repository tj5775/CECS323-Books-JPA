package csulb.cecs323.model;

import javax.persistence.*;

@Entity
public class Ad_Hoc_Teams_Member {
    @Id
    @ManyToOne
    @JoinColumn(name = "individual_authors_email", referencedColumnName = "email")
    private Authoring_Entities individual_email;

    @Id
    @ManyToOne
    @JoinColumn(name = "ad_hoc_teams_email", referencedColumnName = "email")
    private Authoring_Entities teams_email;

    public Ad_Hoc_Teams_Member(Authoring_Entities individual_authors_email, Authoring_Entities ad_hoc_teams_email) {
        this.individual_email = individual_authors_email;
        this.teams_email = ad_hoc_teams_email;
    }

    public Ad_Hoc_Teams_Member() {

    }

    public Authoring_Entities getIndividual_authors_email() {
        return individual_email;
    }

    public void setIndividual_authors_email(Authoring_Entities individual_authors_email) {
        this.individual_email = individual_authors_email;
    }

    public Authoring_Entities getAd_hoc_teams_email() {
        return teams_email;
    }

    public void setAd_hoc_teams_email(Authoring_Entities ad_hoc_teams_email) {
        this.teams_email = ad_hoc_teams_email;
    }

    @Override
    public String toString() {
        return "Ad_Hoc_Teams_Member{" +
                "individual_authors_email='" + individual_email + '\'' +
                ", ad_hoc_teams_email='" + teams_email + '\'' +
                '}';
    }
}
