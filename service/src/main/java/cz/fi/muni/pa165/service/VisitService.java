package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.entity.Visit;

import java.util.Date;
import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 11/23/16
 */
public interface VisitService {

    /**
     * Create new Visit entity.
     *
     * @param visit the visit to be created
     */
    void create(Visit visit);

    /**
     * Update Visit entity.
     *
     * @param visit Visit to be updated
     * @return persisted Visit
     */
    Visit update(Visit visit);

    /**
     * Deletes visit
     *
     * @param visit the visit to be deleted
     */
    void delete(Visit visit);

    /**
     * Find Visit entity by id.
     *
     * @param id identifier of Visit class
     * @return Visit with given id, otherwise null
     */
    Visit findById(Long id);

    /**
     * Find all visits
     *
     * @return the list of all visits
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
     * Finds all visits that can be found in this Date (year and time are ignored)
     *
     * @param date date
     * @return list of found visits
     * @throws IllegalArgumentException if date is null
     */
    List<Visit> findByDate(Date date);

    /**
     * Finds all visits that can be found within the date range (year and time are ignored)
     *
     * @param fromDate
     * @param toDate
     * @return list of found visits
     * @throws IllegalArgumentException if fromDate or toDate is null
     */
    List<Visit> findByDate(Date fromDate, Date toDate);
}
