/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.negocio;

import com.itson.dogos_controller.ProductoJpaController;
import com.itson.dogos_controller.VentaJpaController;
import com.itson.dogos_model.Orden;
import com.itson.dogos_model.Producto;
import com.itson.dogos_model.Venta;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Elkur
 */
public class ReporteNegocio {

    public List<Venta> getVentasEnPeriodo(Calendar fechaInicio, Calendar fechaFin) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");

        VentaJpaController vjc = new VentaJpaController(emf);
        List<Venta> listaVentas = vjc.findVentas(fechaInicio, fechaFin);
        return listaVentas;
    }

    public Calendar convertirStringACalendar(String fechaString) {
        String[] partesFecha = fechaString.split("-");
        int anio = Integer.parseInt(partesFecha[0]);
        int mes = Integer.parseInt(partesFecha[1]) - 1;
        int dia = Integer.parseInt(partesFecha[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(anio, mes, dia);

        return calendar;
    }

    public double calcularTotalProductos(List<Venta> listaVentas) {
        Map<Integer, Integer> cantidadProductosPorId = new HashMap<>();
        Map<Integer, Double> totalPrecioPorOrden = new HashMap<>();
        Map<Integer, String> nombreProductosPorId = obtenerNombresProductos();
        
        double total = 0;
        
        for (Venta venta : listaVentas) {
            int idProducto = venta.getProducto().getId().intValue();
            int cantidad = 1; //cada venta cuenta como 1 unidad del producto
            double precio = venta.getPrecio();

            cantidadProductosPorId.put(idProducto, cantidadProductosPorId.getOrDefault(idProducto, 0) + cantidad);
            totalPrecioPorOrden.put(idProducto, totalPrecioPorOrden.getOrDefault(idProducto, 0.0) + precio);
        }

        for (Integer idProducto : cantidadProductosPorId.keySet()) {
            int cantidadTotal = cantidadProductosPorId.get(idProducto);
            double precioIndividual = totalPrecioPorOrden.get(idProducto) / cantidadTotal; // Calcular el precio individual
            double totalPrecio = totalPrecioPorOrden.get(idProducto);
            double gananciaProducto = precioIndividual * cantidadTotal;
            total += gananciaProducto;
            String nombreProducto = nombreProductosPorId.get(idProducto);
        }
        return total;
    }

    public Map<Integer, String> obtenerNombresProductos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT p.id, p.nombre FROM Producto p");
        List<Object[]> results = query.getResultList();

        Map<Integer, String> nombreProductosPorId = new HashMap<>();
        for (Object[] result : results) {
            Long idProductoLong = (Long) result[0];
            int idProducto = idProductoLong.intValue();
            String nombre = (String) result[1];
            nombreProductosPorId.put(idProducto, nombre);
        }

        em.close();
        emf.close();

        return nombreProductosPorId;
    }
}
