package Servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // Autoriser toutes les origines
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Méthodes permises
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // En-têtes personnalisés acceptés
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Autoriser les requêtes avec des informations d'identification
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // Si c'est une requête préliminaire (OPTIONS), répondre avec 200 OK et ne pas continuer
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
