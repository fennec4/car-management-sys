package application.controllers.employe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.controllers.DataBaseConnection;
import application.controllers.FormController;
import application.controllers.PaneController;
import application.controllers.addresse.Addresse;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PostController {
	
	 	@FXML
	    private TableColumn<Post, String> description_poste;

	    @FXML
	    private TableView<Post> post;

	    @FXML
	    private TableColumn<Post, String> salaire_base;

	    @FXML
	    private TableColumn<Post, String> titre;
	    
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
	    public void ajouter() throws IOException {
	    	var pane = new PaneController();
	        var controller = pane.fenetre("form.fxml",null);
	                
	        if (controller instanceof FormController formController) {
	        	List<String> li = new ArrayList<>(Collections.nCopies(3, "")); // remplire li de ""
	            formController.setFormType("post","ajouter",li,0);
	            formController.setPost(this);  // Rafraîchir la table
	        }
	    }

	    @FXML
	    public void loadDataFromDatabase() throws SQLException {
	    	var data = FXCollections.<Post>observableArrayList();
	        
	        // Utilisation des try-with-resources pour la gestion automatique des ressources
	        try (var conn = DataBaseConnection.getConnection();
	             var stmt = conn.prepareCall("{call showallposte}");
	             var rs = stmt.executeQuery()) {
	            
	            while (rs.next()) {
	                var titre = rs.getString("titre");
	                var salaire_base = rs.getString("salaire_base");
	                var description_poste = rs.getString("description_poste");
	               
	                data.add(new Post(titre, salaire_base, description_poste));
	            }
	            
	            // Configurer les colonnes
	            titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
	            salaire_base.setCellValueFactory(new PropertyValueFactory<>("salaire_base"));
	            description_poste.setCellValueFactory(new PropertyValueFactory<>("description_poste"));
	            
	            post.setItems(data);
	        } catch (SQLException e) {
	            throw e;
	        }
	    }

	    @FXML
	    public void modifier() throws SQLException, IOException {
	    	var selectedPost = post.getSelectionModel().getSelectedItem();
	        if (selectedPost == null) {
	            showAlert("Information", "Veuillez sélectionner un Post à modifier");
	            return;
	        }
	        var pane = new PaneController();
	        var controller = pane.fenetre("form.fxml",null);
	        if (controller instanceof FormController formController) {
	        	List<String> li = List.of(
	        			selectedPost.getTitre(),
	        			selectedPost.getSalaire_base(),
	        			selectedPost.getDescription_poste()
	            		
	        			);
	        	int id = getPost(selectedPost);
	            formController.setFormType("post","modifier",li,id);
	            formController.setPost(this); // Rafraîchir la table
	            
	        }
	    }

	    @FXML
	    public void supprimer() {
	    	var selectedAddress = post.getSelectionModel().getSelectedItem();
	        if (selectedAddress == null) {
	            showAlert("Information", "Veuillez sélectionner une post à supprimer");
	            return;
	        }
	        
	        // Confirmation avant suppression
	        var confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, 
	                "Êtes-vous sûr de vouloir supprimer ce post ?", ButtonType.YES, ButtonType.NO);
	        confirmAlert.setTitle("Confirmation de suppression");
	        confirmAlert.setHeaderText(null);
	        
	        confirmAlert.showAndWait().ifPresent(response -> {
	            if (response == ButtonType.YES) {
	                try {
	                    deletePostFromDatabase(selectedAddress);
	                    loadDataFromDatabase(); // Rafraîchir la table
	                } catch (SQLException e) {
	                    showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
	    
	    private void deletePostFromDatabase(Post selectedPost) throws SQLException {
	        
	        try (var conn = DataBaseConnection.getConnection();
	             var stmt = conn.prepareCall("{call suppost(?)}")) {
	        	int id = getPost(selectedPost); 
	            stmt.setInt(1, id); 
	            stmt.execute();
	        }
	        showAlert("Succès", "Post supprimée avec succès.");
	    }
	    
	    private int getPost(Post selectedPost) throws SQLException {
	    	var conn = DataBaseConnection.getConnection();
	    	
	    	var stmt = conn.prepareCall("{call getposte(?,?)}");
	            
	            stmt.setString(1,selectedPost.getTitre());
	           
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
