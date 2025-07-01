package application.controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.controllers.client.Client;
import application.controllers.employe.Employe;
import application.controllers.rendezvous.Rendezvous;

import application.controllers.stock.StockItem;
import application.controllers.vehicule.vehicule;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class Accueille {
		
		@FXML
		private TableColumn<vehicule, String> annee_fabrication;
		
		@FXML
	    private TableColumn<Rendezvous, String> client_rdv;
		
		@FXML
	    private TableColumn<Rendezvous, String> date_rdv;
		
		@FXML
	    private TableColumn<Rendezvous, String> detail_rdv;
	
	   @FXML
	    private TableColumn<Client, String> Email;
	   
	   @FXML
	   private TableColumn<Employe, String> Email_employe;
	   
	   @FXML
	    private TableColumn<Rendezvous, String> employe_rdv;
	   
	   @FXML
	    private TableColumn<Rendezvous, String> heure_rdv;

	    @FXML
	    private TableColumn<Client, String> Nom;

	    @FXML
	    private TableColumn<Employe, String> Nom_employe;
	    
	    @FXML
	    private TableColumn<Client, String> Num_client;

	    @FXML
	    private TableColumn<Client, String> Phone;

	    @FXML
	    private TableColumn<Employe, String> Phone_employe;

	    @FXML
	    private TableColumn<Client, String> Prenom;

	    @FXML
	    private TableColumn<Employe, String> Prenom_employe;
	    
	    @FXML
	    private TableColumn<vehicule, String> proprietaire;

	    @FXML
	    private TableColumn<Employe, String> Statut;
	    
	    @FXML
	    private TableColumn<vehicule, String> carburant;
	    
	    @FXML
	    private TableColumn<vehicule, String> couleur;
	    
	    @FXML
	    private TableColumn<Employe, String> Matricule;
	    
	    @FXML
	    private TableColumn<Client, String> Type;
	    
	    @FXML
	    private TableColumn<vehicule, String> marque;
	    
	    @FXML
	    private TableColumn<vehicule, String> matricule;
	    
	    @FXML
	    private TableColumn<vehicule, String> modele;
	    
	    @FXML
	    private TableColumn<vehicule, String> num_chassi;
	    
	    @FXML
	    private TableColumn<vehicule, String> kilometrage;
	    
	    @FXML
	    private TableColumn<Rendezvous, String> vehicule_rdv;
	    
	    @FXML
	    private TableView<vehicule> vehicule_tab;
	    
	    @FXML
	    private TableView<Client> client_tab;
	    
	    @FXML
	    private TableView<Employe> employe_tab;
	    
	    @FXML
	    private TableView<Rendezvous> rendezvous_tab;
	    
	    @FXML
	    private AnchorPane client;
	    
	    @FXML
	    private AnchorPane employe;
	    
	    @FXML
	    private AnchorPane Vehicule;

	    @FXML
	    private AnchorPane rendez_vous;	    
	    
	    @FXML
	    private TextField recherche;
	    
	    @FXML
	    private TextField recherche_employe;
	    
	    @FXML
	    private TextField recherche_vehicule;
	    
	    @FXML
	    private TextField recherche_rdv;
	    
	    @FXML
	    private AnchorPane stock;

	    @FXML
	    private TableView<StockItem> stock_tab;

	    @FXML
	    private TableColumn<StockItem, String> reference_col;
	    @FXML
	    private TableColumn<StockItem, String> nom_col;
	    @FXML
	    private TableColumn<StockItem, Integer> quantite_col;   
	    @FXML
	    private TableColumn<StockItem, Integer> prix_col;   
	    @FXML
	    private TableColumn<StockItem, String> Emplacement;
	    @FXML
	    private TableColumn<StockItem, String> description_col;

	    @FXML
	    private void handleShowClient() throws SQLException {
	        client.setVisible(true);
	        employe.setVisible(false);
	        stock.setVisible(false);
	        Vehicule.setVisible(false);
	        rendez_vous.setVisible(false);
	        showallclient();
	        
	    }

	    @FXML
	    private void handleShowEmploye() throws SQLException {
	        client.setVisible(false);
	        employe.setVisible(true);
	        stock.setVisible(false);
	        Vehicule.setVisible(false);
	        rendez_vous.setVisible(false);
	        showallemploye();
	        
	    }
	    
	    @FXML
	    void handleShowVoitures(ActionEvent event) {
	    	client.setVisible(false);
	        employe.setVisible(false);
	        stock.setVisible(false);
	        Vehicule.setVisible(true);
	        rendez_vous.setVisible(false);
	        showallvehicule();
	        
	    }
	    @FXML
	    void handleShowRendezvous(ActionEvent event) {
	    	client.setVisible(false);
	    	employe.setVisible(false);
	    	Vehicule.setVisible(false);
	    	rendez_vous.setVisible(true);
	    	stock.setVisible(false);
	    	showallrendez_vous();
	    	
	    }
	    
	    @FXML
	    private void handleShowStock() {
	        client.setVisible(false);
	        employe.setVisible(false);
	        Vehicule.setVisible(false);
	        rendez_vous.setVisible(false);
	        stock.setVisible(true);
	        
	        recherche_stock(null);
	    }
	    @FXML
	    public void initialize() {
	        try {
	            // Charger les données au démarrage
	        	if(client.isVisible()) {
	        		showallclient();
	        		return;
	        	}
	        	else if(employe.isVisible()) {
	        		showallemploye();
	        		return;
	        	}
	        	else if(Vehicule.isVisible()) {
	        		showallvehicule();
	        		return;
	        	}
	        	else if(rendez_vous.isVisible()) {
	        		showallrendez_vous();
	        		return;
	        	}
	        	else if(stock.isVisible()) {
	        		recherche_stock(null);
	        		return;
	        	}
	            	   
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
	               
	                data.add(new Client(Nom,Prenom,Email,Phone,Num_client,Type,"",""));
	            }
	            
	            // Configurer les colonnes
	            Num_client.setCellValueFactory(new PropertyValueFactory<>("Num_client"));
	            Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
	            Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
	            Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
	            Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
	            Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
	            

	            client_tab.setItems(data);
	        } catch (SQLException e) {
	            throw e;
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
	                // pour afficher que les employe actifs
	                if(Statut.equals("actif")) {
	                	data.add(new Employe(Nom, Prenom, Email,
	                		Phone, Statut,Matricule, "", "",
	                		"", "", "", "", ""));
	                }
	                
	            }
	            
	            // Configurer les colonnes
	            Matricule.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
	            Nom_employe.setCellValueFactory(new PropertyValueFactory<>("Nom"));
	            Prenom_employe.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
	            Email_employe.setCellValueFactory(new PropertyValueFactory<>("Email"));
	            Phone_employe.setCellValueFactory(new PropertyValueFactory<>("Phone"));
	            Statut.setCellValueFactory(new PropertyValueFactory<>("Statut"));
	            // code pour rendre les lignes des employes inactifs rouge / actifs vert
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
	                            
	                        } else {
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
	    void showallvehicule() {
	    	rechercher_vehicule(null);
	    }
	    
	    @FXML
	    void showallrendez_vous() {
	    	rechercher_rdv(null);
	    }
	    
	    @FXML
	    public void addclient(ActionEvent event) throws IOException {
	    	var pane = new PaneController();
	        var controller = pane.fenetre("form.fxml", "Formulaire d'ajout d'adresse");
	        if (controller instanceof FormController formController) {
	        	List<String> li = new ArrayList<>(Collections.nCopies(7, "")); // remplire li de ""
	            formController.setFormType("client","ajouter",li,0);
	            formController.setAccueille(this);  // Rafraîchir la table
	            
	        }
	    }
	    
	    @FXML
	    void details(ActionEvent event) throws IOException {
	    	PaneController pane = new PaneController();
	    	if(client.isVisible()) {
	    		pane.fenetre("client.fxml", "Gestion des Clients");
	    	}
	    	if(employe.isVisible()) {
	    		pane.fenetre("employe.fxml", "Gestion des Employés");
	    	}
	    	if(stock.isVisible()) {
	    		pane.fenetre("stock.fxml", "Gestion du Stock");
	    	}
	    }
	    
	    @FXML
	    void addvehicule(ActionEvent event) throws IOException {
	    	var pane = new PaneController();
	        var controller = pane.fenetre("form.fxml",null);
	        if (controller instanceof FormController formController) {
	        	List<String> li = new ArrayList<>(Collections.nCopies(9, "")); // remplire li de ""
	            formController.setFormType("vehicule","ajouter",li,0);
	            formController.setAccueille(this);  // Rafraîchir la table
	        }
	    }
	    @FXML
	    void addrdv(ActionEvent event) throws IOException {
	    	var pane = new PaneController();
	    	var controller = pane.fenetre("form.fxml",null);
	    	if (controller instanceof FormController formController) {
	    		List<String> li = new ArrayList<>(Collections.nCopies(6, "")); // remplire li de ""
	    		formController.setFormType("rdv","ajouter",li,0);
	    		formController.setAccueille(this);  // Rafraîchir la table
	    	}
	    }
	    
	    @FXML
	    void modvehicule(ActionEvent event) throws IOException, SQLException {
	    	var selectvehicule = vehicule_tab.getSelectionModel().getSelectedItem();
	    	
	    	if(selectvehicule == null) {
	    		showAlert("Information", "Veuillez sélectionner le vehicule à modifier");
	    		return;
	    	}
	    	
	    	var pane = new PaneController();
	    	var controller = pane.fenetre("form.fxml", null);
	    	if(controller instanceof FormController formController) {
	    		
	    		List<String> li = List.of(	    				
	    				selectvehicule.getproprietaire(),
	    				selectvehicule.getcouleur(),
	    				selectvehicule.getkilometrage(),
	    				selectvehicule.getnum_chassi(),
	    				selectvehicule.getmatricule(),	    				
	    				selectvehicule.getmarque(),
	    				selectvehicule.getmodele(),
	    				selectvehicule.getcarburant(),
	    				selectvehicule.getannee_fabrication()
	    				
	    				);
	    		int id = getvehicule(li);
	    		formController.setFormType("vehicule","modifier",li,id);
	    		formController.setAccueille(this); // Rafraîchir la table
	    	}
	    }
	    
	    @FXML
	    void supvehicule() {
	    	var selectvehicule = vehicule_tab.getSelectionModel().getSelectedItem();
	    	
	    	if(selectvehicule == null) {
	    		showAlert("Information", "Veuillez sélectionner le vehicule à modifier");
	    		return;
	    	}
	    	var confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, 
	                "Êtes-vous sûr de vouloir supprimer ce vehicule ?", ButtonType.YES, ButtonType.NO);
	        confirmAlert.setTitle("Confirmation de suppression");
	        confirmAlert.setHeaderText(null);
	        
	        confirmAlert.showAndWait().ifPresent(response -> {
	            if (response == ButtonType.YES) {
	                try (var conn = DataBaseConnection.getConnection();
	                        var stmt = conn.prepareCall("{call supvehicule(?)}")){
	                	List<String> li = List.of(	    				
	    	    				selectvehicule.getproprietaire(),
	    	    				selectvehicule.getcouleur(),
	    	    				selectvehicule.getkilometrage(),
	    	    				selectvehicule.getnum_chassi(),
	    	    				selectvehicule.getmatricule(),	    				
	    	    				selectvehicule.getmarque(),
	    	    				selectvehicule.getmodele(),
	    	    				selectvehicule.getcarburant(),
	    	    				selectvehicule.getannee_fabrication()
	    	    				
	    	    				);
	                	int id = getvehicule(li);
	                    stmt.setInt(1, id); 
	                    stmt.execute();
	                    initialize(); // Rafraîchir la table
	                    showAlert("Succès", "Vehicule supprimée avec succès.");	                    
	                } catch (SQLException e) {
	                    showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
	    
	    @FXML
	    void modvrdv(ActionEvent event) throws IOException, SQLException {
	    	var selectrdv = rendezvous_tab.getSelectionModel().getSelectedItem();
	    	
	    	if(selectrdv == null) {
	    		showAlert("Information", "Veuillez sélectionner le Rendez-vous à modifier");
	    		return;
	    	}
	    	
	    	var pane = new PaneController();
	    	var controller = pane.fenetre("form.fxml", null);
	    	if(controller instanceof FormController formController) {
	    		
	    		List<String> li = List.of(	    				
	    				selectrdv.getClient_rdv(),
	    				selectrdv.getEmploye_rdv(),
	    				selectrdv.getVehicule_rdv(),
	    				selectrdv.getDate_rdv(),
	    				selectrdv.getHeur_rdv(),
	    				selectrdv.getDetail_rdv()
	    					    								
	    				);
	    		int id = getrdv(li);
	    		formController.setFormType("rdv","modifier",li,id);
	    		formController.setAccueille(this); // Rafraîchir la table
	    	}
	    }
	    
	    @FXML
	    private void handleRecherche(KeyEvent event) {	    	    	
	    	    if (event.getCode() == KeyCode.ENTER) {
	    	    	 
	    	    	if(client.isVisible()) {
	    	    		 String valeur = recherche.getText();
	    	    		 recherche_client(valeur);
	    	    		 return;
	    	    	}
	    	    	else if(employe.isVisible()) {
	    	        	String valeur = recherche_employe.getText();
	    	        	rechercher_employe(valeur);
	    	        	return;
	    	        }
	    	    	else if(Vehicule.isVisible()) {	    	    		
	    	    		String valeur = recherche_vehicule.getText();
	    	    		rechercher_vehicule(valeur);
	    	        	return;
	    	    	}
	    	    	else if(rendez_vous.isVisible()) {
	    	    		String valeur = recherche_rdv.getText();
	    	    		rechercher_rdv(valeur);
	    	    	}
	    	    	else if(stock.isVisible()) {
	    	    		String valeur = recherche.getText();
	    	    		recherche_stock(valeur);
	    	    	}
	    	    }	    	 
	    }
	    
	    private void recherche_stock(String critere) {
	    	var data = FXCollections.<StockItem>observableArrayList();
	        try (var conn = DataBaseConnection.getConnection();
	             var stmt = conn.prepareCall("{call rechercher_produit(?)}")) {
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
	                    var reference = rs.getString("ref");
	                    var nom = rs.getString("nom");
	                    var prix = rs.getString("prix");
	                    var quantite = rs.getString("quantite");
	                    var Emplacement = rs.getString("emplacement");
	                    var description = rs.getString("descript");	                  
	                    
	                    data.add(new StockItem(reference, nom, quantite, prix, Emplacement, description));
	                }
	            }

	            //Configuration des colonnes (assure-toi que ce sont bien les colonnes TableColumn)
	            reference_col.setCellValueFactory(new PropertyValueFactory<>("reference"));
	            nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
	            quantite_col.setCellValueFactory(new PropertyValueFactory<>("quantite"));	            
	            prix_col.setCellValueFactory(new PropertyValueFactory<>("prix"));            
	            Emplacement.setCellValueFactory(new PropertyValueFactory<>("Emplacement"));
	            description_col.setCellValueFactory(new PropertyValueFactory<>("description"));

	            stock_tab.setItems(data);            

	        } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	         // Appliquer une rowFactory personnalisée à la TableView
	            stock_tab.setRowFactory(tv -> new TableRow<StockItem>() {
	                @Override
	                protected void updateItem(StockItem item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (item == null || empty) {
	                        setStyle(""); // Réinitialiser le style
	                    } else {
	                    	int nombre = Integer.parseInt(item.getQuantite());
	                    	if (nombre == 0) {
	                        	if (isSelected()) {
	                            	setStyle("-fx-background-color: #7F8C8D; ");
	                            }
	                        	else {
	                            	setStyle("-fx-background-color: #ffcccc;"); // rouge clair
	                            }
	                        } else {
	                        	if (isSelected()) {
	                            	setStyle("-fx-background-color: #7F8C8D; ");
	                            }
	                            else {
	                            	setStyle("-fx-background-color: #ccffcc;");// vert clair
	                            }
	                        }
	                    }
	                }
	            });
	    }
	    
	    private void recherche_client(String critere) {
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
	                    //System.out.println(nom + " " + prenom + " " + email + " " + typeClient);
	                    data.add(new Client(nom, prenom, email, phone, numClient, typeClient, "", ""));
	                }
	            }

	            //Configuration des colonnes (assure-toi que ce sont bien les colonnes TableColumn)
	            Num_client.setCellValueFactory(new PropertyValueFactory<>("Num_client"));
	            Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
	            Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
	            Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
	            Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
	            Type.setCellValueFactory(new PropertyValueFactory<>("Type"));

	            client_tab.setItems(data);	            

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private void rechercher_employe(String critere) {
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
		               
		                data.add(new Employe(Nom, Prenom, Email,
		                		Phone, Statut,Matricule, "", "",
		                		"", "", "", "", ""));
		            }
	            }
		            // Configurer les colonnes
		            Matricule.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
		            Nom_employe.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		            Prenom_employe.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
		            Email_employe.setCellValueFactory(new PropertyValueFactory<>("Email"));
		            Phone_employe.setCellValueFactory(new PropertyValueFactory<>("Phone"));
		            Statut.setCellValueFactory(new PropertyValueFactory<>("Statut"));
		           	
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
		                        } else {
		                            setStyle(""); // réinitialise si aucun cas
		                        }
		                    }
		                }
		            });           
		            employe_tab.setItems(data);
		            


	        }catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void rechercher_vehicule(String critere) {
	    	var data = FXCollections.<vehicule>observableArrayList();
	    	try(var conn = DataBaseConnection.getConnection();
	    		var stmt = conn.prepareCall("{call rechercher_vehicule(?)}")){
	    		//Définir les paramètres AVANT d'exécuter la requête
	            if (critere != null && !critere.isEmpty()) {
	            	//System.out.println(critere);

	                stmt.setString(1, critere); 
	                
	            } else {
	                stmt.setNull(1, Types.NVARCHAR);
	                
	            }
	            try(var rs = stmt.executeQuery()){
	            	
	            	while(rs.next()) {
	            		var proprietaire = rs.getString("proprietaire");
		                var matricule = rs.getString("matricule");
		                var marque = rs.getString("marque");
		                var modele = rs.getString("modele");
		                var couleur = rs.getString("couleur");
		                var carburant = rs.getString("carburant");	
		                var kilometrage = rs.getString("kilometrage");	
		                var num_chassi = rs.getString("num_chassi");	
		                var annee_fabrication = rs.getString("annee_fabrication");	
		            
		               
		                data.add(new vehicule(proprietaire, couleur, kilometrage,num_chassi, 
		                		 matricule, marque, modele, carburant, annee_fabrication));
	            	}
	            	
	            	// Configurer les colonnes
	            	proprietaire.setCellValueFactory(new PropertyValueFactory<>("proprietaire"));
	            	matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
	            	marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
	            	modele.setCellValueFactory(new PropertyValueFactory<>("modele"));
	            	couleur.setCellValueFactory(new PropertyValueFactory<>("couleur"));
	            	carburant.setCellValueFactory(new PropertyValueFactory<>("carburant"));
	            	kilometrage.setCellValueFactory(new PropertyValueFactory<>("kilometrage"));
	            	num_chassi.setCellValueFactory(new PropertyValueFactory<>("num_chassi"));
	            	annee_fabrication.setCellValueFactory(new PropertyValueFactory<>("annee_fabrication"));
	            	
	            	vehicule_tab.setItems(data);
	            }
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    public void rechercher_rdv(String critere) {
	    	var data = FXCollections.<Rendezvous>observableArrayList();
	    	
	    	try(var conn = DataBaseConnection.getConnection();
	    			var stmt = conn.prepareCall("{call rechercher_rdv(?)}")){
	    		//Définir les paramètres AVANT d'exécuter la requête
	    		if (critere != null && !critere.isEmpty()) {
	    			//System.out.println(critere);
	    			
	    			stmt.setString(1, critere); 
	    			
	    		} else {
	    			stmt.setNull(1, Types.NVARCHAR);
	    			
	    		}
	    		try(var rs = stmt.executeQuery()){
	    			
	    			while(rs.next()) {
	    				var date_rdv = rs.getString("date_rdv");
	    				var heur_rdv = rs.getString("heure_rdv");
	    				var client_rdv = rs.getString("client");
	    				var employe_rdv = rs.getString("employe");
	    				var vehicule_rdv = rs.getString("vehicule");
	    				var detail_rdv = rs.getString("detail");	
	    								    				
	    				data.add(new Rendezvous(date_rdv, heur_rdv, client_rdv, employe_rdv, 
	    						vehicule_rdv, detail_rdv));
	    			}
	    			
	    			// Configurer les colonnes
	    			date_rdv.setCellValueFactory(new PropertyValueFactory<>("date_rdv"));
	    			heure_rdv.setCellValueFactory(new PropertyValueFactory<>("heur_rdv"));
	    			client_rdv.setCellValueFactory(new PropertyValueFactory<>("client_rdv"));
	    			employe_rdv.setCellValueFactory(new PropertyValueFactory<>("employe_rdv"));
	    			vehicule_rdv.setCellValueFactory(new PropertyValueFactory<>("vehicule_rdv"));
	    			detail_rdv.setCellValueFactory(new PropertyValueFactory<>("detail_rdv"));
	    			
	    			// Si la date du rendez-vous est dans le passé, la ligne est rouge sinon verte
	    			rendezvous_tab.setRowFactory(tv -> new TableRow<Rendezvous>() {
	    			    @Override
	    			    protected void updateItem(Rendezvous item, boolean empty) {
	    			        super.updateItem(item, empty);

	    			        if (item == null || empty) {
	    			            setStyle("");
	    			        } else {
	    			        	
	    			            try {
	    			                // On suppose que la date est au format yyyy-MM-dd
	    			                LocalDate dateRdv = LocalDate.parse(item.getDate_rdv());
	    			                LocalDate today = LocalDate.now();
	    			                
	    			                if (dateRdv.isBefore(today)) {
	    			                    // Rouge si la date est passée    			                    
		    			                if (isSelected()) {
			                            	setStyle("-fx-background-color: #7F8C8D; ");
			                            }
		    			                else {
		    			        		setStyle("-fx-background-color: #ffcccc;");
		    			                }
	    			                } else {
	    			                    // Vert si aujourd'hui ou futur	    			                    
	    			                    if (isSelected()) {
			                            	setStyle("-fx-background-color: #7F8C8D; ");
			                            }
		    			                else {
		    			        		setStyle("-fx-background-color: #ccffcc;");
		    			                }
	    			                }
	    			            } catch (Exception e) {
	    			                // Si erreur de parsing, on ne change pas le style
	    			                setStyle("");
	    			            }
	    			        }
	    			    }
	    			});

	    			rendezvous_tab.setItems(data);
	    		}
	    	} catch (SQLException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }

	    private int getvehicule(List<String> li) throws SQLException {
	    	
	    	var conn = DataBaseConnection.getConnection();
	    	
	    	var stmt = conn.prepareCall("{call getvehicule(?,?,?,?,?,?,?,?,?)}");
            
            stmt.setString(1,li.get(1));
            stmt.setString(2,li.get(2));
            stmt.setString(3,li.get(3));
            stmt.setString(4,li.get(4));	            
            stmt.setString(5,li.get(5));	            
            stmt.setString(6,li.get(6));	            
            stmt.setString(7,li.get(7));	            
            stmt.setString(8,li.get(8));	            
            // Déclarer le 9 ème paramètre comme paramètre de sortie
            stmt.registerOutParameter(9, Types.INTEGER);
            stmt.execute();	         
            // Récupérer le résultat du paramètre OUT
            return stmt.getInt(9);	    	
	    }
	    private int getrdv(List<String> li) throws SQLException {
	    	
	    	var conn = DataBaseConnection.getConnection();
	    	
	    	var stmt = conn.prepareCall("{call getrdv(?,?,?,?,?,?)}");
	    	
	    	
	    	LocalDate localDate = LocalDate.parse(li.get(3)); 
			Date sqlDate = Date.valueOf(localDate);
			
			LocalTime localTime = LocalTime.parse(li.get(4)+":00"); 
			Time sqlTime = Time.valueOf(localTime);
			
	    	stmt.setString(1,li.get(0));
	    	stmt.setString(2,li.get(1));
	    	stmt.setString(3,li.get(2));
	    	stmt.setDate(4,sqlDate);	            
	    	stmt.setTime(5,sqlTime);	            
	    		            
	    	// Déclarer le 6 ème paramètre comme paramètre de sortie
	    	stmt.registerOutParameter(6, Types.INTEGER);
	    	stmt.execute();	         
	    	// Récupérer le résultat du paramètre OUT
	    	return stmt.getInt(6);	    	
	    }
	    private void showAlert(String title, String message) {
	        var alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
}
