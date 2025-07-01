package application.controllers.employe;

import javafx.beans.property.SimpleStringProperty;

public class Affectation {
	private final SimpleStringProperty poste;
    private final SimpleStringProperty date_debut;
    private final SimpleStringProperty date_fin;
    private final SimpleStringProperty salaire_negocie;

    public Affectation(String poste, String date_debut, String date_fin, String salaire_negocie) {
        this.poste = new SimpleStringProperty(poste);
        this.date_debut = new SimpleStringProperty(date_debut);
        this.date_fin = new SimpleStringProperty(date_fin);
        this.salaire_negocie = new SimpleStringProperty(salaire_negocie);
    }

    // Getters
    public String getPoste() {
        return poste.get();
    }

    public String getDate_debut() {
        return date_debut.get();
    }

    public String getDate_fin() {
        return date_fin.get();
    }

    public String getSalaire_negocie() {
        return salaire_negocie.get();
    }

    // Setters
    public void setPoste(String value) {
    	poste.set(value);
    }

    public void setDate_debut(String value) {
    	date_debut.set(value);
    }

    public void setDate_fin(String value) {
    	date_fin.set(value);
    }

    public void setSalaire_negocie(String value) {
    	salaire_negocie.set(value);
    }

    // Property Getters
    public SimpleStringProperty posteProperty() {
        return poste;
    }

    public SimpleStringProperty date_debutProperty() {
        return date_debut;
    }

    public SimpleStringProperty date_finProperty() {
        return date_fin;
    }

    public SimpleStringProperty salaire_negocieProperty() {
        return salaire_negocie;
    }
}
