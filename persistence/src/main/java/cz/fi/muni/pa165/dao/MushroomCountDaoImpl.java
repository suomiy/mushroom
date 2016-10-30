package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.MushroomCount;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Erik Macej 433744 , on 29.10.16.
 *
 * @author Erik Macej 433744
 */
@Repository
public class MushroomCountDaoImpl implements MushroomCountDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public MushroomCount findById(Long id) {
        return em.find(MushroomCount.class, id);
    }

    @Override
    public List<MushroomCount> findAll() {
        return em.createQuery("select m from MushroomCount m"
                , MushroomCount.class).getResultList();
    }
}
