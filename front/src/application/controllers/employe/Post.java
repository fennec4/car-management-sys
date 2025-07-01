package application.controllers.employe;

import javafx.beans.property.SimpleStringProperty;

public class Post {

	private final SimpleStringProperty titre ;
    private final SimpleStringProperty salaire_base;
    private final SimpleStringProperty description_poste;
    
    public Post(String titre , String salaire_base,
    		String description_poste) {
    	this.titre = new SimpleStringProperty(titre);
    	this.salaire_base = new SimpleStringProperty(salaire_base);
    	this.description_poste = new SimpleStringProperty(description_poste);
    }
    
    public String getTitre() { return titre.get(); }
    public String getSalaire_base() { return salaire_base.get(); }
    public String getDescription_poste() { return description_poste.get(); }
    
    public void setTitre(String value) { titre.set(value); }
    public void setSalaire_base(String value) { salaire_base.set(value); }
    public void setDescription_poste(String value) { description_poste.set(value); }
    
    public SimpleStringProperty TitreProperty() { return titre; }
    public SimpleStringProperty Salaire_baseProperty() { return salaire_base; }
    public SimpleStringProperty Description_posteProperty() { return description_poste; }


}
