/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dogos_controller;

import com.itson.dogos_controller.exceptions.NonexistentEntityException;
import com.itson.dogos_model.InsumoProducto;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ildex
 */
public class InsumoProductoJpaController implements Serializable {

    public InsumoProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InsumoProducto insumoProducto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(insumoProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InsumoProducto insumoProducto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            insumoProducto = em.merge(insumoProducto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = insumoProducto.getId();
                if (findInsumoProducto(id) == null) {
                    throw new NonexistentEntityException("The insumoProducto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InsumoProducto insumoProducto;
            try {
                insumoProducto = em.getReference(InsumoProducto.class, id);
                insumoProducto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The insumoProducto with id " + id + " no longer exists.", enfe);
            }
            em.remove(insumoProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InsumoProducto> findInsumoProductoEntities() {
        return findInsumoProductoEntities(true, -1, -1);
    }

    public List<InsumoProducto> findInsumoProductoEntities(int maxResults, int firstResult) {
        return findInsumoProductoEntities(false, maxResults, firstResult);
    }

    private List<InsumoProducto> findInsumoProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InsumoProducto.class));
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

    public InsumoProducto findInsumoProducto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InsumoProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getInsumoProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InsumoProducto> rt = cq.from(InsumoProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
