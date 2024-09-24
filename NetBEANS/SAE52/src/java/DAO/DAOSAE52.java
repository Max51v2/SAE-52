/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


/**
 *
 * @author root
 */
public class DAOSAE52 {
    
    /**
     * Info de connection à la BD PostgreSQL
     */
    private static final String UrlBD="jdbc:postgresql://localhost:5432/sae_52";
    
    private static final String UserPostgres="postgres";
    private static final String PasswordPostgres="leffe";
    
    private static final String UserAdministrateur="administrateur";
    private static final String PasswordAdministrateur="Administrateur";
    
    private static final String UserTechnicien="technicien";
    private static final String PasswordTechnicien="Technicien";
    
    private static final String UserUtilisateur="utilisateur";
    private static final String PasswordUtilisateur="Utilisateur";
    
    
    //Demarrage du driver postgresql
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // Si le driver PostgreSQL n'est pas trouvé dans le classpath
            e.printStackTrace();
        }
    }
    
    
    /**
     * Connection utilisateur postgres
     * @return
     * @throws SQLException 
     */
    public static Connection getConnectionPostgres() throws SQLException {
        return DriverManager.getConnection(UrlBD, UserPostgres, PasswordPostgres);
    }
    
    /**
     * Connection utilisateur administrateur
     * @return
     * @throws SQLException 
     */
    public static Connection getConnectionAdministrateur() throws SQLException {
        return DriverManager.getConnection(UrlBD, UserAdministrateur, PasswordAdministrateur);
    }
    
    /**
     * Connection utilisateur technicien
     * @return
     * @throws SQLException 
     */
    public static Connection getConnectionTechnicien() throws SQLException {
        return DriverManager.getConnection(UrlBD, UserTechnicien, PasswordTechnicien);
    }
    
    /**
     * Connection utilisateur utilisateur
     * @return
     * @throws SQLException 
     */
    public static Connection getConnectionUtilisateur() throws SQLException {
        return DriverManager.getConnection(UrlBD, UserUtilisateur, PasswordUtilisateur);
    }
    
    
    
    /**
     * Récupération du hash dans la table users en fonction du login donné par l'utilisateur
     * 
     * @param login     login donné par l'utilisateur
     * @var RequeteSQL    Requête pour obtenir le hash associé au login
     * @var hashDB      hash stocké dans la table
     * @return      hash stocké dans la table
     */
    public String GetUserPasswordHash(String login){
        String RequeteSQL="SELECT * FROM users WHERE login = ?";
        String hashDB="";
        
        
        //Connection BD sae_52 en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" par le login (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, login);
            
            // Exécution de la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    hashDB = resultSet.getString("hash");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return hashDB;
    }
    
    
    
    
    /**
     * Récupération des droits de l'utilisateur
     * 
     * @param login     login donné par l'utilisateur
     * @var RequeteSQL    Requête pour obtenir le hash associé au login
     * @var rights      role utilisateur stocké dans la table
     * @return      hash stocké dans la table
     */
    public String GetUserRights(String login){
        String RequeteSQL="SELECT * FROM users WHERE login = ?";
        String rights="";
        
        
        //Connection BD sae_52 en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" par le login (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, login);
            
            // Exécution de la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    rights = resultSet.getString("role");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rights;
    }
    
    
    
    
    /**
     * Enregistrement du token
     * 
     * @param login     login donné par l'utilisateur
     * @var RequeteSQL    Requête pour obtenir le hash associé au login
     * @return      hash stocké dans la table
     */
    public void SetToken(String token, String login){
        String RequeteSQL="UPDATE users SET token = ? WHERE login = ?";
        
        
        //Connection BD sae_52 en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" n°1 par le token et n°2 par le login (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, login);
            
            // Exécution de la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {}
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    /**
     * Vérification du token
     * 
     * @param token     token donné par l'utilisateur
     * @var RequeteSQL    Requête pour obtenir le hash associé au login
     * @return      login stocké dans la table
     */
    public String CheckToken(String token){
        String RequeteSQL="SELECT * FROM users WHERE token = ?";
        String login="";
        
        //Connection BD sae_52 en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" n°1 par le token et n°2 par le login (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, token);
            
            // Exécution de la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    login = resultSet.getString("login");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return login;
    }
    
    
    
    public void addUser(String login, String nom, String prenom){
        //à compléter
        
    }

}
