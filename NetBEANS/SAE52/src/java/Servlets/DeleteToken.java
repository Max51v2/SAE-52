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
 * Servlet suppression token
 * 
 * @author Maxime VALLET
 */
@WebServlet(name = "DeleteToken", urlPatterns = {"/DeleteToken"})
public class DeleteToken extends HttpServlet {
    
    //classe permettant de stocker le contenu du JSON de la requête
    private class Token{
        private String token;
        private String Test;
        
        private Token(String token, String Test){
            this.token=token;
            this.Test=Test;
        }
    }
    
    

    /**
     * Retire le token envoyé de la BD<br><br>
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
        DeleteToken.Token deleteToken = gsonRequest.fromJson(reader, DeleteToken.Token.class);
        
        //Données
        String token = deleteToken.token;
        Boolean TestBoolean = Boolean.valueOf(deleteToken.Test);
        
        //Création du JSON à renvoyer (vide)
        String jsonString = "";
        
        try { 
            //Suppression du token
            DAO.deleteToken(token, TestBoolean);
            
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
