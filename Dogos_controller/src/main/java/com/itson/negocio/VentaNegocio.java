/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.negocio;

import com.itson.dogos_controller.InsumoJpaController;
import com.itson.dogos_controller.OrdenJpaController;
import com.itson.dogos_controller.VentaJpaController;
import com.itson.dogos_model.Insumo;
import com.itson.dogos_model.Orden;
import com.itson.dogos_model.Producto;
import com.itson.dogos_model.Usuario;
import com.itson.dogos_model.Venta;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ildex
 */
public class VentaNegocio {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");

    InsumoJpaController ijc = new InsumoJpaController(emf);
    OrdenJpaController ojc = new OrdenJpaController(emf);
    VentaJpaController vjc = new VentaJpaController(emf);

    public boolean realizarVenta(List<Producto> listaProductos, Usuario usuario) throws Exception {
        double total = 0;
        boolean validador = true;

        for (Producto q : listaProductos) {
            List<Insumo> listaInsumos = ijc.getInsumoProducto(q);

            for (Insumo w : listaInsumos) {
                if (w.getCantidad() > 0) {
                    //TODO controller hacer un metodo que reste 1 cant insumo lolequisde
                    w.setCantidad(w.getCantidad() - 1);
                    ijc.edit(w);
                } else {
                    //TODO throw error no hay 
                }
            }

            total += q.getPrecio();

        }

        Date fecha = new Date();
        Orden orden = new Orden();
        orden.setNumero((int) total + 1);
        orden.setTotal(total);
        orden.setUsuario(usuario);
        orden.setFechaHora(fecha);
        ojc.create(orden);

        for (Producto e : listaProductos) {
            Venta venta = new Venta();
            venta.setProducto(e);
            venta.setOrden(orden);
            venta.setImporte(total);
            venta.setPrecio(e.getPrecio());
            venta.setCantidadProducto(1);
            vjc.create(venta);
            if (venta.getId() == null) {
                validador = false;
            }
        }
        return validador;
    }

}
