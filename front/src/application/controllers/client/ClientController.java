package application.controllers.client;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.controllers.Accueille;
import application.controllers.DataBaseConnection;
import application.controllers.FormController;
import application.controllers.PaneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ClientController {
	
	 @FXML
	    private TableColumn<Client, String> Date_Inscription;

	    @FXML
	    private TableColumn<Client, String> Details;

	    @FXML
	    private TableColumn<Client, String> Email;

	    @FXML
	    private TableColumn<Client, String> Nom;

	    @FXML
	    private TableColumn<Client, String> Num_client;

	    @FXML
	    private TableColumn<Client, String> Phone;

	    @FXML
	    private TableColumn<Client, String> Prenom;

	    @FXML
	    private TableColumn<Client, String> Type;

	    @FXML
	    private TableView<Client> client_tab;
	    
	    @FXML
	    private TextField recherche; 
	   
	    @FXML
	    public void initialize() {
	        try {
	            // Charger les données au démarrage
	            showallclient();
	   
	        } 
	        catch (SQLException e) {
	        	showAlert("Erreur", "Impossible de charger les données: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    @FXML
	    public void showallclient() throws SQLException {
	        var data = FXCollections.<Client>observableArrayList();
	        
	        // Utilisation des try-with-resources pour la gestion automatique des ressources
	        try (var conn = DataBaseConnection.getConnection();
	             var stmt = conn.prepareCall("{call showclientdetails}");
	             var rs = stmt.executeQuery()) {
	            
	            while (rs.next()) {
	                var Nom = rs.getString("nom");
	                var Prenom = rs.getString("prenom");
	                var Email = rs.getString("email");
	                var Phone = rs.getString("phone");
	                var Num_client = rs.getString("num_client");
	                var Type = rs.getString("type_client");	     
	                var Date_inscription = rs.getString("date_inscription");
	                var Details = rs.getString("details_client");
	                data.add(new Client(Nom,Prenom,Email,Phone,Num_client,Type,Date_inscription,Details));
	            }
	            
	            // Configurer les colonnes
	            Num_client.setCellValueFactory(new PropertyValueFactory<>("Num_client"));
	            Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
	            Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
	            Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
	            Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
	            Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
	            Date_Inscription.setCellValueFactory(new PropertyValueFactory<>("Date_Inscription"));
	            Details.setCellValueFactory(new PropertyValueFactory<>("Details"));
	            
	            client_tab.setItems(data);
	        } catch (SQLException e) {
	            throw e;
	        }
	    }
	    
	    @FXML
	    public void addclient(ActionEvent event) throws IOException {
	    	var pane = new PaneController();
	        var controller = pane.fenetre("form.fxml", "Formulaire d'ajout d'adresse");
	        if (controller instanceof FormController formController) {
	        	List<String> li = new ArrayList<>(Collections.nCopies(7, "")); // remplire li de ""
	            formController.setFormType("client","ajouter",li,0);
	            formController.setClient(this);  // Rafraîchir la table
	            
	        }
	    }
	    
	    @FXML
	    void modclient(ActionEvent event) throws IOException, SQLException {
	    	var selectClient = client_tab.getSelectionModel().getSelectedItem();
	    	if(selectClient == null) {
	    		showAlert("Information", "Veuillez sélectionner le Client à modifier");
	    		return;
	    	}
	    	var pane = new PaneController();
	    	var controller = pane.fenetre("form.fxml", null);
	    	if(controller instanceof FormController formController) {
	    		List<String> li = List.of(
	    				selectClient.getNom(),
	    				selectClient.getPrenom(),
	    				selectClient.getEmail(),
	    				selectClient.getPhone(),
	    				selectClient.getType(),
	    				selectClient.getDate_Inscription(),
	    				selectClient.getDetails()
	    				);
	    		int id = getclientId(selectClient);
	    		formController.setFormType("client","modifier",li,id);
	    		formController.setClient(this); // Rafraîchir la table
	    	}
	    }
	    
	    @FXML
	    void supclient(ActionEvent event) {
	    	var selectClient = client_tab.getSelectionModel().getSelectedItem();
	    	if(selectClient == null) {
	    		showAlert("Information", "Veuillez sélectionner le Client à supprimer");
	    		return;
	    	}
	    	// Confirmation avant suppression
	        var confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, 
	                "Êtes-vous sûr de vouloir supprimer ce client ?", ButtonType.YES, ButtonType.NO);
	        confirmAlert.setTitle("Confirmation de suppression");
	        confirmAlert.setHeaderText(null);
	        
	        confirmAlert.showAndWait().ifPresent(response -> {
	            if (response == ButtonType.YES) {
	                try (var conn = DataBaseConnection.getConnection();
	                        var stmt = conn.prepareCall("{call supclient(?)}")){
	                	int id = getclientId(selectClient);
	                    stmt.setInt(1, id); 
	                    stmt.execute();
	                    initialize(); // Rafraîchir la table
	                    showAlert("Succès", "Client supprimée avec succès.");	                    
	                } catch (SQLException e) {
	                    showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
	    
	    @FXML
	    private void  handleRecherche(KeyEvent event) {	    	    	
	    	    if (event.getCode() == KeyCode.ENTER) {
	    	    	 String valeur = recherche.getText();
	    	        recherche(valeur);
	    	    }	    	 
	    }
	    
	    
	    
	    void recherche(String critere) {
	        var data = FXCollections.<Client>observableArrayList();

	        try (var conn = DataBaseConnection.getConnection();
	             var stmt = conn.prepareCall("{call rechercher_client(?)}")) {
	        	
	        
	            //Définir les paramètres AVANT d'exécuter la requête
	            if (critere != null && !critere.isEmpty()) {
	            	//System.out.println(critere);

	                stmt.setString(1, critere); 
	                
	            } else {
	                stmt.setNull(1, Types.NVARCHAR);
	                
	            }

	            //Ensuite seulement on exécute
	            try (var rs = stmt.executeQuery()) {
	            	
	                while (rs.next()) {
	                    var nom = rs.getString("nom");
	                    var prenom = rs.getString("prenom");
	                    var email = rs.getString("email");
	                    var phone = rs.getString("phone");
	                    var numClient = rs.getString("num_client");
	                    var typeClient = rs.getString("type_client");	                  
	                    var Date_inscription = rs.getString("date_inscription");
		                var Details = rs.getString("details_client");
	                    data.add(new Client(nom, prenom, email, phone, numClient, typeClient, Date_inscription, Details));
	                }
	            }

	            //Configuration des colonnes (assure-toi que ce sont bien les colonnes TableColumn)
	            Num_client.setCellValueFactory(new PropertyValueFactory<>("Num_client"));
	            Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
	            Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
	            Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
	            Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
	            Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
	            Date_Inscription.setCellValueFactory(new PropertyValueFactory<>("Date_Inscription"));
	            Details.setCellValueFactory(new PropertyValueFactory<>("Details"));
	            
	            client_tab.setItems(data);

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private int getclientId(Client selectedClient) throws SQLException {
	    	var conn = DataBaseConnection.getConnection();
	    	
	    	var stmt = conn.prepareCall("{call getpersonne(?,?,?,?,?)}");
	            
	            stmt.setString(1,selectedClient.getNom());
	            stmt.setString(2,selectedClient.getPrenom());
	            stmt.setString(3,selectedClient.getEmail());
	            stmt.setString(4,selectedClient.getPhone());	            
	            // Déclarer le 5 ème paramètre comme paramètre de sortie
	            stmt.registerOutParameter(5, Types.INTEGER);
	            stmt.execute();	         
	            // Récupérer le résultat du paramètre OUT
	            return stmt.getInt(5);
	    }
	    private void showAlert(String title, String message) {
	        var alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	    
}
