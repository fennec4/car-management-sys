package application.controllers.stock;

import javafx.beans.property.SimpleStringProperty;

public class Depot {
	
	private final SimpleStringProperty num;
    private final SimpleStringProperty rayon;
    private final SimpleStringProperty etagere;
    private final SimpleStringProperty localisation;
    private final SimpleStringProperty description;

    // Constructeur
    public Depot(String num, String rayon, String etagere, String localisation, String description) {
        this.num = new SimpleStringProperty(num);
        this.rayon = new SimpleStringProperty(rayon);
        this.etagere = new SimpleStringProperty(etagere);
        this.localisation = new SimpleStringProperty(localisation);
        this.description = new SimpleStringProperty(description);
    }

    // Getters
    public String getNum() {
        return num.get();
    }

    public String getRayon() {
        return rayon.get();
    }

    public String getEtagere() {
        return etagere.get();
    }

    public String getLocalisation() {
        return localisation.get();
    }

    public String getDescription() {
        return description.get();
    }

    // Setters
    public void setNum(String num) {
        this.num.set(num);
    }

    public void setRayon(String rayon) {
        this.rayon.set(rayon);
    }

    public void setEtagere(String etagere) {
        this.etagere.set(etagere);
    }

    public void setLocalisation(String localisation) {
        this.localisation.set(localisation);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    // Property getters
    public SimpleStringProperty numProperty() {
        return num;
    }

    public SimpleStringProperty rayonProperty() {
        return rayon;
    }

    public SimpleStringProperty etagereProperty() {
        return etagere;
    }

    public SimpleStringProperty localisationProperty() {
        return localisation;
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }
}
