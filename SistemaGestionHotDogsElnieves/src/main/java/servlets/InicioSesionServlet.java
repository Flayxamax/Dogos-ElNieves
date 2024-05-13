/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import com.itson.dogos_model.TipoUsuario;
import com.itson.dogos_model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itson.negocio.UsuarioNegocio;
import java.io.BufferedReader;

/**
 *
 * @author JORGE
 */
@WebServlet(name = "InicioSesionServlet", urlPatterns = {"/Login"})
public class InicioSesionServlet extends HttpServlet {

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
            out.println("<title>Servlet InicioSesionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InicioSesionServlet at " + request.getContextPath() + "</h1>");
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
        response.sendRedirect("login.jsp");
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
        // Leer los datos del JSON enviado por fetch
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        String requestBody = jsonBuilder.toString();
        // Convertir los datos del JSON a un objeto Java
        Gson gson = new Gson();
        Usuario datosInicioSesion = gson.fromJson(requestBody, Usuario.class);

        try {
            // Obtener el usuario y la contraseña del objeto Java
            String usuario = datosInicioSesion.getNombre();
            String contrasenia = datosInicioSesion.getContrasena();

            // Realizar la validación del usuario (debes implementar esta lógica)
            UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
            System.out.println(usuario + contrasenia);
            TipoUsuario usuarioValido = usuarioNegocio.obtenerTipoUsuario(usuario, contrasenia);
            System.out.println(usuarioValido);

            if (usuarioValido.equals(TipoUsuario.ADMIN)) {
                // Si el usuario es válido, redirigir a la página de éxito
                response.setStatus(HttpServletResponse.SC_OK);
            } else if (usuarioValido.equals(TipoUsuario.NORMAL)) {
                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } 
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(0, "");
        }

        // Redirigir al usuario según el resultado de la validación
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
