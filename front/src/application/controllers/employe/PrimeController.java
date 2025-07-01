package application.controllers.employe;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

public class PrimeController {
	
	  	@FXML
	    private TableColumn<Prime, String> commentaire;

	    @FXML
	    private TableColumn<Prime, String> date_attribution;

	    @FXML
	    private TableColumn<Prime, String> date_fin;

	    @FXML
	    private TableColumn<Prime, String> poste;

	    @FXML
	    private TableColumn<Prime, String> prime;

	    @FXML
	    private TableView<Prime> prime_tab;

	    @FXML
	    private TableColumn<Prime, String> type_prime;
	    
	    @FXML
	    public void chargerPrime() throws SQLException {
	    	chargerPrime(id);
	    }

	    @FXML
	    void ajouter() throws IOException {
	    	var pane = new PaneController();
	        var controller = pane.fenetre("form.fxml",null);
	                
	        if (controller instanceof FormController formController) {
	        	List<String> li = new ArrayList<>(Collections.nCopies(5, ""));        	
	            formController.setFormType("prime","ajouter",li,id);
	            formController.setPrime(this);  
	        }
	    }

	    @FXML
	    void modifier() throws SQLException, IOException {
	    	var selectprime = prime_tab.getSelectionModel().getSelectedItem();
	    	
	    	if(selectprime == null) {
	    		showAlert("Information", "Veuillez sélectionner l'horaire à modifier");
	    		return;
	    	}
	    	var pane = new PaneController();
	        var controller = pane.fenetre("form.fxml",null);
	                
	        if (controller instanceof FormController formController) {
	        	List<String> li ;
	        	if(selectprime.getDate_fin() != null) {
	    			li = List.of(	 
	    				selectprime.getPrime(),
	    				selectprime.getType_prime(),
	    				selectprime.getDate_debut(),
	    				selectprime.getDate_fin(),
	    				selectprime.getCommentaire()
	        	);
	    		}
	        	else {
	        		li = List.of(	 
	        			selectprime.getPrime(),
		    			selectprime.getType_prime(),
		    			selectprime.getDate_debut(),
	                	"",
	                	selectprime.getCommentaire()
	                	);
	        	}
	        	
	        	int idp= getprime(id,selectprime);
	            formController.setJour(0,id);
	        	formController.setFormType("prime","modifier",li,idp);
	            formController.setPrime(this);  // Rafraîchir la table
	        }
	    }
	    
	    private int id;
	    
	    public void setid(int id) {
	    	this.id = id;
	    }
	    
	    public void chargerPrime(int id) throws SQLException {
	        ObservableList<Prime> data = FXCollections.observableArrayList();
	        

	        try (var conn = DataBaseConnection.getConnection();
		             var stmt = conn.prepareCall("{call showprime(?)}")) {
	        		stmt.setInt(1, id);
	        		ResultSet rs = stmt.executeQuery();
		            
		            while (rs.next()) {
		                var poste = rs.getString("poste");
		                var prime = rs.getString("prime");
		                var type_prime = rs.getString("type_prime");
		                var date_attribution = rs.getString("date_attribution");
		                var date_fin = rs.getString("date_fin");
		                var commentaire = rs.getString("commentaire");
		               
		                data.add(new Prime(poste,prime,type_prime,date_attribution,date_fin,commentaire));
		            }
		            
		            // Configurer les colonnes
		            poste.setCellValueFactory(new PropertyValueFactory<>("poste"));
		            prime.setCellValueFactory(new PropertyValueFactory<>("prime"));
		            type_prime.setCellValueFactory(new PropertyValueFactory<>("type_prime"));
		            date_attribution.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
		            date_fin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
		            commentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
		            
		            prime_tab.setItems(data);
		        } catch (SQLException e) {
		            throw e;
		        }
	    }
	    
	    private static int getprime(int id_emp, Prime selectedprime) throws SQLException {
	    	var conn = DataBaseConnection.getConnection();
			var stat = conn.prepareCall("{call getprime(?,?,?,?,?,?,?)}"); 
			
			stat.setInt(1, id_emp);    	
			stat.setString(2, selectedprime.getPrime());
			stat.setString(3, selectedprime.getType_prime());
			LocalDate localDate1 = LocalDate.parse(selectedprime.getDate_debut());
			Date sqlDate1 = Date.valueOf(localDate1);
			LocalDate localDate = LocalDate.parse(selectedprime.getDate_fin());
			Date sqlDate = Date.valueOf(localDate);
			stat.setDate(4, sqlDate1);
			stat.setDate(5, sqlDate);
			stat.setString(6, selectedprime.getCommentaire());
			stat.registerOutParameter(7, Types.INTEGER);
			stat.execute();
			return stat.getInt(7);  
	    }
	    private void showAlert(String title, String message) {
	        var alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

}
