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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
@WebServlet(name = "AddUser", urlPatterns = {"/AddUser"})
public class AddUser extends HttpServlet {
    
    /*
    * classe permettant de stocker le contenu du JSON de la requête
    * @param token      token utilisateur
    */
    private class user{
        private String firstName;
        private String lastName;
        private String login;
        private String password;
        private String role;
        private String token;
        
        private user(String token){
            this.firstName = firstName;
            this.lastName = lastName;
            this.login = login;
            this.password = password;
            this.role = role;
            this.token = token;
        }
    }
    
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
        AddUser.user user = gsonRequest.fromJson(reader, AddUser.user.class);
        
        //Données
        String prenom = user.firstName;
        String nom = user.lastName;
        String login = user.login;
        String password = user.password;
        String role = user.role;
        String token = user.token;
        
        System.out.println(prenom);
        System.out.println(nom);
        System.out.println(role);
        System.out.println(login);
        System.out.println(password);
        System.out.println(token);
        
        //génération du hash du MDP donné par l'utilisateur
        MessageDigest m;
        String hashedPassword="";
        try {
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(password.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            hashedPassword = bigInt.toString(16);

            while(hashedPassword.length() < 32 ){
                hashedPassword = "0"+hashedPassword;
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Création du JSON à renvoyer (vide)
        String jsonString = "";
        
        try { 
            //VERIF si login en doublon !!!
            
            //verif droits utilisateur demande
            String userRights = DAO.GetUserRightsFromToken(token);
            
            //Verification si l'utilisateur a les droits Admin
            if(userRights.equals("Admin")){
                DAO.addUser(login, nom, prenom, role, hashedPassword);
            }
            
            //JSON renvoyé
            jsonString = "{\"result\":\"Fait\"}";
            
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
