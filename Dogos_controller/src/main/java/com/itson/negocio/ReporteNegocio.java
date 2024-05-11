/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.negocio;

import com.itson.dogos_controller.ProductoJpaController;
import com.itson.dogos_controller.VentaJpaController;
import com.itson.dogos_model.Producto;
import com.itson.dogos_model.Venta;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
}
