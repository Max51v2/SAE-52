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
 * Servlet de suppression de ticket
 */
@WebServlet(name = "DeleteTicket", urlPatterns = {"/DeleteTicket"})
public class DeleteTicket extends HttpServlet {

    // Classe pour stocker les informations envoyées dans le JSON
    private class TicketRequest {
        private String token;
        private int id; // Utilisation de l'ID pour identifier le ticket à supprimer
        private String Test; // BD à utiliser (true : test | false : sae_52)

        private TicketRequest(String token, int id, String Test) {
            this.token = token;
            this.id = id;
            this.Test = Test;
        }
    }

    /**
     * Processus principal du servlet : suppression d'un ticket par son ID
     * 
     * Variables à envoyer au servlet (POST) :
     * - String token : token de l'utilisateur connecté
     * - int id : ID du ticket à supprimer
     * - String Test : indique si on utilise la base de données de test (true) ou de production (false)
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException si une erreur spécifique au servlet se produit
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Type de la réponse : JSON
        response.setContentType("application/json;charset=UTF-8");

        // Instancier le DAO
        DAOSAE52 DAO = new DAOSAE52();

        // Récupérer le JSON envoyé
        BufferedReader reader = request.getReader();
        Gson gsonRequest = new Gson();

        // Conversion du JSON en objet Java
        TicketRequest ticketRequest = gsonRequest.fromJson(reader, TicketRequest.class);

        // Données récupérées
        String token = ticketRequest.token;
        int id = ticketRequest.id;
        Boolean TestBoolean = Boolean.valueOf(ticketRequest.Test);

        // Initialisation de la réponse JSON
        String jsonString = "";

        try {
            // Vérification des droits de l'utilisateur connecté (exemple : Admin)
            String userRights = DAO.getUserRightsFromToken(token, TestBoolean);
            
            // Si l'utilisateur a les droits d'Admin
            if (userRights.equals("Admin")) {
                // Suppression du ticket
                DAO.deleteTicket(id, TestBoolean);

                // Réponse JSON de succès
                jsonString = "{\"result\":\"Ticket supprimé avec succès.\"}";
            } else {
                // Si l'utilisateur n'a pas les droits d'Admin
                jsonString = "{\"result\":\"Accès refusé : l'utilisateur n'a pas les droits d'administration.\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonString = "{\"result\":\"Erreur lors de la suppression du ticket.\"}";
        }

        // Envoi de la réponse JSON
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonString);
            out.flush();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Cliquez sur le signe + à gauche pour modifier le code.">
    /**
     * Gestion de la méthode HTTP <code>GET</code>.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException si une erreur spécifique au servlet se produit
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Gestion de la méthode HTTP <code>POST</code>.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException si une erreur spécifique au servlet se produit
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Retourne une courte description du servlet.
     * 
     * @return une chaîne contenant la description du servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet de suppression de ticket";
    }
    // </editor-fold>
}
