package application.controllers.client;

import javafx.beans.property.SimpleStringProperty;

public class Client {
	
	private final SimpleStringProperty Nom;
    private final SimpleStringProperty Prenom;
    private final SimpleStringProperty Email;
    private final SimpleStringProperty Phone;
    private final SimpleStringProperty Num_client;
    private final SimpleStringProperty Type;
    private final SimpleStringProperty Date_Inscription;
    private final SimpleStringProperty Details;
    
 // Constructeur
    public Client(String Nom, String Prenom, String Email, String Phone,
                  String Num_client, String Type, String Date_Inscription, String Details) {
        this.Nom = new SimpleStringProperty(Nom);
        this.Prenom = new SimpleStringProperty(Prenom);
        this.Email = new SimpleStringProperty(Email);
        this.Phone = new SimpleStringProperty(Phone);
        this.Num_client = new SimpleStringProperty(Num_client);
        this.Type = new SimpleStringProperty(Type);
        this.Date_Inscription = new SimpleStringProperty(Date_Inscription);
        this.Details = new SimpleStringProperty(Details);
    }

    // Getters classiques
    public String getNom() { return Nom.get(); }
    public String getPrenom() { return Prenom.get(); }
    public String getEmail() { return Email.get(); }
    public String getPhone() { return Phone.get(); }
    public String getNum_client() { return Num_client.get(); }
    public String getType() { return Type.get(); }
    public String getDate_Inscription() { return Date_Inscription.get(); }
    public String getDetails() { return Details.get(); }

    // Setters classiques
    public void setNom(String value) { Nom.set(value); }
    public void setPrenom(String value) { Prenom.set(value); }
    public void setEmail(String value) { Email.set(value); }
    public void setPhone(String value) { Phone.set(value); }
    public void setNum_client(String value) { Num_client.set(value); }
    public void setType(String value) { Type.set(value); }
    public void setDate_Inscription(String value) { Date_Inscription.set(value); }
    public void setDetails(String value) { Details.set(value); }
    

    // Property getters (pour JavaFX bindings/TableView)
    public SimpleStringProperty NomProperty() { return Nom; }
    public SimpleStringProperty PrenomProperty() { return Prenom; }
    public SimpleStringProperty EmailProperty() { return Email; }
    public SimpleStringProperty PhoneProperty() { return Phone; }
    public SimpleStringProperty Num_clientProperty() { return Num_client; }
    public SimpleStringProperty TypeProperty() { return Type; }
    public SimpleStringProperty Date_InscriptionProperty() { return Date_Inscription; }
    public SimpleStringProperty DetailsProperty() { return Details; }

}
