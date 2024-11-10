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

@WebServlet(name = "SendTicketToAdmin", urlPatterns = {"/SendTicketToAdmin"})
public class SendTicketToAdmin extends HttpServlet {

    // Classe interne pour stocker les données envoyées par la secrétaire
    private class TicketRequest {
        private int id;
        private String token;  // Pour vérifier les droits de l'utilisateur
        private Boolean Test;

        // Constructeur
        private TicketRequest(int id, String token, Boolean Test) {
            this.id = id;
            this.token = token;
            this.Test = Test;
        }
    }

    // Méthode qui gère la requête HTTP
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Type de la réponse
        response.setContentType("application/json;charset=UTF-8");

        // Création de l'objet DAO pour interagir avec la base de données
        DAOSAE52 DAO = new DAOSAE52();

        // Récupération du JSON envoyé dans la requête
        BufferedReader reader = request.getReader();
        Gson gsonRequest = new Gson();

        // Conversion du JSON en objet Java
        TicketRequest ticketRequest = gsonRequest.fromJson(reader, TicketRequest.class);

        // Données
        int ticketId = ticketRequest.id;
        String token = ticketRequest.token;
        Boolean TestBoolean = ticketRequest.Test;

        // Création du JSON à renvoyer (vide)
        String jsonString = "";

        try {
            // Vérification des droits de l'utilisateur via le token
            String userRights = DAO.getUserRightsFromToken(token, TestBoolean);
            if (userRights.equals("Admin")) {
                // Si l'utilisateur est un admin, on envoie le ticket à l'administrateur
                DAO.sendTicketToAdmin(ticketId, TestBoolean);
                jsonString = "{\"result\":\"Ticket envoyé à l'administrateur\"}";
            } else {
                // Si l'utilisateur n'a pas les droits, on renvoie un message d'erreur
                jsonString = "{\"result\":\"Droits insuffisants\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonString = "{\"result\":\"Erreur interne\"}";
        }

        // Envoi des données au client (secrétaire)
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonString);
            out.flush();
        }
    }

    // Méthode pour traiter les requêtes HTTP GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Méthode pour traiter les requêtes HTTP POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Description du servlet
    @Override
    public String getServletInfo() {
        return "Servlet pour envoyer un ticket à l'administrateur";
    }
}
