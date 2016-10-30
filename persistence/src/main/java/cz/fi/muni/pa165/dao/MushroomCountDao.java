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
     * Finds mushroomCount by given id
     *
     * @param id
     */
    MushroomCount findById(Long id);

    /**
     * Finds all entities mushroomCount
     *
     * @return
     */
    List<MushroomCount> findAll();

}
