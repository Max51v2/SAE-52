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
 * Servlet ajout utilisateur
 * 
 * @author Maxime VALLET
 */
@WebServlet(name = "AddUser", urlPatterns = {"/AddUser"})
public class AddUser extends HttpServlet {
    
    //classe permettant de stocker le contenu du JSON de la requête
    private class user{
        private String firstName;
        private String lastName;
        private String login;
        private String password;
        private String role;
        private String token;
        private String Test;
        
        private user(String token, String lastName, String firstName, String login, String password, String role, String Test){
            this.firstName = firstName;
            this.lastName = lastName;
            this.login = login;
            this.password = password;
            this.role = role;
            this.token = token;
            this.Test = Test;
        }
    }
    
    

    /**
     * Ajoute un utilisateur<br><br>
     *
     * Variables à envoyer au servlet (POST)<br>
     * String prenom       &emsp;&emsp;        prénom de l'utilisateur <br>
     * String nom       &emsp;&emsp;       nom de l'utilisateur <br>
     * String login       &emsp;&emsp;         login de l'utilisateur <br>
     * String password       &emsp;&emsp;      MDP de l'utilisateur (clair) <br>
     * String role       &emsp;&emsp;      droits de l'utilisateur <br>
     * String token       &emsp;&emsp;     token de l'utilisateur connecté <br>
     * String Test       &emsp;&emsp;      BD à utiliser (true : test | false : sae_52) <br>
     * 
     * @param request       servlet request
     * @param response      servlet response
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
        AddUser.user user = gsonRequest.fromJson(reader, AddUser.user.class);
        
        //Données
        String prenom = user.firstName;
        String nom = user.lastName;
        String login = user.login;
        String password = user.password;
        String role = user.role;
        String token = user.token;
        Boolean TestBoolean = Boolean.valueOf(user.Test);
        
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
            //VERIF si login en doublon
            Boolean loginExist = DAO.doLoginExist(login, TestBoolean);
            
            if(loginExist == false){
                //verif droits utilisateur demande
                String userRights = DAO.getUserRightsFromToken(token, TestBoolean);
                
                //Verification si l'utilisateur a les droits Admin
                if(userRights.equals("Admin")){
                    DAO.addUser(login, nom, prenom, role, hashedPassword, TestBoolean);
                }
                
                //JSON renvoyé
                jsonString = "{\"result\":\"Fait\"}";
            }
            else{
                //JSON renvoyé
                jsonString = "{\"result\":\"Login exist\"}";
            }
            
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
