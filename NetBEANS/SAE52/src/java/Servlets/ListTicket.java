package Servlets;

import DAO.DAOSAE52;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Récupération des tickets
 * 
 * @author [Ton Nom]
 */
@WebServlet(name = "ListTickets", urlPatterns = {"/ListTickets"})
public class ListTicket extends HttpServlet {
    
    // Classe permettant de stocker le contenu du JSON de la requête
    private class UserRequest {
        private String token;
        private String Test;
        
        private UserRequest(String token, String Test){
            this.token = token;
            this.Test = Test;
        }
    }
    
    /**
     * Renvoi la liste des tickets dans la DB au format JSON<br><br>
     *
     * Variables à envoyer au servlet (POST)<br>
     * String token       &emsp;&emsp;        token de l'utilisateur connecté <br>
     * String Test       &emsp;&emsp;        BD à utiliser (true : test | false : sae_52) <br>
     * 
     * @param request       servlet request
     * @param response      servlet response
     * @throws ServletException si une erreur spécifique du servlet survient
     * @throws IOException si une erreur I/O survient
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
        UserRequest userRequest = gsonRequest.fromJson(reader, UserRequest.class);
        
        // Données
        String token = userRequest.token;
        Boolean TestBoolean = Boolean.valueOf(userRequest.Test);
        
        // Création du JSON à renvoyer (vide)
        String jsonString = "";
        
        try {
            // Vérification des droits de l'utilisateur
            String userRights = DAO.getUserRightsFromToken(token, TestBoolean);
                
            // Vérification si l'utilisateur a les droits Admin
           if(userRights.equals("Admin") || userRights.equals("Secretaire")) {
    // Si l'utilisateur est un admin ou un secrétaire, il peut voir tous les tickets
        jsonString = DAO.getTicket(TestBoolean);
        } else {
        jsonString = "{\"error\": \"Accès refusé. Droits insuffisants.\"}";
                }

        } catch (Exception e) {
            e.printStackTrace();
            jsonString = "{\"error\": \"Erreur serveur\"}";
        }
        
        // Envoi des données
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonString);
            out.flush();
        }
    }

    // Méthode pour gérer les requêtes GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Méthode pour gérer les requêtes POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Description du servlet
    @Override
    public String getServletInfo() {
        return "Servlet pour récupérer les tickets selon les droits de l'utilisateur";
    }
}
