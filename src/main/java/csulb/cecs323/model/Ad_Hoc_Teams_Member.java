package csulb.cecs323.model;

import javax.persistence.*;

@Entity
public class Ad_Hoc_Teams_Member {
    @Id
    //@ManyToOne // Not sure
    @Column(nullable = false, length = 30)
    //@JoinColumn(name = "email", referencedColumnName = "email")
    private String individual_authors_email;

    @Id
    @Column(nullable = false, length = 30)
    private String ad_hoc_teams_email;

    public Ad_Hoc_Teams_Member(String individual_authors_email, String ad_hoc_teams_email) {
        this.individual_authors_email = individual_authors_email;
        this.ad_hoc_teams_email = ad_hoc_teams_email;
    }

    public Ad_Hoc_Teams_Member() {

    }

    public String getIndividual_authors_email() {
        return individual_authors_email;
    }

    public void setIndividual_authors_email(String individual_authors_email) {
        this.individual_authors_email = individual_authors_email;
    }

    public String getAd_hoc_teams_email() {
        return ad_hoc_teams_email;
    }

    public void setAd_hoc_teams_email(String ad_hoc_teams_email) {
        this.ad_hoc_teams_email = ad_hoc_teams_email;
    }

    @Override
    public String toString() {
        return "Ad_Hoc_Teams_Member{" +
                "individual_authors_email='" + individual_authors_email + '\'' +
                ", ad_hoc_teams_email='" + ad_hoc_teams_email + '\'' +
                '}';
    }
}
