package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


/**
 * DAO SAE52
 * 
 * @author Maxime VALLET
 */
public class DAOSAE52 {
    //Info de connection à la BD PostgreSQL
    private static final String UserPostgres="postgres";
    private static final String PasswordPostgres="leffe";
    private static String UrlBD="";
    
    
    //Défini la DB sur laquelle on se connecte
    private void changeConnection(Boolean Test){
        if(Test == false){
            UrlBD="jdbc:postgresql://localhost:5432/sae_52";
        }
        else{
            UrlBD="jdbc:postgresql://localhost:5432/test";
        }
    }
    
    //Connection utilisateur postgres
        private static Connection getConnectionPostgres() throws SQLException {
            return DriverManager.getConnection(UrlBD, UserPostgres, PasswordPostgres);
    }
        
    
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
     * Récupération du hash dans la table users en fonction du login donné par l'utilisateur
     * 
     * @param login       &emsp;&emsp;        login donné par l'utilisateur
     * @param Test       &emsp;&emsp;        Utilisation de la BD test (true si test sinon false !!!)
     * @return        hash stocké dans la table
     */
    public String getUserPasswordHash(String login, Boolean Test){
        String RequeteSQL="SELECT * FROM users WHERE login = ?";
        String hashDB="";
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
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
     * @param login       &emsp;&emsp;        login donné par l'utilisateur
     * @param Test       &emsp;&emsp;        Utilisation de la BD test (true si test sinon false !!!)
     * @return        droits de l'utilisateur
     */
    public String getUserRightsFromLogin(String login, Boolean Test){
        String RequeteSQL="SELECT * FROM users WHERE login = ?";
        String rights="";
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
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
     * @param token       &emsp;&emsp;        token stocké dans le navigateur
     * @param Test       &emsp;&emsp;        Utilisation de la BD test (true si test sinon false !!!)
     * @return        droits de l'utilisateur
     */
    public String getUserRightsFromToken(String token, Boolean Test){
        String RequeteSQL="SELECT * FROM users WHERE token = ?";
        String rights="";
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
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
     * @param login       &emsp;&emsp;        login donné par l'utilisateur
     * @param token       &emsp;&emsp;        token généré par le servlet
     * @param Test       &emsp;&emsp;        Utilisation de la BD test (true si test sinon false !!!)
     */
    public void setToken(String token, String login, Boolean Test){
        String RequeteSQL="UPDATE users SET token = ? WHERE login = ?";
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" n°1 par le token et n°2 par le login (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, login);
            
            // Exécution de la requête
            int affectedRows = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    /**
     * Vérification du token
     * 
     * @param token       &emsp;&emsp;        token donné par l'utilisateur
     * @param Test       &emsp;&emsp;        Utilisation de la BD test (true si test sinon false !!!)
     * @return      login stocké dans la table
     */
    public String checkToken(String token, Boolean Test){
        String RequeteSQL="SELECT * FROM users WHERE token = ?";
        String login="";
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
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
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     */
    public void deleteToken(String token, Boolean Test){
        String RequeteSQL="UPDATE users SET token = ? WHERE token = ?";
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" n°1 par du vide et n°2 par le token (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, "");
            preparedStatement.setString(2, token);
            
            // Exécution de la requête
            int affectedRows = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * Ajout d'un utilisateur
     * 
     * @param login     login de l'utilisateur
     * @param nom     nom de l'utilisateur
     * @param prenom     prenom de l'utilisateur
     * @param role     droits de l'utilisateur
     * @param hashedPassword      MDP hashé de l'utilisateur
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     */
    public void addUser(String login, String nom, String prenom, String role, String hashedPassword, Boolean Test){
        String RequeteSQL="INSERT INTO users (login, nom, prenom, role, hash, token) VALUES (?, ?, ?, ?, ?,'')";
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
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
            int affectedRows = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * Vérifie l'existance du login dans la base de données
     * 
     * @param login     login donné par l'utilisateur
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return loginExist       éxsitance du login (booléen)
     */
    public Boolean doLoginExist(String login, Boolean Test){
        String RequeteSQL="SELECT * FROM users WHERE login = ?";
        String loginDB="";
        Boolean loginExist = false;
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
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
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return JSONString       contenu de la table au format JSON (login/prenom/nom/droits)
     */
    public String getUsers(Boolean Test){
        String RequeteSQL="SELECT * FROM users ORDER BY role ASC, login ASC";
        String login="";
        String prenom="";
        String nom="";
        String droits="";
        String JSONString="";
        
        //Selection de la BD
        changeConnection(Test);
        
        
        //Connection BD en tant que postgres
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
     * @param login     login utilisateur à supprimer
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     */
    public void deleteUser(String login, Boolean Test){
        String RequeteSQL="DELETE FROM users WHERE login = ?";
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" par le login (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, login);
            
            // Exécution de la requête
            int affectedRows = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * Ajout d'un ordinateur
     * 
     * @param processor     processeur de l'appareil
     * @param RAM     Quantité de RAM (Go)
     * @param macAddress     @MAC de la carte réseau
     * @param VLAN     VLAN auquel il a accès
     * @param name      nom de l'appareil
     * @param serialNumber     Numéro de série de l'appareil
     * @param status     Status de la machine (maintenance...)
     * @param other     inutilisé (clé USB ?)
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     */
    public void addPC(String processor, String RAM, String macAddress, String VLAN, String name, String serialNumber, String status, String other, Boolean Test){
        String RequeteSQL="INSERT INTO pc (processor, ram, mac_address, vlan, name, serial_number, status, other) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
        try (Connection connection =
            DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement des "?" par les variables d'entrée (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, processor);
            preparedStatement.setString(2, RAM);
            preparedStatement.setString(3, macAddress);
            preparedStatement.setString(4, VLAN);
            preparedStatement.setString(5, name);
            preparedStatement.setString(3, serialNumber);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, other);
            
            // Exécution de la requête
            int affectedRows = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    /**
     * Vérifie l'existance du nom du PC dans la base de données
     * 
     * @param name     nom du PC
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return nameExist       éxsitance du login (booléen)
     */
    public Boolean doNameExist(String name, Boolean Test){
        String RequeteSQL="SELECT * FROM pc WHERE name = ?";
        String nameDB="";
        Boolean nameExist = false;
        
        //Selection de la BD
        changeConnection(Test);
        
        //Connection BD en tant que postgres
        try (Connection connection =
                DAOSAE52.getConnectionPostgres();
                
            //Requête SQL
            PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
            
            //Remplacement de "?" par le login (pour éviter les injections SQL !!!)
            preparedStatement.setString(1, name);
            
            // Exécution de la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    nameDB = resultSet.getString("login");
                }
            }
            
            //Vérification du nom renvoyé
            if(name.equals(nameDB)){
                nameExist = true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return nameExist;
    }
}
