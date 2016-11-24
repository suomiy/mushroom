package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Forest;

import java.util.List;

/**
 * Created by Erik Macej 433744 , on 23.10.16.
 *
 * @author Erik Macej 433744
 */
public interface ForestDao {

    /**
     * Creates entity forest
     *
     * @param forest
     */
    void create(Forest forest);

    /**
     * Edit entity forest
     *
     * @param forest
     */
    Forest update(Forest forest);

    /**
     * Finds entity forest by given id
     *
     * @param id
     * @return Searched entity or null if entity doesn't found
     */
    Forest findById(Long id);

    /**
     * Remove entity from databbase
     *
     * @param forest
     */
    void delete(Forest forest);

    /**
     * Finds all entities
     *
     * @return - list of all saved entities
     */
    List<Forest> findAll();

    /**
     * Finds forest by given name
     *
     * @param name
     * @return forest or null if forest with given name doesn't exists
     */
    Forest findByName(String name);
}
