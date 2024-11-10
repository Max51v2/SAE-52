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
            preparedStatement.setString(6, serialNumber);
            preparedStatement.setString(7, status);
            preparedStatement.setString(8, other);
            
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
                    nameDB = resultSet.getString("name");
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
    
    
    
    /**
     * Renvoi les PC contenu dans la BD
     * 
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return JSONString       contenu de la table au format JSON (login/prenom/nom/droits)
     */
    public String getPC(Boolean Test){
        String RequeteSQL="SELECT * FROM pc ORDER BY name ASC";
        String processor="";
        String RAM="";
        String macAddress="";
        String VLAN="";
        String name="";
        String serialNumber="";
        String status="";
        String other="";
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
                    processor = resultSet.getString("processor");
                    RAM = resultSet.getString("ram");
                    macAddress = resultSet.getString("mac_address");
                    VLAN = resultSet.getString("vlan");
                    name = resultSet.getString("name");
                    serialNumber = resultSet.getString("serial_number");
                    status = resultSet.getString("status");
                    other = resultSet.getString("other");

                    // Ajouter une virgule avant chaque entrée sauf la première
                    if (c > 1) {
                        JSONString += ",";
                    }

                    // Ajouter l'objet JSON
                    JSONString += "{\"name\":\"" + name + "\", \"processor\":\"" + processor + "\", \"RAM\":\"" + RAM + "\", \"macAddress\":\"" + macAddress + "\", \"VLAN\":\"" + VLAN + "\", \"macAddress\":\"" + macAddress + "\", \"serialNumber\":\"" + serialNumber + "\", \"status\":\"" + status + "\", \"other\":\"" + other + "\"}";

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
    
    public void addSwitch(String switchSpeed, String macAddress, String VLAN, String name, String serialNumber, String status, Boolean Test) {
        String RequeteSQL = "INSERT INTO switch (switch_speed, mac_address, vlan, name, serial_number, status) VALUES (?, ?, ?, ?, ?, ?)";

        // Sélection de la BD
        changeConnection(Test);

        // Connexion à la BD en tant que postgres
        try (Connection connection = DAOSAE52.getConnectionPostgres();
             PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {

            // Remplacement des "?" par les variables d'entrée pour éviter les injections SQL
            preparedStatement.setString(1, switchSpeed);
            preparedStatement.setString(2, macAddress);
            preparedStatement.setString(3, VLAN);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, serialNumber);
            preparedStatement.setString(6, status);

            // Exécution de la requête
            int affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Renvoi les Switch contenu dans la BD
     * 
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return JSONString       contenu de la table au format JSON (login/prenom/nom/droits)
     */
    public String getSwitch(Boolean Test){
        String RequeteSQL="SELECT * FROM switch ORDER BY name ASC";
        String switchSpeed="";
        String macAddress="";
        String VLAN="";
        String name="";
        String serialNumber="";
        String status="";
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
                    switchSpeed = resultSet.getString("switch_speed");
                    macAddress = resultSet.getString("mac_address");
                    VLAN = resultSet.getString("vlan");
                    name = resultSet.getString("name");
                    serialNumber = resultSet.getString("serial_number");
                    status = resultSet.getString("status");

                    // Ajouter une virgule avant chaque entrée sauf la première
                    if (c > 1) {
                        JSONString += ",";
                    }

                    // Ajouter l'objet JSON
                    JSONString += "{\"name\":\"" + name + "\", \"switchSpeed\":\"" + switchSpeed + "\", \"macAddress\":\"" + macAddress + "\", \"VLAN\":\"" + VLAN + "\", \"serialNumber\":\"" + serialNumber + "\", \"status\":\"" + status + "\"}";

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
     * Vérifie l'existance du nom d'un switch dans la base de données
     * 
     * @param name     nom du Switch
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return nameExist       éxsitance du login (booléen)
     */
    public Boolean doNameExistSwitch(String name, Boolean Test){
        String RequeteSQL="SELECT * FROM switch WHERE name = ?";
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
                    nameDB = resultSet.getString("name");
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
    
    
    public void addRouter(String routerPorts, String macAddress, String VLAN, String name, String serialNumber, String status, Boolean Test) {
        String RequeteSQL = "INSERT INTO router (router_ports, mac_address, vlan, name, serial_number, status) VALUES (?, ?, ?, ?, ?, ?)";
    
        // Sélection de la BD
        changeConnection(Test);

        // Connexion à la BD en tant que postgres
        try (Connection connection = DAOSAE52.getConnectionPostgres();
             PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {

            // Remplacement des "?" par les variables d'entrée pour éviter les injections SQL
            preparedStatement.setString(1, routerPorts);
            preparedStatement.setString(2, macAddress);
            preparedStatement.setString(3, VLAN);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, serialNumber);
            preparedStatement.setString(6, status);

            // Exécution de la requête
            int affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Renvoi les Routeurs contenu dans la BD
     * 
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return JSONString       contenu de la table au format JSON (login/prenom/nom/droits)
     */
    public String getRouter(Boolean Test){
        String RequeteSQL="SELECT * FROM router ORDER BY name ASC";
        String routerPorts="";
        String macAddress="";
        String VLAN="";
        String name="";
        String serialNumber="";
        String status="";
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
                    routerPorts = resultSet.getString("router_ports");
                    macAddress = resultSet.getString("mac_address");
                    VLAN = resultSet.getString("vlan");
                    name = resultSet.getString("name");
                    serialNumber = resultSet.getString("serial_number");
                    status = resultSet.getString("status");

                    // Ajouter une virgule avant chaque entrée sauf la première
                    if (c > 1) {
                        JSONString += ",";
                    }

                    // Ajouter l'objet JSON
                    JSONString += "{\"name\":\"" + name + "\", \"routerPorts\":\"" + routerPorts + "\", \"macAddress\":\"" + macAddress + "\", \"VLAN\":\"" + VLAN + "\", \"serialNumber\":\"" + serialNumber + "\", \"status\":\"" + status + "\"}";

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
    
    public void addCable(String cableLength, String name, String serialNumber, String status, Boolean Test) {
        String RequeteSQL = "INSERT INTO cable (cable_lenght, name, serial_number, status) VALUES (?, ?, ?, ?)";
    
        // Sélection de la BD
        changeConnection(Test);

        // Connexion à la BD en tant que postgres
        try (Connection connection = DAOSAE52.getConnectionPostgres();
             PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {

            // Remplacement des "?" par les variables d'entrée pour éviter les injections SQL
            preparedStatement.setString(1, cableLength);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, serialNumber);
            preparedStatement.setString(4, status);

            // Exécution de la requête
            int affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    
    /**
     * Renvoi les Câbles contenu dans la BD
     * 
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return JSONString       contenu de la table au format JSON (login/prenom/nom/droits)
     */
    public String getCable(Boolean Test){
        String RequeteSQL="SELECT * FROM cable ORDER BY name ASC";
        String cableLength="";
        String name="";
        String serialNumber="";
        String status="";
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
                    cableLength = resultSet.getString("cable_lenght");
                    name = resultSet.getString("name");
                    serialNumber = resultSet.getString("serial_number");
                    status = resultSet.getString("status");

                    // Ajouter une virgule avant chaque entrée sauf la première
                    if (c > 1) {
                        JSONString += ",";
                    }

                    // Ajouter l'objet JSON
                    JSONString += "{\"name\":\"" + name + "\", \"cableLength\":\"" + cableLength + "\", \"serialNumber\":\"" + serialNumber + "\", \"status\":\"" + status + "\"}";

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
     * Suppression d'un PC
     * 
     * @param name     nom du PC à supprimer
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     */
    public void DeletePC(String name, Boolean Test){
        String RequeteSQL="DELETE FROM pc WHERE name = ?";
        
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
            int affectedRows = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Vérifie l'existance du nom d'un PC dans la base de données
     * 
     * @param name     nom donné par l'utilisateur
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return loginExist       éxsitance du login (booléen)
     */
    public Boolean doNamePCExist(String name, Boolean Test){
        String RequeteSQL="SELECT * FROM pc WHERE name = ?";
        String NameDB="";
        Boolean NameExist = false;
        
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
                    NameDB = resultSet.getString("name");
                }
            }
            
            //Vérification du login renvoyé
            if(name.equals(NameDB)){
                NameExist = true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return NameExist;
    }
    
    /**
     * Suppression d'un Switch
     * 
     * @param name     nom du Switch à supprimer
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     */
    public void DeleteSwitch(String name, Boolean Test){
        String RequeteSQL="DELETE FROM switch WHERE name = ?";
        
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
            int affectedRows = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Vérifie l'existance du nom d'un Switch dans la base de données
     * 
     * @param name     nom donné par l'utilisateur
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return loginExist       éxsitance du login (booléen)
     */
    public Boolean doNameSwitchExist(String name, Boolean Test){
        String RequeteSQL="SELECT * FROM switch WHERE name = ?";
        String NameDB="";
        Boolean NameExist = false;
        
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
                    NameDB = resultSet.getString("name");
                }
            }
            
            //Vérification du login renvoyé
            if(name.equals(NameDB)){
                NameExist = true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return NameExist;
    }
    
    
    /**
     * Suppression d'un Routeur
     * 
     * @param name     nom du Routeur à supprimer
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     */
    public void DeleteRouter(String name, Boolean Test){
        String RequeteSQL="DELETE FROM router WHERE name = ?";
        
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
            int affectedRows = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Vérifie l'existance du nom d'un Routeur dans la base de données
     * 
     * @param name     nom donné par l'utilisateur
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return loginExist       éxsitance du login (booléen)
     */
    public Boolean doNameRouterExist(String name, Boolean Test){
        String RequeteSQL="SELECT * FROM router WHERE name = ?";
        String NameDB="";
        Boolean NameExist = false;
        
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
                    NameDB = resultSet.getString("name");
                }
            }
            
            //Vérification du login renvoyé
            if(name.equals(NameDB)){
                NameExist = true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return NameExist;
    }
    
    /**
     * Suppression d'un Câble
     * 
     * @param name     nom du Câble à supprimer
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     */
    public void DeleteCable(String name, Boolean Test){
        String RequeteSQL="DELETE FROM cable WHERE name = ?";
        
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
            int affectedRows = preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Vérifie l'existance du nom d'un Câble dans la base de données
     * 
     * @param name     nom donné par l'utilisateur
     * @param Test     Utilisation de la BD test (true si test sinon false !!!)
     * @return loginExist       éxsitance du login (booléen)
     */
    public Boolean doNameCableExist(String name, Boolean Test){
        String RequeteSQL="SELECT * FROM cable WHERE name = ?";
        String NameDB="";
        Boolean NameExist = false;
        
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
                    NameDB = resultSet.getString("name");
                }
            }
            
            //Vérification du login renvoyé
            if(name.equals(NameDB)){
                NameExist = true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return NameExist;
    }
    

    public void addTicket(String description, String service, String status, Boolean Test) {
    String RequeteSQL = "INSERT INTO ticket (description, service, status) VALUES (?, ?, ?)";
    
    // Sélection de la BD
    changeConnection(Test);
    
    try (Connection connection = DAOSAE52.getConnectionPostgres();
         PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
        
        // Remplacement des "?" par les variables d'entrée pour éviter les injections SQL
        preparedStatement.setString(1, description);
        preparedStatement.setString(2, service);
        preparedStatement.setString(3, status);
        
        // Exécution de la requête
        preparedStatement.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public boolean deleteTicket(int id, Boolean Test) {
    String RequeteSQL = "DELETE FROM ticket WHERE id = ?";
    
    // Sélection de la BD
    changeConnection(Test);
    
    try (Connection connection = DAOSAE52.getConnectionPostgres();
         PreparedStatement preparedStatement = connection.prepareStatement(RequeteSQL)) {
        
        // Remplacement de "?" par l'id
        preparedStatement.setInt(1, id);
        
        // Exécution de la requête
        int affectedRows = preparedStatement.executeUpdate();
        
        return affectedRows > 0; // Indique si un ticket a bien été supprimé
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}
