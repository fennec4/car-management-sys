package application.controllers;

import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import application.controllers.addresse.AddresseController;
import application.controllers.client.ClientController;
import application.controllers.employe.AffectationController;
import application.controllers.employe.EmployeController;
import application.controllers.employe.HoraireController;
import application.controllers.employe.PostController;
import application.controllers.employe.PrimeController;
import application.controllers.stock.DepotController;
import application.controllers.stock.StockDetailController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class FormController {
    
    @FXML
    private GridPane formGrid;
    
    @FXML
    private Button confirm;

    private String formType;
    
    private List<String> li;
    
    // Liste pour stocker les champs de texte dans l'ordre
    private List<Control> formFields = new ArrayList<>();
    
    private int id;
    
    private int jour;
    
    private int idemp;
    
    private EmployeController employe;
    
    private AddresseController addresseController;
    
    private Accueille accueille;
    
    private ClientController client;
    
    private PostController post;
    
    private HoraireController heur;
    
    private AffectationController aff;
    
    private PrimeController prime;
    
    private StockDetailController stockController;
    
    private DepotController depot;
   
    public void setAddresseController(AddresseController addresseController) {
        this.addresseController = addresseController;
    }
    
    public void setAccueille(Accueille accueille) {
        this.accueille = accueille;
    }
    
    public void setClient(ClientController client) {
    	this.client = client;
    }
    public void setEmploye(EmployeController employe) {
    	this.employe = employe;
    }
    public void setPost(PostController post) {
    	this.post = post;
    }
    public void setHeur(HoraireController heur) {
    	this.heur = heur;
    }
    
    public void setAff(AffectationController aff) {
    	this.aff = aff;
    }

    public void setPrime(PrimeController prime) {
    	this.prime = prime;
    }
    public void setStockController(StockDetailController stockController) {
    	this.stockController = stockController;
    }   
    public void setDepot(DepotController depot) {
    	this.depot = depot;
    }   
    
    public void setJour(int jour , int idemp) {
    	this.jour = jour;
    	this.idemp = idemp;
    }

    public void setFormType(String type, String fonct, List<String> list, int id) {
        this.formType = type;
        this.li = list;
        this.id = id;
        setupForm(fonct);
    }

    private void setupForm(String btn) {
        formGrid.getChildren().clear();
        formGrid.getColumnConstraints().clear();
        formGrid.getRowConstraints().clear();
        
        // Configuration générale du GridPane
        formGrid.setHgap(10); // 10px entre label et input
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(20));
        formGrid.setAlignment(Pos.CENTER); // Centrer le contenu
        
        // Vider la liste des champs
        formFields.clear();
        
        int row = 0;
        
        switch (formType) {
            case "adresse" -> {
                setupAddressLayout();
                addFormField("Pays", li.get(0), row, 0, 1);
                addFormField("Wilaya", li.get(1), row++, 2, 1);
                addFormField("Code Wilaya", li.get(2), row, 0, 1);
                addFormField("Daira", li.get(3), row++, 2, 1);
                addFormField("Commune", li.get(4), row, 0, 1);
                addFormField("Code Commune", li.get(5), row++, 2, 1);
                addFormField("Rue", li.get(6), row, 0, 1);
                addFormField("Designation", li.get(7), row, 2, 1);
                
                confirm.setOnAction(_ -> {
                    try {
                        if(btn.equals("ajouter")) {
                            ajouter("adresse");
                        } else if(btn.equals("modifier")) {
                            modifier("adresse");
                        }
                    } catch (SQLException e) {
                        showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
            case "client" -> {
                setupLayout();
                addFormField("Nom", li.get(0), row, 0, 1);
                addFormField("Prenom", li.get(1), row++, 2, 1);
                addFormField("Email", li.get(2), row, 0, 1);
                addFormField("Phone", li.get(3), row++, 2, 1);
                addFormField("Type", li.get(4), row, 0, 1);
                addFormField("Date d'inscription", li.get(5), row++, 2, 1);              
                // TextArea prend toute la ligne
                addFormField("Details", li.get(6), row, 0, 4);
                
                confirm.setOnAction(_ -> {
                    try {
                        if(btn.equals("ajouter")) {
                            ajouter("client");
                        } else if(btn.equals("modifier")) {
                            modifier("client");
                        }
                    } catch (SQLException e) {
                        showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
            case "employe" -> {
                setupLayout();
                addFormField("Nom", li.get(0), row, 0, 1);
                addFormField("Prenom", li.get(1), row++, 2, 1);
                addFormField("Email", li.get(2), row, 0, 1);
                addFormField("Phone", li.get(3), row++, 2, 1);
                addFormField("Statut", li.get(4), row, 0, 1);
                addFormField("N° de sécurité sociale", li.get(5), row++, 2, 1);
                addFormField("temps de travail/jour", li.get(6), row, 0, 1);
                addFormField("Adresse", li.get(7), row++, 2, 1);
                addFormField("Lieu de naissance", li.get(8), row, 0, 1);
                addFormField("Date de naissance", li.get(9), row++, 2, 1);
                addFormField("Date de recrutement", li.get(10), row++, 0, 1);
                
             // TextArea prend toute la ligne
                addFormField("Details", li.get(11), row, 0, 4);
                confirm.setOnAction(_ -> {
                    try {
                        if(btn.equals("ajouter")) {
                            ajouter("employe");
                        } else if(btn.equals("modifier")) {
                            modifier("employe");
                        }
                    } catch (SQLException e) {
                        showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
            case "vehicule" ->{
            	setupLayout();
                addFormField("Proprietaire", li.get(0), row, 0, 1);
                addFormField("Couleur", li.get(1), row++, 2, 1);
                addFormField("Kilometrage", li.get(2), row, 0, 1);
                addFormField("N°chassi", li.get(3), row++, 2, 1);
                addFormField("Matricule", li.get(4), row, 0, 1);
                addFormField("Marque", li.get(5), row++, 2, 1);
                addFormField("Modele", li.get(6), row, 0, 1);
                addFormField("Carburant", li.get(7), row++, 2, 1);
                addFormField("Année", li.get(8), row, 0, 1);
                confirm.setOnAction(_ -> {
                    try {
                        if(btn.equals("ajouter")) {
                            ajouter("vehicule");
                        } else if(btn.equals("modifier")) {
                            modifier("vehicule");
                        }
                    } catch (SQLException e) {
                        showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
            case "rdv" ->{
            	setupLayout();
            	addFormField("Client", li.get(0), row, 0, 1);
            	addFormField("Employe chargé", li.get(1), row++, 2, 1);
            	addFormField("Vehicule", li.get(2), row, 0, 1);
            	addFormField("Date", li.get(3), row++, 2, 1);
            	addFormField("Heure", li.get(4), row++, 0, 1);
            	addFormField("Details", li.get(5), row,  0, 4);
            	confirm.setOnAction(_ -> {
            		try {
            			if(btn.equals("ajouter")) {
            				ajouter("rdv");
            			} else if(btn.equals("modifier")) {
            				modifier("rdv");
            			}
            		} catch (SQLException e) {
            			showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
            			e.printStackTrace();
            		}
            	});
            }
            case "post" ->{
            	setupLayout();
            	addFormField("Titre", li.get(0), row, 0, 1);
            	addFormField("Salaire de base", li.get(1), row++, 2, 1);           	
            	addFormField("Description", li.get(2), row,  0, 4);
            	confirm.setOnAction(_ -> {
            		try {
            			if(btn.equals("ajouter")) {
            				ajouter("post");
            			} else if(btn.equals("modifier")) {
            				modifier("post");
            			}
            		} catch (SQLException e) {
            			showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
            			e.printStackTrace();
            		}
            	});
            }
            case "heur" ->{
            	setupLayout();
            	addFormField("Heure debut", li.get(0), row, 0, 1);
            	addFormField("Heure fin", li.get(1), row++, 2, 1);           	           	
            	confirm.setOnAction(_ -> {
            		try {
            			if(btn.equals("ajouter")) {
            				ajouter("heur");
            			} else if(btn.equals("modifier")) {      				
            				modifier("heur");
            			}
            		} catch (SQLException e) {
            			showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
            			e.printStackTrace();
            		}
            	});
            }
            case "aff" ->{
            	setupLayout();
            	addFormField("Post", li.get(0), row, 0, 1);
            	addFormField("Date debut", li.get(1), row++, 2, 1);
            	addFormField("Date fin", li.get(2), row, 0, 1);
            	addFormField("Salaire", li.get(3), row, 2, 1);
            	confirm.setOnAction(_ -> {
            		try {
            			if(btn.equals("ajouter")) {
            				ajouter("aff");
            			} else if(btn.equals("modifier")) {      				
            				modifier("aff");
            			}
            		} catch (SQLException e) {
            			showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
            			e.printStackTrace();
            		}
            	});
            }
            case "prime" ->{
            	setupLayout();
            	addFormField("montant", li.get(0), row, 0, 1);
            	addFormField("Prime de", li.get(1), row++, 2, 1);
            	addFormField("Date attribution", li.get(2), row, 0, 1);
            	addFormField("Date fin", li.get(3), row++, 2, 1);
            	addFormField("commentaire", li.get(4), row, 0, 4);
            	confirm.setOnAction(_ -> {
            		try {
            			if(btn.equals("ajouter")) {
            				ajouter("prime");
            			} else if(btn.equals("modifier")) {      				
            				modifier("prime");
            			}
            		} catch (SQLException e) {
            			showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
            			e.printStackTrace();
            		}
            	});
            }
            case "produit" -> {
                setupLayout();
                addFormField("Emplacement", li.get(0), row, 0, 1);
                addFormField("Référence", li.get(1), row++, 2, 1);
                addFormField("Prix", li.get(2), row, 0, 1);
                addFormField("Nom", li.get(3), row++, 2, 1);
                addFormField("Quantité", li.get(4), row++, 0, 1);
                addFormField("Description", li.get(5), row, 0, 4);
                
                confirm.setOnAction(_ -> {
                    try {
                        if(btn.equals("ajouter")) {
                            ajouter("produit");
                        } else if(btn.equals("modifier")) {
                            modifier("produit");
                        }
                    } catch (SQLException e) {
                        showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
            case "depot" -> {
            	setupLayout();
            	addFormField("N°depot", li.get(0), row, 0, 1);
            	addFormField("Rayon", li.get(1), row++, 2, 1);
            	addFormField("Etagere", li.get(2), row, 0, 1);
            	addFormField("Localisation", li.get(3), row++, 2, 1);         
            	addFormField("Description", li.get(4), row, 0, 4);
            	
            	confirm.setOnAction(_ -> {
            		try {
            			if(btn.equals("ajouter")) {
            				ajouter("depot");
            			} else if(btn.equals("modifier")) {
            				modifier("depot");
            			}
            		} catch (SQLException e) {
            			showAlert("Erreur", "Une erreur est survenue lors de l'opération: " + e.getMessage());
            			e.printStackTrace();
            		}
            	});
            }
        }
        
        setupFieldNavigation();
        
        if (!formFields.isEmpty()) {
            formFields.get(0).requestFocus();
        }
    }

    private void setupAddressLayout() {
        // 4 colonnes : Label1, Field1, Label2, Field2
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(120);
        col1.setPrefWidth(120);
        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setMinWidth(150);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setMinWidth(120);
        col3.setPrefWidth(120);
        
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS);
        col4.setMinWidth(150);
        
        formGrid.getColumnConstraints().addAll(col1, col2, col3, col4);
    }

    private void setupLayout() {
        // 4 colonnes pour permettre 2 champs par ligne + ligne complète pour TextArea
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(120);
        col1.setPrefWidth(120);
        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setMinWidth(150);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setMinWidth(120);
        col3.setPrefWidth(120);
        
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS);
        col4.setMinWidth(150);
        
        formGrid.getColumnConstraints().addAll(col1, col2, col3, col4);
    }

    private void addFormField(String labelText, String text, int rowIndex, int colIndex, int colspan) {
        Label label = new Label(labelText);
        label.getStyleClass().add("label-bold");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER_RIGHT); // Aligner le texte du label à droite
        
        Node inputNode = createInputNode(labelText, text);
        
        // Positionnement du label
        formGrid.add(label, colIndex, rowIndex);
        
        // Positionnement du champ input
        if (colspan > 1) {
            // Pour les champs qui s'étendent sur plusieurs colonnes (comme TextArea)
            formGrid.add(inputNode, colIndex + 1, rowIndex, colspan - 1, 1);
        } else {
            formGrid.add(inputNode, colIndex + 1, rowIndex);
        }
        
        // Configuration spéciale pour TextArea
        if (inputNode instanceof TextArea) {
            GridPane.setHgrow(inputNode, Priority.ALWAYS);
            GridPane.setFillWidth(inputNode, true);
            ((TextArea) inputNode).setPrefRowCount(3);
            ((TextArea) inputNode).setMaxWidth(Double.MAX_VALUE);
        }
        
        // Ajouter le champ à notre liste pour gérer la navigation
        if (inputNode instanceof TextInputControl || inputNode instanceof ComboBox || inputNode instanceof DatePicker) {
            formFields.add((Control) inputNode);
        }
    }

    private Node createInputNode(String labelText, String text) {
    	if(labelText.toLowerCase().contains("date")) {
    		DatePicker datePicker = new DatePicker();
            datePicker.setId(labelText);
            datePicker.setPromptText("YYYY-MM-DD");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            datePicker.setConverter(new StringConverter<LocalDate>() {
                @Override
                public String toString(LocalDate date) {
                    return (date != null) ? formatter.format(date) : "";
                }

                @Override
                public LocalDate fromString(String string) {
                    return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
                }
            });

            if (text != null && !text.isEmpty()) {
                try {
                    datePicker.setValue(LocalDate.parse(text));
                } catch (Exception e) {
                    // Parsing échoué, on ignore
                }
            }

            datePicker.setMaxWidth(Double.MAX_VALUE);
            return datePicker;
    	}
        return switch (labelText.toLowerCase()) {
            case "type" -> {
                ComboBox<String> combo = new ComboBox<>();
                combo.getItems().addAll("individuel", "entreprise");
                combo.setValue(text != null && !text.isEmpty() ? text : "individuel");
                combo.setId(labelText);              
                combo.setMaxWidth(Double.MAX_VALUE);
                yield combo;
            }
            case "statut" -> {
            	ComboBox<String> combo = new ComboBox<>();
            	combo.getItems().addAll("actif", "inactif");
                combo.setValue(text != null && !text.isEmpty() ? text : "actif");
                combo.setId(labelText);
                combo.setMaxWidth(Double.MAX_VALUE);
                yield combo;
            }
            case "details" -> {
                TextArea textArea = new TextArea();
                textArea.setPromptText(labelText);
                if (text != null && !text.isEmpty()) {
                    textArea.setText(text);
                }
                textArea.setId(labelText);
                textArea.setPrefRowCount(3);
                textArea.setMaxWidth(Double.MAX_VALUE);
                yield textArea;
            }          
            default -> {
                TextField textField = new TextField();
                textField.setPromptText(labelText);
                if(labelText.toLowerCase().contains("email")) {
                	textField.setPromptText("xxxx@example.com");                	
                }
                if(labelText.toLowerCase().contains("kilometrage")) {
                	textField.setPromptText("xxx.xx");
                }
                if(labelText.toLowerCase().contains("heur")) {
                	textField.setPromptText("hh:min");
                }
                if(labelText.toLowerCase().contains("emplacement")) {
                	textField.setPromptText("depot-rayon-etagere");
                }
                
                
                textField.setText(text != null ? text : "");
                textField.setId(labelText);
                textField.setMaxWidth(Double.MAX_VALUE);
                yield textField;
            }
        };
    }

    private void setupFieldNavigation() {
        for (int i = 0; i < formFields.size() - 1; i++) {
            final int index = i;
            formFields.get(i).setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    formFields.get(index + 1).requestFocus();
                }
            });
        }
        
        if (!formFields.isEmpty()) {
            formFields.get(formFields.size() - 1).setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    confirm.fire();
                }
            });
        }
    }
    
    public void close() throws SQLException {
        Stage currentStage = (Stage) formGrid.getScene().getWindow();
        currentStage.close();
    }

    public void ajouter(String quoi) throws SQLException {
        // Collecte de tous les champs de saisie
        var allInputs = getAllInputValues();
        
        if(quoi.equals("adresse")) {
            if (allInputs.size() != 8) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
                return;
            }
            
            try (var connection = DataBaseConnection.getConnection();
                 var stmt = connection.prepareCall("{call addaddresse(?, ?, ?, ?, ?, ?, ?, ?)}")) {
                
                for (int i = 0; i < allInputs.size(); i++) {
                    stmt.setString(i + 1, allInputs.get(i));
                }
                
                stmt.execute();
                
                if (addresseController != null) {
                    addresseController.loadDataFromDatabase();
                }
            }
        }
        else if(quoi.equals("client")) {        	
        	if (allInputs.size() <6) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
                return;
            }
            
            try (var connection = DataBaseConnection.getConnection();
                 var stmt = connection.prepareCall("{call addclient(?, ?, ?, ?, ?, ?, ?)}")) {
                
                for (int i = 0; i < allInputs.size(); i++) {
                	if(i == 5) {
                		LocalDate localDate = LocalDate.parse(allInputs.get(i)); 
                		Date sqlDate = Date.valueOf(localDate);
                		stmt.setDate(i+1, sqlDate);
                	}else {
                		stmt.setString(i + 1, allInputs.get(i));
                	}
                    
                }
                
                stmt.execute();
                
                if (accueille != null) {
                	accueille.showallclient();
                }
                if (client != null) {
                	client.showallclient();
                }
            }
        }
        else if(quoi.equals("employe")) {
        	if (allInputs.size() < 11) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	
        	try (var connection = DataBaseConnection.getConnection();
        			var stmt = connection.prepareCall("{call addemploye(?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
        		
        		stmt.setString(1,generateCode());
        		for (int i = 0; i < allInputs.size(); i++) {
        			if(i == 9 || i == 10) {
        				LocalDate localDate = LocalDate.parse(allInputs.get(i)); 
        				Date sqlDate = Date.valueOf(localDate);
        				stmt.setDate(i+2, sqlDate);       			
        			}
        			
        			else {
        				stmt.setString(i + 2, allInputs.get(i));
        			}
        			
        		}
        		
        		stmt.execute();
        		
        		if (accueille != null) {
        			accueille.showallemploye();
        		}
        		if (employe != null) {
        			employe.showallemploye();
        		}
        	}
        }
        else if(quoi.equals("vehicule")) {
        	if (allInputs.size() != 9) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}       	
        	try (var connection = DataBaseConnection.getConnection();
        			var stmt = connection.prepareCall("{call addvehicule(?,?,?,?,?,?,?,?,?)}")) {
        		
        		for (int i = 0; i < allInputs.size(); i++) {        			
        				stmt.setString(i + 1, allInputs.get(i));
	
        		}        		
        		stmt.execute();
        		
        		if (accueille != null) {
        			accueille.showallvehicule();
        		}
        	}
        }
        else if(quoi.equals("rdv")) {
        	if (allInputs.size() < 5) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}       	
        	try (var connection = DataBaseConnection.getConnection();
        			var stmt = connection.prepareCall("{call addrdv(?,?,?,?,?,?)}")) {
        		
        		for (int i = 0; i < allInputs.size(); i++) {  
        			if(i == 3) {
        				LocalDate localDate = LocalDate.parse(allInputs.get(i)); 
        				Date sqlDate = Date.valueOf(localDate);
        				stmt.setDate(i+1, sqlDate);
        			}
        			else if(i == 4) {
        				LocalTime localTime = LocalTime.parse(allInputs.get(i)); // format: "HH:mm"
        				Time sqlTime = Time.valueOf(localTime);
        				stmt.setTime(i+1, sqlTime); 
        			}
        			else {
        				stmt.setString(i + 1, allInputs.get(i));
        			}
        			
        			
        		}        		
        		stmt.execute();
        		
        		if (accueille != null) {
        			accueille.showallvehicule();
        		}
        	}
        }
        else if(quoi.equals("post")) {
        	if (allInputs.size() < 2) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	try (var connection = DataBaseConnection.getConnection();
        		var stmt = connection.prepareCall("{call addposte(?,?,?)}")) {
        		
        		for (int i = 0; i < allInputs.size(); i++) {
        			stmt.setString(i + 1, allInputs.get(i));
        		}
        		stmt.execute();
        		
        		if(post != null) {
        			post.loadDataFromDatabase();
        		}
        	}
        }
        else if(quoi.equals("heur")) {
        	if (allInputs.size() < 2) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	try (var connection = DataBaseConnection.getConnection();
        			var stmt = connection.prepareCall("{call addheur(?,?,?,?)}")) {
        		stmt.setInt(1, idemp);
        		stmt.setInt(2, jour);
        		LocalTime heur_debut = LocalTime.parse(allInputs.get(0)); // format: "HH:mm"
				Time sqlTime = Time.valueOf(heur_debut);
				stmt.setTime(3, sqlTime); 
				
				LocalTime heur_fin = LocalTime.parse(allInputs.get(1)); // format: "HH:mm"
				Time sqlTime2 = Time.valueOf(heur_fin);
				stmt.setTime(4, sqlTime2); 
        		
        		
        		stmt.execute();
        		
        		if(heur != null) {
        			heur.chargerHoraires();
        		}
        	}
        }
        else if(quoi.equals("aff")) {
        	if (allInputs.size() < 2) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}       	
        	try (var connection = DataBaseConnection.getConnection();
        			var stmt = connection.prepareCall("{call addaffectation(?,?,?,?,?)}")) {
        			
        			int idp = AffectationController.getPost(allInputs.get(0));
        			stmt.setInt(1, id); 
        			stmt.setInt(2, idp);
        			LocalDate localDate = LocalDate.parse(allInputs.get(1)); 
        				Date sqlDate = Date.valueOf(localDate);
        				stmt.setDate(3, sqlDate);
        				
        			if(allInputs.size() == 4) {
        				LocalDate localDate1 = LocalDate.parse(allInputs.get(2));
        				Date sqlDate1 = Date.valueOf(localDate1);
        				stmt.setDate(4, sqlDate1);
        				stmt.setString(5, allInputs.get(3));
        			}
        			else {
        				stmt.setNull(4, Types.NVARCHAR);
        				stmt.setString(5, allInputs.get(2));
        			}
        			 
        			
        			stmt.execute();
        			
        		if (aff != null) {
        			aff.chargerAffectation();
        		}
        	}
        }
        else if(quoi.equals("prime")) {
        	if (allInputs.size() < 2) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}       	
        	try (var connection = DataBaseConnection.getConnection();
        			var stmt = connection.prepareCall("{call addprime(?,?,?,?,?,?)}")) {
        		
        		stmt.setInt(1, id);
        		stmt.setString(2, allInputs.get(0));
        		stmt.setString(3, allInputs.get(1));
        	
        		LocalDate localDate = LocalDate.parse(allInputs.get(2)); 
        		Date sqlDate = Date.valueOf(localDate);
        		stmt.setDate(4, sqlDate);
        		
        		if(allInputs.size() == 5) {
        			LocalDate localDate1 = LocalDate.parse(allInputs.get(3));
        			Date sqlDate1 = Date.valueOf(localDate1);
        			stmt.setDate(5, sqlDate1);
        			stmt.setString(6, allInputs.get(4));
        		}
        		else {
        			stmt.setNull(5, Types.NVARCHAR);
        			stmt.setString(6, allInputs.get(3));
        		}
        		
        		
        		stmt.execute();
        		
        		if (prime != null) {
        			prime.chargerPrime();
        		}
        	}
        }
        else if(quoi.equals("produit")) {
            if (allInputs.size() <5) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
                return;
            }
            
            try (var connection = DataBaseConnection.getConnection();
                 var stmt = connection.prepareCall("{call addproduit(?, ?, ?, ?, ?, ?)}")) {
                
            	stmt.setString(1, allInputs.get(0)); 
                stmt.setString(2, allInputs.get(1)); 
                stmt.setString(3, allInputs.get(2)); 
                stmt.setString(4, allInputs.get(3)); 
                stmt.setString(5, allInputs.get(4));
                if(allInputs.size() == 5) {
                	stmt.setString(6, allInputs.get(5)); 
                }
                else {
    				stmt.setNull(6, Types.NVARCHAR);
    			}              
                stmt.execute();
                
                if (stockController != null) {
                    stockController.initialize();
                }
            }
        }
        else if(quoi.equals("depot")) {
        	if (allInputs.size() <4) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	
        	try (var connection = DataBaseConnection.getConnection();
        			var stmt = connection.prepareCall("{call adddepot(?, ?, ?, ?, ?)}")) {
        		
        		stmt.setString(1, allInputs.get(0)); 
        		stmt.setString(2, allInputs.get(1)); 
        		stmt.setString(3, allInputs.get(2)); 
        		stmt.setString(4, allInputs.get(3)); 
        		
        		if(allInputs.size() == 4) {
        			stmt.setString(5, allInputs.get(4)); 
        		}
        		else {
        			stmt.setNull(5, Types.NVARCHAR);
        		}              
        		stmt.execute();
        		
        		if (depot != null) {
        			depot.showdepot();
        		}
        	}
        }
        close();
    }

    private void modifier(String quoi) throws SQLException {
        var allInputs = getAllInputValues();
        
        
        
        if(quoi.equals("adresse")) {
            if (allInputs.size() != 8) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
                return;
            }
            modAddresse(id, allInputs);
            if (addresseController != null) {
                addresseController.loadDataFromDatabase();
            }
        }
        else if(quoi.equals("client")) {
        	if (allInputs.size() != 7) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
                return;
            }
        	modclient(id,allInputs);
        	
        	if (client != null) {
            	client.showallclient();
            }
        }
        else if(quoi.equals("employe")) {
        	if (allInputs.size() != 12) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
                return;
            }
        	
        	modemploye(id,allInputs);
        	
        	if (employe != null) {
            	employe.showallemploye();
            }
        }
        else if(quoi.equals("vehicule")) {
        	if (allInputs.size() != 9) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	modvehicule(id,allInputs);
        	if (accueille  != null) {
        		accueille.showallvehicule();
            }
        }
        
        else if(quoi.equals("rdv")) {
        	if (allInputs.size() != 6) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	modrdv(id,allInputs);
        	if (accueille  != null) {
        		accueille.showallrendez_vous();;
        	}
        }
        
        else if(quoi.equals("post")) {
        	if (allInputs.size() != 3) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	modposte(id,allInputs);
        	if(post != null) {
    			post.loadDataFromDatabase();
    		}
        }
        else if(quoi.equals("heur")) {
        	if (allInputs.size() != 2) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	
        	modheur(id,allInputs);
        	if(heur != null) {
    			heur.chargerHoraires();
    		}
        }
        else if(quoi.equals("aff")) {
        	if (allInputs.size() < 3) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	
        	modaffectation(id,allInputs);
        	if(aff != null) {
        		aff.chargerAffectation();
        	}
        }
        else if(quoi.equals("prime")) {
        	if (allInputs.size() < 5) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	
        	modprime(id,allInputs);
        	if(prime != null) {
        		prime.chargerPrime();
        	}
        }
        else if(quoi.equals("produit")) {
            if (allInputs.size() < 5) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
                return;
            }
            modStock(id, allInputs);
            if (stockController != null) {
               stockController.initialize();
            }
        }
        else if(quoi.equals("depot")) {
        	if (allInputs.size() < 4) {
        		showAlert("Erreur", "Veuillez remplir tous les champs obligatoires");
        		return;
        	}
        	modDepot(id, allInputs);
        	if (depot != null) {
        		depot.showdepot();
        	}
        }
        
        close();
    }

    private List<String> getAllInputValues() {
        List<String> values = new ArrayList<>();
        
        for (Node node : formGrid.getChildren()) { // parcourire tout les element "node" du formGrid
            if (node instanceof TextField tf) {
            	if(tf.getId().equals("Email")) { // verifier le format des email
            		String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
            		if(tf.getText().matches(regex)) {
            			values.add(tf.getText().trim()); // trim() supprime les caracters blanc au debut et la fin 
            		}
            		else {
            			showAlert("Format d'Email incorrect","Format attendu: xxxx@gamil.com");
            		}
            	}
            	else {
            		values.add(tf.getText().trim());
            	}
               
            } else if (node instanceof TextArea ta) {
                values.add(ta.getText().trim());
            } else if (node instanceof ComboBox<?> cb) {
                values.add(cb.getValue() != null ? cb.getValue().toString() : "");
            } else if (node instanceof DatePicker dp) {            	
                
                if (dp.getValue() != null) {                   
                    String dateString = dp.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));                    
                    // Validation supplémentaire                   
                        LocalDate.parse(dateString); // Vérifie que c'est bien au format YYYY-MM-DD
                        values.add(dateString);
                    } else if(dp.getId() != "Date fin"){
                    	showAlert("Format de date incorrect ","Format attendu: YYYY-MM-DD");                            
                    }          
            }
        }
        
        return values;
    }

    private void modAddresse(int id, List<String> list) throws SQLException {
        try (var conn = DataBaseConnection.getConnection();
             var stat = conn.prepareCall("{call modifieraddresse(?,?,?,?,?,?,?,?,?)}")) {
            
            stat.setInt(1, id);
            for (int i = 0; i < list.size(); i++) {
                stat.setString(i + 2, list.get(i));
            }
            stat.execute();
        }
    }
    
    private void modclient(int id, List<String> list) throws SQLException {
    	try (var conn = DataBaseConnection.getConnection();
                var stat = conn.prepareCall("{call modclient(?,?,?,?,?,?,?,?)}")) {
               
               stat.setInt(1, id);
               for (int i = 0; i < list.size(); i++) {
            	   if(i == 7) {
               		LocalDate localDate = LocalDate.parse(li.get(i)); 
               		Date sqlDate = Date.valueOf(localDate);
               		stat.setDate(i + 2, sqlDate);
            	   }
               	   else {
               			stat.setString(i + 2, list.get(i));
               		}
                   
               }
               stat.execute();
           }
    }
    private void modemploye(int id, List<String> list) throws SQLException {
    	try (var conn = DataBaseConnection.getConnection();
    			var stat = conn.prepareCall("{call modemploye(?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
    		
    		stat.setInt(1, id);
    		for (int i = 0; i < list.size(); i++) {
    			if(i == 9|| i == 10 ) {
    				LocalDate localDate = LocalDate.parse(list.get(i)); 
    				Date sqlDate = Date.valueOf(localDate);
    				stat.setDate(i + 2, sqlDate);
    			}
    			else {
    				stat.setString(i + 2, list.get(i));
    			}
    			
    		}
    		stat.execute();
    	}
    }
    
    private void modvehicule(int id, List<String> list) throws SQLException {
    	try (var conn = DataBaseConnection.getConnection();
    		 var stat = conn.prepareCall("{call modvehicule(?,?,?,?,?,?,?,?,?,?)}")) {
    		
    		stat.setInt(1, id);
    		for (int i = 0; i < list.size(); i++) { 			
    				stat.setString(i + 2, list.get(i));   			
    		}
    		stat.execute();
    	}
    }
    private void modposte(int id, List<String> list) throws SQLException {
    	try (var conn = DataBaseConnection.getConnection();
    			var stat = conn.prepareCall("{call modposte(?,?,?,?)}")) {
    		
    		stat.setInt(1, id);
    		for (int i = 0; i < list.size(); i++) { 			
    			stat.setString(i + 2, list.get(i));   			
    		}
    		stat.execute();
    	}
    }
    private void modheur(int id, List<String> list) throws SQLException {
    	try (var conn = DataBaseConnection.getConnection();
    			var stat = conn.prepareCall("{call modheur(?,?,?,?,?)}")) {
    		
    		
    		stat.setInt(1, id);
    		stat.setInt(2, idemp);
    		stat.setInt(3, jour);
    		LocalTime heur_debut = LocalTime.parse(list.get(0)); 
			Time sqlTime1 = Time.valueOf(heur_debut);
			stat.setTime(4, sqlTime1); 
			
			LocalTime heur_fin = LocalTime.parse(list.get(1)); 
			Time sqlTime = Time.valueOf(heur_fin);
			stat.setTime(5, sqlTime);
    		stat.execute();
    	}
    }
    private void modrdv(int id, List<String> list) throws SQLException {
    	try (var conn = DataBaseConnection.getConnection();
    			var stat = conn.prepareCall("{call modrdv(?,?,?,?,?,?,?)}")) {
    		
    		stat.setInt(1, id);
    		for (int i = 0; i < list.size(); i++) { 			
    			if(i == 3) {
    				LocalDate localDate = LocalDate.parse(list.get(i)); 
    				Date sqlDate = Date.valueOf(localDate);
    				stat.setDate(i+2, sqlDate);
    			}
    			else if(i == 4) {
    				LocalTime localTime = LocalTime.parse(list.get(i)); // format: "HH:mm"
    				Time sqlTime = Time.valueOf(localTime);
    				stat.setTime(i+2, sqlTime); 
    			}
    			else {
    				stat.setString(i + 2, list.get(i));
    			}   			
    		}
    		stat.execute();
    	}
    }
    
    private void modaffectation(int id, List<String> list) throws SQLException {
    	try (var conn = DataBaseConnection.getConnection();
    			var stat = conn.prepareCall("{call modaffectation(?,?,?,?,?,?)}")) {
    		
    		stat.setInt(1, id);
    		stat.setInt(2, idemp);    					
    		int idp = AffectationController.getPost(list.get(0));
			stat.setInt(3, idp);
			LocalDate localDate = LocalDate.parse(list.get(1)); 
				Date sqlDate = Date.valueOf(localDate);
				stat.setDate(4, sqlDate);
				
			if(list.size() == 4) {
				LocalDate localDate1 = LocalDate.parse(list.get(2));
				Date sqlDate1 = Date.valueOf(localDate1);
				stat.setDate(5, sqlDate1);
				stat.setString(6, list.get(3));
			}
			else {
				stat.setNull(5, Types.NVARCHAR);
				stat.setString(6, list.get(2));
			}			 			
			stat.execute();
    	}
    }
    private void modprime(int id, List<String> list) throws SQLException {
    	try (var conn = DataBaseConnection.getConnection();
    			var stat = conn.prepareCall("{call modprime(?,?,?,?,?,?,?)}")) {
    		
    		stat.setInt(1, id);
    		stat.setInt(2, idemp);
    		stat.setString(3, list.get(0));
    		stat.setString(4, list.get(1));
    		LocalDate localDate = LocalDate.parse(list.get(2)); 
    		Date sqlDate = Date.valueOf(localDate);
    		stat.setDate(5, sqlDate);
    		
    		if(list.size() == 5) {
    			LocalDate localDate1 = LocalDate.parse(list.get(3));
    			Date sqlDate1 = Date.valueOf(localDate1);
    			stat.setDate(6, sqlDate1);
    			stat.setString(7, list.get(4));
    		}
    		else {
    			stat.setNull(6, Types.NVARCHAR);
    			stat.setString(7, list.get(4));
    		}			 			
    		stat.execute();
    	}
    }
    
    private void modStock(int id, List<String> list) throws SQLException {
        try (var conn = DataBaseConnection.getConnection();
             var stmt = conn.prepareCall("{call moddproduit(?, ?, ?, ?, ?, ?, ?)}")) {
            
            stmt.setInt(1, id);
            stmt.setString(2, list.get(0)); 
            stmt.setString(3, list.get(1)); 
            stmt.setString(4, list.get(2)); 
            stmt.setString(5, list.get(3));
            stmt.setString(6, list.get(4));
            
            if(list.size() == 5) {
            	stmt.setString(7, list.get(5)); 
            }
            else {
				stmt.setNull(7, Types.NVARCHAR);
			}              
            stmt.execute();
        }
    }
    private void modDepot(int id, List<String> list) throws SQLException {
    	try (var conn = DataBaseConnection.getConnection();
    			var stmt = conn.prepareCall("{call moddepot(?, ?, ?, ?, ?, ?)}")) {
    		
    		stmt.setInt(1, id);
    		stmt.setString(2, list.get(0)); 
    		stmt.setString(3, list.get(1)); 
    		stmt.setString(4, list.get(2)); 
    		stmt.setString(5, list.get(3));
    		
    		
    		if(list.size() == 4) {
    			stmt.setString(6, list.get(4));
    		}
    		else {
    			stmt.setNull(6, Types.NVARCHAR);
    		}              
    		stmt.execute();
    	}
    }
    
    
    private static String generateCode() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(5);

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(chars.length());
            code.append(chars.charAt(index));
        }

        return code.toString();
    }

    private void showAlert(String title, String message) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}