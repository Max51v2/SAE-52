/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

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
     * Récupération du hash en fonction du login donné
     * @param login
     */
    public String GetUserPasswordHash(String login){
        String RequeteSQL="SELECT * FROM password_Hash_MD5 WHERE login='"+login+"'";
        String hashDB="";
        
        try (Connection connection = DAOSAE52.getConnectionPostgres();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(RequeteSQL)) {

            if (resultSet.next()) {
                hashDB = resultSet.getString("hash");
                System.out.println(hashDB);
            } 
            else {
                //rien
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return hashDB;
    }

}
