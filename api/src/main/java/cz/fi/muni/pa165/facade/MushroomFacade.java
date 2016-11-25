package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.DateDTO;
import cz.fi.muni.pa165.dto.DateIntervalDTO;
import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.enums.MushroomType;

import java.util.Date;
import java.util.List;

/**
 * Created by Michal Kysilko 436339 on 11/23/16.
 *
 * @author Michal Kysilko 436339
 */
public interface MushroomFacade {

    /**
     * Creates entity mushroom
     *
     * @param mushroom
     */
    void create(MushroomDTO mushroom);

    /**
     * Edit entity mushroom
     *
     * @param mushroom
     */
    MushroomDTO update(MushroomDTO mushroom);

    /**
     * Finds entity mushroom by given id
     *
     * @param id
     * @return Searched entity or null if entity was not found
     */
    MushroomDTO findById(Long id);

    /**
     * Remove entity from databbase
     *
     * @param id
     * @throws IllegalArgumentException
     */
    void delete(Long id) throws IllegalArgumentException;

    /**
     * Finds all entities
     *
     * @return list of all saved entities
     */
    List<MushroomDTO> findAll();

    /**
     * Finds mushroom by given name
     *
     * @param name
     * @return mushroom or null if mushroom with given name doesn't exists
     */
    MushroomDTO findByName(String name);

    /**
     * Finds all mushrooms of given type
     *
     * @param type
     * @return list of all mushrooms of given type
     */
    List<MushroomDTO> findByType(MushroomType type);

    /**
     * Finds all mushrooms that can be found in this Date (year and time are ignored)
     *
     * @param date date
     * @return list of found mushrooms
     * @throws IllegalArgumentException if date is null
     */
    List<MushroomDTO> findByDate(DateDTO date);

    /**
     * Finds all mushrooms that can be found within the date range (year and time are ignored)
     *
     * @param interval
     * @return list of found mushrooms
     * @throws IllegalArgumentException if fromDate or toDate is null
     */
    List<MushroomDTO> findByDate(DateIntervalDTO interval);
}
