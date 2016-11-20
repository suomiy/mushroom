package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.ForestDTO;

import java.util.List;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
public interface ForestFacade {

    /**
     * Creates forest
     * @param forest
     */
    void create(ForestDTO forest);

    /**
     * Edit entity forest
     * @param forest
     */
    ForestDTO update(ForestDTO forest);

    /**
     * Finds forest by given id
     * @param id
     * @return Searched entity or null if entity doesn't found
     */
    ForestDTO findById(Long id);

    /**
     * Remove forest from databbase
     * @param forest
     * @throws IllegalArgumentException - if the forest is not an entity or is a detached entity
     */
    void delete(ForestDTO forest);

    /**
     * Finds all forests
     * @return - list of all saved entities
     */
    List<ForestDTO> findAll();

    /**
     * Finds forest by given name
     * @param name
     * @return forest or null if forest with given name doesn't exists
     */
    ForestDTO findByName(String name);

}
