/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import com.itson.dogos_controller.ProductoJpaController;
import com.itson.dogos_model.Producto;
import com.itson.dogos_model.Usuario;
import com.itson.negocio.ProductoNegocio;
import com.itson.negocio.VentaNegocio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ildex
 */
@WebServlet(name = "VentaServlet", urlPatterns = {"/Venta"})
public class VentaServlet extends HttpServlet {

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
            out.println("<title>Servlet VentaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VentaServlet at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        this.processObtenerProductos(request, response);
        return;

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
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        Gson gson = new Gson();
        Producto[] productosArray = gson.fromJson(jsonBuilder.toString(), Producto[].class);
        List<Producto> productos = new ArrayList<>();
        for (Producto producto : productosArray) {
            productos.add(producto);
            System.out.println(producto.getId());
        }

        VentaNegocio vn = new VentaNegocio();
        Usuario usuario = new Usuario();
        usuario.setId(1l);
        try {
            vn.realizarVenta(productos, usuario);
        } catch (Exception ex) {
            Logger.getLogger(VentaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void processObtenerProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductoNegocio pn = new ProductoNegocio();
            List<Producto> productos = pn.getListaProductos();
            request.setAttribute("productos", productos);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            getServletContext().getRequestDispatcher("error/errorJava.jsp").forward(request, response);
        }
        String paginaDestino = "/CobrarOrden.jsp";
        getServletContext().getRequestDispatcher(paginaDestino).forward(request, response);
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
