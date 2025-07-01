package application;
    
import java.io.IOException;
import java.sql.SQLException;

import application.controllers.DataBaseConnection;
import application.controllers.PaneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        try {
            // Tester la connexion à la base de données au démarrage
            DataBaseConnection.getConnection();
            
            // Lancer l'interface de connexion
            var log = new PaneController();
            log.fenetre("LogPane.fxml", "Connexion");
        } catch (SQLException e) {
            // Afficher une erreur en cas de problème de connexion à la base de données
            System.err.println("Erreur de connexion à la base de données: " + e.getMessage());
            e.printStackTrace();
            
            // Afficher un message d'erreur à l'utilisateur
            var alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText("Impossible de se connecter à la base de données");
            alert.setContentText("""
                Veuillez vérifier que le serveur SQL est bien lancé.
                Erreur: %s
                """.formatted(e.getMessage()));
            alert.showAndWait();
            
            // Fermer l'application
            Platform.exit();
        }
    }
    
    @Override
    public void stop() {
        // Fermer la connexion à la base de données lors de la fermeture de l'application
        DataBaseConnection.closeConnection();
        System.out.println("Application fermée, connexion DB fermée");
    }
    
    public static void main(String[] args) {
        launch(args); // pour lancer l'app javaFX
    }
}