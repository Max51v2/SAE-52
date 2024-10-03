/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOTest;

import DAO.DAOSAE52;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * 
 * @author Maxime VALLET
 */
public final class DAOSAE52Test {

    /**
     * Test of GetUserPasswordHash method, of class DAOSAE52.
     */
    @Test
    public void testGetUserPasswordHash() {
        System.out.println("GetUserPasswordHash");
        
        //Objet DAO
        DAOSAE52 instance = new DAOSAE52();
        
        //Variables
        String login = "login1";
        String nom = "nom1";
        String prenom = "prenom1";
        String role = "Administrateur";
        String hash = "e3afed0047b08059d0fada10f400c1e5";
        Boolean Test = true;
        String ExpResult = "e3afed0047b08059d0fada10f400c1e5";
        
        
        //Ajout utilisateur DB test
        instance.addUser(login, nom, prenom, role, hash, Test);
        
        //Test méthode
        String result = instance.GetUserPasswordHash(login, Test);
        
        //Supression utilisateur
        instance.deleteUser(login, Test);
        
        //Résultat
        System.out.println("resultat : "+result+" | exp : "+ExpResult);
        
        assertEquals(ExpResult, result);
    }

    /**
     * Test of GetUserRightsFromLogin method, of class DAOSAE52.
     */
    @Test
    public void testGetUserRightsFromLogin() {
        System.out.println("GetUserRightsFromLogin");
        
        //Objet DAO
        DAOSAE52 instance = new DAOSAE52();
        
        //Variables
        String login = "login1";
        String nom = "nom1";
        String prenom = "prenom1";
        String role = "Administrateur";
        String hash = "e3afed0047b08059d0fada10f400c1e5";
        Boolean Test = true;
        String ExpResult = "Administrateur";
        
        
        //Ajout utilisateur DB test
        instance.addUser(login, nom, prenom, role, hash, Test);
        
        //Test méthode
        String result = instance.GetUserRightsFromLogin(login, Test);
        
        //Supression utilisateur
        instance.deleteUser(login, Test);
        
        //Résultat
        System.out.println("resultat : "+result+" | exp : "+ExpResult);
        
        assertEquals(ExpResult, result);
    }

    /**
     * Test of GetUserRightsFromToken method, of class DAOSAE52.
     */
    @Test
    public void testGetUserRightsFromToken() {
        System.out.println("GetUserRightsFromToken");
        
        //Objet DAO
        DAOSAE52 instance = new DAOSAE52();
        
        //Variables
        String login = "login1";
        String nom = "nom1";
        String prenom = "prenom1";
        String role = "Administrateur";
        String hash = "e3afed0047b08059d0fada10f400c1e5";
        String token = "00000000000000000000000000000000";
        Boolean Test = true;
        String ExpResult = "Administrateur";
        
        
        //Ajout utilisateur DB test
        instance.addUser(login, nom, prenom, role, hash, Test);
        
        //Ajout token
        instance.SetToken(token, login, Test);
        
        //Test méthode
        String result = instance.GetUserRightsFromToken(token, Test);
        
        //Supression utilisateur
        instance.deleteUser(login, Test);
        
        //Résultat
        System.out.println("resultat : "+result+" | exp : "+ExpResult);
        
        assertEquals(ExpResult, result);
    }

    /**
     * Test of SetToken method, of class DAOSAE52.
     * identique au précédent car il est lié à setToken (pas de méthode getToken)
     */
    @Test
    public void testSetToken() {
        System.out.println("SetToken");
        
        //Objet DAO
        DAOSAE52 instance = new DAOSAE52();
        
        //Variables
        String login = "login1";
        String nom = "nom1";
        String prenom = "prenom1";
        String role = "Administrateur";
        String hash = "e3afed0047b08059d0fada10f400c1e5";
        String token = "00000000000000000000000000000000";
        Boolean Test = true;
        String ExpResult = "Administrateur";
        
        
        //Ajout utilisateur DB test
        instance.addUser(login, nom, prenom, role, hash, Test);
        
        //Ajout token
        instance.SetToken(token, login, Test);
        
        //Test méthode
        String result = instance.GetUserRightsFromToken(token, Test);
        
        //Supression utilisateur
        instance.deleteUser(login, Test);
        
        //Résultat
        System.out.println("resultat : "+result+" | exp : "+ExpResult);
        
        assertEquals(ExpResult, result);
    }

    /**
     * Test of CheckToken method, of class DAOSAE52.
     */
    @Test
    public void testCheckToken() {
        System.out.println("CheckToken");
        
        //Objet DAO
        DAOSAE52 instance = new DAOSAE52();
        
        //Variables
        String login = "login1";
        String nom = "nom1";
        String prenom = "prenom1";
        String role = "Administrateur";
        String hash = "e3afed0047b08059d0fada10f400c1e5";
        String token = "00000000000000000000000000000000";
        Boolean Test = true;
        String ExpResult = "login1";
        
        
        //Ajout utilisateur DB test
        instance.addUser(login, nom, prenom, role, hash, Test);
        
        //Ajout token
        instance.SetToken(token, login, Test);
        
        //Test méthode
        String result = instance.CheckToken(token, Test);
        
        //Supression utilisateur
        instance.deleteUser(login, Test);
        
        //Résultat
        System.out.println("resultat : "+result+" | exp : "+ExpResult);
        
        assertEquals(ExpResult, result);
    }

