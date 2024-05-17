/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.itson.dogos_controller;

import com.itson.dogos_model.CategoriaProducto;
import com.itson.dogos_model.Insumo;
import com.itson.dogos_model.InsumoProveedor;
import com.itson.dogos_model.Orden;
import com.itson.dogos_model.Producto;
import com.itson.dogos_model.Proveedor;
import com.itson.dogos_model.TipoPago;
import com.itson.dogos_model.TipoUsuario;
import com.itson.dogos_model.Usuario;
import com.itson.dogos_model.Venta;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ildex
 */
public class pruebaController {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

        UsuarioJpaController usuarioC = new UsuarioJpaController(emf);
        Usuario usuario = new Usuario();
        usuario.setNombre("admin");
        usuario.setContrasena("admin");
        usuario.setTipo(TipoUsuario.ADMIN);
        usuarioC.create(usuario);

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("FUD");
        proveedor.setTelefono("6441233434");

        Proveedor proveedor2 = new Proveedor();
        proveedor2.setNombre("Kowi");
        proveedor2.setTelefono("6441233434");

        ProveedorJpaController pjc = new ProveedorJpaController(emf);
        pjc.create(proveedor);
        pjc.create(proveedor2);

        Insumo insumo = new Insumo();
        insumo.setNombre("Salchicha");
        insumo.setCantidad(10);

        Insumo insumo2 = new Insumo();
        insumo2.setNombre("Pan");
        insumo2.setCantidad(100);

        InsumoJpaController ijc = new InsumoJpaController(emf);
        ijc.create(insumo);
        ijc.create(insumo2);

        InsumoProveedor insumoP = new InsumoProveedor();
        insumoP.setInsumo(insumo);
        insumoP.setProveedor(proveedor);

        InsumoProveedor insumoP2 = new InsumoProveedor();
        insumoP2.setInsumo(insumo);
        insumoP2.setProveedor(proveedor2);

        InsumoProveedorJpaController ipjc = new InsumoProveedorJpaController(emf);
        ipjc.create(insumoP);
        ipjc.create(insumoP2);

        Producto producto = new Producto();
        producto.setNombre("Sencillo");
        producto.setPrecio(30);
        producto.setCategoria(CategoriaProducto.dogo);

        ProductoJpaController pjcc = new ProductoJpaController(emf);
        pjcc.create(producto);

        Date fecha = new Date();
        
        Orden orden = new Orden();
        orden.setNumero(1);
        orden.setTotal(30);
        orden.setUsuario(usuario);
        orden.setFechaHora(fecha);

        OrdenJpaController ojc = new OrdenJpaController(emf);
        ojc.create(orden);

        Venta venta = new Venta();
        venta.setImporte(30);
        venta.setPrecio(30);
        venta.setProducto(producto);
        venta.setOrden(orden);
        venta.setTipoPago(TipoPago.efectivo);

        VentaJpaController vjc = new VentaJpaController(emf);
        vjc.create(venta);

        em.close();
        emf.close();
    }

}
