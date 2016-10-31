package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.entity.Visit;

import java.util.Date;
import java.util.List;

/**
 * @author Jiri Sacha 409861
 *         The interface VisitDao is providing all operations
 */
public interface VisitDao {

    /**
     * Find Visit entity by id.
     *
     * @param id identifier of Visit class
     * @return Visit with given id, otherwise null
     */
    Visit findById(Long id);

    /**
     * Create new Visit entity.
     *
     * @param visit the visit to be put into DB
     */
    void create(Visit visit);

    /**
     * Update Visit entity.
     *
     * @param visit Visit in Dadatabase, which will be updated
     */
    Visit update(Visit visit);

    /**
     * Delete visit from Database.
     *
     * @param visit the visit to be deleted from DB
     */
    void delete(Visit visit);

    /**
     * Find all visits in Database and return them as List
     *
     * @return the list of all visits in DB
     */
    List<Visit> findAll();

    /**
     * Find all visits for given forest.
     *
     * @param forest Forest entity
     * @return list of all visits for given forest
     */
    List<Visit> findByForest(Forest forest);


    /**
     * Find all visits for given hunter.
     *
     * @param hunter Hunter entity
     * @return list of all visits for given hunter
     */
    List<Visit> findByHunter(Hunter hunter);


    /**
     * Find all visits for given mushroom.
     *
     * @param mushroom Mushroom entity
     * @return list of all visits for given mushroom
     */
    List<Visit> findByMushroom(Mushroom mushroom);

    /**
     * Find visits for given date.
     *
     * @param date Date entity
     * @return list of all visits for given date
     */
    List<Visit> findByDate(Date date);
}
