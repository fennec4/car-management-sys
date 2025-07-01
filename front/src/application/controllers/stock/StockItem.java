package application.controllers.stock;

import javafx.beans.property.SimpleStringProperty;

public class StockItem {
	
    private final SimpleStringProperty reference;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty description; 
    private final SimpleStringProperty quantite;
    private final SimpleStringProperty prix;
    private final SimpleStringProperty Emplacement;    

    public StockItem(String reference, String nom, 
                    String quantite, String prix, String Emplacement,
                    String description) {
    	
    	this.reference = new SimpleStringProperty(reference);
        this.nom = new SimpleStringProperty(nom);               
        this.quantite = new SimpleStringProperty(quantite);
        this.prix = new SimpleStringProperty(prix);
        this.Emplacement = new SimpleStringProperty(Emplacement);
        this.description = new SimpleStringProperty(description); 
        
    }

    // Getters
    
    public String getReference() { return reference.get(); }
    public String getNom() { return nom.get(); }
    public String getDescription() { return description.get(); }   
    public String getQuantite() { return quantite.get(); }
    public String getPrix() { return prix.get(); }
    public String getEmplacement() { return Emplacement.get(); }
   

    // Setters
   
    public void setReference(String value) { reference.set(value); }
    public void setNom(String value) { nom.set(value); }
    public void setDescription(String value) { description.set(value); }
    public void setQuantite(String value) { quantite.set(value); }
    public void setPrix(String value) { prix.set(value); }
    public void setEmplacement(String value) { Emplacement.set(value); }
    

    // Property getters

    public SimpleStringProperty referenceProperty() { return reference; }
    public SimpleStringProperty nomProperty() { return nom; }
    public SimpleStringProperty descriptionProperty() { return description; }   
    public SimpleStringProperty quantiteProperty() { return quantite; }
    public SimpleStringProperty prixProperty() { return prix; }
    public SimpleStringProperty EmplacementProperty() { return Emplacement; }
    
}