package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Hunter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * This following code is a work of fiction. Variables, methods, classes and interfaces are
 * either the products of the author’s imagination or used in a fictitious manner.
 * Any resemblance to actual persons's code, living, dead or in coma is purely coincidental.
 *
 * @author Filip Krepinsky (410022) on 10/29/16
 */

@Repository
public class HunterDaoImpl implements HunterDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Hunter hunter) {
        em.persist(hunter);
    }

    @Override
    public Hunter update(Hunter hunter) {
        return em.merge(hunter);
    }

    @Override
    public void delete(Hunter hunter) {
        em.remove(hunter);
    }

    @Override
    public Hunter findById(Long id) {
        return em.find(Hunter.class, id);
    }

    @Override
    public Hunter findByEmail(String email) {
        List<Hunter> hunters = em.createQuery("SELECT h FROM Hunter h WHERE email = :email", Hunter.class)
                .setMaxResults(1)
                .setParameter("email", email)
                .getResultList();
        return hunters.isEmpty() ? null : hunters.get(0);
    }

    @Override
    public List<Hunter> findAll() {
        return em.createQuery("select h from Hunter h",
                Hunter.class).getResultList();
    }
}
