/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itson.dogos_model.Venta;
import com.itson.negocio.ReporteNegocio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author ildex
 */
@WebServlet(name = "ReporteVentaServlet", urlPatterns = {"/ReporteVenta"})
public class ReporteVentaServlet extends HttpServlet {

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
            out.println("<title>Servlet ReporteVentaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReporteVentaServlet at " + request.getContextPath() + "</h1>");
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
        String paginaDestino = "/GenerarReporte.html";
        getServletContext().getRequestDispatcher(paginaDestino).forward(request, response);
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
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            String jsonString = sb.toString();
            System.out.println("JSON recibido: " + jsonString);

            JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);

            String desdeString = jsonObject.get("desde").getAsString();
            String hastaString = jsonObject.get("hasta").getAsString();

            ReporteNegocio rn = new ReporteNegocio();

            Calendar desdeCalendar = rn.convertirStringACalendar(desdeString);
            Calendar hastaCalendar = rn.convertirStringACalendar(hastaString);

            this.exportarReporteVenta(request, response, desdeCalendar, hastaCalendar);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error interno del servidor\"}");
        }
    }

    private void exportarReporteVenta(HttpServletRequest request, HttpServletResponse response, Calendar desdeCalendar, Calendar hastaCalendar) throws ServletException, IOException {
        ReporteNegocio rn = new ReporteNegocio();
        List<Venta> ventas = rn.getVentasEnPeriodo(desdeCalendar, hastaCalendar);
        double total = rn.calcularTotalProductos(ventas);
        List<Map<String, Object>> dataSource = calcularProductos(ventas, total);

        try (ServletOutputStream out = response.getOutputStream()) {
            InputStream logoEmpresa = this.getServletConfig().getServletContext().getResourceAsStream("assets/reportesJasper/img/logoelnieves.jpg");
            InputStream reporteVenta = this.getServletConfig().getServletContext().getResourceAsStream("assets/reportesJasper/ReporteVenta.jasper");

            if (logoEmpresa != null && reporteVenta != null) {
                response.setContentType("application/pdf");
                response.addHeader("Content-disposition", "inline; filename=ReportesVenta.pdf");

                Map<String, Object> parameters = new HashMap<>();

                Date dateDesde = desdeCalendar.getTime();
                Date dateHasta = hastaCalendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String fechaD = sdf.format(dateDesde);
                String fechaH = sdf.format(dateHasta);
                parameters.put("fechaInicio", fechaD);
                parameters.put("fechaFin", fechaH);
                parameters.put("logo", logoEmpresa);

                JasperPrint jasperPrint = JasperFillManager.fillReport(reporteVenta, parameters, new JRBeanCollectionDataSource(dataSource));
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                out.flush();
            } else {
                response.setContentType("text/plain");
                out.println("No se pudo generar el reporte.");
            }
        } catch (Exception e) {
            response.setContentType("text/plain");
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> calcularProductos(List<Venta> listaVentas, double total) {
        Map<Integer, Integer> cantidadProductosPorId = new HashMap<>();
        Map<Integer, Double> totalPrecioPorOrden = new HashMap<>();
        ReporteNegocio rn = new ReporteNegocio();
        Map<Integer, String> nombreProductosPorId = rn.obtenerNombresProductos();

        List<Map<String, Object>> dataSource = new ArrayList<>();

        for (Venta venta : listaVentas) {
            int idProducto = venta.getProducto().getId().intValue();
            int cantidad = 1;
            double precio = venta.getPrecio();

            cantidadProductosPorId.put(idProducto, cantidadProductosPorId.getOrDefault(idProducto, 0) + cantidad);
            totalPrecioPorOrden.put(idProducto, totalPrecioPorOrden.getOrDefault(idProducto, 0.0) + precio);
        }

        for (Integer idProducto : cantidadProductosPorId.keySet()) {
            int cantidadTotal = cantidadProductosPorId.get(idProducto);
            double precioIndividual = totalPrecioPorOrden.get(idProducto) / cantidadTotal;
            String nombreProducto = nombreProductosPorId.get(idProducto);

            double gananciaProducto = precioIndividual * cantidadTotal;

            Map<String, Object> ventaMap = new HashMap<>();
            ventaMap.put("nombre", nombreProducto);
            ventaMap.put("precio", precioIndividual);
            ventaMap.put("cantidad", cantidadTotal);
            ventaMap.put("gananciaProducto", gananciaProducto);
            ventaMap.put("gananciaBruta", total);

            dataSource.add(ventaMap);
        }

        return dataSource;
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
