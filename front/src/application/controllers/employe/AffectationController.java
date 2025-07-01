package application.controllers.employe;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import application.controllers.DataBaseConnection;
import application.controllers.FormController;
import application.controllers.PaneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AffectationController {
	
	@FXML
    private TableView<Affectation> Affectation_tab;

    @FXML
    private TableColumn<Affectation, String> date_debut;

    @FXML
    private TableColumn<Affectation, String> date_fin;

    @FXML
    private TableColumn<Affectation, String> poste;

    @FXML
    private TableColumn<Affectation, String> salaire_negocie;
    
    @FXML
    public void chargerAffectation() throws SQLException {
    	chargerAffectation(id);
    }

    @FXML
    void ajouter() throws IOException {
    	var pane = new PaneController();
        var controller = pane.fenetre("form.fxml",null);
                
        if (controller instanceof FormController formController) {
        	List<String> li = new ArrayList<>(Collections.nCopies(4, ""));        	
            formController.setFormType("aff","ajouter",li,id);
            formController.setAff(this);  
        }
    }

    @FXML
    void modifier() throws SQLException, IOException {
    	var selectaff = Affectation_tab.getSelectionModel().getSelectedItem();
    	
    	if(selectaff == null) {
    		showAlert("Information", "Veuillez sélectionner l'horaire à modifier");
    		return;
    	}
    	var pane = new PaneController();
        var controller = pane.fenetre("form.fxml",null);
                
        if (controller instanceof FormController formController) {
        	List<String> li ;
        	if(selectaff.getDate_fin() != null) {
    			li = List.of(	 
    				selectaff.getPoste(),
    				selectaff.getDate_debut(),
    				selectaff.getDate_fin(),
    				selectaff.getSalaire_negocie()
        	);
    		}
        	else {
        		li = List.of(	 
                	selectaff.getPoste(),
                	selectaff.getDate_debut(),
                	"",
                	selectaff.getSalaire_negocie()
                	);
        	}
        	
        	int ida= getaffectation(id,selectaff);
            formController.setJour(0,id);
        	formController.setFormType("aff","modifier",li,ida);
            formController.setAff(this);  // Rafraîchir la table
        }
    }
    
    private int id;
    
    public void setid(int id) {
    	this.id = id;
    }
    
    public void chargerAffectation(int id) throws SQLException {
        ObservableList<Affectation> data = FXCollections.observableArrayList();
        

        try (var conn = DataBaseConnection.getConnection();
	             var stmt = conn.prepareCall("{call showaffectation(?)}")) {
        		stmt.setInt(1, id);
        		ResultSet rs = stmt.executeQuery();
	            
	            while (rs.next()) {
	                var poste = rs.getString("poste");
	                var date_debut = rs.getString("date_debut");
	                var date_fin = rs.getString("date_fin");
	                var salaire_negocie = rs.getString("salaire_negocie");
	               
	                data.add(new Affectation(poste,date_debut,date_fin,salaire_negocie));
	            }
	            
	            // Configurer les colonnes
	            poste.setCellValueFactory(new PropertyValueFactory<>("poste"));
	            date_debut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
	            date_fin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
	            salaire_negocie.setCellValueFactory(new PropertyValueFactory<>("salaire_negocie"));
	            
	            Affectation_tab.setItems(data);
	        } catch (SQLException e) {
	            throw e;
	        }
    }
    public static int getaffectation(int id_emp , Affectation selectedAffectation) throws SQLException {
		var conn = DataBaseConnection.getConnection();
		var stat = conn.prepareCall("{call getaffectation(?,?,?)}"); 
		int id_post = getPost(selectedAffectation.getPoste());
		stat.setInt(1, id_emp);    	
		stat.setInt(2, id_post);
		stat.registerOutParameter(3, Types.INTEGER);
		stat.execute();
		return stat.getInt(3);   	
}
    public static int getPost(String post) throws SQLException {
    	var conn = DataBaseConnection.getConnection();
    	
    	var stmt = conn.prepareCall("{call getposte(?,?)}");
            
            stmt.setString(1,post);
           
            // Déclarer le 9ème paramètre comme paramètre de sortie
            stmt.registerOutParameter(2, java.sql.Types.INTEGER);
            stmt.execute();
         
            // Récupérer le résultat du paramètre OUT
            return stmt.getInt(2);
    }
    
    private void showAlert(String title, String message) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
