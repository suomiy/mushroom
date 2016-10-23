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
     * @param forest
     */
    public void create(Forest forest);

    /**
     * Edit entity forest
     * @param forest
     */
    public void edit(Forest forest);

    /**
     * Finds entity forest by given id
     * @param id
     * @return Searched entity or null if entity doesn't found
     */
    public Forest findById(Long id);

    /**
     * Remove entity from databbase
     * @param forest
     * @throws IllegalArgumentException - if the forest is not an entity or is a detached entity
     */
    public void delete(Forest forest) throws IllegalArgumentException;

    /**
     * Finds all entities
     * @return - list of all saved entities
     */
    public List<Forest> findAll();

    /**
     * Finds forest by given name
     * @param name
     * @return forest or null if forest with given name doesn't exists
     */
    Forest findByName(String name);

}
