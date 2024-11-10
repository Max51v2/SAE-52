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


/**
 * Servlet ajout ticket
 * 
 * @author HAMDANI Ishac
 */
@WebServlet(name = "AddTicket", urlPatterns = {"/AddTicket"})
public class AddTicket extends HttpServlet {
    
    //classe permettant de stocker le contenu du JSON de la requête
    private class ticket{
        private String description;
        private String service;
        private String status;
        private String Test;
        
        private ticket(String description, String service, String status, String Test){
            this.description = description;
            this.service = service;
            this.status = status;
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
        AddTicket.ticket ticket = gsonRequest.fromJson(reader, AddTicket.ticket.class);
        
        //Données
        String description = ticket.description;
        String service = ticket.service;
        String status = ticket.status;
        Boolean TestBoolean = Boolean.valueOf(ticket.Test);
        

String jsonString;

    try {
        // Ajouter le ticket dans la base de données
        DAO.addTicket(description, service, status, TestBoolean);
        jsonString = "{\"result\":\"Ticket ajouté avec succès\"}";
    } catch (Exception e) {
        e.printStackTrace();
        jsonString = "{\"result\":\"Erreur lors de l'ajout du ticket\"}";
    }

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
