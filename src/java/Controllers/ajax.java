/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import security.Environment;
import security.Term;

/**
 *
 * @author ryanj
 */
@WebServlet(name = "ajax", urlPatterns = {"/ajax", "/ajax/getVariables", "/ajax/changeVariable", "/ajax/getNetworkBuffer"})
@MultipartConfig
public class ajax extends HttpServlet {

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
            out.println("<title>Servlet ajax</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ajax at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Environment environment = (Environment)session.getAttribute("environment");
        int lol = Integer.parseInt(request.getParameter("runID"));
        String path = request.getServletPath();
        if (path.equals("/ajax/getVariables")) {
            List<Term> list = environment.getAgents().get(lol).getVariables();
            //Json myJson = new Gson();
            String json = new Gson().toJson(list);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            System.out.println("lel");
        }
        else if(path.equals("/ajax/changeVariable")) {
            List<Term> list = environment.getAgents().get(lol).getVariables();
            int variableIndex = Integer.parseInt(request.getParameter("variableIndex"));
            int variableType = Integer.parseInt(request.getParameter("variableType"));
            String newTermString = request.getParameter("newTermString");
            System.out.println("stalllol");
            environment.correctVariable(lol,variableIndex,variableType,newTermString);
        }
        else if(path.equals("/ajax/getNetworkBuffer")) {
            List<Term> list = environment.getNetworkBuffer();
            String json = new Gson().toJson(list);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
        //processRequest(request, response);
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
