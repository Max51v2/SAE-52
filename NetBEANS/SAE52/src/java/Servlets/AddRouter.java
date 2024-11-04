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
 * Servlet ajout d'un Routeur
 * 
 * @author Valentin Millot
 */

@WebServlet(name = "AddRouter", urlPatterns = {"/AddRouter"})
public class AddRouter extends HttpServlet {

    private class Router {
        private String routerPorts;
        private String macAddress;
        private String VLAN;
        private String name;
        private String serialNumber;
        private String status;
        private String token;
        private String Test;

        private Router(String routerPorts, String macAddress, String VLAN, String name, String serialNumber, String status, String token, String Test) {
            this.routerPorts = routerPorts;
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
        
        AddRouter.Router Router = gsonRequest.fromJson(reader, AddRouter.Router.class);
        
        String routerPorts = Router.routerPorts;
        String macAddress = Router.macAddress;
        String VLAN = Router.VLAN;
        String name = Router.name;
        String serialNumber = Router.serialNumber;
        String status = Router.status;
        String token = Router.token;
        Boolean TestBoolean = Boolean.valueOf(Router.Test);
        

        String jsonString = "";

        try {
            Boolean nameExist = DAO.doNameExist(Router.name, Boolean.valueOf(Router.Test));

            if (!nameExist) {
                String userRights = DAO.getUserRightsFromToken(Router.token, Boolean.valueOf(Router.Test));

                if ("Admin".equals(userRights)) {
                    DAO.addRouter(routerPorts, macAddress, VLAN, name, serialNumber, status, nameExist);
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