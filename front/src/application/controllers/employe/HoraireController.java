package application.controllers.employe;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class HoraireController {
	
	 	@FXML
	    private TableColumn<Horaire, String> heure_debut;

	    @FXML
	    private TableColumn<Horaire, String> heure_fin;

	    @FXML
	    private TableView<Horaire> horaire;

	    @FXML
	    private TableColumn<Horaire, String> jour_semaine;
	    
	    @FXML
	    public void chargerHoraires() {
	    	chargerHoraires(id);
	    }
	    
	    @FXML
	    void ajouter() throws IOException {
	    	var selectJour = horaire.getSelectionModel().getSelectedItem();
	    	
	    	if(selectJour == null) {
	    		showAlert("Information", "Veuillez sélectionner le jour à ajouter");
	    		return;
	    	}
	    	var pane = new PaneController();
	        var controller = pane.fenetre("form.fxml",null);
	                
	        if (controller instanceof FormController formController) {
	        	List<String> li = new ArrayList<>(Collections.nCopies(2, "")); // remplire li de ""
	            formController.setJour(selectJour.getJour_semaine(),id);
	        	formController.setFormType("heur","ajouter",li,id);
	            formController.setHeur(this);  // Rafraîchir la table
	        }
	    }    

	    @FXML
	    void modifier() throws IOException, SQLException {
	    	var selectJour = horaire.getSelectionModel().getSelectedItem();
	    	
	    	if(selectJour == null) {
	    		showAlert("Information", "Veuillez sélectionner l'horaire à modifier");
	    		return;
	    	}
	    	var pane = new PaneController();
	        var controller = pane.fenetre("form.fxml",null);
	                
	        if (controller instanceof FormController formController) {
	        	List<String> li = List.of(	        		
	        		selectJour.getHeure_debut(),
	        		selectJour.getHeure_fin()
	        	);
	        	int idh= getheur(id,selectJour.getJour_semaine(),selectJour);
	            formController.setJour(selectJour.getJour_semaine(),id);
	        	formController.setFormType("heur","modifier",li,idh);
	            formController.setHeur(this);  // Rafraîchir la table
	        }
	    }
	    
	    private int id;
	    
	    public void setid(int id) {
	    	this.id = id;
	    }
	    
	    
	    
	    public void chargerHoraires(int id) {
	        ObservableList<Horaire> horaires = FXCollections.observableArrayList();
	        String[] nomsJours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

	        try (Connection con = DataBaseConnection.getConnection();
	             CallableStatement stmt = con.prepareCall("{call afficherheur(?)}")) {

	            stmt.setInt(1, id);
	            ResultSet rs = stmt.executeQuery();

	            // Dictionnaire temporaire pour résultats SQL
	            Map<Integer, Horaire> horairesMap = new HashMap<>();

	            while (rs.next()) {
	                int jour = rs.getInt("jour_semaine");
	                String debut = rs.getTime("heure_debut") != null ? rs.getTime("heure_debut").toString().substring(0,5) : null;
	                String fin = rs.getTime("heure_fin") != null ? rs.getTime("heure_fin").toString().substring(0,5) : null;
	                String jourNom = nomsJours[jour - 1];

	                Horaire h = new Horaire(jour, jourNom, debut, fin);
	                horairesMap.put(jour, h);
	            }

	            // Remplir les 7 jours
	            for (int i = 0; i < 7; i++) {
	                int jourNum = i + 1;
	                if (horairesMap.containsKey(jourNum)) {
	                    horaires.add(horairesMap.get(jourNum));
	                } else {
	                    horaires.add(new Horaire(jourNum, nomsJours[i], null, null));
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            // En cas d'erreur SQL, on affiche quand même les 7 jours vides
	            for (int i = 0; i < 7; i++) {
	                horaires.add(new Horaire(i + 1, nomsJours[i], null, null));
	            }
	        }

	        // Lier les colonnes (à faire une seule fois normalement)
	        jour_semaine.setCellValueFactory(new PropertyValueFactory<>("jour_nom"));
	        heure_debut.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
	        heure_fin.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));

	        horaire.setItems(horaires);
	    }
	    private static int getheur(int id_emp , int jour_sem, Horaire selectedHoraire) throws SQLException {
    		var conn = DataBaseConnection.getConnection();
    		var stat = conn.prepareCall("{call getheur(?,?,?,?,?)}"); 
    		
    		stat.setInt(1, id_emp);    	
    		stat.setInt(2, jour_sem);
    		LocalTime heur_debut = LocalTime.parse(selectedHoraire.getHeure_debut()); 
			Time sqlTime = Time.valueOf(heur_debut);
			stat.setTime(3, sqlTime); 
			
			LocalTime heur_fin = LocalTime.parse(selectedHoraire.getHeure_fin()); 
			Time sqlTime2 = Time.valueOf(heur_fin);
			stat.setTime(4, sqlTime2);
			stat.registerOutParameter(5, Types.INTEGER);
    		stat.execute();
    		return stat.getInt(5);   	
    }
	    
	    private void showAlert(String title, String message) {
	        var alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }


	    
}
