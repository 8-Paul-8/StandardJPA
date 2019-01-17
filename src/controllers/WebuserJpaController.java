/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import entities.Webuser;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Webusergroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nikolaos
 */
public class WebuserJpaController implements Serializable {

    public WebuserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Webuser webuser) {
        if (webuser.getWebusergroupCollection() == null) {
            webuser.setWebusergroupCollection(new ArrayList<Webusergroup>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Webusergroup> attachedWebusergroupCollection = new ArrayList<Webusergroup>();
            for (Webusergroup webusergroupCollectionWebusergroupToAttach : webuser.getWebusergroupCollection()) {
                webusergroupCollectionWebusergroupToAttach = em.getReference(webusergroupCollectionWebusergroupToAttach.getClass(), webusergroupCollectionWebusergroupToAttach.getWebusergroupPK());
                attachedWebusergroupCollection.add(webusergroupCollectionWebusergroupToAttach);
            }
            webuser.setWebusergroupCollection(attachedWebusergroupCollection);
            em.persist(webuser);
            for (Webusergroup webusergroupCollectionWebusergroup : webuser.getWebusergroupCollection()) {
                Webuser oldWebuserOfWebusergroupCollectionWebusergroup = webusergroupCollectionWebusergroup.getWebuser();
                webusergroupCollectionWebusergroup.setWebuser(webuser);
                webusergroupCollectionWebusergroup = em.merge(webusergroupCollectionWebusergroup);
                if (oldWebuserOfWebusergroupCollectionWebusergroup != null) {
                    oldWebuserOfWebusergroupCollectionWebusergroup.getWebusergroupCollection().remove(webusergroupCollectionWebusergroup);
                    oldWebuserOfWebusergroupCollectionWebusergroup = em.merge(oldWebuserOfWebusergroupCollectionWebusergroup);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Webuser webuser) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Webuser persistentWebuser = em.find(Webuser.class, webuser.getUserid());
            Collection<Webusergroup> webusergroupCollectionOld = persistentWebuser.getWebusergroupCollection();
            Collection<Webusergroup> webusergroupCollectionNew = webuser.getWebusergroupCollection();
            List<String> illegalOrphanMessages = null;
            for (Webusergroup webusergroupCollectionOldWebusergroup : webusergroupCollectionOld) {
                if (!webusergroupCollectionNew.contains(webusergroupCollectionOldWebusergroup)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Webusergroup " + webusergroupCollectionOldWebusergroup + " since its webuser field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Webusergroup> attachedWebusergroupCollectionNew = new ArrayList<Webusergroup>();
            for (Webusergroup webusergroupCollectionNewWebusergroupToAttach : webusergroupCollectionNew) {
                webusergroupCollectionNewWebusergroupToAttach = em.getReference(webusergroupCollectionNewWebusergroupToAttach.getClass(), webusergroupCollectionNewWebusergroupToAttach.getWebusergroupPK());
                attachedWebusergroupCollectionNew.add(webusergroupCollectionNewWebusergroupToAttach);
            }
            webusergroupCollectionNew = attachedWebusergroupCollectionNew;
            webuser.setWebusergroupCollection(webusergroupCollectionNew);
            webuser = em.merge(webuser);
            for (Webusergroup webusergroupCollectionNewWebusergroup : webusergroupCollectionNew) {
                if (!webusergroupCollectionOld.contains(webusergroupCollectionNewWebusergroup)) {
                    Webuser oldWebuserOfWebusergroupCollectionNewWebusergroup = webusergroupCollectionNewWebusergroup.getWebuser();
                    webusergroupCollectionNewWebusergroup.setWebuser(webuser);
                    webusergroupCollectionNewWebusergroup = em.merge(webusergroupCollectionNewWebusergroup);
                    if (oldWebuserOfWebusergroupCollectionNewWebusergroup != null && !oldWebuserOfWebusergroupCollectionNewWebusergroup.equals(webuser)) {
                        oldWebuserOfWebusergroupCollectionNewWebusergroup.getWebusergroupCollection().remove(webusergroupCollectionNewWebusergroup);
                        oldWebuserOfWebusergroupCollectionNewWebusergroup = em.merge(oldWebuserOfWebusergroupCollectionNewWebusergroup);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = webuser.getUserid();
                if (findWebuser(id) == null) {
                    throw new NonexistentEntityException("The webuser with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Webuser webuser;
            try {
                webuser = em.getReference(Webuser.class, id);
                webuser.getUserid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The webuser with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Webusergroup> webusergroupCollectionOrphanCheck = webuser.getWebusergroupCollection();
            for (Webusergroup webusergroupCollectionOrphanCheckWebusergroup : webusergroupCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Webuser (" + webuser + ") cannot be destroyed since the Webusergroup " + webusergroupCollectionOrphanCheckWebusergroup + " in its webusergroupCollection field has a non-nullable webuser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(webuser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Webuser> findWebuserEntities() {
        return findWebuserEntities(true, -1, -1);
    }

    public List<Webuser> findWebuserEntities(int maxResults, int firstResult) {
        return findWebuserEntities(false, maxResults, firstResult);
    }

    private List<Webuser> findWebuserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Webuser.class));
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

    public Webuser findWebuser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Webuser.class, id);
        } finally {
            em.close();
        }
    }
    
    public Webuser findWebuserByName(String username) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Webuser.findByUsername", Webuser.class);
            q.setParameter("username", username);
            return (Webuser) q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public List<Webuser> findWebuserByNameLike(String usernameLike) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Webuser.findByUsernameLike", Webuser.class);
            q.setParameter("username", "%" + usernameLike + "%");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getWebuserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Webuser> rt = cq.from(Webuser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
