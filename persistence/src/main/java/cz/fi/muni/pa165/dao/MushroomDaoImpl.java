package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.enums.MushroomType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cz.fi.muni.pa165.entity.Mushroom;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Michal Kysilko on 26.10.16.
 */

@Repository
@Transactional
public class MushroomDaoImpl implements MushroomDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Mushroom mushroom) {
        em.persist(mushroom);
    }

    @Override
    public void edit(Mushroom mushroom) {
        em.merge(mushroom);
    }

    @Override
    public Mushroom findById(Long id) {
        return em.find(Mushroom.class,id);
    }

    @Override
    public void delete(Mushroom mushroom) throws IllegalArgumentException {
        em.remove(mushroom);
    }

    @Override
    public List<Mushroom> findAll() {
        return em.createQuery("select m from Mushroom m"
                , Mushroom.class).getResultList();
    }

    @Override
    public Mushroom findByName(String name) {
        try {
            return em.createQuery("select m from Mushroom m where name = :name",
                    Mushroom.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public List<Mushroom> findByType(MushroomType type) {
        try {
            return em.createQuery("select m from Mushroom m where type = :type",
                    Mushroom.class).setParameter("type", type).getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public List<Mushroom> findByDate(Month month) {
        List<Mushroom> allMushrooms = findAll();
        List<Mushroom> resultList = new ArrayList<>();

        for(Mushroom oneMushroom : allMushrooms) {
            if(oneMushroom.getFromDate().getValue() <= month.getValue()
                    && oneMushroom.getToDate().getValue() >= month.getValue()) {
                resultList.add(oneMushroom);
            }
        }
        return  resultList;
    }


}