    /**
     * Test of DeleteToken method, of class DAOSAE52.
     */
    @Test
    public void testDeleteToken() {
        System.out.println("DeleteToken");
        
        //Objet DAO
        DAOSAE52 instance = new DAOSAE52();
        
        //Variables
        String login = "login1";
        String nom = "nom1";
        String prenom = "prenom1";
        String role = "Administrateur";
        String hash = "e3afed0047b08059d0fada10f400c1e5";
        String token = "00000000000000000000000000000000";
        Boolean Test = true;
        String ExpResult = "";
        
        
        //Ajout utilisateur DB test
        instance.addUser(login, nom, prenom, role, hash, Test);
        
        //Ajout token
        instance.SetToken(token, login, Test);
        
        //Test méthode
        instance.DeleteToken(token, Test);
        String result = instance.CheckToken(token, Test);
        
        //Supression utilisateur
        instance.deleteUser(login, Test);
        
        //Résultat
        System.out.println("resultat : "+result+" | exp : "+ExpResult);
        
        assertEquals(ExpResult, result);
    }

    /**
     * Test of addUser method, of class DAOSAE52.
     */
    @Test
    public void testAddUser() {
        System.out.println("AddUser");
        
        //Objet DAO
        DAOSAE52 instance = new DAOSAE52();
        
        //Variables
        String login = "login1";
        String nom = "nom1";
        String prenom = "prenom1";
        String role = "Administrateur";
        String hash = "e3afed0047b08059d0fada10f400c1e5";
        String token = "00000000000000000000000000000000";
        Boolean Test = true;
        String ExpResult = "[{\"login\":\"login1\", \"prenom\":\"prenom1\", \"nom\":\"nom1\", \"droits\":\"Administrateur\"}]";
        
        
        //Ajout utilisateur DB test
        instance.addUser(login, nom, prenom, role, hash, Test);
        
        //Test méthode
        String result = instance.getUsers(Test);
        
        //Supression utilisateur
        instance.deleteUser(login, Test);
        
        //Résultat
        System.out.println("resultat : "+result+" | exp : "+ExpResult);
        
        assertEquals(ExpResult, result);
    }

    /**
     * Test of doLoginExist method, of class DAOSAE52.
     */
    @Test
    public void testDoLoginExist() {
        System.out.println("DoLoginExist");
        
        //Objet DAO
        DAOSAE52 instance = new DAOSAE52();
        
        //Variables
        String login = "login1";
        String nom = "nom1";
        String prenom = "prenom1";
        String role = "Administrateur";
        String hash = "e3afed0047b08059d0fada10f400c1e5";
        String token = "00000000000000000000000000000000";
        Boolean Test = true;
        Boolean ExpResult = true;
        
        
        //Ajout utilisateur DB test
        instance.addUser(login, nom, prenom, role, hash, Test);
        
        //Test méthode
        Boolean result = instance.doLoginExist(login, Test);
        
        //Supression utilisateur
        instance.deleteUser(login, Test);
        
        //Résultat
        System.out.println("resultat : "+result+" | exp : "+ExpResult);
        
        assertEquals(ExpResult, result);
    }

    /**
     * Test of getUsers method, of class DAOSAE52.
     */
    @Test
    public void testGetUsers() {
        System.out.println("GetUsers");
        
        //Objet DAO
        DAOSAE52 instance = new DAOSAE52();
        
        //Variables
        String login = "login1";
        String nom = "nom1";
        String prenom = "prenom1";
        String role = "Administrateur";
        String hash = "e3afed0047b08059d0fada10f400c1e5";
        String token = "00000000000000000000000000000000";
        Boolean Test = true;
        String ExpResult = "[{\"login\":\"login1\", \"prenom\":\"prenom1\", \"nom\":\"nom1\", \"droits\":\"Administrateur\"}]";
        
        
        //Ajout utilisateur DB test
        instance.addUser(login, nom, prenom, role, hash, Test);
        
        //Test méthode
        String result = instance.getUsers(Test);
        
        //Supression utilisateur
        instance.deleteUser(login, Test);
        
        //Résultat
        System.out.println("resultat : "+result+" | exp : "+ExpResult);
        
        assertEquals(ExpResult, result);
    }

    /**
     * Test of deleteUser method, of class DAOSAE52.
     */
    @Test
    public void testDeleteUser() {
        System.out.println("GetUsers");
        
        //Objet DAO
        DAOSAE52 instance = new DAOSAE52();
        
        //Variables
        String login = "login1";
        String nom = "nom1";
        String prenom = "prenom1";
        String role = "Administrateur";
        String hash = "e3afed0047b08059d0fada10f400c1e5";
        String token = "00000000000000000000000000000000";
        Boolean Test = true;
        String ExpResult = "[]";
        
        
        //Ajout utilisateur DB test
        instance.addUser(login, nom, prenom, role, hash, Test);
        
        //Supression utilisateur
        instance.deleteUser(login, Test);
        
        //Test méthode
        String result = instance.getUsers(Test);
        
        //Résultat
        System.out.println("resultat : "+result+" | exp : "+ExpResult);
        
        assertEquals(ExpResult, result);
    }
    
}
