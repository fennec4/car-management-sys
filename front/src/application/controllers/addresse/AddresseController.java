package application.controllers.addresse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.controllers.DataBaseConnection;
import application.controllers.FormController;
import application.controllers.PaneController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddresseController {
    
    @FXML 
    private TableView<Addresse> addresse;

    @FXML
    private TableColumn<Addresse, String> Codecommune;

    @FXML
    private TableColumn<Addresse, String> Codewilaya;

    @FXML
    private TableColumn<Addresse, String> Commune;

    @FXML
    private TableColumn<Addresse, String> Daira;

    @FXML
    private TableColumn<Addresse, String> Designation;

    @FXML
    private TableColumn<Addresse, String> Pays;

    @FXML
    private TableColumn<Addresse, String> Rue;

    @FXML
    private TableColumn<Addresse, String> Wilaya;
    
    @FXML
    public void initialize() {
        try {
            // Charger les données au démarrage
            loadDataFromDatabase();
   
        } 
        catch (SQLException e) {
            showAlert("Erreur", "Impossible de charger les données: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    


    @FXML
    public void ajouter() throws IOException, SQLException {
        var pane = new PaneController();
        var controller = pane.fenetre("form.fxml", "Formulaire d'ajout d'adresse");
                
        if (controller instanceof FormController formController) {
        	List<String> li = new ArrayList<>(Collections.nCopies(8, "")); // remplire li de ""
            formController.setFormType("adresse","ajouter",li,0);
            formController.setAddresseController(this);  // Rafraîchir la table
            
        }
    }
    
    @FXML
    public void supprimer() {
        var selectedAddress = addresse.getSelectionModel().getSelectedItem();
        if (selectedAddress == null) {
            showAlert("Information", "Veuillez sélectionner une adresse à supprimer");
            return;
        }
        
        // Confirmation avant suppression
        var confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, 
                "Êtes-vous sûr de vouloir supprimer cette adresse?", ButtonType.YES, ButtonType.NO);
        confirmAlert.setTitle("Confirmation de suppression");
        confirmAlert.setHeaderText(null);
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    deleteAddressFromDatabase(selectedAddress);
                    loadDataFromDatabase(); // Rafraîchir la table
                } catch (SQLException e) {
                    showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
    
    @FXML
    public void modifier() throws IOException, SQLException {
        var selectedAddress = addresse.getSelectionModel().getSelectedItem();
        if (selectedAddress == null) {
            showAlert("Information", "Veuillez sélectionner une adresse à modifier");
            return;
        }
        var pane = new PaneController();
        var controller = pane.fenetre("form.fxml", "Formulaire d'ajout d'adresse");
        if (controller instanceof FormController formController) {
        	List<String> li = List.of(
        			selectedAddress.getpays(),
        			selectedAddress.getwilaya(),
        			selectedAddress.getcodeWilaya(),
            		selectedAddress.getdaira(),
            		selectedAddress.getcommune(),
            		selectedAddress.getcodeCommune(),
					selectedAddress.getrue(),
					selectedAddress.getdesignation()
        			);
        	int id = AddresseID(selectedAddress);
            formController.setFormType("adresse","modifier",li,id);
            formController.setAddresseController(this); // Rafraîchir la table
            
        }
        
    }

    @FXML
    public void loadDataFromDatabase() throws SQLException {
        var data = FXCollections.<Addresse>observableArrayList();
        
        // Utilisation des try-with-resources pour la gestion automatique des ressources
        try (var conn = DataBaseConnection.getConnection();
             var stmt = conn.prepareCall("{call showalladdresse}");
             var rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                var pays = rs.getString("nom_pay");
                var wilaya = rs.getString("nom_wilaya");
                var codewilaya = rs.getString("code_wilaya");
                var daira = rs.getString("nom_daira");
                var commune = rs.getString("nom_commune");
                var codecommune = rs.getString("code_commune");
                var rue = rs.getString("nom_Rue");
                var designation = rs.getString("designation");

                data.add(new Addresse(pays, wilaya, codewilaya, daira, commune, codecommune, rue, designation));
            }
            
            // Configurer les colonnes
            Pays.setCellValueFactory(new PropertyValueFactory<>("pays"));
            Wilaya.setCellValueFactory(new PropertyValueFactory<>("wilaya"));
            Codewilaya.setCellValueFactory(new PropertyValueFactory<>("codeWilaya"));
            Daira.setCellValueFactory(new PropertyValueFactory<>("daira"));
            Commune.setCellValueFactory(new PropertyValueFactory<>("commune"));
            Codecommune.setCellValueFactory(new PropertyValueFactory<>("codeCommune"));
            Rue.setCellValueFactory(new PropertyValueFactory<>("rue"));
            Designation.setCellValueFactory(new PropertyValueFactory<>("designation"));

            addresse.setItems(data);
        } catch (SQLException e) {
            throw e;
        }
    }
    
    private void deleteAddressFromDatabase(Addresse selectedAddress) throws SQLException {
        
        try (var conn = DataBaseConnection.getConnection();
             var stmt = conn.prepareCall("{call supaddresse(?)}")) {
        	int id = AddresseID(selectedAddress); 
            stmt.setInt(1, id); 
            stmt.execute();
        }
        showAlert("Succès", "Adresse supprimée avec succès.");
    }
    
    private int AddresseID(Addresse selectedAddress) throws SQLException {
    	var conn = DataBaseConnection.getConnection();
    	
    	var stmt = conn.prepareCall("{call getAddresse(?,?,?,?,?,?,?, ?,?)}");
            
            stmt.setString(1,selectedAddress.getpays());
            stmt.setString(2,selectedAddress.getwilaya());
            stmt.setString(3,selectedAddress.getcodeWilaya());
            stmt.setString(4,selectedAddress.getdaira());
            stmt.setString(5,selectedAddress.getcommune());
            stmt.setString(6,selectedAddress.getcodeCommune());
            stmt.setString(7,selectedAddress.getrue());
            stmt.setString(8,selectedAddress.getdesignation());
            // Déclarer le 9ème paramètre comme paramètre de sortie
            stmt.registerOutParameter(9, java.sql.Types.INTEGER);
            stmt.execute();
         
            // Récupérer le résultat du paramètre OUT
            return stmt.getInt(9);
    }
    
    private void showAlert(String title, String message) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}