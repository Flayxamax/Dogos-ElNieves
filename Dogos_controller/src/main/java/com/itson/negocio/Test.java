/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.itson.negocio;

import com.itson.dogos_controller.InsumoJpaController;
import com.itson.dogos_controller.InsumoProductoJpaController;
import com.itson.dogos_controller.InsumoProveedorJpaController;
import com.itson.dogos_controller.OrdenJpaController;
import com.itson.dogos_controller.ProductoJpaController;
import com.itson.dogos_controller.ProveedorJpaController;
import com.itson.dogos_controller.UsuarioJpaController;
import com.itson.dogos_controller.VentaJpaController;
import com.itson.dogos_model.CategoriaProducto;
import com.itson.dogos_model.Insumo;
import com.itson.dogos_model.InsumoProducto;
import com.itson.dogos_model.InsumoProveedor;
import com.itson.dogos_model.Orden;
import com.itson.dogos_model.Producto;
import com.itson.dogos_model.Proveedor;
import com.itson.dogos_model.TipoPago;
import com.itson.dogos_model.TipoUsuario;
import com.itson.dogos_model.Usuario;
import com.itson.dogos_model.Venta;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ildex
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");

        EntityManager em = emf.createEntityManager();

        UsuarioJpaController usuarioC = new UsuarioJpaController(emf);
        Usuario usuario = new Usuario();
        usuario.setNombre("Godge Quezada");
        usuario.setContrasena("qwerty");
        usuario.setTipo(TipoUsuario.NORMAL);
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
        
        InsumoProducto insumoProducto1 = new InsumoProducto();
        insumoProducto1.setInsumo(insumo);
        insumoProducto1.setProducto(producto);
        InsumoProducto insumoProducto2 = new InsumoProducto();
        insumoProducto2.setInsumo(insumo2);
        insumoProducto2.setProducto(producto);
        
        InsumoProductoJpaController ipj = new InsumoProductoJpaController(emf);
        
        ipj.create(insumoProducto1);
        ipj.create(insumoProducto2);
             
        em.close();
        emf.close();
    }

}
