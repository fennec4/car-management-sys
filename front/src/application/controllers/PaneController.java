package application.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PaneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    // Variables pour le déplacement de la fenêtre
    private double x = 0;
    private double y = 0;
    
    /**
     * Cette fonction génère une fenêtre et charge le FXML spécifié
     *  lien Le nom du fichier FXML à charger
     *  titre Le titre de la fenêtre
     *  return -> Le contrôleur du FXML chargé
     *  IOException Si le fichier FXML n'est pas trouvé
     */
    public Object fenetre(String lien, String titre) throws IOException {
    	
        // Utilisation de var pour la déclaration de variables locales (Java 10+)
        var loader = new FXMLLoader(getClass().getResource("/application/interfaces/" + lien));
        root = loader.load();
         
        stage = new Stage();
        scene = new Scene(root);
        
        // Charger l'icône
        var iconUrl = getClass().getResource("/application/interfaces/logo.png");
        if (iconUrl != null) {
            var icon = new Image(iconUrl.toExternalForm());
            stage.getIcons().add(icon);
        }
        
        // Configurer la fenêtre
         
        stage.setTitle(titre);	  //  titre
        stage.centerOnScreen();	 //   la fenetre apparait au centre de l'ecran
        
        // Gestion spéciale pour certaines fenêtres
        if (lien.equals("LogPane.fxml") || lien.equals("form.fxml")) {
        	
            stage.initStyle(StageStyle.TRANSPARENT); // ne pas afficher la bordure de la fenetre
            configurerDeplacement(root); // pour deplacer la fenetre 
            stage.setResizable(false);// pour que la fenetre ne soit pas redimentionable
        }
        
        // Appliquer la feuille de style CSS
        var cssUrl = getClass().getResource("/application/interfaces/application.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        
        // Afficher la fenêtre
        stage.setScene(scene);
        stage.show();
        
        return loader.getController(); // retourner le controller du FXML
    }
    
    /**
     * Configure le déplacement de la fenêtre en cliquant et glissant
     *  root -> L'élément racine de la scène
     */
    private void configurerDeplacement(Parent root) {
        // Utilisation d'expressions lambda pour les gestionnaires d'événements
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }
}