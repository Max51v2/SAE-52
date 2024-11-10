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

@WebServlet(name = "DeleteTicket", urlPatterns = {"/DeleteTicket"})
public class DeleteTicket extends HttpServlet {

    private class Ticket {
        private String token;
        private String id;
        private String Test;
        
        private Ticket(String token, String id, String Test){
            this.token = token;
            this.id = id;
            this.Test = Test;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Type de la réponse
        response.setContentType("application/json;charset=UTF-8");
        
        DAOSAE52 DAO = new DAOSAE52();  

        // Récupération du JSON envoyé
        BufferedReader reader = request.getReader();
        Gson gsonRequest = new Gson();
        
        // Conversion du JSON en objet Java
        DeleteTicket.Ticket ticket = gsonRequest.fromJson(reader, DeleteTicket.Ticket.class);
        
        // Données
        String token = ticket.token;
        String id = ticket.id;
        Boolean TestBoolean = Boolean.valueOf(ticket.Test);
        
        // Création du JSON à renvoyer
        String jsonString = "";
        
        try {
            // Vérification des droits de l'utilisateur
            String userRights = DAO.getUserRightsFromToken(token, TestBoolean);
            
            // Vérification si l'utilisateur a les droits Admin
            if(userRights.equals("Admin")) {
                DAO.deleteTicket(id, TestBoolean);
                jsonString = "{\"result\":\"Ticket supprimé avec succès\"}";
            } else {
                jsonString = "{\"result\":\"Droits insuffisants\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Envoi des données
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonString);
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet de suppression d'un ticket";
    }
}
