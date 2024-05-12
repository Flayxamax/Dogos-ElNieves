package servlets;

import com.itson.dogos_model.Producto;
import com.itson.negocio.ProductoNegocio;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/Productos"})
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        if (action == null) {
            processObtenerProductos(request, response);
        } else {
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void processObtenerProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductoNegocio productoNegocio = new ProductoNegocio();
            List<Producto> productos = productoNegocio.getListaProductos();
            
            request.setAttribute("productos", productos);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        } 
        String paginaDestino = "/Productos.jsp";
        getServletContext().getRequestDispatcher(paginaDestino).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para manejar solicitudes relacionadas con productos";
    }
}
