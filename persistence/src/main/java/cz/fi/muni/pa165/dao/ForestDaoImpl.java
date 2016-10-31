package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Forest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Erik Macej 433744 , on 23.10.16.
 *
 * @author Erik Macej 433744
 */

@Repository
public class ForestDaoImpl implements ForestDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Forest forest) {
        em.persist(forest);
    }

    @Override
    public Forest update(Forest forest) {
        return em.merge(forest);
    }

    @Override
    public Forest findById(Long id) {
        return em.find(Forest.class,id);
    }

    @Override
    public void delete(Forest forest) throws IllegalArgumentException {
        em.remove(forest);
    }

    @Override
    public List<Forest> findAll() {
        return em.createQuery("select f from Forest f"
                , Forest.class).getResultList();
    }

    @Override
    public Forest findByName(String name) {
        try {
            return em.createQuery("select f from Forest f where name = :name",
                            Forest.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
