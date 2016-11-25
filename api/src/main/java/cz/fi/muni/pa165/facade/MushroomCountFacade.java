package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.MushroomCountDTO;

import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 11/23/16
 */
public interface MushroomCountFacade {

    /**
     * Creates mushroomCount
     *
     * @param mushroomCount mushroom count to be created
     */
    void create(MushroomCountDTO mushroomCount);

    /**
     * Updates mushroomCount
     *
     * @param mushroomCount mushroom count to be created updated
     */
    MushroomCountDTO update(MushroomCountDTO mushroomCount);

    /**
     * Deletes mushroomCount
     *
     * @param id of mushroom count to be deleted
     */
    void delete(Long id);

    /**
     * Finds mushroomCount by given id
     *
     * @param id id
     */
    MushroomCountDTO findById(Long id);

    /**
     * Finds all entities mushroomCount
     *
     * @return all mushroom count
     */
    List<MushroomCountDTO> findAll();

    /**
     * Finds all mushrooms and their respective count found by all visitors last week
     *
     * @return mushroom count
     */
    List<MushroomCountDTO> findRecentlyFoundPickableMushrooms();
}
