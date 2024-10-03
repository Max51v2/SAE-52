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
 * Servlet suppression utilisateur
 * 
 * @author Maxime VALLET
 */
@WebServlet(name = "DeleteUser", urlPatterns = {"/DeleteUser"})
public class DeleteUser extends HttpServlet {

    //classe permettant de stocker le contenu du JSON de la requête
    private class user{
        private String token;
        private String login;
        private String Test;
        
        private user(String token, String login, String Test){
            this.token = token;
            this.login = login;
            this.Test = Test;
        }
    }
    
    
    
    
    /**
     * Suppression d'un utilisateur à partir de son login<br><br>
     *
     * Variables à envoyer au servlet (POST)<br>
     * String login       &emsp;&emsp;        login de l'utilisateur à supprimer <br>
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
        DeleteUser.user user = gsonRequest.fromJson(reader, DeleteUser.user.class);
        
        //Données
        String token = user.token;
        String login = user.login;
        Boolean TestBoolean = Boolean.valueOf(user.Test);
        
        //Création du JSON à renvoyer (vide)
        String jsonString = "";
            
        try { 
            //VERIF si login en doublon
            Boolean loginExist = DAO.doLoginExist(login, TestBoolean);
            
            if(loginExist == true){
                //verif droits utilisateur demande
                String userRights = DAO.GetUserRightsFromToken(token, TestBoolean);
                
                //Verification si l'utilisateur a les droits Admin
                if(userRights.equals("Admin")){
                    //Suppression utilisateur
                    DAO.deleteUser(login, TestBoolean);

                    //JSON renvoyé
                    jsonString = "{\"result\":\"Fait\"}";
                }
            }
            else{
                //JSON renvoyé
                jsonString = "{\"result\":\"Login doesn't exist\"}";
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