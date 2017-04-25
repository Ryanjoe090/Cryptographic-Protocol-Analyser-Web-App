/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import security.Derivation;
import security.Environment;
import security.Term;
import security.Term.Type;

/**
 *
 * @author Think
 */
@WebServlet(name = "networkbuffer", urlPatterns = {"/networkbuffer"})
@MultipartConfig
public class networkbuffer extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet networkbuffer</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet networkbuffer at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        //processRequest(request, response);
        HttpSession session = request.getSession();
        RequestDispatcher rd = request.getRequestDispatcher("/networkbuffer.jsp");
        rd.forward(request, response);
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
        HttpSession session = request.getSession();
        Environment environment = (Environment) session.getAttribute("environment");

        if (request.getParameter("postCommand").equals("ENCRYPT")) {
            //Object generic = request.getPart("selectedTerm");
            String[] parameters = request.getParameterValues("selectedTerm");
            if (parameters.length > 1) {
                List<Term> finalList = new LinkedList<>();
                for (int i = 0; i < parameters.length; i++) {
                    System.out.println(environment.getProtocol().getNetworkKnowledge().get(Integer.parseInt(parameters[i])).getTermString());
                    if(environment.getProtocol().getNetworkKnowledge().get(Integer.parseInt(parameters[i])).getType().equals(Type.LIST))
                    {
                        Derivation.breakdownTermList(environment.getProtocol().getNetworkKnowledge().get(Integer.parseInt(parameters[i])));
                    }
                }
            }

        }

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
