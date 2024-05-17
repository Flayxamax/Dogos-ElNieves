/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.negocio;

import com.itson.dogos_model.TipoUsuario;
import com.itson.dogos_model.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author JORGE
 */
public class UsuarioNegocio {

    public TipoUsuario obtenerTipoUsuario(String nombre, String contrasenia) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.itson_Dogos_model_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            Root<Usuario> root = cq.from(Usuario.class);

            cq.select(root)
              .where(cb.and(
                cb.equal(root.get("nombre"), nombre),
                cb.equal(root.get("contrasena"), contrasenia)
              ));
            
            TypedQuery<Usuario> query = em.createQuery(cq);
            Usuario usuario = query.getSingleResult();
            
            return usuario.getTipo(); // Devuelve el tipo de usuario
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
