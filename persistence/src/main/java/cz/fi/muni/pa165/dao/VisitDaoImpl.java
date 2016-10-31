package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
<<<<<<< HEAD
=======
import javax.transaction.Transactional;
import java.util.ArrayList;
>>>>>>> e8df2d7... VisitDaoImpl, VisitDaoTestImpl fix
import java.util.Date;
import java.util.List;

/**
 * Created by Jiří Šácha 409861 on 10/23/16.
 */
@Repository
public class VisitDaoImpl implements VisitDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * Find Visit entity by id.
     *
     * @param id identifier of Visit class
     * @return Visit with given id, otherwise null
     */
    @Override
    public Visit findById(Long id) {

        return em.find(Visit.class, id);
    }

    /**
     * Create new Visit entity.
     *
     * @param visit the visit to be put into DB
     */
    @Override
    public void create(Visit visit) {
        em.persist(visit);
    }

    /**
     * Update Visit entity.
     *
     * @param visit Visit in Dadatabase, which will be updated
     */
    @Override
    public void update(Visit visit) {
        em.merge(visit);
    }

    /**
     * Delete visit from Database.
     *
     * @param visit the visit to be deleted from DB
     */
    @Override
    public void delete(Visit visit) {
        em.remove(visit);
    }

    /**
     * Find all visits in Database and return them as List
     *
     * @return the list of all visits in DB
     */
    @Override
    public List<Visit> findAll() {
        return em.createQuery("select v from Visit v",
                Visit.class).getResultList();
    }

    /**
     * Find all visits for given forest.
     *
     * @param forest Forest entity
     * @return list of all visits for given forest
     */
    public List<Visit> findByForest(Forest forest) {
        try {
            return em.createQuery("select v from Visit v where forest = :forest",
                    Visit.class).setParameter("forest", forest).getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    /**
     * Find all visits for given hunter.
     *
     * @param hunter Hunter entity
     * @return list of all visits for given hunter
     */
    public List<Visit> findByHunter(Hunter hunter) {
        try {
            return em.createQuery("select v from Visit v where hunter = :hunter",
                    Visit.class).setParameter("hunter", hunter).getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    /**
     * Find all visits for given mushroom.
     *
     * @param mushroom Mushroom entity
     * @return list of all visits for given mushroom
     */
    public List<Visit> findByMushroom(Mushroom mushroom) {

        List<Visit> visits = findAll();
        List<Visit> resultList = new ArrayList<>();

        for(Visit v : visits) {
            for(MushroomCount mc : v.getMushroomsCount()) {
                if(mc.getMushroom().equals(mushroom)) {
                    resultList.add(v);
                }
            }
        }
        return resultList;
    }

    /**
     * Find visits for given date.
     *
     * @param date Date entity
     * @return list of all visits for given date
     */
    public List<Visit> findByDate(Date date) {
        try {
            return em.createQuery("select v from Visit v where date = :date",
                    Visit.class).setParameter("date", date).getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
