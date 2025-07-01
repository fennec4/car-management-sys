package application.controllers.stock;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.controllers.DataBaseConnection;
import application.controllers.FormController;
import application.controllers.PaneController;
import application.controllers.client.Client;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DepotController {
	
	@FXML
    private TableColumn<String, Depot> Description;

    @FXML
    private TableView<Depot> depot;

    @FXML
    private TableColumn<String, Depot> etagere;

    @FXML
    private TableColumn<String, Depot> localisation;

    @FXML
    private TableColumn<String, Depot> num;

    @FXML
    private TableColumn<String, Depot> rayon;
    
    @FXML
    public void initialize() throws SQLException {
    	showdepot();
    }

    @FXML 
    void addresse() throws IOException {
    	var log = new PaneController();
        log.fenetre("AddressePane.fxml", "localisation");
    }

    @FXML
    void ajouter() throws IOException {
    	var pane = new PaneController();
        var controller = pane.fenetre("form.fxml", "Formulaire d'ajout d'adresse");
        if (controller instanceof FormController formController) {
        	List<String> li = new ArrayList<>(Collections.nCopies(5, "")); // remplire li de ""
            formController.setFormType("depot","ajouter",li,0);
            formController.setDepot(this);  // Rafraîchir la table
            
        }
    }

    @FXML
    void modifier() throws IOException, SQLException {
    	var selectdepot = depot.getSelectionModel().getSelectedItem();
    	if(selectdepot == null) {
    		showAlert("Information", "Veuillez sélectionner le depot à modifier");
    		return;
    	}
    	var pane = new PaneController();
    	var controller = pane.fenetre("form.fxml", null);
    	if(controller instanceof FormController formController) {
    		List<String> li;
    		if(selectdepot.getDescription() != null) {
    		 li = List.of(    				
    				selectdepot.getNum(),
    				selectdepot.getRayon(),
    				selectdepot.getEtagere(),
    				selectdepot.getLocalisation(),
    				selectdepot.getDescription()
    				   				
       				);
    		}
    		else {
    			 li = List.of(    				
    					 selectdepot.getNum(),
    	    				selectdepot.getRayon(),
    	    				selectdepot.getEtagere(),
    	    				selectdepot.getLocalisation(),
    	    				""     				
    	       				);
    		}
    		int id = getdepot(selectdepot);
    		formController.setFormType("depot","modifier",li,id);
    		formController.setDepot(this); // Rafraîchir la table
    	}
    }

    @FXML
    public void showdepot() throws SQLException {
    	var data = FXCollections.<Depot>observableArrayList();
        
        // Utilisation des try-with-resources pour la gestion automatique des ressources
        try (var conn = DataBaseConnection.getConnection();
             var stmt = conn.prepareCall("{call showdepot}");
             var rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                var num = rs.getString("num");
                var rayon = rs.getString("rayon");
                var etagere = rs.getString("etagere");
                var localisation = rs.getString("localisation");
                var descript = rs.getString("descript");
                data.add(new Depot(num,rayon,etagere,localisation,descript));
            }
            
            // Configurer les colonnes
            num.setCellValueFactory(new PropertyValueFactory<>("num"));
            rayon.setCellValueFactory(new PropertyValueFactory<>("rayon"));
            etagere.setCellValueFactory(new PropertyValueFactory<>("etagere"));
            localisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
            Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            depot.setItems(data);
        } catch (SQLException e) {
            throw e;
        }
    }
    private int getdepot(Depot selectedDepot) throws SQLException {
    	var conn = DataBaseConnection.getConnection();
    	
    	var stmt = conn.prepareCall("{call getdepot(?,?,?,?)}");
            
            stmt.setString(1,selectedDepot.getNum());
            stmt.setString(2,selectedDepot.getRayon());
            stmt.setString(3,selectedDepot.getEtagere());	            
            // Déclarer le 4 ème paramètre comme paramètre de sortie
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.execute();	         
            // Récupérer le résultat du paramètre OUT
            return stmt.getInt(4);
    }

    private void showAlert(String title, String message) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
