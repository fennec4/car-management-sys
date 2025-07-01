package application.controllers.employe;

import javafx.beans.property.SimpleStringProperty;

public class Prime {
	private final SimpleStringProperty poste;
	private final SimpleStringProperty prime;
	private final SimpleStringProperty type_prime;
    private final SimpleStringProperty date_debut;
    private final SimpleStringProperty date_fin;
    private final SimpleStringProperty commentaire;

    public Prime(String poste,String prime, String type_prime,
    		String date_debut, String date_fin, String commentaire) {
        this.poste = new SimpleStringProperty(poste);
        this.prime = new SimpleStringProperty(prime);
        this.type_prime = new SimpleStringProperty(type_prime);
        this.date_debut = new SimpleStringProperty(date_debut);
        this.date_fin = new SimpleStringProperty(date_fin);
        this.commentaire = new SimpleStringProperty(commentaire);
    }

    // Getters
    public String getPoste() {
        return poste.get();
    }
    
    public String getPrime() {
    	return prime.get();
    }
    
    public String getType_prime() {
    	return type_prime.get();
    }

    public String getDate_debut() {
        return date_debut.get();
    }

    public String getDate_fin() {
        return date_fin.get();
    }

    public String getCommentaire() {
        return commentaire.get();
    }

    // Setters
    public void setPoste(String value) {
    	poste.set(value);
    }
    
    public void setPrime(String value) {
    	prime.set(value);
    }
    
    public void setType_prime(String value) {
    	type_prime.set(value);
    }

    public void setDate_debut(String value) {
    	date_debut.set(value);
    }

    public void setDate_fin(String value) {
    	date_fin.set(value);
    }

    public void setCommentaire(String value) {
    	commentaire.set(value);
    }

    // Property Getters
    public SimpleStringProperty posteProperty() {
        return poste;
    }
    
    public SimpleStringProperty primeProperty() {
    	return prime;
    }
    
    public SimpleStringProperty type_primeProperty() {
    	return type_prime;
    }

    public SimpleStringProperty date_debutProperty() {
        return date_debut;
    }

    public SimpleStringProperty date_finProperty() {
        return date_fin;
    }

    public SimpleStringProperty commentaireProperty() {
        return commentaire;
    }
}
