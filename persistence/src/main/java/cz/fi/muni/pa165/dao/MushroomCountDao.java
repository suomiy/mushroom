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
     * Creates entity mushroomCount
     * @param mushroomCount
     */
    public void create(MushroomCount mushroomCount);

    /**
     * Edit entity mushroomCount
     * @param mushroomCount
     */
    public void edit(MushroomCount mushroomCount);

    /**
     * Remove mushroomCount from database
     * @param mushroomCount
     * @throws IllegalArgumentException - if the mushroomCount is not an entity or is a detached entity
     */
    public void delete(MushroomCount mushroomCount) throws IllegalArgumentException;


    /**
     * Finds mushroomCount by given id
     * @param id
     */
    public MushroomCount findById(Long id);

    /**
     * Finds all entities mushroomCount
     * @return
     */
    public List<MushroomCount> findAll();

}
