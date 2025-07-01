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
import javafx.collections.ObservableList;
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

public class StockDetailController {
	@FXML
    private TableColumn<StockItem, String> Emplacement;

    @FXML
    private TableColumn<StockItem, String> description_col;

    @FXML
    private TableColumn<StockItem, String> nom_col;

    @FXML
    private TableColumn<StockItem, String> prix_col;

    @FXML
    private TableColumn<StockItem, String> quantite_col;

    @FXML
    private TextField recherche;

    @FXML
    private TableColumn<StockItem, String> reference_col;

    @FXML
    private TableView<StockItem> stock_tab;
    
    @FXML
    public void initialize() {
        recherche_stock(null);
    }
    
    @FXML
    void addStock() throws IOException {
    	var pane = new PaneController();
        var controller = pane.fenetre("form.fxml",null);
                
        if (controller instanceof FormController formController) {
        	List<String> li = new ArrayList<>(Collections.nCopies(6, ""));        	
            formController.setFormType("produit","ajouter",li,0);
            formController.setStockController(this);  
        }
    }

    @FXML
    void depot() throws IOException {
    	var log = new PaneController();
        log.fenetre("depot.fxml", "Connexion");
    }
    
    @FXML
    void modifyStock() throws SQLException, IOException  {
    	var selectproduit = stock_tab.getSelectionModel().getSelectedItem();
    	if(selectproduit == null) {
    		showAlert("Information", "Veuillez sélectionner le Produit à modifier");
    		return;
    	}
    	var pane = new PaneController();
    	var controller = pane.fenetre("form.fxml", null);
    	if(controller instanceof FormController formController) {
    		List<String> li;
    		if(selectproduit.getDescription() != null) {
    		 li = List.of(    				
    				selectproduit.getEmplacement(),
    				selectproduit.getReference(),
    				selectproduit.getPrix(),
    				selectproduit.getNom(),
    				selectproduit.getQuantite(),
    				selectproduit.getDescription()     				
       				);
    		}
    		else {
    			 li = List.of(    				
    	    				selectproduit.getEmplacement(),
    	    				selectproduit.getReference(),
    	    				selectproduit.getPrix(),
    	    				selectproduit.getNom(),
    	    				selectproduit.getQuantite(),
    	    				""     				
    	       				);
    		}
    		int id = getproduit(selectproduit);
    		formController.setFormType("produit","modifier",li,id);
    		formController.setStockController(this); // Rafraîchir la table
    	}
    }

    @FXML
    void suppStock() {
    	var selectproduit = stock_tab.getSelectionModel().getSelectedItem();
    	if(selectproduit == null) {
    		showAlert("Information", "Veuillez sélectionner le produit à supprimer");
    		return;
    	}
    	// Confirmation avant suppression
        var confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, 
                "Êtes-vous sûr de vouloir supprimer ce Produit ?", ButtonType.YES, ButtonType.NO);
        confirmAlert.setTitle("Confirmation de suppression");
        confirmAlert.setHeaderText(null);
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try (var conn = DataBaseConnection.getConnection();
                        var stmt = conn.prepareCall("{call supproduit(?)}")){
                	int id = getproduit(selectproduit);
                    stmt.setInt(1, id); 
                    stmt.execute();
                    initialize(); // Rafraîchir la table
                    showAlert("Succès", "Produit supprimée avec succès.");	                    
                } catch (SQLException e) {
                    showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void handleRecherche(KeyEvent event) {
    	if (event.getCode() == KeyCode.ENTER) {
    		String valeur = recherche.getText();
    		recherche_stock(valeur);
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
    private int getproduit(StockItem selectedproduit) throws SQLException {
    	var conn = DataBaseConnection.getConnection();
    	
    	var stmt = conn.prepareCall("{call getproduit(?,?,?,?)}");
            
            stmt.setString(1,selectedproduit.getReference());
            stmt.setString(2,selectedproduit.getPrix());
            stmt.setString(3,selectedproduit.getNom());	            
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