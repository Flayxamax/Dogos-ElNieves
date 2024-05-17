package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itson.dogos_model.CategoriaProducto;
import com.itson.dogos_model.Producto;
import com.itson.negocio.ProductoNegocio;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetalleProductoServlet", urlPatterns = {"/DetalleProducto"})
public class DetalleProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idProductoStr = request.getParameter("id");
        if (idProductoStr != null) {
            try {
                Long idProducto = Long.valueOf(idProductoStr);
                ProductoNegocio productoNegocio = new ProductoNegocio();
                Producto producto = productoNegocio.getProducto(idProducto);
                request.setAttribute("producto", producto);
                System.out.println(request);
                request.getRequestDispatcher("/infoProducto.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID de producto inválido");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error al obtener el detalle del producto: " + e.getMessage());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Se requiere el ID del producto");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String requestBody = stringBuilder.toString();
        Gson gson = new Gson();
        Producto nuevoProducto = gson.fromJson(requestBody, Producto.class);

        try {
            if (nuevoProducto.getNombre() == null || nuevoProducto.getPrecio() <= 0) {
                throw new IllegalArgumentException("Nombre o precio inválido");
            }
            ProductoNegocio productoNegocio = new ProductoNegocio();
            productoNegocio.agregarProducto(nuevoProducto);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Producto creado exitosamente");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error al crear el producto: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al crear el producto: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductoNegocio productoNegocio = new ProductoNegocio();
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String requestBody = stringBuilder.toString();
        Gson gson = new Gson();
        gson.fromJson(requestBody, Producto.class);
        JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
        String Nombre = jsonObject.get("nombre").getAsString();
        String NombreOriginal = jsonObject.get("nombreOriginal").getAsString();
        double Precio = jsonObject.get("precio").getAsDouble();
        String Categoria = jsonObject.get("categoria").getAsString();
        Long idProducto = jsonObject.get("id").getAsLong();

        // Crear el objeto Producto y setear sus propiedades
        Producto productoModificado = new Producto();
        productoModificado.setId(idProducto);
        productoModificado.setNombre(Nombre);
        productoModificado.setPrecio(Precio);
        switch (Categoria.toLowerCase()) {
            case "bebida":
                productoModificado.setCategoria(CategoriaProducto.bebida);
                break;
            case "dogo":
                productoModificado.setCategoria(CategoriaProducto.dogo);
                break;
            case "extra":
                productoModificado.setCategoria(CategoriaProducto.extra);
                break;
            case "hamburguesa":
                productoModificado.setCategoria(CategoriaProducto.hamburguesa);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Error: Categoría de producto inválida");
                return;
        }
        try {
            // Validar el precio
            if (productoModificado.getPrecio() < 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Error: El precio no puede ser negativo");
                return;
            }

            // Verificar si el nombre ha cambiado y si el nuevo nombre ya existe
            if (!productoModificado.getNombre().equalsIgnoreCase(NombreOriginal)) {
                if (productoNegocio.productoExistente(productoModificado.getNombre())) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Error: El nombre del producto ya existe");
                    return;
                }
            }

            productoNegocio.editarProducto(productoModificado);
            productoModificado = productoNegocio.getProducto(productoModificado.getId());
            String productoJson = gson.toJson(productoModificado);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(productoJson);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error: formato de parámetros inválido");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error: categoría de producto inválida");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al modificar el producto: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idProductoStr = request.getParameter("id");

        if (idProductoStr != null) {
            try {
                Long idProducto = Long.valueOf(idProductoStr);
                ProductoNegocio productoNegocio = new ProductoNegocio();
                productoNegocio.eliminarProducto(idProducto);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Producto eliminado exitosamente");
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID de producto inválido");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error al eliminar el producto: " + e.getMessage());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Se requiere el ID del producto");
        }
    }

}
