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
 * Servlet création de ticket
 * 
 * @author Ishac Hamdani
 */
@WebServlet(name = "CreateTicket", urlPatterns = {"/CreateTicket"})
public class CreateTicket extends HttpServlet {

    // Classe pour stocker le contenu du JSON de la requête
    private class Ticket {
        private final String title;
        private final String description;
        private final String status;
        private final String senderToken;

        // Constructeur
        private Ticket(String title, String description, String status, String senderToken) {
            this.title = title;
            this.description = description;
            this.status = status;
            this.senderToken = senderToken;
        }
    }

    /**
     * Crée un ticket<br><br>
     *
     * Variables à envoyer au servlet (POST)<br>
     * String title        &emsp;&emsp;   titre du ticket <br>
     * String description  &emsp;&emsp;   description du ticket <br>
     * String status       &emsp;&emsp;   statut initial du ticket (ex: "Nouveau") <br>
     * String senderToken  &emsp;&emsp;   token de l'utilisateur connecté <br>
     * 
     * @param request      servlet request
     * @param response     servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Type de la réponse
        response.setContentType("application/json;charset=UTF-8");
        
        DAOSAE52 DAO = new DAOSAE52();

        // Récupération du JSON envoyé
        BufferedReader reader = request.getReader();
        Gson gsonRequest = new Gson();
        
        // Conversion du JSON en objet Java
        Ticket ticket = gsonRequest.fromJson(reader, Ticket.class);
        
        // Données
        String title = ticket.title;
        String description = ticket.description;
        String status = ticket.status;
        String senderToken = ticket.senderToken;

        // Création du JSON de réponse
        String jsonString;

        try {
            // Vérification des droits de l'utilisateur (doit être "Secrétaire")
            String userRole = DAO.getUserRightsFromToken(senderToken, true);
            
            if (userRole.equals("Secrétaire")) {
                // Ajout du ticket à la base de données
                DAO.createTicket(title, description, status, senderToken);
                
                // JSON de confirmation de la création du ticket
                jsonString = "{\"result\":\"Ticket créé avec succès\"}";
            } else {
                jsonString = "{\"result\":\"Accès refusé : vous n'avez pas les droits suffisants\"}";
            }

        } catch (Exception e) {
            e.printStackTrace();
            jsonString = "{\"result\":\"Erreur lors de la création du ticket\"}";
        }

        // Envoi de la réponse JSON
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
        return "Servlet de création de tickets";
    }
}
