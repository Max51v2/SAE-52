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
 * Servlet ajout d'un Cable
 * 
 * @author Valentin Millot
 */

@WebServlet(name = "AddCable", urlPatterns = {"/AddCable"})
public class AddCable extends HttpServlet {

    private class Cable{
        private String cableLength;
        private String name;
        private String serialNumber;
        private String status;
        private String token;
        private String Test;

        private Cable(String cableLength, String name, String serialNumber, String status, String token, String Test) {
            this.cableLength = cableLength;
            this.name = name;
            this.serialNumber = serialNumber;
            this.status = status;
            this.token = token;
            this.Test = Test;
        }
    }

    
    
    /**
     * Ajoute un câble<br><br>
     *
     * Variables à envoyer au servlet (POST)<br>
     * 
     * String cableLength       &emsp;&emsp;        longueur du câble <br>
     * String name       &emsp;&emsp;        nom du câble <br>
     * String serialNumber       &emsp;&emsp;        numéro de série du câble <br>
     * String status       &emsp;&emsp;        status des stocks du câble <br>
     * String token       &emsp;&emsp;     token de l'utilisateur connecté <br>
     * String Test       &emsp;&emsp;        BD à utiliser (true : test | false : sae_52) <br><br>
     * 
     * Renvoi : <br>
     * &emsp;   - "Fait" si OK
     * &emsp;   - "Name exist" si le nom exite déjà dans la BD
     * 
     * @param request       servlet request
     * @param response      servlet response
     * @throws      ServletException if a servlet-specific error occurs
     * @throws      IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        DAOSAE52 DAO = new DAOSAE52();
        BufferedReader reader = request.getReader();
        Gson gsonRequest = new Gson();
        AddCable.Cable Cable = gsonRequest.fromJson(reader, AddCable.Cable.class);
        
        String cableLength = Cable.cableLength;
        String name = Cable.name;
        String serialNumber = Cable.serialNumber;
        String status = Cable.status;
        String token = Cable.token;
        Boolean TestBoolean = Boolean.valueOf(Cable.Test);
        
        

        String jsonString = "";

        try {
            Boolean nameExist = DAO.doNameCableExist(name, TestBoolean);

            if (!nameExist) {
                String userRights = DAO.getUserRightsFromToken(Cable.token, Boolean.valueOf(Cable.Test));

                if ("Admin".equals(userRights)) {
                    DAO.addCable(cableLength, name, serialNumber, status, nameExist);
                }

                jsonString = "{\"result\":\"Fait\"}";
            } else {
                jsonString = "{\"result\":\"Name exist\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
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