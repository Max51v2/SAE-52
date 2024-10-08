package Servlets;

import DAO.DAOSAE52;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Vérification token
 * 
 * @author Maxime VALLET
 */
@WebServlet(name = "TokenCheck", urlPatterns = {"/TokenCheck"})
public class TokenCheck extends HttpServlet {
    
    //classe permettant de stocker le contenu du JSON de la requête
    private class Token{
        private String token;
        private String Test;
        
        private Token(String token, String Test){
            this.token = token;
            this.Test = Test;
        }
    }
    
    
    

    /**
     * Récupère le login correspondant au token et les droit de l'utilisateur (par rapport au login)<br><br>
     *
     * Variables à envoyer au servlet (POST)<br>
     * String token       &emsp;&emsp;        token de l'utilisateur connecté <br>
     * String Test       &emsp;&emsp;        BD à utiliser (true : test | false : sae_52) <br>
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
        TokenCheck.Token tokenRequest = gsonRequest.fromJson(reader, TokenCheck.Token.class);
        
        //Données
        String login = "";
        String rights = "Aucun";
        String token = tokenRequest.token;
        Boolean TestBoolean = Boolean.valueOf(tokenRequest.Test);
        
        //Création du JSON à renvoyer (vide)
        String jsonString = "";
        
        try { 
            //Récuperation du login correspondant au token
            login = DAO.checkToken(token, TestBoolean);
            
            //si il n'y a pas de hash, utilisateur inexistant
            if(login.equals("")){
                //rien
            }
            //l'utilisateur existe mais il faut vérifier le MDP
            else{
                //Récupération des droits de l'utilisateur
                rights = DAO.getUserRightsFromLogin(login, TestBoolean);
            }
            
            
            //JSON renvoyé
            jsonString = "{\"login\":\""+login+"\", \"droits\":\""+rights+"\"}";
            
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
