/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.dogos_controller;

import com.itson.dogos_controller.exceptions.NonexistentEntityException;
import com.itson.dogos_model.CompraInsumo;
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
public class CompraInsumoJpaController implements Serializable {

    public CompraInsumoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CompraInsumo compraInsumo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(compraInsumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CompraInsumo compraInsumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            compraInsumo = em.merge(compraInsumo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = compraInsumo.getId();
                if (findCompraInsumo(id) == null) {
                    throw new NonexistentEntityException("The compraInsumo with id " + id + " no longer exists.");
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
            CompraInsumo compraInsumo;
            try {
                compraInsumo = em.getReference(CompraInsumo.class, id);
                compraInsumo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compraInsumo with id " + id + " no longer exists.", enfe);
            }
            em.remove(compraInsumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CompraInsumo> findCompraInsumoEntities() {
        return findCompraInsumoEntities(true, -1, -1);
    }

    public List<CompraInsumo> findCompraInsumoEntities(int maxResults, int firstResult) {
        return findCompraInsumoEntities(false, maxResults, firstResult);
    }

    private List<CompraInsumo> findCompraInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CompraInsumo.class));
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

    public CompraInsumo findCompraInsumo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CompraInsumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CompraInsumo> rt = cq.from(CompraInsumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
