package application.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LogController {
    
    @FXML
    private Button close;

    @FXML
    private Button connecter;

    @FXML
    private PasswordField motdepasse;

    @FXML
    private TextField nom;

    @FXML
    public void initialize() {
        // Ajouter des écouteurs d'événements clavier pour les champs de saisie
        nom.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                motdepasse.requestFocus(); // Passer au champ mot de passe
            }
        });
        
        motdepasse.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    connect(); // Appeler la méthode de connexion
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        
        // Mettre le focus sur le champ de nom au démarrage
        Platform.runLater(() -> nom.requestFocus());
    }
    @FXML
    public void close() {
        Stage currentStage = (Stage) nom.getScene().getWindow();
        currentStage.close();
    }
    
    @FXML
    public void connect() throws IOException {
   
        var username = nom.getText().trim();  // trim() : Supprime les espaces blancs
        var password = motdepasse.getText().trim();
        
        // Vérifier que les champs ne sont pas vides
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs");
            return;
        }
        
        // Vérifier les identifiants
        if ("sa".equals(username) && "root".equals(password)) {
            close();
            var pane = new PaneController();
            pane.fenetre("accueille.fxml", "Acceille");
        } else {
            showAlert("Erreur", "Nom d'utilisateur ou mot de passe incorrect !");
        }
    }
    
    private void showAlert(String title, String message) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}