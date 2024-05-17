/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.negocio;

import com.itson.dogos_controller.ProductoJpaController;
import com.itson.dogos_model.Producto;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ildex
 */
public class ProductoNegocio {

    public List<Producto> getListaProductos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");

        ProductoJpaController pjc = new ProductoJpaController(emf);
        List<Producto> listaProductos = pjc.getEntityManager()
                .createQuery("SELECT p FROM Producto p WHERE p.activo = true", Producto.class)
                .getResultList();
        return listaProductos;
    }

    public Producto getProducto(Long idProducto) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");

        ProductoJpaController pjc = new ProductoJpaController(emf);
        Producto producto = pjc.findProducto(idProducto);
        return producto;
    }

    public void editarProducto(Producto producto) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");
        ProductoJpaController pjc = new ProductoJpaController(emf);
        pjc.edit(producto);
    }

    public void eliminarProducto(Long idProducto) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");
        ProductoJpaController pjc = new ProductoJpaController(emf);
        pjc.destroy(idProducto);
    }

    public void agregarProducto(Producto producto) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");
        ProductoJpaController pjc = new ProductoJpaController(emf);
        pjc.create(producto);
    }
}
