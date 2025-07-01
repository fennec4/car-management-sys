package application.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String URL = "jdbc:sqlserver://localhost:38189;instanceName=MSSQLSERVER01;databaseName=MotorLab;encrypt=false;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "root";
    
    // Pool de connexions simple avec une seule connexion
    private static Connection connection = null;
    
    // Obtenir une connexion (réutilise la connexion existante si disponible)
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                // Pas besoin de charger explicitement le pilote dans Java 23
                // Le DriverManager trouvera automatiquement le pilote
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(true);
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données: " + e.getMessage());
            throw e;
        }
    }
    
    // Fermer la connexion (appelé lors de la fermeture de l'application)
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
            }
        }
    }
}