package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.entity.Hunter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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
        return em.find(Forest.class, id);
    }

    @Override
    public void delete(Forest forest) {
        em.remove(forest);
    }

    @Override
    public List<Forest> findAll() {
        return em.createQuery("select f from Forest f"
                , Forest.class).getResultList();
    }

    @Override
    public Forest findByName(String name) {
        List<Forest> forests = em.createQuery("select f from Forest f WHERE lower(name) like CONCAT('%', lower(:name), '%')",
                Forest.class)
                .setMaxResults(1)
                .setParameter("name", name)
                .getResultList();
        return forests.isEmpty() ? null : forests.get(0);
    }
}
