package application.controllers.employe;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.controllers.DataBaseConnection;
import application.controllers.FormController;
import application.controllers.PaneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EmployeController {
	
	 	@FXML
	    private TableColumn<Employe, String> Addresse;
	 	
	 	@FXML
	 	private TableColumn<Employe, String> Date_naissance;
	 	
	 	@FXML
	 	private TableColumn<Employe, String> Details;

	 	@FXML
	 	private TableColumn<Employe, String> Date_recrutement;

	    @FXML
	    private TableColumn<Employe, String> Email;
	    
	    @FXML
	    private TableColumn<Employe, String> Heures_travail_par_jour;
	    
	    @FXML
	    private TableColumn<Employe, String> Lieu_naissance;

	    @FXML
	    private TableColumn<Employe, String> Matricule;

	    @FXML
	    private TableColumn<Employe, String> Nom;
	    
	    @FXML
	    private TableColumn<Employe, String> Num_securite_sociale;

	    @FXML
	    private TableColumn<Employe, String> Phone;

	    @FXML
	    private TableColumn<Employe, String> Prenom;

	    @FXML
	    private TableColumn<Employe, String> Statut;

	    @FXML
	    private TableView<Employe> employe_tab;

	    @FXML
	    private TextField recherche;
	    
	    @FXML
	    public void initialize() {
	        try {
	            // Charger les données au démarrage	        		        	
	        	showallemploye();
	        		        	
	        } 
	        catch (SQLException e) {
	            showAlert("Erreur", "Impossible de charger les données: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    @FXML
	    public void showallemploye() throws SQLException {
	    	var data = FXCollections.<Employe>observableArrayList();
	    	
	        try (var conn = DataBaseConnection.getConnection();
	             var stmt = conn.prepareCall("{call showemployedetails}");
	             var rs = stmt.executeQuery()) {
	            
	            while (rs.next()) {
	                var Nom = rs.getString("nom");
	                var Prenom = rs.getString("prenom");
	                var Email = rs.getString("email");
	                var Phone = rs.getString("phone");
	                var Statut = rs.getString("statut");
	                var Matricule = rs.getString("matricule");	
	                var Num_securite_sociale = rs.getString("num_securite_sociale");	
	                var Heures_travail_par_jour = rs.getString("heures_travail_par_jour");	
	                var Addresse = rs.getString("addresse");	
	                var Lieu_naissance = rs.getString("lieu_naissance");	
	                var Date_naissance = rs.getString("Date_naissance");	
	                var Date_recrutement = rs.getString("date_naissance");
	                var Details = rs.getString("details");
	               
	                data.add(new Employe(Nom, Prenom, Email,
	                		Phone, Statut,Matricule, Num_securite_sociale, 
	                		Heures_travail_par_jour, Addresse, Lieu_naissance,
	                		Date_naissance, Date_recrutement, Details));
	            }
	            
	            // Configurer les colonnes
	            Matricule.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
	            Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
	            Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
	            Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
	            Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
	            Statut.setCellValueFactory(new PropertyValueFactory<>("Statut"));
	            Num_securite_sociale.setCellValueFactory(new PropertyValueFactory<>("Num_securite_sociale"));
	            Heures_travail_par_jour.setCellValueFactory(new PropertyValueFactory<>("Heures_travail_par_jour"));
	            Addresse.setCellValueFactory(new PropertyValueFactory<>("Addresse"));
	            Lieu_naissance.setCellValueFactory(new PropertyValueFactory<>("Lieu_naissance"));
	            Date_naissance.setCellValueFactory(new PropertyValueFactory<>("Date_naissance"));
	            Date_recrutement.setCellValueFactory(new PropertyValueFactory<>("Date_recrutement"));
	            Details.setCellValueFactory(new PropertyValueFactory<>("Details"));
	            
	            // code pour rendre le background des employe acif vert et inactif rouge
	            employe_tab.setRowFactory(tv -> new TableRow<Employe>() {
	                @Override
	                protected void updateItem(Employe item, boolean empty) {
	                    super.updateItem(item, empty);

	                    if (item == null || empty) {
	                        setStyle("");
	                    } else {
	                    	if ("inactif".equalsIgnoreCase(item.getStatut())) {
	                        	if (isSelected()) {
	                            	setStyle("-fx-background-color: #7F8C8D; ");
	                            }
	                        	else {
	                            	setStyle("-fx-background-color: #ffcccc;"); // rouge clair
	                            }
	                            	                            	                          
	                        } else if ("actif".equalsIgnoreCase(item.getStatut())) {
	                            
	                            if (isSelected()) {
	                            	setStyle("-fx-background-color: #7F8C8D; ");
	                            }
	                            else {
	                            	setStyle("-fx-background-color: #ccffcc;");// vert clair
	                            }
	                        }
	                        else {
	                            setStyle(""); // réinitialise si aucun cas
	                        }
	                    }
	                }
	            });
	            employe_tab.setItems(data);
	        } catch (SQLException e) {
	            throw e;
	        }
	    }
	    
	    @FXML
	    public void addemploye(ActionEvent event) throws IOException {
	    	var pane = new PaneController();
	        var controller = pane.fenetre("form.fxml", "Formulaire d'ajout d'adresse");
	        if (controller instanceof FormController formController) {
	        	List<String> li = new ArrayList<>(Collections.nCopies(12, "")); // remplire li de ""
	            formController.setFormType("employe","ajouter",li,0);
	            formController.setEmploye(this);  // Rafraîchir la table
	            
	        }
	    }
	    
	    @FXML
	    void modemploye(ActionEvent event) throws IOException, SQLException {
	    	var selectEmploye = employe_tab.getSelectionModel().getSelectedItem();
	    	
	    	if(selectEmploye == null) {
	    		showAlert("Information", "Veuillez sélectionner l'Employe à modifier");
	    		return;
	    	}
	    	
	    	var pane = new PaneController();
	    	var controller = pane.fenetre("form.fxml", null);
	    	if(controller instanceof FormController formController) {
	    		List<String> li = List.of(	    				
	    				selectEmploye.getNom(),
	    				selectEmploye.getPrenom(),
	    				selectEmploye.getEmail(),
	    				selectEmploye.getPhone(),
	    				selectEmploye.getStatut(),	    				
	    				selectEmploye.getNum_securite_sociale(),
	    				selectEmploye.getHeures_travail_par_jour(),
	    				selectEmploye.getAddresse(),
	    				selectEmploye.getLieu_naissance(),
	    				selectEmploye.getDate_naissance(),
	    				selectEmploye.getDate_recrutement(),
	    				selectEmploye.getDetails()
	    				);
	    		int id = getemployeId(selectEmploye);
	    		formController.setFormType("employe","modifier",li,id);
	    		formController.setEmploye(this); // Rafraîchir la table
	    	}
	    }
	    @FXML
	    void desactiver(ActionEvent event) {
	    	var selectEmploye = employe_tab.getSelectionModel().getSelectedItem();
	    	
	    	if(selectEmploye == null) {
	    		showAlert("Information", "Veuillez sélectionner l'Employe à activer/desactiver");
	    		return;
	    	}
	    	try (var conn = DataBaseConnection.getConnection();
                    var stmt = conn.prepareCall("{call desactiver_employe(?)}")){
            	int id = getemployeId(selectEmploye);
                stmt.setInt(1, id); 
                stmt.execute();
                initialize(); // Rafraîchir la table	                    
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
                e.printStackTrace();
            }
	    }
	    
	    @FXML
	    void details(ActionEvent event) throws IOException, SQLException {
	    	var selectEmploye = employe_tab.getSelectionModel().getSelectedItem();
	    	
	    	if(selectEmploye == null) {
	    		showAlert("Information", "Veuillez sélectionner le Client à modifier");
	    		return;
	    	}
	    	
	    	var pane = new PaneController();
	    	var controller = pane.fenetre("details.fxml", null);
	    	if(controller instanceof DetailController detailController) {
	    		List<String> li = List.of(	    				
	    				selectEmploye.getNom(),
	    				selectEmploye.getPrenom(),
	    				selectEmploye.getEmail(),
	    				selectEmploye.getPhone(),
	    				selectEmploye.getStatut(),
	    				selectEmploye.getMatricule(),
	    				selectEmploye.getNum_securite_sociale(),
	    				selectEmploye.getHeures_travail_par_jour(),
	    				selectEmploye.getAddresse(),
	    				selectEmploye.getLieu_naissance(),
	    				selectEmploye.getDate_naissance(),
	    				selectEmploye.getDate_recrutement(),
	    				selectEmploye.getDetails()
	    				);	   
	    		int id = getemployeId(selectEmploye);
	    		detailController.setinfo(li,id);
	    		detailController.showEmploye(li);;
	    	}
	    }
	    
	    @FXML
	    void post(ActionEvent event) throws IOException {
	    	var pane = new PaneController();
	    	var controller = pane.fenetre("Post.fxml", null);
	    }

	    @FXML
	    void handleRecherche(KeyEvent event) {
	    	if (event.getCode() == KeyCode.ENTER) {
	    		String valeur = recherche.getText();
	        	rechercher(valeur);
	    	}
	    }
	    private void rechercher(String critere) {
	    	var data = FXCollections.<Employe>observableArrayList();

	        try (var conn = DataBaseConnection.getConnection();
	             var stmt = conn.prepareCall("{call rechercher_employe(?)}")) {
	        	
	        
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
		                var Nom = rs.getString("nom");
		                var Prenom = rs.getString("prenom");
		                var Email = rs.getString("email");
		                var Phone = rs.getString("phone");
		                var Statut = rs.getString("statut");
		                var Matricule = rs.getString("matricule");	
		                var Num_securite_sociale = rs.getString("num_securite_sociale");	
		                var Heures_travail_par_jour = rs.getString("heures_travail_par_jour");	
		                var Addresse = rs.getString("addresse");	
		                var Lieu_naissance = rs.getString("lieu_naissance");	
		                var Date_naissance = rs.getString("Date_naissance");	
		                var Date_recrutement = rs.getString("date_naissance");
		                var Details = rs.getString("details");
		               
		                data.add(new Employe(Nom, Prenom, Email,
		                		Phone, Statut,Matricule, Num_securite_sociale, 
		                		Heures_travail_par_jour, Addresse, Lieu_naissance,
		                		Date_naissance, Date_recrutement, Details));
		            }
		            
		            // Configurer les colonnes
		            Matricule.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
		            Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		            Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
		            Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
		            Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
		            Statut.setCellValueFactory(new PropertyValueFactory<>("Statut"));
		            Num_securite_sociale.setCellValueFactory(new PropertyValueFactory<>("Num_securite_sociale"));
		            Heures_travail_par_jour.setCellValueFactory(new PropertyValueFactory<>("Heures_travail_par_jour"));
		            Addresse.setCellValueFactory(new PropertyValueFactory<>("Addresse"));
		            Lieu_naissance.setCellValueFactory(new PropertyValueFactory<>("Lieu_naissance"));
		            Date_naissance.setCellValueFactory(new PropertyValueFactory<>("Date_naissance"));
		            Date_recrutement.setCellValueFactory(new PropertyValueFactory<>("Date_recrutement"));
		            Details.setCellValueFactory(new PropertyValueFactory<>("Details"));
		            
		            // code pour rendre le background des employe acif vert et inactif rouge
		            employe_tab.setRowFactory(tv -> new TableRow<Employe>() {
		                @Override
		                protected void updateItem(Employe item, boolean empty) {
		                    super.updateItem(item, empty);

		                    if (item == null || empty) {
		                        setStyle("");
		                    } else {
		                    	if ("inactif".equalsIgnoreCase(item.getStatut())) {
		                        	if (isSelected()) {
		                            	setStyle("-fx-background-color: #7F8C8D; ");
		                            }
		                        	else {
		                            	setStyle("-fx-background-color: #ffcccc;"); // rouge clair
		                            }
		                            	                            	                          
		                        } else if ("actif".equalsIgnoreCase(item.getStatut())) {
		                            
		                            if (isSelected()) {
		                            	setStyle("-fx-background-color: #7F8C8D; ");
		                            }
		                            else {
		                            	setStyle("-fx-background-color: #ccffcc;");// vert clair
		                            }
		                        }
		                        else {
		                            setStyle(""); // réinitialise si aucun cas
		                        }
		                    }
		                }
		            });
		            employe_tab.setItems(data);
		        }

	        }catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private int getemployeId(Employe selectedEmploye) throws SQLException {
	    	var conn = DataBaseConnection.getConnection();
	    	
	    	var stmt = conn.prepareCall("{call getpersonne(?,?,?,?,?)}");
            
            stmt.setString(1,selectedEmploye.getNom());
            stmt.setString(2,selectedEmploye.getPrenom());
            stmt.setString(3,selectedEmploye.getEmail());
            stmt.setString(4,selectedEmploye.getPhone());	            
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
