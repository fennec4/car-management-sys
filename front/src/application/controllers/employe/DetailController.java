package application.controllers.employe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import application.controllers.PaneController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DetailController {
	
	 	@FXML
	    private TableColumn<Employe, String> Addresse;

	    @FXML
	    private TableColumn<Employe, String> Date_naissance;

	    @FXML
	    private TableColumn<Employe, String> Date_recrutement;

	    @FXML
	    private TableColumn<Employe, String> Details;

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
	    void afficherAffectation() throws IOException, SQLException {
	    	var pane = new PaneController();
	    	var controller = pane.fenetre("Affectation.fxml", null);
	    	if(controller instanceof AffectationController affectationController) {
	    		affectationController.setid(id);
	    		affectationController.chargerAffectation(id);
	    	}
	    	
	    }

	    @FXML
	    void afficherHoraire() throws IOException {
	    	var pane = new PaneController();
	    	var controller = pane.fenetre("Horaire.fxml", null);
	    	if(controller instanceof HoraireController horaireController) {
	    		horaireController.setid(id);
	    		horaireController.chargerHoraires(id);
	    	}
	    }

	    @FXML
	    void afficherPrime() throws IOException, SQLException {
	    	var pane = new PaneController();
	    	var controller = pane.fenetre("Prime.fxml", null);
	    	if(controller instanceof PrimeController primeController) {
	    		primeController.setid(id);
	    		primeController.chargerPrime(id);
	    	}
	    }
	    
	   private int id; 
	    	    
	   private List<String> li;
	    	    
	    public void setinfo(List<String> list,int id) {
	    	this.li = list;	    	
	    	this.id = id;
	    }
	    
	    public void showEmploye(List<String> li) {
	    	var data = FXCollections.<Employe>observableArrayList();
	    	
            data.add(new Employe(li.get(0), li.get(1), li.get(2),
            		li.get(3), li.get(4), li.get(5), li.get(6), 
            		li.get(7), li.get(8), li.get(9),
            		li.get(10), li.get(11), li.get(12)));
	    	
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
            employe_tab.setItems(data);
	    }
}
