package application.controllers.vehicule;

import javafx.beans.property.SimpleStringProperty;

public class vehicule {
	
    private final SimpleStringProperty proprietaire;
    private final SimpleStringProperty couleur;
    private final SimpleStringProperty kilometrage;
    private final SimpleStringProperty num_chassi;
    private final SimpleStringProperty matricule;
    private final SimpleStringProperty marque;
    private final SimpleStringProperty modele;
    private final SimpleStringProperty carburant;
    private final SimpleStringProperty annee_fabrication;

    // constructeur
    public vehicule(String proprietaire, String couleur, String kilometrage,
                    String num_chassi, String matricule, String marque,
                    String modele, String carburant, String annee_fabrication) {
        this.proprietaire = new SimpleStringProperty(proprietaire);
        this.couleur = new SimpleStringProperty(couleur);
        this.kilometrage = new SimpleStringProperty(kilometrage); // Stocké comme String pour éviter des erreurs d'affichage
        this.num_chassi = new SimpleStringProperty(num_chassi);
        this.matricule = new SimpleStringProperty(matricule);
        this.marque = new SimpleStringProperty(marque);
        this.modele = new SimpleStringProperty(modele);
        this.carburant = new SimpleStringProperty(carburant);
        this.annee_fabrication = new SimpleStringProperty(annee_fabrication);
    }

    // Getters
    public String getproprietaire() { return proprietaire.get(); }
    public String getcouleur() { return couleur.get(); }
    public String getkilometrage() { return kilometrage.get(); }
    public String getnum_chassi() { return num_chassi.get(); }
    public String getmatricule() { return matricule.get(); }
    public String getmarque() { return marque.get(); }
    public String getmodele() { return modele.get(); }
    public String getcarburant() { return carburant.get(); }
    public String getannee_fabrication() { return annee_fabrication.get(); }

    // Setters
    public void setproprietaire(String value) { proprietaire.set(value); }
    public void setcouleur(String value) { couleur.set(value); }
    public void setkilometrage(String value) { kilometrage.set(value); }
    public void setnum_chassi(String value) { num_chassi.set(value); }
    public void setmatricule(String value) { matricule.set(value); }
    public void setmarque(String value) { marque.set(value); }
    public void setmodele(String value) { modele.set(value); }
    public void setcarburant(String value) { carburant.set(value); }
    public void setannee_fabrication(String value) { annee_fabrication.set(value); }

    // Property Getters (pour TableView)
    public SimpleStringProperty proprietaireProperty() { return proprietaire; }
    public SimpleStringProperty couleurProperty() { return couleur; }
    public SimpleStringProperty kilometrageProperty() { return kilometrage; }
    public SimpleStringProperty num_chassiProperty() { return num_chassi; }
    public SimpleStringProperty matriculeProperty() { return matricule; }
    public SimpleStringProperty marqueProperty() { return marque; }
    public SimpleStringProperty modeleProperty() { return modele; }
    public SimpleStringProperty carburantProperty() { return carburant; }
    public SimpleStringProperty annee_fabricationProperty() { return annee_fabrication; }
}
