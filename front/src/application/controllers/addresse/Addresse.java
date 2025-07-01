package application.controllers.addresse;

import javafx.beans.property.SimpleStringProperty;

public class Addresse {

	private final SimpleStringProperty pays;
    private final SimpleStringProperty wilaya;
    private final SimpleStringProperty codeWilaya;
    private final SimpleStringProperty daira;
    private final SimpleStringProperty commune;
    private final SimpleStringProperty codeCommune;
    private final SimpleStringProperty rue;
    private final SimpleStringProperty designation;

    // Constructeur
    public Addresse(String pays, String wilaya, String codeWilaya, String daira,
                    String commune, String codeCommune, String rue, String designation) {
        this.pays = new SimpleStringProperty(pays);
        this.wilaya = new SimpleStringProperty(wilaya);
        this.codeWilaya = new SimpleStringProperty(codeWilaya);
        this.daira = new SimpleStringProperty(daira);
        this.commune = new SimpleStringProperty(commune);
        this.codeCommune = new SimpleStringProperty(codeCommune);
        this.rue = new SimpleStringProperty(rue);
        this.designation = new SimpleStringProperty(designation);
    }

    // Getters classiques
    public String getpays() { return pays.get(); }
    public String getwilaya() { return wilaya.get(); }
    public String getcodeWilaya() { return codeWilaya.get(); }
    public String getdaira() { return daira.get(); }
    public String getcommune() { return commune.get(); }
    public String getcodeCommune() { return codeCommune.get(); }
    public String getrue() { return rue.get(); }
    public String getdesignation() { return designation.get(); }

    // Setters classiques
    public void setpays(String value) { pays.set(value); }
    public void setwilaya(String value) { wilaya.set(value); }
    public void setcodeWilaya(String value) { codeWilaya.set(value); }
    public void setdaira(String value) { daira.set(value); }
    public void setcommune(String value) { commune.set(value); }
    public void setcodeCommune(String value) { codeCommune.set(value); }
    public void setrue(String value) { rue.set(value); }
    public void setdesignation(String value) { designation.set(value); }

    // Property getters (pour JavaFX bindings/TableView)
    public SimpleStringProperty paysProperty() { return pays; }
    public SimpleStringProperty wilayaProperty() { return wilaya; }
    public SimpleStringProperty codeWilayaProperty() { return codeWilaya; }
    public SimpleStringProperty dairaProperty() { return daira; }
    public SimpleStringProperty communeProperty() { return commune; }
    public SimpleStringProperty codeCommuneProperty() { return codeCommune; }
    public SimpleStringProperty rueProperty() { return rue; }
    public SimpleStringProperty designationProperty() { return designation; }

	}

        

