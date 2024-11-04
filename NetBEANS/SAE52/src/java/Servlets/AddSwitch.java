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

@WebServlet(name = "AddSwitch", urlPatterns = {"/AddSwitch"})
public class AddSwitch extends HttpServlet {

    private class Switch {
        private String switchSpeed;
        private String macAddress;
        private String VLAN;
        private String name;
        private String serialNumber;
        private String status;
        private String token;
        private String Test;

        private Switch(String switchSpeed, String macAddress, String VLAN, String name, String serialNumber, String status, String token, String Test) {
            this.switchSpeed = switchSpeed;
            this.macAddress = macAddress;
            this.VLAN = VLAN;
            this.name = name;
            this.serialNumber = serialNumber;
            this.status = status;
            this.token = token;
            this.Test = Test;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        DAOSAE52 DAO = new DAOSAE52();
        BufferedReader reader = request.getReader();
        Gson gsonRequest = new Gson();

        AddSwitch.Switch Switch = gsonRequest.fromJson(reader, AddSwitch.Switch.class);
       
        
        //Données
        String switchSpeed = Switch.switchSpeed; 
        String macAddress = Switch.macAddress;
        String VLAN = Switch.VLAN;
        String name = Switch.name;
        String serialNumber = Switch.serialNumber;
        String status = Switch.status;
        String token = Switch.token;
        String Test = Switch.Test;
        Boolean TestBoolean = Boolean.valueOf(Switch.Test);

        String jsonString = "";

        try { 
            //VERIF si nom en doublon
            Boolean nameExist = DAO.doNameExist(name, TestBoolean);
            
            if(nameExist == false){
                //verif droits utilisateur demande
                String userRights = DAO.getUserRightsFromToken(token, TestBoolean);
                
                //Verification si l'utilisateur a les droits Admin
                if(userRights.equals("Admin")){
                    DAO.addSwitch(switchSpeed, macAddress, VLAN, name, serialNumber, status, nameExist);
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
