package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.MushroomCount;

import java.util.List;

/**
 * Created by Erik Macej 433744 , on 29.10.16.
 *
 * @author Erik Macej 433744
 */

public interface MushroomCountDao {

    /**
     * Creates mushroomCount
     *
     * @param mushroomCount mushroomCount
     */
    void create(MushroomCount mushroomCount);

    /**
     * Updates mushroomCount entity.
     *
     * @param mushroomCount mushroomCount
     */
    MushroomCount update(MushroomCount mushroomCount);

    /**
     * Deletes mushroomCount from Database.
     *
     * @param mushroomCount mushroomCount
     */
    void delete(MushroomCount mushroomCount);

    /**
     * Finds mushroomCount by given id
     *
     * @param id id
     */
    MushroomCount findById(Long id);

    /**
     * Finds all entities mushroomCount
     *
     * @return list of mushroomCount
     */
    List<MushroomCount> findAll();
}
