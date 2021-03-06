package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Forest;

import java.util.List;

public interface ForestService {

    /**
     * Creates forest
     * @param forest
     */
    void create(Forest forest);

    /**
     * Edit entity forest
     * @param forest
     */
    Forest update(Forest forest);

    /**
     * Finds forest by given id
     * @param id
     * @return Searched entity or null if entity doesn't found
     */
    Forest findById(Long id);

    /**
     * Remove forest from databbase
     * @param forest
     * @throws IllegalArgumentException - if the forest is not an entity or is a detached entity
     */
    void delete(Forest forest);

    /**
     * Finds all forests
     * @return - list of all saved entities
     */
    List<Forest> findAll();

    /**
     * Finds forest by given name
     * @param name
     * @return forest or null if forest with given name doesn't exists
     */
    Forest findByName(String name);

}
