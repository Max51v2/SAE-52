/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import DAO.DAOSAE52;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.*;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author root
 */
@WebServlet(name = "PasswordCheck", urlPatterns = {"/PasswordCheck"})
public class PasswordCheck extends HttpServlet {

    /*
    * classe permettant de stocker le contenu du JSON de la requête
    * @param login      login utilisateur
    * @param password       MDP utilisateur
    */
    private class User{
        private String login;
        private String password;
        
        private User(String login, String password){
            this.login=login;
            this.password=password;
        }
    }
    
    
    
    /**
     * Hash le MDP envoyé et le compare à celui dans la base de données puis renvoi un token avec les droits de l'utilisateur si tout est bon
     * entrées : MDP utilisateur et login
     * Récupération du hash associé au login
     * hashage du MDP utilisateur et comparaison
     * si bon : envoi des droits de l'utilsateur + token
     *
     * @param request       servlet request
     * @param response      servlet response
     * @var login       login envoyé par l'utilsateur (requête POST)
     * @var password        MDP envoyé par l'utilsateur (requête POST)
     * @throws      ServletException if a servlet-specific error occurs
     * @throws      IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Type de la réponse
        response.setContentType("application/json;charset=UTF-8");
        
        DAOSAE52 DAO = new DAOSAE52();
        
        //Récuperation du JSON envoyé
        BufferedReader reader = request.getReader();
        Gson gsonRequest = new Gson();
        
        // Convertion du JSON en objet Java
        User user = gsonRequest.fromJson(reader, User.class);
        
        //Données
        String login = user.login;
        String password = user.password;
        String rights = "Aucun";
        String token = "";
        
        //Création du JSON à renvoyer (vide)
        String jsonString = "";
        
        try { 
            //Récuperation du hash
            String hashDB = DAO.GetUserPasswordHash(login);
            
            
            //si il n'y a pas de hash, utilisateur inexistant
            if(hashDB.equals("")){
                //rien
            }
            //l'utilisateur existe mais il faut vérifier le MDP
            else{
                //génération du hash du MDP donné par l'utilisateur
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.reset();
                m.update(password.getBytes());
                byte[] digest = m.digest();
                BigInteger bigInt = new BigInteger(1,digest);
                String hashtext = bigInt.toString(16);
                
                while(hashtext.length() < 32 ){
                    hashtext = "0"+hashtext;
                }
                
                //si le hash de la DB est identique au hash envoyé 
                if(hashDB.equals(hashtext)){
                    //Récupération des droits utilisateur
                    rights = DAO.GetUserRightsFromLogin(login);
                    
                    //Génération d'une chaine de 32 caractères (token)
                    byte[] array = new byte[32];
                    new Random().nextBytes(array);
                    token = RandomStringUtils.randomAlphanumeric(32);
                    
                    //Enregistrement du token dans la DB
                    DAO.SetToken(token, login);
                }
            }
            
            
            //JSON renvoyé
            jsonString = "{\"droits\":\""+rights+"\", \"token\":\""+token+"\"}";
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Envoi des données
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonString);
            out.flush();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
