package application.controllers.employe;

import javafx.beans.property.SimpleStringProperty;

public class Employe {
	
	private final SimpleStringProperty Nom ;
    private final SimpleStringProperty Prenom;
    private final SimpleStringProperty Email;
    private final SimpleStringProperty Phone;
    private final SimpleStringProperty Statut;
    private final SimpleStringProperty Matricule;
    private final SimpleStringProperty Num_securite_sociale;
    private final SimpleStringProperty Heures_travail_par_jour;
    private final SimpleStringProperty Addresse;
    private final SimpleStringProperty Lieu_naissance;
    private final SimpleStringProperty Date_naissance;
    private final SimpleStringProperty Date_recrutement;
    private final SimpleStringProperty Details;
    
 // Constructeur
    public Employe(String Nom, String Prenom, String Email, String Phone, String Statut,
    		String Matricule, String Num_securite_sociale, String Heures_travail_par_jour,
    		String Addresse, String Lieu_naissance, String Date_naissance,
    		String Date_recrutement, String Details) {
        this.Nom = new SimpleStringProperty(Nom);
        this.Prenom = new SimpleStringProperty(Prenom);
        this.Email = new SimpleStringProperty(Email);
        this.Statut = new SimpleStringProperty(Statut);
        this.Phone = new SimpleStringProperty(Phone);
        this.Matricule = new SimpleStringProperty(Matricule);
        this.Num_securite_sociale = new SimpleStringProperty(Num_securite_sociale);
        this.Heures_travail_par_jour = new SimpleStringProperty(Heures_travail_par_jour);
        this.Addresse = new SimpleStringProperty(Addresse);
        this.Lieu_naissance = new SimpleStringProperty(Lieu_naissance);
        this.Date_naissance = new SimpleStringProperty(Date_naissance);
        this.Date_recrutement = new SimpleStringProperty(Date_recrutement);
        this.Details = new SimpleStringProperty(Details);
    }
    
 // Getters
    public String getNom() { return Nom.get(); }
    public String getPrenom() { return Prenom.get(); }
    public String getEmail() { return Email.get(); }
    public String getPhone() { return Phone.get(); }
    public String getStatut() { return Statut.get(); }
    public String getMatricule() { return Matricule.get(); }
    public String getNum_securite_sociale() { return Num_securite_sociale.get(); }
    public String getHeures_travail_par_jour() { return Heures_travail_par_jour.get(); }
    public String getAddresse() { return Addresse.get(); }
    public String getLieu_naissance() { return Lieu_naissance.get(); }
    public String getDate_naissance() { return Date_naissance.get(); }
    public String getDate_recrutement() { return Date_recrutement.get(); }
    public String getDetails() { return Details.get(); }

    // Setters
    public void setNom(String value) { Nom.set(value); }
    public void setPrenom(String value) { Prenom.set(value); }
    public void setEmail(String value) { Email.set(value); }
    public void setPhone(String value) { Phone.set(value); }
    public void setStatut(String value) { Statut.set(value); }
    public void setMatricule(String value) { Matricule.set(value); }
    public void setNum_securite_sociale(String value) { Num_securite_sociale.set(value); }
    public void setHeures_travail_par_jour(String value) { Heures_travail_par_jour.set(value); }
    public void setAddresse(String value) { Addresse.set(value); }
    public void setLieu_naissance(String value) { Lieu_naissance.set(value); }
    public void setDate_naissance(String value) { Date_naissance.set(value); }
    public void setDate_recrutement(String value) { Date_recrutement.set(value); }
    public void setDetails(String value) { Details.set(value); }

    // Property Getters
    public SimpleStringProperty NomProperty() { return Nom; }
    public SimpleStringProperty PrenomProperty() { return Prenom; }
    public SimpleStringProperty EmailProperty() { return Email; }
    public SimpleStringProperty PhoneProperty() { return Phone; }
    public SimpleStringProperty StatutProperty() { return Statut; }
    public SimpleStringProperty MatriculeProperty() { return Matricule; }
    public SimpleStringProperty Num_securite_socialeProperty() { return Num_securite_sociale; }
    public SimpleStringProperty Heures_travail_par_jourProperty() { return Heures_travail_par_jour; }
    public SimpleStringProperty AddresseProperty() { return Addresse; }
    public SimpleStringProperty Lieu_naissanceProperty() { return Lieu_naissance; }
    public SimpleStringProperty Date_naissanceProperty() { return Date_naissance; }
    public SimpleStringProperty Date_recrutementProperty() { return Date_recrutement; }
    public SimpleStringProperty DetailsProperty() { return Details; }
}
