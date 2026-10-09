package entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@XmlRootElement
public class Etudiant {
    private String identifiant;
    private String nom;
    private String prenom;
    // @XmlTransient (sur le getter) masque l'option dans le XML ;
    // @JsonProperty la conserve dans le JSON (Jackson honore aussi les annotations JAXB).
    @JsonProperty("option")
    private Option option;
    private int anneeEtude;
    private String email;

    public Etudiant() {}

    public Etudiant(String identifiant, String nom, String prenom, Option option, int anneeEtude, String email) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.option = option;
        this.anneeEtude = anneeEtude;
        this.email = email;
    }

    // Getters et Setters
    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @XmlTransient
    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public int getAnneeEtude() {
        return anneeEtude;
    }

    public void setAnneeEtude(int anneeEtude) {
        this.anneeEtude = anneeEtude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etudiant etudiant = (Etudiant) o;
        return Objects.equals(identifiant, etudiant.identifiant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiant);
    }
}
