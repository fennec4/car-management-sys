package application.controllers.rendezvous;

import javafx.beans.property.SimpleStringProperty;

public class Rendezvous {
	
	 	private final SimpleStringProperty date_rdv;
	    private final SimpleStringProperty heur_rdv;
	    private final SimpleStringProperty client_rdv;
	    private final SimpleStringProperty employe_rdv;
	    private final SimpleStringProperty vehicule_rdv;
	    private final SimpleStringProperty detail_rdv;

	    // Constructeur
	    public Rendezvous(String date_rdv, String heur_rdv, String client_rdv,
	                      String employe_rdv, String vehicule_rdv, String detail_rdv) {

	        this.date_rdv = new SimpleStringProperty(date_rdv);
	        this.heur_rdv = new SimpleStringProperty(heur_rdv);
	        this.client_rdv = new SimpleStringProperty(client_rdv); 
	        this.employe_rdv = new SimpleStringProperty(employe_rdv);
	        this.vehicule_rdv = new SimpleStringProperty(vehicule_rdv);
	        this.detail_rdv = new SimpleStringProperty(detail_rdv);
	    }

	    // Getters
	    public String getDate_rdv() { return date_rdv.get(); }
	    public String getHeur_rdv() { return heur_rdv.get(); }
	    public String getClient_rdv() { return client_rdv.get(); }
	    public String getEmploye_rdv() { return employe_rdv.get(); }
	    public String getVehicule_rdv() { return vehicule_rdv.get(); }
	    public String getDetail_rdv() { return detail_rdv.get(); }

	    // Setters
	    public void setDate_rdv(String value) { date_rdv.set(value); }
	    public void setHeur_rdv(String value) { heur_rdv.set(value); }
	    public void setClient_rdv(String value) { client_rdv.set(value); }
	    public void setEmploye_rdv(String value) { employe_rdv.set(value); }
	    public void setVehicule_rdv(String value) { vehicule_rdv.set(value); }
	    public void setDetail_rdv(String value) { detail_rdv.set(value); }

	    // Property Getters (pour TableView)
	    public SimpleStringProperty date_rdvProperty() { return date_rdv; }
	    public SimpleStringProperty heur_rdvProperty() { return heur_rdv; }
	    public SimpleStringProperty client_rdvProperty() { return client_rdv; }
	    public SimpleStringProperty employe_rdvProperty() { return employe_rdv; }
	    public SimpleStringProperty vehicule_rdvProperty() { return vehicule_rdv; }
	    public SimpleStringProperty detail_rdvProperty() { return detail_rdv; }

}
