package application.controllers.employe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Horaire {
	
	 	private final IntegerProperty jour_semaine;
	    private final SimpleStringProperty jour_nom;
	    private final SimpleStringProperty heure_debut;
	    private final SimpleStringProperty heure_fin;

	    public Horaire(int jour_semaine, String jour_nom, String heure_debut, String heure_fin) {
	        this.jour_semaine = new SimpleIntegerProperty(jour_semaine);
	        this.jour_nom = new SimpleStringProperty(jour_nom);
	        this.heure_debut = new SimpleStringProperty(heure_debut);
	        this.heure_fin = new SimpleStringProperty(heure_fin);
	    }

	    // Getters
	    public int getJour_semaine() {
	        return jour_semaine.get();
	    }

	    public String getJour_nom() {
	        return jour_nom.get();
	    }

	    public String getHeure_debut() {
	        return heure_debut.get();
	    }

	    public String getHeure_fin() {
	        return heure_fin.get();
	    }

	    // Setters
	    public void setJour_semaine(int value) {
	        jour_semaine.set(value);
	    }

	    public void setHeure_debut(String value) {
	        heure_debut.set(value);
	    }

	    public void setHeure_fin(String value) {
	        heure_fin.set(value);
	    }

	    public void setJour_nom(String value) {
	        jour_nom.set(value);
	    }

	    // Property Getters
	    public IntegerProperty jour_semaineProperty() {
	        return jour_semaine;
	    }

	    public SimpleStringProperty jour_nomProperty() {
	        return jour_nom;
	    }

	    public SimpleStringProperty heure_debutProperty() {
	        return heure_debut;
	    }

	    public SimpleStringProperty heure_finProperty() {
	        return heure_fin;
	    }
}
