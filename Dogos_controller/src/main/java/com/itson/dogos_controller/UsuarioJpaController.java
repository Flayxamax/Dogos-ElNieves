/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dogos_controller;

import com.itson.dogos_controller.exceptions.IllegalOrphanException;
import com.itson.dogos_controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itson.dogos_model.Compra;
import java.util.ArrayList;
import java.util.List;
import com.itson.dogos_model.Orden;
import com.itson.dogos_model.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ildex
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getCompra() == null) {
            usuario.setCompra(new ArrayList<Compra>());
        }
        if (usuario.getOrden() == null) {
            usuario.setOrden(new ArrayList<Orden>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Compra> attachedCompra = new ArrayList<Compra>();
            for (Compra compraCompraToAttach : usuario.getCompra()) {
                compraCompraToAttach = em.getReference(compraCompraToAttach.getClass(), compraCompraToAttach.getId());
                attachedCompra.add(compraCompraToAttach);
            }
            usuario.setCompra(attachedCompra);
            List<Orden> attachedOrden = new ArrayList<Orden>();
            for (Orden ordenOrdenToAttach : usuario.getOrden()) {
                ordenOrdenToAttach = em.getReference(ordenOrdenToAttach.getClass(), ordenOrdenToAttach.getId());
                attachedOrden.add(ordenOrdenToAttach);
            }
            usuario.setOrden(attachedOrden);
            em.persist(usuario);
            for (Compra compraCompra : usuario.getCompra()) {
                Usuario oldUsuarioOfCompraCompra = compraCompra.getUsuario();
                compraCompra.setUsuario(usuario);
                compraCompra = em.merge(compraCompra);
                if (oldUsuarioOfCompraCompra != null) {
                    oldUsuarioOfCompraCompra.getCompra().remove(compraCompra);
                    oldUsuarioOfCompraCompra = em.merge(oldUsuarioOfCompraCompra);
                }
            }
            for (Orden ordenOrden : usuario.getOrden()) {
                Usuario oldUsuarioOfOrdenOrden = ordenOrden.getUsuario();
                ordenOrden.setUsuario(usuario);
                ordenOrden = em.merge(ordenOrden);
                if (oldUsuarioOfOrdenOrden != null) {
                    oldUsuarioOfOrdenOrden.getOrden().remove(ordenOrden);
                    oldUsuarioOfOrdenOrden = em.merge(oldUsuarioOfOrdenOrden);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            List<Compra> compraOld = persistentUsuario.getCompra();
            List<Compra> compraNew = usuario.getCompra();
            List<Orden> ordenOld = persistentUsuario.getOrden();
            List<Orden> ordenNew = usuario.getOrden();
            List<String> illegalOrphanMessages = null;
            for (Compra compraOldCompra : compraOld) {
                if (!compraNew.contains(compraOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraOldCompra + " since its usuario field is not nullable.");
                }
            }
            for (Orden ordenOldOrden : ordenOld) {
                if (!ordenNew.contains(ordenOldOrden)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orden " + ordenOldOrden + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Compra> attachedCompraNew = new ArrayList<Compra>();
            for (Compra compraNewCompraToAttach : compraNew) {
                compraNewCompraToAttach = em.getReference(compraNewCompraToAttach.getClass(), compraNewCompraToAttach.getId());
                attachedCompraNew.add(compraNewCompraToAttach);
            }
            compraNew = attachedCompraNew;
            usuario.setCompra(compraNew);
            List<Orden> attachedOrdenNew = new ArrayList<Orden>();
            for (Orden ordenNewOrdenToAttach : ordenNew) {
                ordenNewOrdenToAttach = em.getReference(ordenNewOrdenToAttach.getClass(), ordenNewOrdenToAttach.getId());
                attachedOrdenNew.add(ordenNewOrdenToAttach);
            }
            ordenNew = attachedOrdenNew;
            usuario.setOrden(ordenNew);
            usuario = em.merge(usuario);
            for (Compra compraNewCompra : compraNew) {
                if (!compraOld.contains(compraNewCompra)) {
                    Usuario oldUsuarioOfCompraNewCompra = compraNewCompra.getUsuario();
                    compraNewCompra.setUsuario(usuario);
                    compraNewCompra = em.merge(compraNewCompra);
                    if (oldUsuarioOfCompraNewCompra != null && !oldUsuarioOfCompraNewCompra.equals(usuario)) {
                        oldUsuarioOfCompraNewCompra.getCompra().remove(compraNewCompra);
                        oldUsuarioOfCompraNewCompra = em.merge(oldUsuarioOfCompraNewCompra);
                    }
                }
            }
            for (Orden ordenNewOrden : ordenNew) {
                if (!ordenOld.contains(ordenNewOrden)) {
                    Usuario oldUsuarioOfOrdenNewOrden = ordenNewOrden.getUsuario();
                    ordenNewOrden.setUsuario(usuario);
                    ordenNewOrden = em.merge(ordenNewOrden);
                    if (oldUsuarioOfOrdenNewOrden != null && !oldUsuarioOfOrdenNewOrden.equals(usuario)) {
                        oldUsuarioOfOrdenNewOrden.getOrden().remove(ordenNewOrden);
                        oldUsuarioOfOrdenNewOrden = em.merge(oldUsuarioOfOrdenNewOrden);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Compra> compraOrphanCheck = usuario.getCompra();
            for (Compra compraOrphanCheckCompra : compraOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Compra " + compraOrphanCheckCompra + " in its compra field has a non-nullable usuario field.");
            }
            List<Orden> ordenOrphanCheck = usuario.getOrden();
            for (Orden ordenOrphanCheckOrden : ordenOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Orden " + ordenOrphanCheckOrden + " in its orden field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
