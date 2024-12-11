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
 * Servlet ajout d'un ticket
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
        private String token;
        private String Test;
        
        private ticket(String description, String service, String status, String token, String Test){
            this.description = description;
            this.service = service;
            this.status = status;
            this.token = token;
            this.Test = Test;
        }
    }
    
    

    /**
     * Ajout d'un ticket<br><br>
     *
     * Variables à envoyer au servlet (POST)<br>
     * String description       &emsp;&emsp;        description du ticket <br>
     * String service       &emsp;&emsp;       type de service <br>
     * String status       &emsp;&emsp;         statut du ticket <br>
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
        String token = ticket.token;
        Boolean TestBoolean = Boolean.valueOf(ticket.Test);
        
        
        //Création du JSON à renvoyer (vide)
        String jsonString = "";
        
        try { 
            //VERIF si nom en doublon
            Boolean nameExist = DAO.doNameExist(description, TestBoolean);
            
            if(nameExist == false){
                //verif droits utilisateur demande
                String userRights = DAO.getUserRightsFromToken(token, TestBoolean);
                
                // Vérification si l'utilisateur des droits
                if(userRights.equals("Aucun")){

                }
                else{
                    // JSON renvoyé | Ajout du ticket
                    DAO.addTicket(description, service, status, TestBoolean);
                }
                
                //JSON renvoyé
                jsonString = "{\"result\":\"Fait\"}";
            }
            else{
                //JSON renvoyé
                jsonString = "{\"result\":\"Name exist\"}";
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
