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
     * Récupération des droits de l'utilisateur à partir du login
     * 
     * @param login     login donné par l'utilisateur
     * @var RequeteSQL    Requête pour obtenir le hash associé au login
     * @var rights      role utilisateur stocké dans la table
     * @return      hash stocké dans la table
     */
    public String GetUserRightsFromLogin(String login){
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
     * Récupération des droits de l'utilisateur à partir du token
     * 
     * @param token     token stocké dans le navigateur
     * @var RequeteSQL    Requête pour obtenir le hash associé au login
     * @var rights      role utilisateur stocké dans la table
     * @return      hash stocké dans la table
     */
    public String GetUserRightsFromToken(String token){
        String RequeteSQL="SELECT * FROM users WHERE token = ?";
        String rights="";
        
        
        //Connection BD sae_52 en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" par le login (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, token);
            
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
     * @param token     token généré par le servlet
     * @var RequeteSQL    Requête pour mettre à jour le token pour un login donné
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
     * @var RequeteSQL    Requête pour obtenir l(utilisateur correspondant au token
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
    
    
    
    
    /**
     * Supression du token
     * 
     * @param token     token stocké dans le navigateur
     * @var RequeteSQL    Requête pour mettre à jour le token donné (par du vide)
     */
    public void DeleteToken(String token){
        String RequeteSQL="UPDATE users SET token = ? WHERE token = ?";
        
        
        //Connection BD sae_52 en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" n°1 par du vide et n°2 par le token (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, "");
            preparedStatement.setString(2, token);
            
            // Exécution de la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {}
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    public void addUser(String login, String nom, String prenom, String role, String hashedPassword){
        String RequeteSQL="INSERT INTO users (login, nom, prenom, role, hash, token) VALUES (?, ?, ?, ?, ?,'')";
        
        
        //Connection BD sae_52 en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement des "?" par les variables d'entrée (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, role);
            preparedStatement.setString(5, hashedPassword);
            
            // Exécution de la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {}
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * Vérifie l'existance du login dans la base de données
     * 
     * @param login     login donné par l'utilisateur
     * @var RequeteSQL    Requête pour obtenir le hash associé au login
     * @var rights      role utilisateur stocké dans la table
     * @return loginExist       éxsitance du login (booléen)
     */
    public Boolean doLoginExist(String login){
        String RequeteSQL="SELECT * FROM users WHERE login = ?";
        String loginDB="";
        Boolean loginExist = false;
        
        
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
                    loginDB = resultSet.getString("login");
                }
            }
            
            //Vérification du login renvoyé
            if(login.equals(loginDB)){
                loginExist = true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return loginExist;
    }
    
    
    
    /**
     * Renvoi les utilisateurs contenu dans la BD
     * 
     * @var RequeteSQL    Requête pour obtenir le hash associé au login
     * @var rights      role utilisateur stocké dans la table
     * @return JSONString       contenu de la table au format JSON (login/prenom/nom/droits)
     */
    public String getUsers(){
        String RequeteSQL="SELECT * FROM users ORDER BY role ASC, login ASC";
        String login="";
        String prenom="";
        String nom="";
        String droits="";
        String JSONString="";
        
        
        //Connection BD sae_52 en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            // Exécution de la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Integer c = 1;
                
                // Ouvrir le tableau JSON
                JSONString += "[";

                while (resultSet.next()) {
                    login = resultSet.getString("login");
                    prenom = resultSet.getString("prenom");
                    nom = resultSet.getString("nom");
                    droits = resultSet.getString("role");

                    // Ajouter une virgule avant chaque entrée sauf la première
                    if (c > 1) {
                        JSONString += ",";
                    }

                    // Ajouter l'objet JSON
                    JSONString += "{\"login\":\"" + login + "\", \"prenom\":\"" + prenom + "\", \"nom\":\"" + nom + "\", \"droits\":\"" + droits + "\"}";

                    c += 1;
                }

                // Fermer le tableau JSON
                JSONString += "]";

            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return JSONString;
    }
    
    
    
    
    /**
     * Suppression d'un utilisateur
     * 
     * @var RequeteSQL    Requête pour obtenir le hash associé au login
     * @var rights      role utilisateur stocké dans la table
     * @param login     login utilisateur à supprimer
     */
    public void deleteUser(String login){
        String RequeteSQL="DELETE FROM users WHERE login = ?";
        
        //Connection BD sae_52 en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" par le login (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, login);
            
            // Exécution de la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
               //Rien
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
